package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.model.AlimentModel;
import com.google.zxing.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler  {

    private Button save;
    private Bitmap bmp;
    private Spinner spinnerTypeAliment;
    private ImageView imageView;
    private EditText editNom;
    private EditText editProteine;
    private EditText editGlucide;
    private EditText editLipide;
    private CheckBox checkMatin;
    private CheckBox checkMidi;
    private CheckBox checkDiner;
    private CheckBox checkEncas;
    private ZXingScannerView zXingScannerView;
    private String protein="",glucide="",lipide="",nom="", imageUrl="", codeBarre="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        setTitle("Nouvel aliment");

        imageView = findViewById(R.id.imageView);
        editNom = findViewById(R.id.editNom);
        editProteine = findViewById(R.id.editProteine);
        editGlucide = findViewById(R.id.editGlucide);
        editLipide = findViewById(R.id.editLipide);
        checkMatin = findViewById(R.id.checkbox_matin);
        checkMidi = findViewById(R.id.checkbox_midi);
        checkDiner = findViewById(R.id.checkbox_diner);
        checkEncas = findViewById(R.id.checkbox_encas);
        spinnerTypeAliment = (Spinner) findViewById(R.id.spinnerTypeAliment);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_aliment_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTypeAliment.setAdapter(adapter);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlimentModel alimentModel = new AlimentModel();
                alimentModel.setNom(editNom.getText().toString());
                alimentModel.setProteine(Double.parseDouble(editProteine.getText().toString()));
                alimentModel.setGlucide(Double.parseDouble(editGlucide.getText().toString()));
                alimentModel.setLipide(Double.parseDouble(editLipide.getText().toString()));
                alimentModel.setTypeAliment(spinnerTypeAliment.getSelectedItem().toString());
                alimentModel.setMatin(checkMatin.isChecked());
                alimentModel.setMidi(checkMidi.isChecked());
                alimentModel.setDiner(checkDiner.isChecked());
                alimentModel.setEncas(checkEncas.isChecked());
                alimentModel.save();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",1);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        String code = getIntent().getStringExtra("codeBarre");
        if(code != null && !code.isEmpty()){
            codeBarre=code;
            fillFormWithDataFromBarCode();
        }
    }
    public void scan(View view) {
        fillFormWithDataFromBarCode();
        //TODO ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    public void onPause() {
        super.onPause();
        if(zXingScannerView!=null){
            zXingScannerView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        zXingScannerView.stopCamera();
        String resultText = result.getText();
        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SimpleScannerActivity.class);
        intent.putExtra("codeBarre", resultText);
        startActivity(intent);
    }

    public void fillFormWithDataFromBarCode(){
        Thread thread = new Thread(new Runnable() {
            HttpURLConnection con = null;
            public void run() {
                try  {
                    URL url = new URL("http://world.openfoodfacts.org/api/v0/product/"+codeBarre+".json");
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    //Traitement json
                    String json = content.toString();
                    String[] splitJson = json.split(",");
                    ArrayList<String> note = new ArrayList<>();
                    for (String couple : splitJson) {
                        couple = couple.replaceAll("\\}","").replaceAll("\\{","");
                        if(couple.contains("\"proteins_100g\"")){
                            couple = couple.replaceAll("\"","");
                            protein=couple.split(":")[1];
                        }else if(couple.contains("\"fat_100g\"")){
                            couple = couple.replaceAll("\"","");
                            lipide=couple.split(":")[1];
                        }else if(couple.contains("\"carbohydrates_100g\"")){
                            couple = couple.replaceAll("\"","");
                            glucide=couple.split(":")[1];
                        }else if(couple.contains("\"product_name_fr\"")){
                            couple = couple.replaceAll("\"","");
                            nom=couple.split(":")[1];
                        }else if(couple.contains("\"image_url\"")){
                            couple = couple.replaceAll("\"","");
                            imageUrl="http"+couple.split("http")[1];
                            url = new URL(imageUrl);
                            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        //Attend la fin du thread pour update le GUI
        try {
            thread.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Pré-remplir les champs
        editNom.setText(nom);
        editProteine.setText(protein);
        editGlucide.setText(glucide);
        editLipide.setText(lipide);

        imageView.setImageBitmap(bmp);
    }

}
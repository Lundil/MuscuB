package com.example.muscu.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.model.AlimentModel;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AddFoodActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private Button save, delete;
    private Spinner spinnerTypeAliment;
    private EditText editNom;
    private EditText editProteine;
    private EditText editGlucide;
    private EditText editLipide;
    private CheckBox checkMatin;
    private CheckBox checkMidi;
    private CheckBox checkDiner;
    private CheckBox checkEncas;
    private AlimentModel alimentSelected;
    private Long idAlimentSelected = 0L;
    private String protein="",glucide="",lipide="",nom="", imageUrl="", codeBarre="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_food);
        setTitle("Nouvel aliment");

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

        save = findViewById(R.id.saveFood);
        delete = findViewById(R.id.deleteFood);


        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!isGUIFilled()){
                    Toast.makeText(getApplicationContext(), "Renseigner tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    AlimentModel alimentModel;
                    if(alimentSelected!=null){
                        alimentModel = alimentSelected;
                    }else{
                        alimentModel = new AlimentModel();
                    }
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
                    finish();
                }

            }
        });
        Long idAlimentSelected = (Long) getIntent().getLongExtra("idAlimentSelected", 0L);
        if(idAlimentSelected != 0L){
            alimentSelected = AlimentModel.getAlimentById(idAlimentSelected);
            editNom.setText(alimentSelected.getNom());
            editProteine.setText(alimentSelected.getProteine().toString());
            editLipide.setText(alimentSelected.getLipide().toString());
            editGlucide.setText(alimentSelected.getGlucide().toString());
            if("Boisson".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(0);
            }else if("Céréales".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(1);
            }else if("Féculent".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(2);
            }else if("Fruit".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(3);
            }else if("Produit laitier".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(4);
            }else if("Légume".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(5);
            }else if("Poisson".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(6);
            }else if("Poudre".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(7);
            }else if("Sauce".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(8);
            }else if("Viande".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(9);
            }
            checkMatin.setChecked(alimentSelected.isMatin);
            checkMidi.setChecked(alimentSelected.isMidi);
            checkDiner.setChecked(alimentSelected.isDiner);
            checkEncas.setChecked(alimentSelected.isEncas);
            delete.setVisibility(View.VISIBLE);
        }else{
            delete.setVisibility(View.GONE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlimentModel alimentModel = alimentSelected;
                alimentModel.delete();
                finish();
            }
        });

        String code = getIntent().getStringExtra("codeBarre");
        if(code != null && !code.isEmpty()){
            codeBarre=code;
            fillFormWithDataFromBarCode();
        }
    }

    private boolean isGUIFilled() {
        return !editNom.getText().toString().isEmpty() &&
        !editProteine.getText().toString().isEmpty() &&
        !editGlucide.getText().toString().isEmpty() &&
        !editLipide.getText().toString().isEmpty();
    }

    public void scan(View view) {
        if (checkPermission()) {
            Intent intent = new Intent(this, ScannerActivity.class);
            startActivityForResult(intent,1);
        } else {
            requestPermission();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            String code = getIntent().getStringExtra("codeBarre");
            if(code != null && !code.isEmpty()){
                codeBarre=code;
                fillFormWithDataFromBarCode();
            }
        }
    }

    public void fillFormWithDataFromBarCode(){
        Thread thread = new Thread(new Runnable() {
            HttpURLConnection con = null;
            public void run() {
                try  {
                    URL url;
                    String inputLine, json;
                    BufferedReader in;
                    StringBuffer content;
                    String[] splitJson;
                    ArrayList<String> note;
                    for (int i=0; i<3;i++){
                        url = new URL("http://world.openfoodfacts.org/api/v0/product/"+codeBarre+".json");
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        content = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        //Traitement json
                        json = content.toString();
                        splitJson = json.split(",");
                        note = new ArrayList<>();
                        for (String couple : splitJson) {
                            couple = couple.replaceAll("\\}","").replaceAll("\\{","");
                            if(couple.contains("\"proteins_100g\"")){
                                couple = couple.replaceAll("\"","");
                                if(NumberUtils.isCreatable(couple.split(":")[1])){
                                    protein=couple.split(":")[1];
                                }
                            }else if(couple.contains("\"fat_100g\"")){
                                couple = couple.replaceAll("\"","");
                                if(NumberUtils.isCreatable(couple.split(":")[1])){
                                    lipide=couple.split(":")[1];
                                }
                            }else if(couple.contains("\"carbohydrates_100g\"")){
                                couple = couple.replaceAll("\"","");
                                if(NumberUtils.isCreatable(couple.split(":")[1])){
                                    glucide=couple.split(":")[1];
                                }
                            }else if(couple.contains("\"product_name_fr\"")){
                                couple = couple.replaceAll("\"","");
                                nom=couple.split(":")[1];
                            }
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
        if(nom.isEmpty() && protein.isEmpty() && glucide.isEmpty() && lipide.isEmpty()){
            Toast.makeText(getApplicationContext(), "Aucune informations trouvées", Toast.LENGTH_SHORT).show();
        }else{
            editNom.setText(nom);
            editProteine.setText(protein);
            editGlucide.setText(glucide);
            editLipide.setText(lipide);
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission accordée, vous pouvez scanner vos aliments", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission refusée, vous ne pouvez pas scanner vos aliments", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                                /*showMessageOKCancel("Vous avez besoin ",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });*/
                        }
                    }
                }
                break;
        }
    }

    /*private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(AddFoodActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/
}
package com.example.muscu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.muscu.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler   {
    private ZXingScannerView zXingScannerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scan);
        setTitle("Nouvel aliment");

        //lance le scan
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    public void handleResult(Result result) {
        zXingScannerView.stopCamera();
        String resultText = result.getText();
        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddFoodActivity.class);
        intent.putExtra("codeBarre", resultText);
        startActivity(intent);
        this.finish();
    }

    public void onPause() {
        super.onPause();
        if(zXingScannerView!=null){
            zXingScannerView.stopCamera();
        }
    }

}

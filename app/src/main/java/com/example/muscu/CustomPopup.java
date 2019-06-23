package com.example.muscu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomPopup extends Dialog {

    //Fields
    private Button save, delete;
    private EditText editText;

    public CustomPopup(final Activity activity){
        super(activity, R.style.Theme_AppCompat_Light_Dialog_Alert);
        setContentView(R.layout.activity_pop_repas_layout);

        //FINDS
        save = findViewById(R.id.buttonSave);
        delete = findViewById(R.id.buttonDelete);
        editText = findViewById(R.id.editText);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!isGUIFilled()){
                    //
                }else{
                    hide();
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!isGUIFilled()){

                }else{
                    //Calcul
                    hide();
                }

            }
        });

    }

    public void build() {
        show();
    }

    private boolean isGUIFilled(){
        return editText.getText() != null;
    }
}

package com.vasseur.aufildelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SpecInteret extends AppCompatActivity {

    private String identifiant;
    private String commune;
    private String patri;
    private String principale;

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec_interet);

        Intent intent = getIntent();
        identifiant = intent.getStringExtra("id");
        commune = intent.getStringExtra("com");
        patri = intent.getStringExtra("patri");
        principale = intent.getStringExtra("princ");

        textView = (TextView) findViewById(R.id.textView4);
        textView.setText("Identifiant : " + identifiant +  "\nCommune : " + commune +  "\nNom officiel : " + patri +  "\nDescription technique :  " + principale);
    }
}

package com.vasseur.aufildelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Repository.FavorisRepository;

public class SpecInteret extends AppCompatActivity {

    private String identifiant = "";
    private String commune = "";
    private String patri = "";
    private String principale = "";

    private TextView information;
    private Button ajouterFav;
    private Button supprimerFav;
    private FavorisRepository favorisRepository = new FavorisRepository(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec_interet);

        Intent intent = getIntent();
        identifiant = intent.getStringExtra("id");
        commune = intent.getStringExtra("com");
        patri = intent.getStringExtra("patri");
        principale = intent.getStringExtra("princ");

        information = (TextView) findViewById(R.id.textView4);
        information.setText("Identifiant : " + identifiant + "\nCommune : " + commune + "\nNom officiel : " + patri + "\nDescription technique : " + principale);

        ajouterFav = (Button) findViewById(R.id.button2);
        supprimerFav = (Button) findViewById(R.id.button3);

        if (favorisRepository.getFavoris("favoris").indexOf(identifiant + "/" + commune + "/" + patri + "/" + principale) == -1) {
            supprimerFav.setEnabled(false);
            supprimerFav.setVisibility(View.INVISIBLE);
        } else {
            ajouterFav.setEnabled(false);
            ajouterFav.setVisibility(View.INVISIBLE);
        }
        ajouterFav.setOnClickListener(listenerAjout);
        supprimerFav.setOnClickListener(listenerSuppr);

    }

    private View.OnClickListener listenerAjout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            supprimerFav.setEnabled(true);
            supprimerFav.setVisibility(View.VISIBLE);
            ajouterFav.setEnabled(false);
            ajouterFav.setVisibility(View.INVISIBLE);
            favorisRepository.setFavoris(identifiant + "/" + commune + "/" + patri + "/" + principale);
            Log.i("favo", favorisRepository.getFavoris("favoris"));
        }
    };

    private View.OnClickListener listenerSuppr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ajouterFav.setEnabled(true);
            ajouterFav.setVisibility(View.VISIBLE);
            supprimerFav.setEnabled(false);
            supprimerFav.setVisibility(View.INVISIBLE);
            favorisRepository.unsetFavoris(identifiant + "/" + commune + "/" + patri + "/" + principale);
            Log.i("favo", favorisRepository.getFavoris("favoris"));
        }
    };

}

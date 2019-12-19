package com.vasseur.aufildelo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Repository.FavorisRepository;

public class ListeFavoris extends AppCompatActivity {

    private FavorisRepository favorisRepository = new FavorisRepository(this);
    private ListView listDesFavoris;
    private String favoris;
    private String[] strFavoris;
    private String[] infoFavoris;

    private ArrayList<String> iden = new ArrayList<String>();
    private ArrayList<String> com = new ArrayList<String>();
    private ArrayList<String> patri = new ArrayList<String>();
    private ArrayList<String> princ = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_favoris);

        listDesFavoris = (ListView) findViewById(R.id.listFavoris);
        if(favorisRepository.isFavorisConfigured("favoris")) {
            favoris = favorisRepository.getFavoris("favoris");
            favoris = favoris.substring(1, favoris.length() - 1);
            strFavoris = favoris.split("--");
            for (int i = 0; i < strFavoris.length; i++) {
                infoFavoris = strFavoris[i].split("/");
                iden.add(infoFavoris[0]);
                com.add(infoFavoris[1]);
                patri.add(infoFavoris[2]);
                princ.add(infoFavoris[3]);
            }

            ArrayAdapter<String> adapterFavo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patri);
            listDesFavoris.setAdapter(adapterFavo);
            listDesFavoris.setOnItemClickListener(listenerList);
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ListeFavoris.this);
            builder.setMessage("Aucun favoris n'a été enregistré.").setTitle("Affichage des favoris :").setNegativeButton("Retour", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    private AdapterView.OnItemClickListener listenerList = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplication(), SpecInteret.class);
            intent.putExtra("id", iden.get(position));
            intent.putExtra("com", com.get(position));
            intent.putExtra("patri", patri.get(position));
            intent.putExtra("princ", princ.get(position));
            startActivity(intent);
            finish();
        }
    };
}

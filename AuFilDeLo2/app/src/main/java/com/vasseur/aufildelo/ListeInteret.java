package com.vasseur.aufildelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListeInteret extends AppCompatActivity {

    private String departement;
    private String commune;
    private String interet;
    private String URL = "http://192.168.43.36:8080/Mission2/api.php";

    private ArrayList<String> iden = new ArrayList<String>();
    private ArrayList<String> com = new ArrayList<String>();
    private ArrayList<String> patri = new ArrayList<String>();
    private ArrayList<String> princ = new ArrayList<String>();

    private ListView listRecherche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_interet);

        listRecherche = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();
        departement = intent.getStringExtra("departement");
        commune = intent.getStringExtra("commune");
        interet = intent.getStringExtra("interet");

        URL = URL + "?dep=" + departement + "&commune=" + commune + "&interet=" + interet;

        requestServer(URL);

        listRecherche.setOnItemClickListener(listenerList);

    }


    public void requestServer(String URL){

        StringRequest requestApi = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("reponseinformation",  response);
                parseJSON(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", "" + error);
                Toast.makeText(ListeInteret.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue fileDattente = Volley.newRequestQueue(this);
        fileDattente.add(requestApi);
    }

    private void parseJSON(String json) {//modifier
        Log.i("json", json);

        try {
            JSONArray information = new JSONArray(json);

            for (int k = 0; k < information.length(); k++) {
                JSONObject object = new JSONObject(information.getString(k));
                iden.add(object.getString("identifiant"));
                com.add(object.getString("commune"));
                patri.add(object.getString("elem_patri"));
                princ.add(object.getString("elem_princ"));

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        alimListe(patri);
        Log.i("commune", "" + com);
    }

    private void alimListe(ArrayList arrayList){
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listRecherche.setAdapter(adapterList);
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
        }
    };
}

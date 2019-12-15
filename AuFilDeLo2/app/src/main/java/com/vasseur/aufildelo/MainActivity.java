package com.vasseur.aufildelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static androidx.appcompat.widget.AppCompatDrawableManager.get;


public class MainActivity extends AppCompatActivity {

    private Button btnRecherche;
    private String URL = "http://192.168.43.36:8080/Mission2/api.php?dep=75";
    private String departement="75";
    private String commune="Paris";
    private String interet="Pont";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinDep = (Spinner) findViewById(R.id.spinnerDep);
        Spinner spinInt = (Spinner) findViewById(R.id.spinnerInt);
        Spinner spinCom = (Spinner) findViewById(R.id.spinnerCom);
        //modifier
        btnRecherche = (Button) findViewById(R.id.button);

        ArrayAdapter<CharSequence> adapterDep = ArrayAdapter.createFromResource(this, R.array.departement, android.R.layout.simple_spinner_item);
        adapterDep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDep.setAdapter(adapterDep);

        ArrayAdapter<CharSequence> adapterInt = ArrayAdapter.createFromResource(this, R.array.interet, android.R.layout.simple_spinner_item);
        adapterInt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinInt.setAdapter(adapterInt);


        btnRecherche.setOnClickListener(listenerRecherche);
        spinDep.setOnItemSelectedListener(listenerDep);
        spinInt.setOnItemSelectedListener(listenerInt);
        spinCom.setOnItemSelectedListener(listenerCom);


        //le code ci-dessous est le test de communication avec l'api


        requestServer(URL);

    }

    public void requestServer(String URL){

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("reponse", response);
                parseJSON(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", "" + error);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parseJSON(String data) {//modifier
        Log.i("data", "" + data);
        //data = "[{\"commune\":\"Annet-sur-Marne\"},{\"commune\":\"Balloy\"},{\"commune\":\"Bois-le-Roi\"},{\"commune\":\"Boissise-le-Roi\"},{\"commune\":\"Bray-sur-Seine\"},{\"commune\":\"Chalifert\"},{\"commune\":\"Chamigny\"},{\"commune\":\"Champagne-sur-Seine\"},{\"commune\":\"Changis-sur-Marne\"},{\"commune\":\"Congis-sur-Thérouanne\"},{\"commune\":\"Dammarie-les-Lys\"},{\"commune\":\"Fontaine-le-Port\"},{\"commune\":\"Fresnes-sur-Marne\"},{\"commune\":\"Germigny-l'Evêque\"},{\"commune\":\"Isles-les-Meldeuses\"},{\"commune\":\"Isles-lès-Villenoy\"},{\"commune\":\"Jaulnes\"},{\"commune\":\"La Ferté-sous-Jouarre\"},{\"commune\":\"La Grande-Paroisse\"},{\"commune\":\"La Rochette\"},{\"commune\":\"Lagny-sur-Marne\"},{\"commune\":\"Luzancy\"},{\"commune\":\"Mareuil-lès-Meaux\"},{\"commune\":\"Marolles-sur-Seine\"},{\"commune\":\"Mary-sur-Marne\"},{\"commune\":\"Meaux\"},{\"commune\":\"Melun\"},{\"commune\":\"Méry-sur-Marne\"},{\"commune\":\"Montereau-Fault-Yonne\"},{\"commune\":\"Montévrain\"},{\"commune\":\"Noisiel\"},{\"commune\":\"Noyen-sur-Seine\"},{\"commune\":\"Poincy\"},{\"commune\":\"Ponthierry-Saint-Fargeau\"},{\"commune\":\"Précy-sur-Marne\"},{\"commune\":\"Saâcy-sur-Marne\"},{\"commune\":\"Saint-Mammès\"},{\"commune\":\"Saint-Thibault-des-Vignes\"},{\"commune\":\"Samois-sur-Seine\"},{\"commune\":\"Thomery\"},{\"commune\":\"Thorigny-sur-Marne\"},{\"commune\":\"Tombe (la)\"},{\"commune\":\"Torcy\"},{\"commune\":\"Trilbardou\"},{\"commune\":\"Trilport\"},{\"commune\":\"Ussy-sur-Marne\"},{\"commune\":\"Varreddes\"},{\"commune\":\"Villiers-sur-Seine\"},{\"commune\":\"Vimpelles\"},{\"commune\":\"Vulaines-sur-Seine\"}]";
        ArrayList com = new ArrayList();
        try {
            JSONArray communes = new JSONArray(data);

            for (int k = 0; k < communes.length(); k++) {
                JSONObject objOffers = new JSONObject(communes.getString(k));
                com.add(objOffers.getString("commune"));
            }
            Log.i("data", com.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        alimCommune(com);
        Log.i("commune", "" + com);
    }

    private void alimCommune(ArrayList com) {

        Spinner spinCom = (Spinner) findViewById(R.id.spinnerCom);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, com);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCom.setAdapter(adapter);
    }

    private final View.OnClickListener listenerRecherche = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplication(), ListeInteret.class);
            intent.putExtra("departement", departement);
            intent.putExtra("commune", commune);
            intent.putExtra("interet", interet);
            startActivity(intent);
        }
    };

    private AdapterView.OnItemSelectedListener listenerDep = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            departement = parent.getSelectedItem().toString().substring(0,2);
            URL = URL.substring(0,URL.length()-2) + departement;
            Log.i("url", URL);
            requestServer(URL);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener listenerCom = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            commune = parent.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener listenerInt = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            interet = parent.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


}

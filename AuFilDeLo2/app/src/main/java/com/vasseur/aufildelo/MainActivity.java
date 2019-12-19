package com.vasseur.aufildelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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


public class MainActivity extends AppCompatActivity {

    private Button btnRecherche;
    private String URL = "http://192.168.43.36:8080/Mission2/api.php?dep=75";
    private String departement = "75";
    private String commune = "Paris";
    private String interet = "Pont";

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

        requestServer(URL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favoris:
                Intent intent = new Intent(getApplication(), ListeFavoris.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestServer(String URL) {

        StringRequest requestApi = new StringRequest(URL, new Response.Listener<String>() {
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
        RequestQueue fileDattente = Volley.newRequestQueue(this);
        fileDattente.add(requestApi);
    }

    private void parseJSON(String json) {//modifier
        Log.i("json", "" + json);
        ArrayList com = new ArrayList();
        try {
            JSONArray communes = new JSONArray(json);

            for (int k = 0; k < communes.length(); k++) {
                JSONObject object = new JSONObject(communes.getString(k));
                com.add(object.getString("commune"));
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

        ArrayAdapter<String> adapterCom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, com);
        adapterCom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCom.setAdapter(adapterCom);
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
            departement = parent.getSelectedItem().toString().substring(0, 2);
            URL = URL.substring(0, URL.length() - 2) + departement;
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

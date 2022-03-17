package com.example.catalogueusestate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.catalogueusestate.Model.Maison;
import com.example.catalogueusestate.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    private Maison maison;
    private TextView  storiesID_details,noise_scoreID_details, display_property_typeID_details;
    private RequestQueue queue;
    private String maisonId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        queue = Volley.newRequestQueue(this);

       maison = (Maison) getIntent().getSerializableExtra("maison"); //recuperer tous les elements
        maisonId = maison.getproperty_id();
        storiesID_details=findViewById(R.id.storiesID_details);
        noise_scoreID_details=findViewById(R.id.noise_scoreID_details);
        display_property_typeID_details=findViewById(R.id.display_property_typeID_details);


        //Log.d("test1",maisonId);

        getMaisonDetails(maisonId);

    }


    private void getMaisonDetails(String id)
    {
        String myUrl="https://us-real-estate.p.rapidapi.com/v2/property-detail?property_id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {


                    JSONObject dataObject=response.getJSONObject("data");
                    JSONObject property_detailObject=dataObject.getJSONObject("property_detail");
                    if(property_detailObject.has("stories") && !property_detailObject.isNull("stories")) {
                      storiesID_details.setText("stories: " + property_detailObject.getString("stories"));
                    }
                    else
                    {
                        storiesID_details.setText("stories: " + "N/A");
                    }

                   if(property_detailObject.has("display_property_type") && !property_detailObject.isNull("display_property_type")) {

                      display_property_typeID_details.setText("property type: " + property_detailObject.getString("display_property_type"));
                   }
                   else
                   {
                       display_property_typeID_details.setText("property type: " + "N/A");
                   }

                    JSONObject noiseObject = property_detailObject.getJSONObject("noise");
                    {
                        if(noiseObject.has("score") && !noiseObject.isNull("score")) {
                            // noise score est donnee en String pas int
                       noise_scoreID_details.setText("noise score: " + noiseObject.getString("score") + "dB");
                        }
                        else
                        {
                            noise_scoreID_details.setText("noise score: " + "N/A");
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("Erreur", "Err"+error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

               params.put("x-rapidapi-host", "us-real-estate.p.rapidapi.com");
               params.put("x-rapidapi-key", "ae8f8e47d9msh3220eb11d1277f7p1fec80jsn142c3ea073b6");

                //params.put("X-RapidAPI-Host","us-real-estate.p.rapidapi.com" );
                //params.put("X-RapidAPI-Key", "31d289ed40msh7bde6504ce6fe00p151958jsna0ad9e40232e");
                return params;
            }};
        ;
        queue.add(jsonObjectRequest);
    }
}


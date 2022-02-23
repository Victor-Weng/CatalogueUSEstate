package com.example.catalogueusestate.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.catalogueusestate.Model.Maison;
import com.example.catalogueusestate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    private Maison maison;
    private TextView  storiesID_details,sold_priceID_details, noise_scoreID_details;
    private TextView listpriceID_details,statusID_details, bedID_details, bathID_details;
    private ImageView primaryPhotoID_details;
    private RequestQueue queue;
    private String maisonId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        maison = (Maison) getIntent().getSerializableExtra("maison"); //recuperer tous les elements
        maisonId = maison.getproperty_id();
        listpriceID_details=findViewById(R.id.listpriceID_details);
        statusID_details=findViewById(R.id.statusID_details);
        bedID_details=findViewById(R.id.bedID_details);
        bathID_details=findViewById(R.id.bathID_details);

    }

    private void getMaisonDetails(String id)
    {
        String myUrl="https://us-real-estate.p.rapidapi.com/v2/property-detail?property_id=" + maisonId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    storiesID_details.setText(response.getString("stories"));

                    JSONArray price_historyArray = response.getJSONArray("price_history");
                    {
                        sold_priceID_details.setText(response.getString("price"));
                    }

                    JSONArray noiseArray = response.getJSONArray("noise");
                    {
                        noise_scoreID_details.setText(response.getString("score"));
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

                //which ones do we put
                params.put("x-rapidapi-host", "us-real-estate.p.rapidapi.com");
                params.put("x-rapidapi-key", "ae8f8e47d9msh3220eb11d1277f7p1fec80jsn142c3ea073b6");
                return params;
            }};
        ;
        queue.add(jsonObjectRequest);
    }
}


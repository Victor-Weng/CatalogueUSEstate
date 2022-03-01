package com.example.catalogueusestate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.catalogueusestate.Data.MaisonRecyclerViewAdapter;
import com.example.catalogueusestate.Model.Maison;
import com.example.catalogueusestate.R;
import com.example.catalogueusestate.Util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MaisonRecyclerViewAdapter maisonRecyclerViewAdapter;
    private List<Maison> maisonList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue= Volley.newRequestQueue(this);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.recyclerView.getContext(),DividerItemDecoration.VERTICAL));

        maisonList=new ArrayList<>();
        //Chercher les preferences de la derniere recherche
        Prefs prefs=new Prefs(MainActivity.this);
        String search=prefs.getSearch();

        maisonList=getMaison(search);
        maisonRecyclerViewAdapter = new MaisonRecyclerViewAdapter(this,maisonList);
        recyclerView.setAdapter(maisonRecyclerViewAdapter);

        maisonRecyclerViewAdapter.notifyDataSetChanged();
    }

    public List<Maison> getMaison(String searchTerm)
    {
        maisonList.clear();
        String myUrl="https://us-real-estate.p.rapidapi.com/v2/for-sale?offset=0&limit=42&state_code=MI&city=" + searchTerm + "&sort=newest";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject dataObject=response.getJSONObject("data");
                    JSONObject home_searchObject=dataObject.getJSONObject("home_search");
                            JSONArray resultsArray = home_searchObject.getJSONArray("results");
                            for (int l = 0; l < resultsArray.length(); l++) {


                                JSONObject maisonObj = resultsArray.getJSONObject(l);
                                Maison maison = new Maison();

                                // LIST PRICE, STATUS
                                maison.setList_price(maisonObj.getString("list_price"));
                                maison.setStatus(maisonObj.getString("status"));

                                JSONObject descriptionObject = maisonObj.getJSONObject("description");
                                // BEDS, BATHS
                                maison.setBeds(descriptionObject.getInt("baths"));
                                maison.setBaths(descriptionObject.getInt("beds"));

                                JSONObject locationObject = maisonObj.getJSONObject("location");
                                JSONObject addressObject = locationObject.getJSONObject("address");
                                // ADDRESS LINE
                                maison.setLine(addressObject.getString("line"));

                                if(maisonObj.has("href")){
                                JSONObject primary_photoObject = maisonObj.getJSONObject("primary_photo");
                                maison.setHref("https://sc01.alicdn.com/kf/HTB1o9j7dyFTMKJjSZFAq6AkJpXa8/229360046/HTB1o9j7dyFTMKJjSZFAq6AkJpXa8.jpg");
                                }
                                else{
                                    JSONObject primary_photoObject = maisonObj.getJSONObject("primary_photo");
                                    maison.setHref(primary_photoObject.getString("href"));


                                }
                                // LINK TO PRIMARY PHOTO OF THE HOUSE

                                maisonList.add(maison);
                                Log.d("rrr =", maison.getList_price());

                            }
                    maisonRecyclerViewAdapter.notifyDataSetChanged();
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
                params.put("X-RapidAPI-Host","us-real-estate.p.rapidapi.com" );
                params.put("X-RapidAPI-Key", "ae8f8e47d9msh3220eb11d1277f7p1fec80jsn142c3ea073b6");
                return params;
                }};
        ;
        requestQueue.add(jsonObjectRequest);
        return maisonList;
    }
}


package com.example.catalogueusestate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    private String StateCode;

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
        String  searchState=prefs.getSearchState();

        maisonList=getMaison(searchState,search);
        maisonRecyclerViewAdapter = new MaisonRecyclerViewAdapter(this,maisonList);
        recyclerView.setAdapter(maisonRecyclerViewAdapter);

        maisonRecyclerViewAdapter.notifyDataSetChanged();


        //SPINNER COMBOBOX FOR STATE
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {


                    StateCode = spinner.getItemAtPosition(position).toString();

                    Log.d("CHECKSTATE", "onitemselected worked");
                    Log.d("STATECODE", StateCode);



                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recherche, menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("City");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Prefs prefs=new Prefs(MainActivity.this);

                //City
                String search=prefs.getSearch();
                search=query;

                //State
                //Refer to a previous instance



                //Call function
                getMaison(StateCode,search);

                // gestion du clavier
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getCurrentFocus() !=null)
                {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    public List<Maison> getMaison(String searchState,String searchTerm)
    {
        maisonList.clear();
        String myUrl="https://us-real-estate.p.rapidapi.com/v2/for-sale?offset=0&limit=42&state_code=" + searchState + "&city=" + searchTerm + "&sort=newest";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject dataObject=response.getJSONObject("data");
                    JSONObject home_searchObject=dataObject.getJSONObject("home_search");
                    JSONArray resultsArray = home_searchObject.getJSONArray("results");

                    for (int l = 0; l < resultsArray.length(); l++)
                    {


                        JSONObject maisonObj = resultsArray.getJSONObject(l);
                        Maison maison = new Maison();
                        if(maisonObj.has("primary_photo")&& !maisonObj.isNull("primary_photo"))
                        {
                            JSONObject primary_photoObject = maisonObj.getJSONObject("primary_photo");

                            // LIST PRICE, STATUS
                            maison.setList_price(maisonObj.getString("list_price"));
                            maison.setStatus(maisonObj.getString("status"));

                            // property id
                            maison.setProperty_id(maisonObj.getString("property_id"));

                            JSONObject descriptionObject = maisonObj.getJSONObject("description");
                            // BEDS, BATHS
                            if(descriptionObject.has("beds") && !descriptionObject.isNull("beds"))
                            {
                                maison.setBeds(descriptionObject.getInt("beds"));
                            }
                            if(descriptionObject.has("baths") && !descriptionObject.isNull("baths"))
                            {
                                maison.setBaths(descriptionObject.getInt("baths"));
                            }


                            JSONObject locationObject = maisonObj.getJSONObject("location");
                            JSONObject addressObject = locationObject.getJSONObject("address");
                            // ADDRESS LINE
                            maison.setLine(addressObject.getString("line"));





                            // LINK TO PRIMARY PHOTO OF THE HOUSE

                            maison.setHref(primary_photoObject.getString("href"));


                            maisonList.add(maison);

                        }

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
                params.put("x-rapidapi-host","us-real-estate.p.rapidapi.com" );
                params.put("x-rapidapi-key","ae8f8e47d9msh3220eb11d1277f7p1fec80jsn142c3ea073b6");


                return params;
            }};
        ;
        requestQueue.add(jsonObjectRequest);
        return maisonList;
    }
}


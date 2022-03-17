package com.example.catalogueusestate.Util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.security.PublicKey;

public class Prefs
{
    SharedPreferences sharedPreferences;
    public Prefs(Activity activity)
    {
        sharedPreferences=activity.getPreferences(activity.MODE_PRIVATE);
    }
    public String getSearch()
    {
        return sharedPreferences.getString("search", "huntsville");
    }
    public String getSearchState() { return sharedPreferences.getString("searchState", "AL"); }

    public void setSearch(String search)
    {
        sharedPreferences.edit().putString("search","huntsville");
    }
}

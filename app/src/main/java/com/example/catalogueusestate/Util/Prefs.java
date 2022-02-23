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
        return sharedPreferences.getString("search", "Detroit");
    }
    public void setSearch(String search)
    {
        sharedPreferences.edit().putString("search","Detroit");
    }
}

package com.example.hellojuaracoding.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {

    private static  final String SHARED_APP_PREFERENCED_NAME = "JuaraCodingPreferenced";
    Context contex ;
    private SharedPreferences pref;
    private SharedPreferences.Editor mEditor;

    public  SharedPrefUtil(Context context){
        this.pref = context.getSharedPreferences(SHARED_APP_PREFERENCED_NAME,context.MODE_PRIVATE);

    }

    public  static  SharedPrefUtil getInstance(Context context){
        return new SharedPrefUtil(context);
    }

    public  void put (String key, String vaule){
        this.pref.edit().putString(key,vaule).apply();

    }

    public  String getString(String key){
        return pref.getString(key, "");
    }



}

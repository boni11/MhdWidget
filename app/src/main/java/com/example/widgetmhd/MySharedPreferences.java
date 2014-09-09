package com.example.widgetmhd;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lukas.stefanko on 5. 9. 2014.
 */
public class MySharedPreferences {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor edit;
    private static Context mContext;

    static void initSharedPreferences(SharedPreferences iPrefs,Context iContext){
        prefs=iPrefs;
        edit=prefs.edit();
        mContext=iContext;
    }

    static void initSharedPreferences(SharedPreferences iPrefs){
        prefs=iPrefs;
        edit=prefs.edit();
    }

    static void setPreferences(String key,String value){
        edit.putString(key,value);
        edit.commit();
    }

    static String getPreferences(String key){
        return prefs.getString(key,"");
    }

    static Boolean getSharedPrefObj(){
        if (prefs == null){
            return Boolean.FALSE;
        }else{
            return Boolean.TRUE;
        }
    }

}

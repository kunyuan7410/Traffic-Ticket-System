package com.example.trafficticketsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class preferences2 {
    SharedPreferences checkAdminSession;
    SharedPreferences.Editor editor2;
    Context context;

    public static final String KEY_PLATE_NO = "plate";
    public static final String KEY_NO = "no";
    public static final String KEY_TYPE = "type";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_STATUS = "status";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_POLICE = "police";

    public preferences2(Context _context) {
        context = _context;
        checkAdminSession = _context.getSharedPreferences("checkSession",Context.MODE_PRIVATE);
        editor2 = checkAdminSession.edit();


    }

    public void CheckAdminSession(String plate, String no, String type, String location, String date, String time, String amount, String status, String image, String police){
        editor2.putString(KEY_PLATE_NO,plate);
        editor2.putString(KEY_NO,no);
        editor2.putString(KEY_TYPE,type);
        editor2.putString(KEY_LOCATION,location);
        editor2.putString(KEY_DATE,date);
        editor2.putString(KEY_TIME,time);
        editor2.putString(KEY_AMOUNT,amount);
        editor2.putString(KEY_STATUS,status);
        editor2.putString(KEY_IMAGE,image);
        editor2.putString(KEY_POLICE,police);

        editor2.commit();
    }

    public HashMap<String, String> getAdminCheckSession(){
        HashMap<String, String> checkData = new HashMap<String, String>();
        checkData.put(KEY_PLATE_NO,checkAdminSession.getString(KEY_PLATE_NO,null));
        checkData.put(KEY_NO,checkAdminSession.getString(KEY_NO,null));
        checkData.put(KEY_TYPE,checkAdminSession.getString(KEY_TYPE,null));
        checkData.put(KEY_LOCATION,checkAdminSession.getString(KEY_LOCATION,null));
        checkData.put(KEY_DATE,checkAdminSession.getString(KEY_DATE,null));
        checkData.put(KEY_TIME,checkAdminSession.getString(KEY_TIME,null));
        checkData.put(KEY_AMOUNT,checkAdminSession.getString(KEY_AMOUNT,null));
        checkData.put(KEY_STATUS,checkAdminSession.getString(KEY_STATUS,null));
        checkData.put(KEY_IMAGE,checkAdminSession.getString(KEY_IMAGE,null));
        checkData.put(KEY_POLICE,checkAdminSession.getString(KEY_POLICE,null));
        return checkData;
    }

//    private static SharedPreferences getSharedPreferences(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context);
//    }
}

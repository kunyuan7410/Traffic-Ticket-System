package com.example.trafficticketsystem;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class preference3 {
    SharedPreferences checkPoliceSession;
    SharedPreferences.Editor editor3;
    Context context;

    public static final String KEY_PLATE_NO = "plate";
    public static final String KEY_NO = "no";
    public static final String KEY_TYPE = "type";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_IMAGE = "image";

    public preference3(Context _context){
        context = _context;
        checkPoliceSession = _context.getSharedPreferences("checkSession",Context.MODE_PRIVATE);
        editor3 = checkPoliceSession.edit();
    }

    public void CheckPoliceSession(String plate, String no, String type, String location, String date, String time, String amount, String image){
        editor3.putString(KEY_PLATE_NO,plate);
        editor3.putString(KEY_NO,no);
        editor3.putString(KEY_TYPE,type);
        editor3.putString(KEY_LOCATION,location);
        editor3.putString(KEY_DATE,date);
        editor3.putString(KEY_TIME,time);
        editor3.putString(KEY_AMOUNT,amount);
        editor3.putString(KEY_IMAGE,image);

        editor3.commit();
    }

    public HashMap<String, String> getPoliceCheckSession(){
        HashMap<String, String> checkData = new HashMap<String, String>();
        checkData.put(KEY_PLATE_NO,checkPoliceSession.getString(KEY_PLATE_NO,null));
        checkData.put(KEY_NO,checkPoliceSession.getString(KEY_NO,null));
        checkData.put(KEY_TYPE,checkPoliceSession.getString(KEY_TYPE,null));
        checkData.put(KEY_LOCATION,checkPoliceSession.getString(KEY_LOCATION,null));
        checkData.put(KEY_DATE,checkPoliceSession.getString(KEY_DATE,null));
        checkData.put(KEY_TIME,checkPoliceSession.getString(KEY_TIME,null));
        checkData.put(KEY_AMOUNT,checkPoliceSession.getString(KEY_AMOUNT,null));
        checkData.put(KEY_IMAGE,checkPoliceSession.getString(KEY_IMAGE,null));

        return checkData;
    }
}

package com.example.trafficticketsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class preferences {
    SharedPreferences usersSession, checkSession;
    SharedPreferences.Editor editor,editor1;
    Context context;
    private static final String DATA_LOGIN = "status_login", DATA_AS = "as";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PLATE = "plate";
    public static final String KEY_IC = "ic";
    public static final String KEY_PROFILE = "profile";

    //=====================================================================
  public static final String KEY_PLATE_NO = "plate";
  public static final String KEY_NO = "no";
  public static final String KEY_TYPE = "type";
  public static final String KEY_LOCATION = "location";
  public static final String KEY_DATE = "date";
  public static final String KEY_TIME = "time";
  public static final String KEY_AMOUNT = "amount";
  public static final String KEY_STATUS = "status";
  public static final String KEY_IMAGE = "image";

    public preferences(Context _context) {
        context = _context;
        usersSession = _context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        checkSession = _context.getSharedPreferences("checkSession",Context.MODE_PRIVATE);
        editor1 = checkSession.edit();
        editor = usersSession.edit();

    }

    public void CheckSession(String plate, String no, String type, String location, String date, String time, String amount, String status, String image){
        editor1.putString(KEY_PLATE_NO,plate);
        editor1.putString(KEY_NO,no);
        editor1.putString(KEY_TYPE,type);
        editor1.putString(KEY_LOCATION,location);
        editor1.putString(KEY_DATE,date);
        editor1.putString(KEY_TIME,time);
        editor1.putString(KEY_AMOUNT,amount);
        editor1.putString(KEY_STATUS,status);
        editor1.putString(KEY_IMAGE,image);

        editor1.commit();
    }

    public HashMap<String, String> getCheckSession(){
        HashMap<String, String> checkData = new HashMap<String, String>();
        checkData.put(KEY_PLATE_NO,checkSession.getString(KEY_PLATE_NO,null));
        checkData.put(KEY_NO,checkSession.getString(KEY_NO,null));
        checkData.put(KEY_TYPE,checkSession.getString(KEY_TYPE,null));
        checkData.put(KEY_LOCATION,checkSession.getString(KEY_LOCATION,null));
        checkData.put(KEY_DATE,checkSession.getString(KEY_DATE,null));
        checkData.put(KEY_TIME,checkSession.getString(KEY_TIME,null));
        checkData.put(KEY_AMOUNT,checkSession.getString(KEY_AMOUNT,null));
        checkData.put(KEY_STATUS,checkSession.getString(KEY_STATUS,null));
        checkData.put(KEY_IMAGE,checkSession.getString(KEY_IMAGE,null));
        return checkData;
    }

    public void createLoginSession(String username, String password, String name, String email, String phone, String plate, String ic, String profile) {
        editor.putBoolean(DATA_LOGIN, true);

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PLATE, plate);
        editor.putString(KEY_IC, ic);
        editor.putString(KEY_PROFILE,profile);

        editor.commit();
    }



    public HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_USERNAME, usersSession.getString(KEY_USERNAME, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_NAME, usersSession.getString(KEY_NAME, null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONE, usersSession.getString(KEY_PHONE, null));
        userData.put(KEY_PLATE, usersSession.getString(KEY_PLATE, null));
        userData.put(KEY_IC, usersSession.getString(KEY_IC, null));
        userData.put(KEY_PROFILE,usersSession.getString(KEY_PROFILE,null));

        return userData;
    }



//    public void getUserContact(String plateNo, String typeSummon, String Location, String Date, String time){
//        editor
//
//    }

    public boolean checkLogin() {
        if (usersSession.getBoolean(DATA_LOGIN, false)) {
            return true;
        } else
            return false;
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataAs(Context context, String data) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(DATA_AS, data);
        editor.apply();
    }

    public static String getDataAs(Context context) {
        return getSharedPreferences(context).getString(DATA_AS, "");
    }

    public static void setDataLogin(Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(DATA_LOGIN, status);
        editor.apply();
    }

    public static boolean getDataLogin(Context context) {
        return getSharedPreferences(context).getBoolean(DATA_LOGIN, false);
    }

    public static void clearData(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(DATA_AS);
        editor.remove(DATA_LOGIN);
        editor.apply();
    }


}


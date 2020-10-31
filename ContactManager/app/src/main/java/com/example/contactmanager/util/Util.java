package com.example.contactmanager.util;

public class Util {
    //Things which are not going to change or we want them to be global declare them in Util class:
    //Database related items
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contact_db";
    public static final String TABLE_NAME = "contacts";

    //Contacs Table columns
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE_NUMBER = "phone_number";
}

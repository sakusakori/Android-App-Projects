package com.example.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contactmanager.R;
import com.example.contactmanager.model.Contact;
import com.example.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    //String name: is the name of the database table we are creating.
    //,String name,SQLiteDatabase.CursorFactory factory,int version
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    //Here we create our table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL->Structured Query Language (To query our database Structure)
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME
                + "(" + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT)";
        //System.out.println(CREATE_CONTACT_TABLE);
        db.execSQL(CREATE_CONTACT_TABLE);
        Log.d("CHECK:", "onCreate: "+CREATE_CONTACT_TABLE);
    }

    //This function is called when we update our database..
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.drop_table);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        onCreate(db);
    }
    /*
    CRUD: Create, Read, Update, Delete
     */
    //Add Contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());

        //Insert to row
        db.insert(Util.TABLE_NAME,null, values);
        Log.d("ADDING", "addContact: Item added");
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        //To iterate through our table we use class Cursor
        Cursor cursor = db.query(Util.TABLE_NAME
                ,new String[] {Util.KEY_ID, Util.KEY_NAME,Util.KEY_PHONE_NUMBER}
                ,Util.KEY_ID +"=?",new String[] {String.valueOf(id)}
                ,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst(); // making cursor to move at first row

        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));

        return contact;
    }
    //Get all contacts
    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        //Select all contacts from our database table
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll,null);
        //Loop through our data
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext()); //While cursor has something to point it will move to next.
        }
        return contactList;
    }
}

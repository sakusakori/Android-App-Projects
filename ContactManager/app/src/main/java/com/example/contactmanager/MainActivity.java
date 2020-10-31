package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.contactmanager.data.DatabaseHandler;
import com.example.contactmanager.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        Contact saksham = new Contact();
        saksham.setName("Saksham Sachdeva");
        saksham.setPhoneNumber("98268378");
        Contact saksham2 = new Contact();
        saksham2.setName("saksham2 Sachdeva 2");
        saksham2.setPhoneNumber("987654321");
        db.addContact(saksham2);
        Log.d("RR", "onCreate: "+saksham2.getId());

//        List<Contact> contactList = db.getAllContacts();
//        for(Contact contact: contactList){
//            Log.d("MAINACT", "onCreate: "+contact.getName());
//        }
    }
}

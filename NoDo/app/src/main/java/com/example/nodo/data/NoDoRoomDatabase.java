package com.example.nodo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nodo.model.NoDo;

//Abstract it can no be initiated i.e new NoDoRoomDatabase is not possible because it is a Abstract Class
@Database(entities = {NoDo.class}, version = 1)
public abstract class NoDoRoomDatabase extends RoomDatabase {

    public static volatile NoDoRoomDatabase INSTANCE; //We are making this as Singleton so that it has only one
    //initialization
    public abstract NoDoDao noDoDao();
    //this is singleton class no we must have private default constructor.
    public static NoDoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoDoRoomDatabase.class) {
                if (INSTANCE == null) {
                    //create our db
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoDoRoomDatabase.class,"nodo_database")
                            .addCallback(roomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallBack =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final NoDoDao noDoDao;
        public PopulateDbAsync(NoDoRoomDatabase db) {
            noDoDao = db.noDoDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //noDoDao.deleteAll();//removes all items from our table
            //testing
//            NoDo noDo = new NoDo("Saksham Sachdeva");
//            noDoDao.insert(noDo);
//            noDo = new NoDo("I am from Gwalior");
//            noDoDao.insert(noDo);
            return null;
        }
    }
}

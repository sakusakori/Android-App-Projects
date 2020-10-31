package com.example.nodo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nodo_table")
public class NoDo {
    @PrimaryKey(autoGenerate = true)
    private int id;//Recognised as a primary key

    @NonNull //by this we tell our column should be never null
    @ColumnInfo(name = "nodo_column")
    private String noDo;


    //this says whenever we create a nodo we can not pass empty string
    public NoDo(@NonNull String noDo) {
        this.noDo = noDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoDo() {
        return noDo;
    }

    public void setNoDo(@NonNull String noDo) {
        this.noDo = noDo;
    }
}

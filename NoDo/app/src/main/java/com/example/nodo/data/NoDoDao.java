package com.example.nodo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nodo.model.NoDo;

import java.util.List;

@Dao  //By typing this our android room get to know that this is our dao class
public interface NoDoDao {
    //CRUD
    @Insert //by using this our insert will come operation of CRUD
    void insert(NoDo noDO);

    @Query("DELETE FROM nodo_table")
    void deleteAll();

    @Query("DELETE FROM nodo_table WHERE id = :id")
    int deleteANoDo(int id);

    @Query("UPDATE nodo_table SET nodo_column = :nodoText WHERE id = :id")
    int updateNoDoItem(int id,String nodoText);

    @Query("SELECT * FROM nodo_table ORDER BY nodo_column DESC")
    LiveData<List<NoDo>> getAllNoDos();
}

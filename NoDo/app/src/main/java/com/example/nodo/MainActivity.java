package com.example.nodo;

import android.content.Intent;
import android.os.Bundle;

import com.example.nodo.model.NoDo;
import com.example.nodo.model.NoDoViewModel;
import com.example.nodo.ui.NoDoListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NEW_NODO_REQUEST_CODE = 1;
    private NoDoListAdapter noDoListAdapter;
    private NoDoViewModel noDoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noDoViewModel = ViewModelProviders.of(this)
                .get(NoDoViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noDoListAdapter = new NoDoListAdapter(this);
        recyclerView.setAdapter(noDoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NewNoDoActivity.class);
                startActivityForResult(intent, NEW_NODO_REQUEST_CODE);
            }
        });
        //very important
        noDoViewModel.getAllNoDos().observe(this, new Observer<List<NoDo>>() {
            @Override
            public void onChanged(List<NoDo> noDos) {
                //update cached(saved) copy of nodos in the adapter
                noDoListAdapter.setNoDos(noDos);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_NODO_REQUEST_CODE && resultCode == RESULT_OK){
            NoDo noDo = new NoDo(data.getStringExtra(NewNoDoActivity.EXTRA_REPLY));
            noDoViewModel.insert(noDo);
        } else {
            Toast.makeText(this,R.string.empty_not_saved,Toast.LENGTH_SHORT).show();
        }
    }
}

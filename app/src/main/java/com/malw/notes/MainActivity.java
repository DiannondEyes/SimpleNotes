package com.malw.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }


    void refresh() {
        ListView list = findViewById(R.id.listOfTitles);
        try {
            File notes = new File(getFilesDir(), "notes");
            if (!notes.exists()) notes.mkdir();
            String[] titles = Arrays.stream(notes.list()).map(filename -> filename.replaceFirst("[.][^.]+$", "")).toArray(String[]::new);
            list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles));
            list.setOnItemClickListener((parent, v, position, id) -> {
                startActivity(new Intent(MainActivity.this, InfoActivity.class).putExtra("name", titles[position]));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void edit(View view) {
        startActivity(new Intent(MainActivity.this, EditActivity.class).putExtra("title", "").putExtra("content", ""));
    }
}
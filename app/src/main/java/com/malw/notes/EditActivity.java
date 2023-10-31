package com.malw.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ((TextView) findViewById(R.id.title)).setText(getIntent().getStringExtra("title").toString());
        ((TextView) findViewById(R.id.content)).setText(getIntent().getStringExtra("content").toString());
    }

    public void edit(View view) {
        String oldTitle = getIntent().getStringExtra("title").toString();
        String newTitle = ((TextView) findViewById(R.id.title)).getText().toString();
        if (!oldTitle.equals(newTitle)) {
            new File(getFilesDir(), "notes/" + oldTitle + ".txt").delete();
        }
        try {
            File newNote = new File(getFilesDir(), "notes/"+newTitle+".txt");
            if (!newNote.exists()) {
                Log.e("IOException", String.valueOf(newNote.createNewFile()));
            }
            FileWriter w = new FileWriter(newNote);
            w.write(((TextView) findViewById(R.id.content)).getText().toString());
            w.close();
        } catch (IOException e) {
            Log.e("IOException", Arrays.toString(e.getStackTrace()));
        }
        setResult(Activity.RESULT_OK, new Intent().putExtra("title", newTitle).putExtra("content", ((TextView) findViewById(R.id.content)).getText().toString()));
        finish();
    }
}
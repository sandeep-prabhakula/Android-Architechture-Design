package com.sandeepprabhakula.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    EditText data;
    Button addNotes;
    public static final String EXTRA_ID = "com.sandeepprabhakula.demo.EXTRA_ID";
    public static final String EXTRA_DATA = "com.sandeepprabhakula.demo.EXTRA_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        data = findViewById(R.id.edit_text_data);
        addNotes = findViewById(R.id.add);
        addNotes.setOnClickListener(v -> {
            String note = data.getText().toString();
            if (note.trim().isEmpty()) {
                Toast.makeText(this, "insert data", Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent();
            i.putExtra(EXTRA_DATA, note);

            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            if(id!=-1){
                i.putExtra(EXTRA_ID,id);
            }
            setResult(RESULT_OK, i);
            finish();
        });
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            data.setText(intent.getStringExtra(EXTRA_DATA));
        } else {
            setTitle("Add note");
        }
    }
}
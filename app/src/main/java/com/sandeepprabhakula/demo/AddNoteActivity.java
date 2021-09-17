package com.sandeepprabhakula.demo;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {
    EditText editTextNote;
    Button addNotes;
    ImageView mic;
    public static final int REQUEST_CODE_SPEECH_INPUT = 2;
    public static final String EXTRA_ID = "com.sandeepprabhakula.demo.EXTRA_ID";
    public static final String EXTRA_DATA = "com.sandeepprabhakula.demo.EXTRA_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editTextNote = findViewById(R.id.edit_text_data);
        addNotes = findViewById(R.id.add);
        mic = findViewById(R.id.microphone);
        mic.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to Text");
            try{
                startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
            }catch(Exception e){
                Toast.makeText(AddNoteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        addNotes.setOnClickListener(v -> {
            String note = editTextNote.getText().toString();
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
            editTextNote.setText(intent.getStringExtra(EXTRA_DATA));
        } else {
            setTitle("Add note");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SPEECH_INPUT){
            if(resultCode==RESULT_OK && data!=null){
                ArrayList<String>result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editTextNote.setText(result.get(0));
            }
        }
    }
}
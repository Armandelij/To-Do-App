package com.example.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class NoteDetail extends AppCompatActivity
{
    private EditText titleEditText, descEditText;
    private Button deletedButton;
    private Note seleectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
        checkForEditNote();
    }



    private void initWidgets()
    {
        titleEditText = findViewById(R.id.titleEditText);
        descEditText = findViewById(R.id.descriptionEditText);
        deletedButton = findViewById(R.id.deleteNoteButton);
    }

    private void checkForEditNote()
    {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        seleectedNote = Note.getNoteForID(passedNoteID);

        if (seleectedNote != null)
        {
            titleEditText.setText(seleectedNote.getTitle());
            descEditText.setText(seleectedNote.getDescription());
        }
        else
        {
            deletedButton.setVisibility(View.INVISIBLE);
        }
    }


    public void saveNote(View view)
    {
        SQliteManager sqLiteManager = SQliteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());

        if (seleectedNote == null )
        {
            int id = Note.noteArrayList.size();
            Note newNote = new Note(id, title, desc);
            Note.noteArrayList.add(newNote);
            sqLiteManager.addNoteToDataBase(newNote);
        }
        else
            {
                seleectedNote.setTitle(title);
                seleectedNote.setDescription(desc);
                sqLiteManager.updatenoteDB(seleectedNote);
            }
        finish();
    }

    public void deleteNote(View view)
    {
        seleectedNote.setDeleted(new Date());
        SQliteManager sqLiteManager = SQliteManager.instanceOfDatabase(this);
        sqLiteManager.updatenoteDB(seleectedNote);
        finish();
    }
}
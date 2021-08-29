package com.sandeepprabhakula.demo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>>allNotes;

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }
    public void insert(Note note){
        new InsertAsync(noteDao).execute(note);
    }
    public void update(Note note){
        new UpdateAsync(noteDao).execute(note);
    }
    public void delete(Note note){
        new DeleteAsync(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new DeleteAllAsync(noteDao).execute();
    }
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    private static class InsertAsync extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private InsertAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateAsync extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private UpdateAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAsync extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private DeleteAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllAsync extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        private DeleteAllAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }
}

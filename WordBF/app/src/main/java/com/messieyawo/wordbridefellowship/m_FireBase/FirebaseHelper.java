package com.messieyawo.wordbridefellowship.m_FireBase;



import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.messieyawo.wordbridefellowship.m_Model.Spacecraft;

import java.util.ArrayList;



public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL
    public Boolean save(Spacecraft spacecraft)
    {
        if(spacecraft==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.getRoot().child("WordBrideVerses").push().setValue(spacecraft);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }
    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        spacecrafts.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Spacecraft spacecraft=ds.getValue(Spacecraft.class);
            spacecrafts.add(spacecraft);
        }
    }
    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<Spacecraft> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return spacecrafts;
    }
}




















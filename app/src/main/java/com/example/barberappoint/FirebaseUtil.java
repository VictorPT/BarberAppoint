package com.example.barberappoint;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseUtil;
    public static ArrayList<Appointment> mAppoint;

    private FirebaseUtil() {};

    public  static void openFbReference(String ref){
        if(firebaseUtil == null){
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mAppoint = new ArrayList<Appointment>();
        }
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }
}

package com.example.qrgameteam15;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.List;

public class ExistingUser extends AppCompatActivity {

    SingletonPlayer singletonPlayer = new SingletonPlayer();
    FirebaseFirestore db;
    // ---------------------------------
    // each qrcode we fetch from database from "QRCodes" collection set these value so we can easily
    // create document
//    public boolean qrExistInDB = false;
//    public int individualQRcodescore = -1;
//    public String individualQRcodeDate = "";
//    public String individualQRcodeName = "";
//    public String individualQRcodeLocation = "";
    // ------------------------------------
    // Access a Cloud FireStore instance from Activity
    String TAG = "tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user);
    }
    /**
     * This method is called when the user taps the Log In button, and it opens the user menu activity 
     * if the user successfully logs in.
     * @param view
     * Expects an object from the View class
     */
    public void login(View view) {
        /** To do.. verify it is a valid user */
        EditText usernameEdit = (EditText) findViewById(R.id.username1_text);
        String username = usernameEdit.getText().toString();

        singletonPlayer.player.setUsername(username);
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Players");
        final CollectionReference collectionReferenceQR = db.collection("QRCodes");
        /*
            ON NEW USER, we fetch from firebase and recreate the Player class
         */

        DocumentReference playerDocRef = db.collection("Players").document(username);
        playerDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        singletonPlayer.player = documentSnapshot.toObject(Player.class);
                        Log.d("Success","12");
                    }
                }
            }
        });
        Intent intent = new Intent(getApplicationContext(), UserMenu.class);
        intent.putExtra("userMenu_session", (String) null);
        startActivity(intent);
    }

}
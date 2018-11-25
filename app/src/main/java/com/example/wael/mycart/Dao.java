package com.example.wael.mycart;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Dao {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();


    public Dao(){}


    CollectionReference collCat = db.collection("/menu/UJ3eL7npm2V6H3MEizSs/categories");

   public Map<String, Categories> getCat() throws InterruptedException {

       final Map<String, Categories> docData = new HashMap<>();

       collCat
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               Log.d("mesage", "******************************message****************************************");
                               //Log.d(TAG, document.getId() + " => " + document.getData());*/
                              //docData.put(document.getId(),document.getData());
                               Categories cat = document.toObject(Categories.class);
                               cat.setId(document.getId());
                               Log.d("fds",cat.toString());
                               docData.put(document.getId(),cat);
                           }
                       } else {
                           Log.d(TAG, "**************************************************Error getting documents: ", task.getException());
                           return;
                       }
                   }
               });

       return docData;
   }


}

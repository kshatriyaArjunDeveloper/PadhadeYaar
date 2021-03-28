package com.example.padadeyaarmad._0_repository

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepositoryImpl : FirestoreRepository {

    // Firestore queries
    private val firestore = Firebase.firestore

    override suspend fun isUserAccountMade(firebaseUser: FirebaseUser?):Boolean {
        Log.d("MYTAG","isUserAccountMade FUNCTION CALLED")
        Log.d("MYTAG",firestore.document("users/${firebaseUser?.email.toString()}").id.equals(firebaseUser?.email.toString()).toString())
        return firestore.document("users/${firebaseUser?.email.toString()}").id.equals(firebaseUser?.email.toString())

    }


}
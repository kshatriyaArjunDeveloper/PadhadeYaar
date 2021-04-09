package com.example.padadeyaarmad.repository

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreRepositoryImpl : FirestoreRepository {

    // Firestore queries
    private val firestore = Firebase.firestore

    override suspend fun isUserAccountMade(firebaseUser: FirebaseUser?):Boolean {
        Log.d("MYTAG","isUserAccountMade FUNCTION CALLED")
        Log.d("MYTAG",firebaseUser?.email.toString())
        val userBoolean = firestore.document("users/${firebaseUser?.email.toString()}").get().await().exists()
        Log.d("MYTAG",userBoolean.toString())
        Log.d("MYTAG","  " + Thread.currentThread().name)
        return userBoolean
    }


}
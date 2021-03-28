package com.example.padadeyaarmad._0_repository

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseRepositoryImpl : FirebaseRepository {

    // For authentication
    private val auth = Firebase.auth

    override suspend fun getUserFromFirebase(): FirebaseUser? {
        Log.d("MYTAG", "getUserFromFirebase CALLED")
        return auth.currentUser
    }

    override suspend fun logOutUser() {
        Log.d("MYTAG", "logOutUser Called")
        auth.signOut()
    }

}
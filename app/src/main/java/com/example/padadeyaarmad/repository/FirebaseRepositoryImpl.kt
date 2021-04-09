package com.example.padadeyaarmad.repository

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl : FirebaseRepository {

    // For authentication
    private val auth = Firebase.auth

    override suspend fun getUserFromFirebase(): FirebaseUser? {
        Log.d("MYTAG", "getUserFromFirebase CALLED")
        Log.d("MYTAG", auth.currentUser?.email.toString())
        Log.d("MYTAG","  " + Thread.currentThread().name)
        return auth.currentUser
    }

    override suspend fun logOutUser() {
        Log.d("MYTAG", "logOutUser Called")
        Log.d("MYTAG","  " + Thread.currentThread().name)
        auth.signOut()
    }

    override suspend fun firebaseSignInWithGoogle(credential: AuthCredential): FirebaseUser? {
        Log.d("MYTAG", "firebaseSignInWithGoogle CALLED")
        val user:FirebaseUser? = auth.signInWithCredential(credential).await().user
        Log.d("MYTAG", user?.email.toString())
        Log.d("MYTAG","  " + Thread.currentThread().name)
        return user
    }

}
package com.example.padadeyaarmad.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    suspend fun getUserFromFirebase(): FirebaseUser?

    suspend fun logOutUser()

    suspend fun firebaseSignInWithGoogle(credential: AuthCredential): FirebaseUser?

}
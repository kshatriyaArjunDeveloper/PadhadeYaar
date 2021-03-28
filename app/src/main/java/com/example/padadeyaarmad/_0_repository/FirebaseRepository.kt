package com.example.padadeyaarmad._0_repository

import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    suspend fun getUserFromFirebase(): FirebaseUser?

    suspend fun logOutUser()

}
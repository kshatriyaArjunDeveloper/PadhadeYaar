package com.example.padadeyaarmad.repository

import com.google.firebase.auth.FirebaseUser

interface FirestoreRepository {

    suspend fun isUserAccountMade(firebaseUser: FirebaseUser?):Boolean

}
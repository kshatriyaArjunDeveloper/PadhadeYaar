package com.example.padadeyaarmad._0_repository

import com.google.firebase.auth.FirebaseUser

interface FirestoreRepository {

    suspend fun isUserAccountMade(firebaseUser: FirebaseUser?):Boolean

}
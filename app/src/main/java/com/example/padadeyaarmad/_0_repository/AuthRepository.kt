package com.example.padadeyaarmad._0_repository

import com.example.padadeyaarmad._0_model.Result
import com.example.padadeyaarmad._0_model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun getUserFromFirestore(userEmailId: String): Result<User>?

    suspend fun getFirebaseUser(): FirebaseUser?

    suspend fun logOutUser()

//    suspend fun signInWithCredential(
//        authCredential: AuthCredential
//    ): Result<AuthResult?>

}
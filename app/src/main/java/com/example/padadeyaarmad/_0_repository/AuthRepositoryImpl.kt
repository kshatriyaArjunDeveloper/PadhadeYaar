package com.example.padadeyaarmad._0_repository

import com.example.padadeyaarmad._0_model.Result
import com.example.padadeyaarmad._0_model.User
import com.example.padadeyaarmad.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

private val TAG = "AuthRepositoryImpl"

class AuthRepositoryImpl: AuthRepository {

    //CONST
    private val USER_COLLECTION_NAME = "users"
    private val firestoreInstance = FirebaseFirestore.getInstance()
    private val userCollection = firestoreInstance.collection(USER_COLLECTION_NAME)

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()



    override suspend fun getFirebaseUser(): FirebaseUser?
    {
        return firebaseAuth.currentUser
    }

    override suspend fun logOutUser()
    {
        firebaseAuth.signOut()
    }

    override suspend fun getUserFromFirestore(userEmailId: String): Result<User>?
    {
        try
        {
            return when(val resultDocumentSnapshot = userCollection.document(userEmailId).get().await())
            {
                is Result.Success -> {
                    val user = resultDocumentSnapshot.data.toObject(User::class.java)!!
                    Result.Success(user)
                }
                is Result.Error -> Result.Error(resultDocumentSnapshot.exception)
                is Result.Canceled -> Result.Canceled(resultDocumentSnapshot.exception)
            }
        }
        catch (exception: Exception)
        {
            return Result.Error(exception)
        }
    }

//    override suspend fun signInWithCredential(authCredential: AuthCredential): Result<AuthResult?>
//    {
//        return firebaseAuth.signInWithCredential(authCredential).await()
//    }
}
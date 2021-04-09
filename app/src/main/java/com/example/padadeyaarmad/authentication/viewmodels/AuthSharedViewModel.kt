package com.example.padadeyaarmad.authentication.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.padadeyaarmad.repository.FirebaseRepository
import com.example.padadeyaarmad.repository.FirestoreRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class AuthSharedViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    // User Details for new account
    private val _userName = MutableLiveData<String>()
    private val _userEmail = MutableLiveData<String>()
    private val _userCollege = MutableLiveData<String>()
    private val _userCourse = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    val userEmail: LiveData<String> = _userEmail
    val userCollege: LiveData<String> = _userCollege
    val userCourse: LiveData<String> = _userCourse

    // For checking new or old account
    private val _userStatus = MutableLiveData<String>()
    val userStatus: LiveData<String> = _userStatus

    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        viewModelScope.launch(IO) {
            Log.d("MYTAG", "4 " + Thread.currentThread().name)
            val firebaseUser = firebaseRepository.firebaseSignInWithGoogle(googleAuthCredential)
            val isUserAccountMadeBoolean = firestoreRepository.isUserAccountMade(firebaseUser)
            // IF USER IS FOUND IN DB SIGNING IN
            withContext(Main)
            {
                Log.d("MYTAG", "4 " + Thread.currentThread().name)
                if (isUserAccountMadeBoolean) {
                    Log.d("MYTAG", "4 " + Thread.currentThread().name)
                    // IF USER IN DB SENDING
                    Log.d("MYTAG", "USER IS FOUND IN DB GO TO HOME SCREEN")
                    _userStatus.value = "USER_FOUND"
                } else if (!isUserAccountMadeBoolean) {
                    Log.d("MYTAG", "4 " + Thread.currentThread().name)
                    // IF USER IS NOT IN DB TAKING USER NAME AND CREATING NEW ACCOUNT
                    Log.d("MYTAG", "USER IS NOT IN DB, CREATING NEW ACCOUNT ")
                    _userName.value = firebaseUser!!.displayName
                    _userEmail.value = firebaseUser.email
                    _userStatus.value = "USER_NEW_ACCOUNT"
                }
            }
            Log.d("MYTAG", "4 " + Thread.currentThread().name)
        }
    }

}
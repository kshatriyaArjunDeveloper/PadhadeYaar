package com.example.padadeyaarmad.onBoardings.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.padadeyaarmad.repository.FirebaseRepository
import com.example.padadeyaarmad.repository.FirestoreRepository
import kotlinx.coroutines.*

private val TAG = "SplashFragmentViewModel"

class SplashFragmentViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

        private val _userStatus = liveData(Dispatchers.IO) {
            Log.d("MYTAG","1  " + Thread.currentThread().name)
        coroutineScope {
            Log.d("MYTAG","2  " + Thread.currentThread().name)
            Log.d("MYTAG", "Delayed")
            delay(1000)
                val firebaseUser = firebaseRepository.getUserFromFirebase()
                if (firebaseUser != null) {
                    val isUserAccountMadeBoolean = firestoreRepository.isUserAccountMade(firebaseUser)
                    // IF USER IS FOUND IN DB GO TO HOME SCREEN
                    if (isUserAccountMadeBoolean) {
                        Log.d("MYTAG", "USER IS FOUND IN DB GO TO HOME SCREEN")
                        emit("USER_FOUND")
                    } else if (!isUserAccountMadeBoolean) {
                        // IF USER IS NOT IN DB LOGOUT AND START AUTH AGAIN
                        Log.d("MYTAG", "USER IS NOT IN DB LOGOUT AND START AUTH AGAIN")
                        firebaseRepository.logOutUser()
                        emit("USER_NO_ACCOUNT")
                    }
                } else emit("USER_NOT_FOUND")
        }
            Log.d("MYTAG","3  " + Thread.currentThread().name)
    }

    val userStatus: LiveData<String> = _userStatus

}
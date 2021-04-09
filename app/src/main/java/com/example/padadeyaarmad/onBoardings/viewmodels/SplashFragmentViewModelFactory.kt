package com.example.padadeyaarmad.onBoardings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.padadeyaarmad.repository.FirebaseRepository
import com.example.padadeyaarmad.repository.FirestoreRepository

class SplashFragmentViewModelFactory(private val firebaseRepository: FirebaseRepository, private val firestoreRepository: FirestoreRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashFragmentViewModel::class.java)){
            return SplashFragmentViewModel(firebaseRepository, firestoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
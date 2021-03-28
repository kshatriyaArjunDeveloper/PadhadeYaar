package com.example.padadeyaarmad._1_onBoardings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.padadeyaarmad._0_repository.AuthRepository
import com.example.padadeyaarmad._0_repository.FirebaseRepository
import com.example.padadeyaarmad._0_repository.FirestoreRepository
import java.lang.IllegalArgumentException

class SplashFragmentViewModelFactory(private val firebaseRepository: FirebaseRepository, private val firestoreRepository: FirestoreRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashFragmentViewModel::class.java)){
            return SplashFragmentViewModel(firebaseRepository, firestoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
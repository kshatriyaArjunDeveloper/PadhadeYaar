package com.example.padadeyaarmad._1_onBoardings.viewmodels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.example.padadeyaarmad.R
import com.example.padadeyaarmad._0_repository.FirebaseRepository
import com.example.padadeyaarmad._0_repository.FirestoreRepository
import com.example.padadeyaarmad._2_authentication.AuthenticationActivity
import com.example.padadeyaarmad._3_mainApp.HomeActivity
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*

private val TAG = "SplashFragmentViewModel"

class SplashFragmentViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    var userStatus = liveData(Dispatchers.IO) {

        coroutineScope {
            val firebaseUser: Deferred<FirebaseUser?> =
                async { return@async firebaseRepository.getUserFromFirebase() }

            if (firebaseUser.await() != null) {
                val isUserAccountMadeBoolean: Deferred<Boolean> =
                    async { return@async firestoreRepository.isUserAccountMade(firebaseUser.await()) }
                // IF USER IS FOUND IN DB GO TO HOME SCREEN
                if (isUserAccountMadeBoolean.await()) {
                    Log.d("MYTAG", "USER IS FOUND IN DB GO TO HOME SCREEN")
                    emit("USER_FOUND")
                } else if (!isUserAccountMadeBoolean.await()) {
                    // IF USER IS NOT IN DB LOGOUT AND START AUTH AGAIN
                    Log.d("MYTAG", "USER IS NOT IN DB LOGOUT AND START AUTH AGAIN")
                    launch { firebaseRepository.logOutUser() }
                    emit("USER_NO_ACCOUNT")
                }
            } else emit("USER_NOT_FOUND")
        }
    }


    /** OLD CODE */
//    private lateinit var googleSingInClient: GoogleSignInClient
//    private val RC_SIGN_IN = 1

    /*private val userFirestoreMLD = MutableLiveData<User>(User())
    val currentUserLD: LiveData<User>
        get() = userFirestoreMLD*/

    //Google
    /*fun signInWithGoogle(activity: Activity) {
        val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSingInClient = GoogleSignIn.getClient(activity, googleSignInOptions)

        val intent = googleSingInClient.signInIntent
        activity.startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun handleSignInResult (completedTask: Task<GoogleSignInAccount>, activity: Activity)
    {
        viewModelScope.launch {
            try
            {
                val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
                account?.let {
                    val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                    when(val result = authRepository.signInWithCredential(credential))
                    {
                        is Result.Success -> {
                            Log.d(TAG, "Result.Success - ${result.data?.user?.uid}")
                            result.data?.user?.let {user ->
                                val _user = user.displayName?.let {
                                    createUserObject(user)
                                }
                            }
                        }
                        is Result.Error -> {
                            Log.e(TAG, "Result.Error - ${result.exception.message}")
                        }
                        is Result.Canceled -> {
                            Log.d(TAG, "Result.Canceled")
                        }
                    }
                }
            }
            catch (exception: Exception)
            {
                Toast.makeText(activity.applicationContext, "Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }*/
    /*fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity)
    {
        if(requestCode == RC_SIGN_IN)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task, activity)
        }
    }*/

    /*suspend fun getUserFromFirestore(userId: String)
    {
        when(val result = authRepository.getUserFromFirestore(userId))
        {
            is Result.Success -> {
                val _user = result.data
                Log.d(TAG, "${result.data}")
                userFirestoreMLD.value = _user
            }
            is Result.Error -> {
                Log.d(TAG, "Result.Erroe - ${result.exception.message}")
            }
            is Result.Canceled -> {
                Log.d(TAG, "Result.Canceled -")
            }
        }
    }*/

    /*fun getFirebaseUser(): FirebaseUser?
    {
        var _firebaseUser: FirebaseUser? = null
        viewModelScope.launch {
            _firebaseUser = authRepository.getFirebaseUser()
        }
        return _firebaseUser
    }*/

    /*fun logOutUser()
    {
        viewModelScope.launch {
            authRepository.logOutUser()
        }
    }*/

//    fun createUserObject(firebaseUser: FirebaseUser): User = User(email = firebaseUser.email!!)

}
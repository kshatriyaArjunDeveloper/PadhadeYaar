package com.example.padadeyaarmad._1_onBoardings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.padadeyaarmad.R
import com.example.padadeyaarmad._0_repository.FirebaseRepositoryImpl
import com.example.padadeyaarmad._0_repository.FirestoreRepositoryImpl
import com.example.padadeyaarmad._1_onBoardings.viewmodels.SplashFragmentViewModel
import com.example.padadeyaarmad._1_onBoardings.viewmodels.SplashFragmentViewModelFactory
import com.example.padadeyaarmad._2_authentication.AuthenticationActivity
import com.example.padadeyaarmad._3_mainApp.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO

class SplashFragment : Fragment() {

    //ViewModel
    private lateinit var viewModel: SplashFragmentViewModel
    private lateinit var viewModelFactory: SplashFragmentViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialize ViewModel
        viewModelFactory =
            SplashFragmentViewModelFactory(FirebaseRepositoryImpl(), FirestoreRepositoryImpl())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SplashFragmentViewModel::class.java)

        Handler(Looper.getMainLooper()).postDelayed({

            viewModel.userStatus.observe(viewLifecycleOwner, Observer {

                when (it) {
                    "USER_FOUND" -> {
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                        this.activity?.finish()
                    }
                    "USER_NO_ACCOUNT" -> {
                        val intent = Intent(activity, AuthenticationActivity::class.java)
                        startActivity(intent)
                        this.activity?.finish()
                    }
                    "USER_NOT_FOUND" -> {

                        // IF NO USER AND ONBOARDING FINISHED GO FOR AUTH
                        if (onBoardingFinished()) {
                            Log.d("MYTAG", "NO USER AND ONBOARDING FINISHED GO FOR AUTH")
                            val intent = Intent(activity, AuthenticationActivity::class.java)
                            startActivity(intent)
                            this.activity?.finish()
                        }
                        // NO USER AND ONBOARDING NOT FINISHED GO FOR ONBOARDING SCREENS
                        else {
                            Log.d("MYTAG", " NO USER AND ONBOARDING NOT FINISHED GO FOR ONBOARDING SCREENS")
                            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                        }
                    }
                }
            })


            /*when {
                // USER IS SIGNED IN FROM FIREBASE AUTH
                firebaseUser != null -> {
                    // CHECKING USER IN DB

                    // IF USER IS FOUND IN DB GO TO HOME SCREEN
                    if (viewModel.isUserAccountMade(firebaseUser)) {
                        Log.d("MYTAG", "USER IS FOUND IN DB GO TO HOME SCREEN")
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                        this.activity?.finish()
                    } else {
                        // IF USER IS NOT IN DB LOGOUT AND START AUTH AGAIN
                        Log.d("MYTAG", "USER IS NOT IN DB LOGOUT AND START AUTH AGAIN")
                        viewModel.logoutUser()
                        val intent = Intent(activity, AuthenticationActivity::class.java)
                        startActivity(intent)
                        this.activity?.finish()
                    }

                    *//*Firebase.firestore.document("users/${firebaseUser.email.toString()}").get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                // IF USER IS FOUND IN DB GO TO HOME SCREEN
                                Log.d(ContentValues.TAG, firebaseUser.email.toString())
                                Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
                                val intent = Intent(activity, HomeActivity::class.java)
                                startActivity(intent)
                                this.activity?.finish()

                            } else {
                                // IF USER IS NOT IN DB LOGOUT AND START AUTH AGAIN
                                viewModel.logoutUser()
                                val intent = Intent(activity, AuthenticationActivity::class.java)
                                startActivity(intent)
                                this.activity?.finish()
                                Log.d(ContentValues.TAG, "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(ContentValues.TAG, "get failed with ", exception)
                        }*//*

                }
                // IF NO USER AND ONBOARDING FINISHED GO FOR AUTH
                onBoardingFinished() -> {
                    Log.d("MYTAG", "NO USER AND ONBOARDING FINISHED GO FOR AUTH")
                    val intent = Intent(activity, AuthenticationActivity::class.java)
                    startActivity(intent)
                    this.activity?.finish()
                }

                // NO USER AND ONBOARDING NOT FINISHED GO FOR ONBOARDING SCREENS
                else -> {
                    Log.d("MYTAG", " NO USER AND ONBOARDING NOT FINISHED GO FOR ONBOARDING SCREENS")
                    findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                }
            }*/

        }, 1000)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}

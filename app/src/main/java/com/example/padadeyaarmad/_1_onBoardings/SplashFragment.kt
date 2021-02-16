package com.example.padadeyaarmad._1_onBoardings

import android.content.ContentValues
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
import androidx.navigation.fragment.findNavController
import com.example.padadeyaarmad.R
import com.example.padadeyaarmad._2_authentication.AuthenticationActivity
import com.example.padadeyaarmad.mainApp.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SplashFragment : Fragment() {

    // For authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialize Firebase Auth
        auth = Firebase.auth
        val currentUser = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            when {
                // GO TO HOME SCREEN
                currentUser != null -> {

                    // CHECKING USER IN DB
                    Firebase.firestore.document("users/${currentUser.email.toString()}").get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                // IF USER IS FOUND IN DB
                                Log.d(ContentValues.TAG, currentUser.email.toString())
                                Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
                                val intent = Intent(activity, HomeActivity::class.java)
                                startActivity(intent)
                                this.activity?.finish()

                            } else {
                                // IF USER IS NOT IN DB
                                auth.signOut()
                                val intent = Intent(activity, AuthenticationActivity::class.java)
                                startActivity(intent)
                                this.activity?.finish()
                                Log.d(ContentValues.TAG, "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(ContentValues.TAG, "get failed with ", exception)
                        }

                }
                // GO TO SIGN IN SIGN UP SCREEN
                onBoardingFinished() -> {
                    val intent = Intent(activity, AuthenticationActivity::class.java)
                    startActivity(intent)
                    this.activity?.finish()
                }
                // GO TO ONBOARDING SCREENS
                else -> findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }

        }, 1000)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}

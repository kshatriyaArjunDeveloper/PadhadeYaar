package com.example.padadeyaarmad._2_authentication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.padadeyaarmad.R
import com.example.padadeyaarmad.databinding.FragmentAuthenticationBinding
import com.example.padadeyaarmad._3_mainApp.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.
 * Use the [AuthenticationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthenticationFragment : Fragment() {

    // For authentication
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    //    SIGN IN OR CREATE NEW ACCOUNT WITH SAME FUCNTION
    private lateinit var signInOrNewAccount: String
    private lateinit var binding: FragmentAuthenticationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_authentication, container, false
        )

        // FOR NEW ACCOUNT
        binding.buttonNewAccount.setOnClickListener {
            signInOrNewAccount = "new_account"
            signIn()
        }
        // FOR GOOGLE SIGN IN
        binding.buttonSignInWithGoogle.setOnClickListener {
            signInOrNewAccount = "sign_in"
            signIn()
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Initialize Firebase Auth
        auth = Firebase.auth

        return binding.root
    }

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                    if (user != null) {

                        // CHECKING USER IN DB
                        Firebase.firestore.document("users/${user.email.toString()}").get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {

                                    /** IF USER IS FOUND IN DB SIGNING IN */
                                    Log.d(ContentValues.TAG, user.email.toString())
                                    Log.d(
                                        ContentValues.TAG,
                                        "DocumentSnapshot data: ${document.data}"
                                    )
                                    val intent = Intent(activity, HomeActivity::class.java)
                                    startActivity(intent)
                                    this.activity?.finish()

                                } else {

                                    /** IF USER IS NOT IN DB SENDING USER NAME AND CREATING NEW ACCOUNT */
                                    val bundle = bundleOf(
                                        "userName" to user.displayName.toString(),
                                        "userEmail" to (user.email.toString())
                                    )
                                    //TODO
                                    /*findNavController().navigate(
                                        R.id.action_logInFragment_to_newAccountFragment,
                                        bundle)*/
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.d(ContentValues.TAG, "get failed with ", exception)
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    // [START signin]
    private fun signIn() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

}
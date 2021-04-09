package com.example.padadeyaarmad.authentication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.padadeyaarmad.R
import com.example.padadeyaarmad.repository.FirebaseRepositoryImpl
import com.example.padadeyaarmad.repository.FirestoreRepositoryImpl
import com.example.padadeyaarmad.authentication.viewmodels.AuthSharedViewModel
import com.example.padadeyaarmad.authentication.viewmodels.AuthFragmentViewModelFactory
import com.example.padadeyaarmad.mainApp.HomeActivity
import com.example.padadeyaarmad.databinding.FragmentAuthenticationBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider


/**
 * A simple [Fragment] subclass.
 * Use the [AuthenticationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthenticationFragment : Fragment() {

    // For authentication
    private lateinit var googleSignInClient: GoogleSignInClient

    //ViewModel and binding
    private lateinit var viewModel: AuthSharedViewModel
    private lateinit var viewModelFactory: AuthFragmentViewModelFactory
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

        // Initialize ViewModel
        viewModelFactory = AuthFragmentViewModelFactory(FirebaseRepositoryImpl(), FirestoreRepositoryImpl())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(AuthSharedViewModel::class.java)

        // FOR NEW ACCOUNT
        binding.buttonNewAccount.setOnClickListener { signIn() }
        // FOR GOOGLE SIGN IN
        binding.buttonSignInWithGoogle.setOnClickListener { signIn() }

        // Configure Google Sign In
        initGoogleSignInClient()

        // Checking user
        viewModel.userStatus.observe(viewLifecycleOwner, {

            when (it) {
                "USER_FOUND" -> {
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    this.activity?.finish()
                }
                "USER_NEW_ACCOUNT" -> {
                    Log.d("MYTAG", "USER_NEW_ACCOUNT")
                    findNavController().navigate(R.id.action_authenticationFragment_to_newAccountFragment)
                }
            }
        })

        return binding.root
    }

    private fun initGoogleSignInClient() {
        Log.d("MYTAG", "initGoogleSignInClient:1 " + Thread.currentThread().name)
            // Configure Google Sign In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        Log.d("MYTAG", "initGoogleSignInClient:2 " + Thread.currentThread().name)
    }

    // [START signin]
    private fun signIn() {
        Log.d("MYTAG", "signIn1: " + Thread.currentThread().name)
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        Log.d("MYTAG", "signIn2: " + Thread.currentThread().name)
    }

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("MYTAG", "onActivityResult:1 " + Thread.currentThread().name)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "AuthWithGoogle:" + account.id)
                    getGoogleAuthCredential(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private  fun getGoogleAuthCredential(googleSignInAccount: GoogleSignInAccount) {
        Log.d("MYTAG", "getGoogleAuthCredential:1 " + Thread.currentThread().name)
        val googleTokenId = googleSignInAccount.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        signInWithGoogleAuthCredential(googleAuthCredential)
    }

    private  fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {
        Log.d("MYTAG", "signInWithGoogleAuthCredential:1 " + Thread.currentThread().name)
        viewModel.signInWithGoogle(googleAuthCredential)
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

}
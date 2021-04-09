package com.example.padadeyaarmad.onBoardings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.padadeyaarmad.R
import com.example.padadeyaarmad.repository.FirebaseRepositoryImpl
import com.example.padadeyaarmad.repository.FirestoreRepositoryImpl
import com.example.padadeyaarmad.onBoardings.viewmodels.SplashFragmentViewModel
import com.example.padadeyaarmad.onBoardings.viewmodels.SplashFragmentViewModelFactory
import com.example.padadeyaarmad.authentication.ui.AuthenticationActivity
import com.example.padadeyaarmad.mainApp.HomeActivity

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

        // Checking user
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}

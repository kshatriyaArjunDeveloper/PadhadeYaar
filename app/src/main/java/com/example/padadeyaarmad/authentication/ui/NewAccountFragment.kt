package com.example.padadeyaarmad.authentication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.padadeyaarmad.R
import com.example.padadeyaarmad.authentication.viewmodels.AuthFragmentViewModelFactory
import com.example.padadeyaarmad.authentication.viewmodels.AuthSharedViewModel
import com.example.padadeyaarmad.databinding.FragmentNewAccountBinding
import com.example.padadeyaarmad.repository.FirebaseRepositoryImpl
import com.example.padadeyaarmad.repository.FirestoreRepositoryImpl
import com.example.padadeyaarmad.statusBarColorWhite

class NewAccountFragment : Fragment() {

    //ViewModel and binding
    private lateinit var viewModel: AuthSharedViewModel
    private lateinit var viewModelFactory: AuthFragmentViewModelFactory
    private lateinit var binding: FragmentNewAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_account, container, false
        )

        /* changing STATUS BAR color*/
        statusBarColorWhite(requireActivity())

        // Initialize ViewModel
        viewModelFactory = AuthFragmentViewModelFactory(FirebaseRepositoryImpl(), FirestoreRepositoryImpl())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(AuthSharedViewModel::class.java)

        viewModel.userName.observe(viewLifecycleOwner, {
            binding.editTextName.setText(it)
        })


        // CHOOSING COLLEGE
        binding.buttonChooseCourse.setOnClickListener {
//            findNavController().navigate(R.id.action_newAccountFragment_to_collegeSelectFragment)
        }

        /** CREATING A NEW ACCOUNT IN DB WITH GIVEN DETAILS AND THEN GO TO HOME ACTIVITY */
        binding.buttonStart.setOnClickListener {
        }

        return binding.root
    }

}
package com.example.padadeyaarmad.onBoardings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.padadeyaarmad.R
import com.example.padadeyaarmad.onBoardings.viewmodels.ViewPagerFragmentViewModel
import com.example.padadeyaarmad.authentication.ui.AuthenticationActivity
import com.example.padadeyaarmad.databinding.FragmentViewPagerBinding
import com.example.padadeyaarmad.statusBarColorWhite


/**
 * A simple [Fragment] subclass.
 * Use the [ViewPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding
    private lateinit var viewModel: ViewPagerFragmentViewModel
    private lateinit var viewPager2PageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_view_pager, container, false
        )
        // View Model
        viewModel = ViewModelProvider(this).get(ViewPagerFragmentViewModel::class.java)

        statusBarColorWhite(activity)

        val fragmentList = arrayListOf<Fragment>(
            OnBoardingScreenFragment(R.drawable.ic_sc1,viewModel.getText1list()[0],viewModel.getText2list()[0],viewModel.getText3list()[0]),
            OnBoardingScreenFragment(R.drawable.ic_om2,viewModel.getText1list()[1],viewModel.getText2list()[1],viewModel.getText3list()[1]),
            OnBoardingScreenFragment(R.drawable.ic_om3,viewModel.getText1list()[2],viewModel.getText2list()[2],viewModel.getText3list()[2]),
            OnBoardingScreenFragment(R.drawable.ic_om4,viewModel.getText1list()[3],viewModel.getText2list()[3],viewModel.getText3list()[3])
        )

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter

//        For NEXT button
        binding.floatingActionButtonNext.setOnClickListener {
            binding.viewPager.currentItem++
        }

//        For BACK button
        binding.floatingActionButtonBack.setOnClickListener {
            binding.viewPager.currentItem--
        }

//        For SKIP button
        binding.textViewSkipButton.setOnClickListener {
            val intent = Intent(activity, AuthenticationActivity::class.java)
            startActivity(intent)
            onBoardingFinished()
            this.activity?.finish()
        }

        /** VIEW PAGER CALLBACKS FOR ANIMATING A.T.BOARDING SCREENS */
        viewPager2PageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.motionLayout.transitionToStart()
                    1 -> {
                        binding.motionLayout.transitionToEnd()
                    }
                    2 -> //        For NEXT button
                        binding.floatingActionButtonNext.setOnClickListener {
                            binding.viewPager.currentItem++
                        }

                    3 -> {
                        /** FOR NAVIGATING FOR SIGN UP SIGN IN ACTIVITY */
                        binding.floatingActionButtonNext.setOnClickListener {
                            val intent = Intent(activity, AuthenticationActivity::class.java)
                            startActivity(intent)
                            onBoardingFinished()
                            requireActivity().finish()
                        }
                        binding.motionLayout.transitionToEnd()
                    }
                }
            }
        }

        binding.viewPager.registerOnPageChangeCallback(viewPager2PageChangeCallback)

        return binding.root
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    /** To unregister View Pager Callbacks*/
    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback((viewPager2PageChangeCallback))
    }

    /** Adapter for view pager */
    class ViewPagerAdapter(list: ArrayList<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fm, lifecycle) {

        private val fragmentList = list

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

    }

}
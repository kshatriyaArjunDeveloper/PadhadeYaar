package com.example.padadeyaarmad._1_onBoardings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.padadeyaarmad.R

/**
 * A simple [Fragment] subclass.
 * Use the [OnBoardingScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnBoardingScreenFragment(private var imageResource: Int,private var text1:String,private var text2: String,private var text3: String) : Fragment() {

    lateinit var imageView: ImageView
    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_on_boarding_screen, container, false)

        imageView = view.findViewById(R.id.imageView_onBoarding)
        textView1 = view.findViewById(R.id.textView_onBoarding1)
        textView2 = view.findViewById(R.id.textView_onBoarding2)
        textView3 = view.findViewById(R.id.textView_onBoarding3)

        imageView.setImageResource(imageResource)
        textView1.text = text1
        textView2.text = text2
        textView3.text = text3

        return view
    }
}

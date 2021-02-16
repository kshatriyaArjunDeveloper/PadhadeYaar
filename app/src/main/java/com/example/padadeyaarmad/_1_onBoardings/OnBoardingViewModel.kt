package com.example.padadeyaarmad._1_onBoardings

import androidx.lifecycle.ViewModel

class OnBoardingViewModel : ViewModel() {

    private val text1list = listOf<String>("Welcome to ","All Notes ","Standard Quality ","Last Night ")
    private val text2list = listOf<String>("PADADE YAAR","VERIFIED","MATERIAL","WARRIOR")
    private val text3list = listOf<String>("A great startup that FOCUSES on just making college students life easier by improving their academics result with least of hardwork, time.",
        "All notes are VERIFIED by your college so that you don't worry about authenticity and just trust us and study !",
        "No matter which semester, course and college each and every material is created by following a standard process of content creation for maintaining quality.",
        "Study begins 1 day before exams no problem still you will score as CONCISE NOTES are our top most priority")

    fun getText1list():List<String>{
        return text1list
    }
    fun getText2list():List<String>{
        return text2list
    }
    fun getText3list():List<String>{
        return text3list
    }

}
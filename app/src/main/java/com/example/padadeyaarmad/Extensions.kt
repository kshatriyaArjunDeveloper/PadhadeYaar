package com.example.padadeyaarmad

import android.graphics.Color
import android.view.View
import androidx.fragment.app.FragmentActivity

/* changing STATUS BAR color for WHITE*/
fun statusBarColorWhite(fragmentActivity: FragmentActivity?){
    fragmentActivity?.window?.statusBarColor = Color.WHITE
    fragmentActivity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

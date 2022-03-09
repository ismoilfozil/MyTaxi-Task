package com.example.mytaxi_task.utils

import android.util.Log
import timber.log.Timber

fun String.showLog(){
    Timber.e(this)
}

fun String.showLogTTT(){
    Timber.tag("TTT").e(this)
}

fun String.showLog(tag:String){
    Timber.tag(tag).e(this)
}
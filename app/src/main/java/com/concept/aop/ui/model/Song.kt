package com.concept.aop.ui.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(val id:Int, val title:String,val desc:String,val linkSong:String?,val linkImg:String) :
    Parcelable

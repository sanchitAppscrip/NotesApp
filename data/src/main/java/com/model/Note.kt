package com.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Long? = null,
    var title:String? = null,
    var subtitle:String? = null,
    var description:String? = null, var time:String?  = null
    ):Parcelable

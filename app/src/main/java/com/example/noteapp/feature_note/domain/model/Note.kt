package com.example.noteapp.feature_note.domain.model

import android.hardware.lights.Light
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.ui.theme.*
import java.sql.Timestamp

@Entity
data class Note(
    val title : String ,
    val content : String,
    val timestamp : Long,
    val color : Int,
    @PrimaryKey val id : Int? = null
){
    companion object{
        val noteColor = listOf(RedOrange , LightGreen , Violet , BabyBlue , RedPink)
    }
}

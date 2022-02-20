package com.serkan.notesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.serkan.notesapp.dao.DaoNotes
import com.serkan.notesapp.ent.Notes


@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class DataBaseNotes:RoomDatabase() {
    companion object{
        var notesDatabase:DataBaseNotes?=null

        @Synchronized
        fun getDatabase(context:Context):DataBaseNotes{
            if(notesDatabase ==null){
                notesDatabase = Room.databaseBuilder(
                    context,DataBaseNotes::class.java,"notes.db"
                ).build()
            }
            return notesDatabase!!
        }
    }


    abstract fun noteDao():DaoNotes












}
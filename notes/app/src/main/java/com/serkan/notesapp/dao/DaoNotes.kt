package com.serkan.notesapp.dao

import androidx.room.*
import com.serkan.notesapp.ent.Notes


@Dao
interface DaoNotes {

    @Query("SELECT * FROM notes ORDER BY id DESC")
   suspend fun getAllNotes(): List<Notes>

    @Query("SELECT * FROM notes WHERE id =:id")
    suspend fun getIdNotes(id:Int): Notes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)

    @Delete
   suspend fun deleteNote(notes:Notes)

    @Query("DELETE FROM notes WHERE id =:id")
    suspend fun deleteIdNote(id:Int)

   @Update
   suspend fun updateNote(notes:Notes)


}
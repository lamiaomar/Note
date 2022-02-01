package com.example.noteapp.di

import android.app.Application
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_soruce.NoteDatabase
import com.example.noteapp.feature_note.domain.repo.NoteRepo
import com.example.noteapp.feature_note.domain.repo.NoteRpoImpl
import com.example.noteapp.feature_note.domain.use_case.AddNoteUseCase
import com.example.noteapp.feature_note.domain.use_case.DeleteNoteUseCase
import com.example.noteapp.feature_note.domain.use_case.GetNotesUseCase
import com.example.noteapp.feature_note.domain.use_case.NoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app : Application) : NoteDatabase{
        return  Room.databaseBuilder(
            app ,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepo(database : NoteDatabase) : NoteRepo{
        return NoteRpoImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repo : NoteRepo) : NoteUseCase{
        return NoteUseCase(
            getNotesUseCase = GetNotesUseCase(repo),
            deleteNoteUseCase = DeleteNoteUseCase(repo),
            addNoteUseCase = AddNoteUseCase(repo)
        )
    }
}
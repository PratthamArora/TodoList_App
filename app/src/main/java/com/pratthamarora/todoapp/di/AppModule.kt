package com.pratthamarora.todoapp.di

import android.content.Context
import androidx.room.Room
import com.pratthamarora.todoapp.data.db.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        TodoDatabase::class.java,
        "todo_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideTodoDao(db: TodoDatabase) = db.toDoDao()
}
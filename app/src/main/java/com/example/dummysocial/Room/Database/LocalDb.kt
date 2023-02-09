package com.example.dummysocial.Room.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dummysocial.Room.Dao.PostDao
import com.example.dummysocial.Room.Model.FavoritePost

@Database(entities = [FavoritePost::class], version = 3, exportSchema = false)
abstract class LocalDb : RoomDatabase() {

    abstract fun getDao(): PostDao
}
package com.example.e_times.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.e_times.models.Article

@TypeConverters(Converters::class)
@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase:RoomDatabase() {

    abstract fun getArticleDao(): ArticleDAO

    companion object{
        private var INSTANCE:NewsDatabase? = null

        fun getDatabase(context: Context):NewsDatabase{

            return INSTANCE ?: synchronized(this){
               val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                ).build()
                INSTANCE = instance
                return instance

            }
        }



    }

}
/*
Room can only identify primitive data types lyk Int,String etc while building the database
by using the entities that we created in order to persist the data.
In this case we have a field with data type of Source in our Article data class.
In order to mitigate this problem we use @TypeConverter and specify what should we do to convert
from and to Source.
We then annotate the DB class with @TypeConverters.
 */
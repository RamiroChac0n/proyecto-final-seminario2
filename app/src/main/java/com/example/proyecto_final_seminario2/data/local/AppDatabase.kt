package com.example.proyecto_final_seminario2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyecto_final_seminario2.data.local.dao.PlaceDao
import com.example.proyecto_final_seminario2.data.local.dao.UserDao
import com.example.proyecto_final_seminario2.data.local.entities.PlaceEntity
import com.example.proyecto_final_seminario2.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PlaceEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun placeDao(): PlaceDao

    companion object {
        private const val DATABASE_NAME = "punto_local_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS places (
                        place_id TEXT NOT NULL PRIMARY KEY,
                        name TEXT NOT NULL,
                        category TEXT NOT NULL,
                        address TEXT,
                        rating REAL,
                        photo_uri TEXT,
                        latitude REAL,
                        longitude REAL,
                        updated_at INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE places ADD COLUMN user_rating_count INTEGER")
                db.execSQL("ALTER TABLE places ADD COLUMN phone_number TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN website_uri TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN google_maps_uri TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN opening_hours TEXT")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
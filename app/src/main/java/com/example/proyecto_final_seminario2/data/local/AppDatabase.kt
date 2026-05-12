package com.example.proyecto_final_seminario2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyecto_final_seminario2.data.local.dao.PlaceDao
import com.example.proyecto_final_seminario2.data.local.dao.RatingDao
import com.example.proyecto_final_seminario2.data.local.dao.UserDao
import com.example.proyecto_final_seminario2.data.local.entities.PlaceEntity
import com.example.proyecto_final_seminario2.data.local.entities.RatingEntity
import com.example.proyecto_final_seminario2.data.local.entities.UserEntity

/**
 * Base de datos local de la aplicacion.
 *
 * Guarda usuarios, lugares consultados y valoraciones locales. Tambien sirve
 * como cache cuando Google Places no esta disponible o falla una consulta.
 */
@Database(
    entities = [
        UserEntity::class,
        PlaceEntity::class,
        RatingEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun placeDao(): PlaceDao

    abstract fun ratingDao(): RatingDao

    companion object {
        private const val DATABASE_NAME = "punto_local_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Version 2: agrega la tabla de lugares para cachear resultados de Places.
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

        // Version 3: amplifica la cache con campos usados en la pantalla de detalle.
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE places ADD COLUMN user_rating_count INTEGER")
                db.execSQL("ALTER TABLE places ADD COLUMN phone_number TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN website_uri TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN google_maps_uri TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN opening_hours TEXT")
            }
        }

        // Version 4: introduce valoraciones locales de negocios.
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS ratings (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        business_id TEXT NOT NULL,
                        paid_price TEXT NOT NULL,
                        visit_date TEXT NOT NULL,
                        service_rating INTEGER NOT NULL,
                        attention_rating INTEGER NOT NULL,
                        satisfaction_rating INTEGER NOT NULL,
                        wait_time TEXT NOT NULL,
                        recommends INTEGER NOT NULL,
                        highlighted_qualities TEXT NOT NULL,
                        created_at INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        // Version 5: relaciona valoraciones con usuario y guarda datos basicos del lugar.
        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE ratings ADD COLUMN user_id INTEGER NOT NULL DEFAULT -1")
                db.execSQL("ALTER TABLE ratings ADD COLUMN place_name TEXT")
                db.execSQL("ALTER TABLE ratings ADD COLUMN place_category TEXT")
                db.execSQL("ALTER TABLE ratings ADD COLUMN place_address TEXT")
            }
        }

        /**
         * Devuelve una unica instancia de Room para toda la app.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3,
                        MIGRATION_3_4,
                        MIGRATION_4_5
                    )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}

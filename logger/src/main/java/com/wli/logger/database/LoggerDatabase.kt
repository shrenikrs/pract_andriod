package com.wli.logger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wli.logger.R
import com.wli.logger.database.dao.LoggerDao
import com.wli.logger.database.entity.Logs


@Database(entities = [Logs::class], version = 1)
abstract class LoggerDatabase : RoomDatabase() {

    abstract fun daoLogger(): LoggerDao

    /**
     * single instance object to access database
     */
    companion object {
        private var ourInstance: LoggerDatabase? = null

        fun getInstance(context: Context): LoggerDatabase? {
            if (ourInstance == null) {
                synchronized(LoggerDatabase::class.java) {
                    if (ourInstance == null) {
                        ourInstance = Room.databaseBuilder(
                            context,
                            LoggerDatabase::class.java,
                            context.getString(R.string.app_name)
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .setJournalMode(JournalMode.TRUNCATE)
                            .addMigrations(MIGRATION_1_2)
                            .build()
                    }
                }
            }
            return ourInstance
        }

        /**
         * Migration methods should be different release wise.
         * if you add column or change datatype of field, add table or delete table etc
         * then you should write relevant queries in specific migration version.
         */
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //database.execSQL("CREATE TABLE `Player` (`id` INTEGER, " + "`name` TEXT, PRIMARY KEY(`id`))")
            }
        }
    }
}
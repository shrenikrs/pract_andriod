package com.wli.logger.database.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import org.jetbrains.annotations.NotNull
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Entity(
    indices = [Index(value = arrayOf("id"), unique = true)],
    tableName = "Table_Logs"
)
class Logs {

    @NotNull
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    /**
     * logType values can be Error, Normal etc...
     */
    var logType: String? = null

    /**
     * Auto-generate date and time with time stamp
     */
    val logDate: OffsetDateTime? = null

    /**
     * one formats a OffsetDateTime to a String, and the other parses a String into an OffsetDateTime.
     * The key puzzle here is making sure that we use the correct String format.
     * Thankfully ThreeTen-BP provides a compatible one for us as DateTimeFormatter.ISO_OFFSET_DATE_TIME.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    object TiviTypeConverters {

        private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        @TypeConverter
        @JvmStatic
        fun toOffsetDateTime(value: String?): OffsetDateTime? {
            return value?.let {
                return formatter.parse(value, OffsetDateTime::from)
            }
        }

        @TypeConverter
        @JvmStatic
        fun fromOffsetDateTime(date: OffsetDateTime?): String? {
            return date?.format(formatter)
        }
    }
}
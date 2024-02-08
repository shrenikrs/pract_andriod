package com.wli.logger.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wli.logger.database.entity.Logs


/**
 * Data Access Object interface to perform database operations
 */
@Dao
interface LoggerDao {

    @Query("Select * from Table_Logs")
    fun getAllPlayer(): List<Logs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayers(list: List<Logs>?)

    /*@Query("Select ifnull(TennisPlayers.firstName || ' ', '') || ifnull(TennisPlayers.lastName,'') from TennisPlayers")
    fun getPlayerName(): String*/

    @Query("Select ifnull(Table_Logs.name || ' ', '') from Table_Logs where Table_Logs.id=:playerId")
    fun getPlayerName(playerId: String): String

    @Query("DELETE FROM Table_Logs")
    fun deleteAll()

}
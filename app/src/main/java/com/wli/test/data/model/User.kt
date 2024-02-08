package com.wli.test.data.model

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wli.test.utils.Constants
import java.io.Serializable

@Entity(
    indices = [Index(value = arrayOf("id"), unique = true)],
    tableName = Constants.Database.TABLE_USER
)
data class User (
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Dob,
    val phone: String,
    val cell: String,
    val id: ID,
    val picture: Picture,
    val nat: String
): Serializable

data class Dob (
    val date: String,
    val age: Long
): Serializable

data class ID (
    val name: String,
    val value: String
): Serializable

data class Location (
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Long,
    val coordinates: Coordinates,
    val timezone: Timezone
): Serializable

data class Coordinates (
    val latitude: String,
    val longitude: String
): Serializable

data class Street (
    val number: Long,
    val name: String
): Serializable

data class Timezone (
    val offset: String,
    val description: String
): Serializable

data class Login (
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
): Serializable

data class Name (
    val title: String,
    val first: String,
    val last: String
): Serializable

data class Picture (
    val large: String,
    val medium: String,
    val thumbnail: String
): Serializable

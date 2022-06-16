package com.mansao.submissionandroidjetpackpro.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movieentities")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var movieId: Int? = 0,

    @ColumnInfo(name = "title")
    var movieTitle: String?,

    @ColumnInfo(name = "description")
    var movieDescription: String?,

    @ColumnInfo(name = "released")
    var movieReleased: String?,

    @ColumnInfo(name = "poster")
    var moviePoster: String?,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
) : Parcelable
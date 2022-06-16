package com.mansao.submissionandroidjetpackpro.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tvshowentities")
data class TvShowEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var showId: Int?,

    @ColumnInfo(name = "title")
    var showTitle: String?,

    @ColumnInfo(name = "description")
    var showDescription: String?,

    @ColumnInfo(name = "released")
    var showReleased: String?,

    @ColumnInfo(name = "poster")
    var showPoster: String?,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false

): Parcelable
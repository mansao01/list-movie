package com.mansao.submissionandroidjetpackpro.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<ResultsTvShowItem>,

	@field:SerializedName("total_results")
	val totalResults: Int
)

data class ResultsTvShowItem(

	@field:SerializedName("id")
	val id: Int?,

	@field:SerializedName("name")
	val title: String?,

	@field:SerializedName("overview")
	val overview: String?,

	@field:SerializedName("first_air_date")
	val firstAirDate: String?,

	@field:SerializedName("poster_path")
	val posterPath: String?

)

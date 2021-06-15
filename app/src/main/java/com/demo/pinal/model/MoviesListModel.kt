package com.demo.pinal.model

data class MoviesListModel(
	val page: Int? = null,
	val results: List<ResultsItem?>? = null
)

data class ResultsItem(

	val original_title: String? = null,

	val poster_path: String? = null,

	val vote_average: String? = null,
	val id:String?=null

)


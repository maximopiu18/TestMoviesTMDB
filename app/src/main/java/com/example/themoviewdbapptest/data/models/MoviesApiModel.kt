package com.example.themoviewdbapptest.data.models

import com.google.gson.annotations.SerializedName

data class MoviesApiModel(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val total_pages: Int? = null,

    @field:SerializedName("total_results")
    val total_results: Int? = null,

    @field:SerializedName("results")
    val moviesList: ArrayList<MoviesDetails>? = null

)

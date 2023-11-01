package com.example.themoviewdbapptest.data.repository

import com.example.themoviewdbapptest.data.api.MoviesService
import com.example.themoviewdbapptest.data.models.MoviesApiModel
import io.reactivex.Single

class MoviesApiRepository (private val moviesService: MoviesService) {
    fun getMovieModel(nameMovie :String?, include_adult: Boolean?, language: String?
    ): Single<MoviesApiModel> = moviesService.getMoviesApiData(nameMovie!!, false, "en-US")

    fun getMovieModelPage(nameMovie :String?, include_adult: Boolean?, language: String?, page : Int
    ): Single<MoviesApiModel> = moviesService.getMoviesApiDataWithPage(nameMovie!!, false, "en-US", page)
    /*
    fun getMovieDescription(url : String):Single<ResponseMoviesDescription> = moviesService.getMovieDescription(url)
    fun getMovieNextPage(url : String):Single<MoviesApiModel> =moviesService.getMovieNextPage(url)
}*/

}
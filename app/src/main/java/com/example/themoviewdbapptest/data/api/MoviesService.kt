package com.example.themoviewdbapptest.data.api

import com.example.themoviewdbapptest.data.models.MoviesApiModel
import io.reactivex.Single
import retrofit2.http.*


interface MoviesService {

    @Headers("Content-Type: application/json")
    @GET("3/search/movie?")
    fun getMoviesApiData(
        @Query("query") movie: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("language") language: String


        ): Single<MoviesApiModel>

    @Headers("Content-Type: application/json")
    @GET("3/search/movie?")
    fun getMoviesApiDataWithPage(
        @Query("query") movie: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int


    ): Single<MoviesApiModel>

    @Headers("Content-Type: application/json")
    @GET("3/search/movie?query=resident&include_adult=false&language=en-US&page={page}")
    fun getMoviesApiDataNextPage(@Path("page") page: String): Single<MoviesApiModel>


    /*    @Headers("Content-Type: application/json")
        @GET("pokemon/")
        fun getMoviesApiData(): Single<MoviesApiModel>

        @Headers("Content-Type: application/json")
        @GET
        fun getMovieDescription(@Url url: String): Single<ResponseMoviesDescription>
        @Headers("Content-Type: application/json")
        @GET
        fun getMovieNextPage(@Url url: String): Single<MoviesApiModel>*/
/*
    @GET("pokemon/{idLocation}/")
    //pokemon?offset=20&limit=20
    fun getPokemonNextPage(@Path("next") idLocation: String): Single<Locations>

    @GET("pokemon/{idLocation}/")
    fun getPokemonLastPage(@Path("previews") idLocation: String, @Query("category") idCategory: String,): Single<PokeApiModel>
*/



}
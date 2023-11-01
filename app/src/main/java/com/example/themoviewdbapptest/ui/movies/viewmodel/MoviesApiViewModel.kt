package com.example.themoviewdbapptest.ui.movies.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themoviewdbapptest.data.models.MoviesApiModel
import com.example.themoviewdbapptest.data.models.MoviesDetails
import com.example.themoviewdbapptest.data.repository.MoviesApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MoviesApiViewModel(
    private val repository: MoviesApiRepository,
    private val callback: MoviesDataCallback,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    lateinit var moviesDetailsLiveData: MutableLiveData<MoviesDetails>
    lateinit var moviesApiModel: MutableLiveData<MoviesApiModel>
    var moviesFullData: MutableList<MoviesDetails> = ArrayList()
    var moviesList: MutableList<MoviesDetails> = ArrayList()
    var nextPage: String? = null

    fun getNextPageString(): String? {
        return nextPage
    }

    fun getMovie(nameMovie:String) {

        compositeDisposable.add(
            repository.getMovieModel(nameMovie, false, null)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    moviesApiModel = MutableLiveData()
                    moviesApiModel.value = response
                    nextPage = response.page.toString()
                    Log.e("response: ", "response: " + response.toString())
                    moviesList = response.moviesList as MutableList<MoviesDetails>
                    //nextPage = response.next.toString
                    callback.onSuccess(response.moviesList!!, response.page!!, response.total_pages!!)
                }, { throwable ->
                    callback.onError(throwable.toString())
                    // error
                    Log.e("error", "error: $throwable")
                })
        )
    }
    fun getMovieWithPage(nameMovie:String, page : Int) {

        compositeDisposable.add(
            repository.getMovieModelPage(nameMovie, false, null, page)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    moviesApiModel = MutableLiveData()
                    moviesApiModel.value = response
                    nextPage = response.page.toString()
                    moviesList = response.moviesList as MutableList<MoviesDetails>
                    //nextPage = response.next.toString
                    callback.onSuccess(response.moviesList!!, response.page!!, response.total_pages!!)
                }, { throwable ->
                    callback.onError(throwable.toString())
                    // error
                    Log.e("error", "error: $throwable")
                })
        )
    }
/*
    fun initMovieServiceDescription(pos: Int, lista: ArrayList<MoviesDetails>) {

        compositeDisposable.add(
            repository!!.getMovieDescription(lista[pos].url.toString())
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Log.e("response", "response description$response")
                    if(pos<lista.size-1) {
                        moviesFullData.add(response)
                        var position = pos+1
                        initMovieServiceDescription(position, lista)
                    }
                    else {
                        callback!!.onSuccessDescription(response)
                        moviesFullData.add(response)
                        callback.onSuccessFullList(moviesFullData)
                    }
                }, { throwable ->
                    Log.e("error", "error: $throwable")
                    callback!!.onError(throwable.toString())
                })
        )
    }


    fun getMovieNextPage(url: String?) {

        url?.let {
            Log.e("URL", "URL NEXT PAGE: " + it)
            compositeDisposable.add(
                repository.getMovieNextPage(url)
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        moviesApiModel = MutableLiveData()
                        moviesApiModel.value = response
                        nextPage = response.next.toString()
                        Log.e("nexPage", "nexPage: " +response.next)
                        callback.onSuccess(response.pokemones!!)


                    }, { throwable ->
                        callback.onError(throwable.toString())
                        // error
                        Log.e("error", "error: $throwable")
                    })
            )
        }

    }*/

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    interface MoviesDataCallback {
        fun onSuccess(lista: ArrayList<MoviesDetails>, page : Int, maxPage : Int)
        fun onError(messageError: String)
        fun onSuccessFullList(pokemonesViewModelList: MutableList<MoviesDetails> = ArrayList())
    }

}

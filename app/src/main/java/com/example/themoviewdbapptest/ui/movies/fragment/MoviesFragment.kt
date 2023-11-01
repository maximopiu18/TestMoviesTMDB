package com.example.themoviewdbapptest.ui.movies.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.themoviewdbapptest.ui.movies.adapter.AdapterMovies
import com.example.themoviewdbapptest.ui.movies.factory.MoviesViewModelFactory
import com.example.themoviewdbapptest.ui.movies.viewmodel.MoviesApiViewModel
import com.example.themoviewdbapptest.data.models.MoviesDetails
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviewdbapptest.R
import com.example.themoviewdbapptest.base.BaseFragment
import com.example.themoviewdbapptest.data.network.NetworkService.Companion.getRetrofitService
import com.example.themoviewdbapptest.data.repository.MoviesApiRepository
import com.example.themoviewdbapptest.databinding.FragmentMoviesBinding
import com.example.themoviewdbapptest.utils.Constants


class MoviesFragment : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MoviesApiViewModel.MoviesDataCallback, AdapterMovies.AdapterListener {

    private var retrofit = getRetrofitService()
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var productsViewModel: MoviesApiViewModel
    private var adapterMovies: AdapterMovies? = null
    private var isRvlastPosition = false
    private var rvPositionY = -1
    private lateinit var textNameMovie: String
    private  var page = 1
    private  var maxpage = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun FragmentMoviesBinding.initialize() {
        binding = this
        setUpViewModel()
        listenersSearch()
 //       setUpObserverProducts()
        //
    }
    private fun listenersSearch (){
        binding.imgSearch.setOnClickListener {
            textNameMovie = binding.edBuscador.text.toString()
            setUpObserverProducts(textNameMovie)
        }
        binding.imgNext.setOnClickListener {
            setUpObserverMoviesNext(textNameMovie)
        }
        binding.imgPreviews.setOnClickListener {
            setUpObserverMoviesBefore(textNameMovie)
        }
    }


    private fun setUpViewModel() {
        productsViewModel = ViewModelProvider(this, MoviesViewModelFactory(MoviesApiRepository(retrofit), this)).get(
            MoviesApiViewModel::class.java
        )
    }

    private fun setUpObserverProducts(nameMovie: String) {
        productsViewModel.getMovie(nameMovie)
    }
    private fun setUpObserverMoviesNext(nameMovie: String) {
        page = page+1
        if (page>= maxpage){
            page = maxpage
        }
        productsViewModel.getMovieWithPage(nameMovie, page)
    }
    private fun setUpObserverMoviesBefore(nameMovie: String) {
        page = page-1
        if(page==0){
            page = 1
        }
        productsViewModel.getMovieWithPage(nameMovie, page)
    }

    override fun onSuccess(lista: ArrayList<MoviesDetails>, page : Int, maxPage : Int) {
        Log.e("correcto", "correcto" + lista.size)
        uploadRvData(lista)
        hideLoading()
        requireActivity().runOnUiThread {
            binding.tvPageNumber.text = "page " + page + " / " + maxpage
        }
    }

    override fun onError(messageError: String) {
        Log.e("error", "error: $messageError")
    }



    override fun onSuccessFullList(pokemonesViewModelList: MutableList<MoviesDetails>) {
        var list = pokemonesViewModelList as ArrayList<MoviesDetails>
        uploadRvData(list)
        hideLoading()
    }


    private fun uploadRvData(list: ArrayList<MoviesDetails>) {
        adapterMovies = AdapterMovies(activity, list, this)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 2)
        binding.rvMovies.layoutManager = mLayoutManager
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.adapter = adapterMovies
        binding.rvMovies.visibility = View.VISIBLE
        //binding.tvTitle.text = args.tittle

    }




    override fun listenerButtonOnClick(objectResult: MoviesDetails) {
        Log.e("title", "tittle:  "  + objectResult.title)
        alertDialogDetallesPokemon(objectResult)
    }


    @SuppressLint("SetTextI18n")
    private fun alertDialogDetallesPokemon(response: MoviesDetails) {
        lateinit var alertDialog: AlertDialog

        val dialogBuilder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView: View = inflater.inflate(R.layout.alert_dialog_movie_descripcion, null)
        dialogBuilder.setView(dialogView)

        val btnCerrar = dialogView.findViewById<ImageView>(R.id.btnCerrar) as ImageView
        var tvNombre = dialogView.findViewById<View>(R.id.tvNameMovie) as TextView
        var tvOverview = dialogView.findViewById<View>(R.id.tvDescription) as TextView
        var imgMovie= dialogView.findViewById<View>(R.id.imgMovie) as ImageView
        tvNombre.text = "Nombre: " + response.title
        tvOverview.text = "Descripcion: " + response.overview



        var url = Constants.BASE_URL_IMAGES+response.posterPath
        url = url!!.replace("http://", "https://")
        Picasso
            .with(requireActivity())
            .load(url)
            .resize(500, 500) // resizes the image to these dimensions (in pixel)
            .centerCrop()
            .into(imgMovie)


        alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.setCancelable(false)
        btnCerrar.setOnClickListener {
            alertDialog.cancel()
        }

    }

    fun showLoading() {
        binding.frameLoading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.frameLoading.visibility = View.GONE
        isRvlastPosition = false
        binding.rvMovies.scrollToPosition(rvPositionY)
    }

}


package com.example.themoviewdbapptest.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.themoviewdbapptest.R
import kotlinx.coroutines.*



class SplashMoviesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash_movies, container, false)
        initTime()
        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initTime(){
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                delay(1500)
                try {
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.action_splashMoviesFragment_to_moviesFragment)
                    }
                } catch (e:Exception){
                    Log.e("error", "$e")
                }
            }
        }
    }

}
package com.example.themoviewdbapptest.ui.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviewdbapptest.R
import com.example.themoviewdbapptest.data.models.MoviesDetails
import com.example.themoviewdbapptest.utils.Constants
import com.squareup.picasso.Picasso
import java.util.*

    class AdapterMovies(private var context : Context, private var listResultsMovies: ArrayList<MoviesDetails>, private  var listener: AdapterListener)
        : RecyclerView.Adapter<AdapterMovies.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val listItem = layoutInflater.inflate(R.layout.item_movies, parent, false)
            return ViewHolder(listItem)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            context.run {
                var url =Constants.BASE_URL_IMAGES+listResultsMovies[position].posterPath
                url = url.replace("http://","https://")
                Picasso
                    .with(context)
                    .load(url)
                    .resize(500, 500)
                    .centerCrop()
                    .into(holder.imgProduct)
                holder.tvTittle.text = listResultsMovies[position].title
                holder.layoutItem.setOnClickListener {
                    listener.listenerButtonOnClick(listResultsMovies[position])
                }
            }
        }

        override fun getItemCount(): Int {
            return listResultsMovies.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var imgProduct: ImageView = itemView.findViewById<View>(R.id.item_img_produto) as ImageView
            var tvTittle : TextView = itemView.findViewById<View>(R.id.item_tv_tittle) as TextView
            var layoutItem: LinearLayout = itemView.findViewById<View>(R.id.item_layout_main) as LinearLayout

        }

         interface AdapterListener {
            fun listenerButtonOnClick(objectResult: MoviesDetails)
        }
    }
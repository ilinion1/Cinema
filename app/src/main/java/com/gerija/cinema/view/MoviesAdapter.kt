package com.gerija.cinema.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gerija.cinema.R
import com.gerija.cinema.model.network.dto.ResultsDto
import com.squareup.picasso.Picasso

class MoviesAdapter(private val imageList: List<ResultsDto>,
                    private val myItemClickListener: ItemClickListener
)
    : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {


    interface ItemClickListener{
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return MoviesHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val imMov = imageList[position]
        Picasso.get().load("https://image.tmdb.org/t/p/w500${imMov.poster_path}")
            .into(holder.imageMovies)


    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class MoviesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageMovies = itemView.findViewById<ImageView>(R.id.imageMov)

        init {
            itemView.setOnClickListener {
                myItemClickListener.onClick(imageList[adapterPosition].id)
            }
        }
    }
}
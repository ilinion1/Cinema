package com.gerija.cinema

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gerija.cinema.movies.network.api.ApiFactory
import com.gerija.cinema.movies.network.model.ResultsDto
import com.gerija.cinema.movies.network.model.Test
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesAdapter(private val imageList: List<ResultsDto>, val context: Context): RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

    private val adress = "/videos??api_key=d9ca6800f61b80298e342b05311fea59"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return MoviesHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val imMov = imageList[position]
        Picasso.get().load("https://image.tmdb.org/t/p/w500${imMov.poster_path}").into( holder.imageMovies)
        holder.itemView.setOnClickListener {
            startDescriptionsAct(imMov.id.toString())
            }

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    private fun startDescriptionsAct(id: String){
        ApiFactory.create().getDescriptionMovies(id).enqueue(object: Callback<Test>{
            override fun onResponse(call: Call<Test>, response: Response<Test>) {
                Log.d("MyLog1", "${response.body()?.results}")
                val intent = Intent(context, DescriptionActivity::class.java)
                context.startActivity(intent)
            }

            override fun onFailure(call: Call<Test>, t: Throwable) {
                Log.d("MyLog1", "Не случилось")
            }
        })
    }

    class MoviesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageMovies = itemView.findViewById<ImageView>(R.id.imageMov)
    }

}
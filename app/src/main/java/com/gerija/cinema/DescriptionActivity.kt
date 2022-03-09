package com.gerija.cinema

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import com.gerija.cinema.databinding.ActivityDescriptionBinding
import com.gerija.cinema.movies.network.api.ApiFactory
import com.gerija.cinema.movies.network.model.DetailsContainer
import com.gerija.cinema.movies.network.model.ResultMovies
import com.gerija.cinema.movies.network.model.VideoContainer
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import androidx.annotation.NonNull

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

import android.R

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView




class DescriptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDetails()

    }

    private fun getDetails(){
        val i = intent.getIntExtra("id", 0)
        Log.d("MyLog3", "$i")
        ApiFactory.create().getMoviesDetails(i).enqueue(object : Callback<DetailsContainer>{

            override fun onResponse(call: Call<DetailsContainer>, response: Response<DetailsContainer>) {
                setContent(response)
                setVisibility()
            }

            override fun onFailure(call: Call<DetailsContainer>, t: Throwable) {
                Log.d("onFailureDescriptionAct", "${t.message}")
            }
        })

        ApiFactory.create().getVideo(i).enqueue(object : Callback<VideoContainer>{
            override fun onResponse(call: Call<VideoContainer>, response: Response<VideoContainer>) {
                Log.d("MyLog", "${response.body()?.results}")
                val list = arrayListOf<String>()

                response.body()?.results?.forEach {
                    list.add(it.key)
                }

                lifecycle.addObserver(binding.youtubePlayerView)


                binding.youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        //youTubePlayer.loadVideo(it.key, 0f)
                        youTubePlayer.cueVideo(list[0], 0f)
                    }
                })

            }

            override fun onFailure(call: Call<VideoContainer>, t: Throwable) {
                Log.d("onFailureVideo", "${t.message}")
            }

        })

    }

    private fun setContent(response: Response<DetailsContainer>) = with(binding){
        val path = "https://image.tmdb.org/t/p/w500"
        Picasso.get().load(path+response.body()?.backdrop_path).into(imageBanner)
        detailsTitle.text = response.body()?.title
        detailsData.text = response.body()?.release_date
        detailsOverview.text = response.body()?.overview
        detailsScore.text = response.body()?.vote_average?.toString()
        Log.d("MyLog3", "${response.body()}")

    }

    private fun setVisibility() = with(binding){
        detailsTitle.visibility = View.VISIBLE
        headerContainer.visibility = View.VISIBLE
        bodyContainer.visibility = View.VISIBLE
        binding.progressBId.visibility = View.GONE
    }
}
package com.gerija.cinema.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.gerija.cinema.databinding.ActivityDescriptionBinding
import com.gerija.cinema.model.network.api.ApiFactory
import com.gerija.cinema.model.network.dto.DetailsContainerDto
import com.gerija.cinema.model.network.dto.VideoContainerDto
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


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
        ApiFactory.create().getMoviesDetails(i).enqueue(object : Callback<DetailsContainerDto>{

            override fun onResponse(call: Call<DetailsContainerDto>, response: Response<DetailsContainerDto>) {
                setContent(response)
                setVisibility()
            }

            override fun onFailure(call: Call<DetailsContainerDto>, t: Throwable) {
                Log.d("onFailureDescriptionAct", "${t.message}")
            }
        })

        ApiFactory.create().getVideo(i).enqueue(object : Callback<VideoContainerDto>{
            override fun onResponse(call: Call<VideoContainerDto>, response: Response<VideoContainerDto>) {
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

            override fun onFailure(call: Call<VideoContainerDto>, t: Throwable) {
                Log.d("onFailureVideo", "${t.message}")
            }

        })

    }

    private fun setContent(response: Response<DetailsContainerDto>) = with(binding){
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
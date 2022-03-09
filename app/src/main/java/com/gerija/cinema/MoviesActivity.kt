package com.gerija.cinema


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerija.cinema.databinding.ActivityMoviewsBinding
import com.gerija.cinema.movies.network.api.ApiFactory
import com.gerija.cinema.movies.network.model.MoviesContainerDto
import com.gerija.cinema.movies.network.model.ResultsDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesActivity : AppCompatActivity(), MoviesAdapter.ItemClickListener {
    lateinit var binding: ActivityMoviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMovies()

    }

    private fun getMovies() {
        ApiFactory.create().getTopMovies().enqueue(object : Callback<MoviesContainerDto> {
            override fun onResponse(
                call: Call<MoviesContainerDto>,
                response: Response<MoviesContainerDto>
            ) {
                response.body()?.let { it ->
                    startAdapter(it.results)
                    binding.progressBarId.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MoviesContainerDto>, t: Throwable) {
                Log.d("MyLog", "${t.message}")
            }

        })
    }

    private fun startAdapter(list: List<ResultsDto>) {
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MoviesAdapter(list, this)
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(this@MoviesActivity, 2)
        Log.d("MyLog", "1")
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onClick(id: Int) {

        val intent = Intent(this@MoviesActivity, DescriptionActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }


}
package com.gerija.cinema.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerija.cinema.R
import com.gerija.cinema.databinding.ActivityMoviewsBinding
import com.gerija.cinema.viewmodel.MoviesViewModel
import com.gerija.cinema.model.network.dto.ResultsDto

class MoviesActivity : AppCompatActivity(), MoviesAdapter.ItemClickListener {
    lateinit var binding: ActivityMoviewsBinding
    private val  viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this) {
                Log.d("MyLog", "${it.results}")
                startAdapter(it.results)
            binding.progressBarId.visibility = View.GONE
        }

        viewModel.error.observe(this){
            Log.d("MyLog1", it)
        }
//        getMovies()

    }

//    private fun getMovies() {
//        ApiFactory.create().getTopMovies().enqueue(object : Callback<MoviesContainerDto> {
//            override fun onResponse(
//                call: Call<MoviesContainerDto>,
//                response: Response<MoviesContainerDto>
//            ) {
//                response.body()?.let { it ->
//                    startAdapter(it.results)
//                    binding.progressBarId.visibility = View.GONE
//                }
//            }
//
//            override fun onFailure(call: Call<MoviesContainerDto>, t: Throwable) {
//                Log.d("MyLog", "${t.message}")
//            }
//
//        })
//    }

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
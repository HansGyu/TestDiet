package com.project.testdiet

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.testdiet.adapter.FoodAdapter
import com.project.testdiet.databinding.ActivityMealBinding
import com.project.testdiet.model.FoodDTO
import com.project.testdiet.network.ApiService

import com.project.testdiet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var apiService: ApiService
    private lateinit var foodList: List<FoodDTO>
    private lateinit var binding: ActivityMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

        loadFoods()

        binding.btnDone.setOnClickListener {
            finish()
        }

        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                foodAdapter.getFilter().filter(newText)
                return false
            }
        })
    }

    private fun loadFoods() {
        apiService.getAllFoods().enqueue(object : Callback<List<FoodDTO>> {
            override fun onResponse(call: Call<List<FoodDTO>>, response: Response<List<FoodDTO>>) {
                if (response.isSuccessful) {
                    response.body()?.let { foods ->
                        foodAdapter = FoodAdapter(foods) { food ->
                            addFood(food)
                        }
                        binding.recyclerView.adapter = foodAdapter
                    }
                } else {
                    showToast("Failed to load foods: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FoodDTO>>, t: Throwable) {
                showToast("Failed to load foods: ${t.message}")
                Log.e("MealActivity", "Error: ${t.message}", t)
            }
        })
    }
    private fun addFood(food: FoodDTO) {
        // 음식 추가 시 실행할 코드
        Toast.makeText(this, "${food.식품명} 추가됨", Toast.LENGTH_SHORT).show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
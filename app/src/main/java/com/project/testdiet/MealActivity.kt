package com.project.testdiet

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.testdiet.adapter.FoodAdapter
import com.project.testdiet.model.FoodDTO
import com.project.testdiet.network.ApiService

import com.project.testdiet.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private lateinit var apiService: ApiService
    private lateinit var foodList: List<FoodDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

        loadFoods()

        findViewById<Button>(R.id.btn_done).setOnClickListener {
            finish()
        }
    }

    private fun loadFoods() {
        apiService.getAllFoods().enqueue(object : Callback<List<FoodDTO>> {
            override fun onResponse(call: Call<List<FoodDTO>>, response: Response<List<FoodDTO>>) {
                if (response.isSuccessful) {
                    foodList = response.body()!!
                    adapter = FoodAdapter(foodList, this@MealActivity::addFood)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<FoodDTO>>, t: Throwable) {
                Toast.makeText(this@MealActivity, "Failed to load foods", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addFood(food: FoodDTO) {
        // 음식 추가 시 실행할 코드
        Toast.makeText(this, "${food.식품명} 추가됨", Toast.LENGTH_SHORT).show()
    }
}
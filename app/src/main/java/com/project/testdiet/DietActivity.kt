package com.project.testdiet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.testdiet.databinding.ActivityDietBinding

class DietActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDietBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBreakfast.setOnClickListener {
            openMealActivity("breakfast")
        }
        binding.btnLunch.setOnClickListener {
            openMealActivity("lunch")
        }
        binding.btnDinner.setOnClickListener {
            openMealActivity("dinner")
        }
        binding.btnCompleteDiet.setOnClickListener {
            finish()
        }
    }

    private fun openMealActivity(mealType: String) {
        val intent = Intent(this, MealActivity::class.java)
        intent.putExtra("mealType", mealType)
        startActivity(intent)
    }
}
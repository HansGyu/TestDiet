package com.project.testdiet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.testdiet.R
import com.project.testdiet.model.FoodDTO

class FoodAdapter(private val foodList: List<FoodDTO>, private val addFood: (FoodDTO) -> Unit) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodName: TextView = view.findViewById(R.id.food_name)
        val foodEnergy: TextView = view.findViewById(R.id.food_energy)
        val foodProtein: TextView = view.findViewById(R.id.food_protein)
        val foodFat: TextView = view.findViewById(R.id.food_fat)
        val foodCarbs: TextView = view.findViewById(R.id.food_carbs)
        val addButton: Button = view.findViewById(R.id.add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.foodName.text = food.식품명
        holder.foodEnergy.text = "에너지: ${food.에너지} kcal"
        holder.foodProtein.text = "단백질: ${food.단백질} g"
        holder.foodFat.text = "지방: ${food.지방} g"
        holder.foodCarbs.text = "탄수화물: ${food.탄수화물} g"

        holder.addButton.setOnClickListener {
            addFood(food)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}
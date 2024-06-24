package com.project.testdiet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.testdiet.R
import com.project.testdiet.model.FoodDTO

class FoodAdapter(private var foodList: List<FoodDTO>, private val addFood: (FoodDTO) -> Unit) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(), Filterable {

    private var filteredFoodList: List<FoodDTO> = foodList

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.food_name)
        val foodEnergy: TextView = itemView.findViewById(R.id.food_energy)
        val foodProtein: TextView = itemView.findViewById(R.id.food_protein)
        val foodFat: TextView = itemView.findViewById(R.id.food_fat)
        val foodCarbs: TextView = itemView.findViewById(R.id.food_carbs)
        val addButton: Button = itemView.findViewById(R.id.add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = filteredFoodList[position]
        holder.foodName.text = food.식품명
        holder.foodEnergy.text = "에너지: ${food.에너지} kcal"
        holder.foodProtein.text = "단백질: ${food.단백질} g"
        holder.foodFat.text = "지방: ${food.지방} g"
        holder.foodCarbs.text = "탄수화물: ${food.탄수화물} g"

        holder.addButton.setOnClickListener {
            addFood(food)
        }
    }

    override fun getItemCount() = filteredFoodList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredFoodList = if (charString.isEmpty()) {
                    foodList
                } else {
                    foodList.filter {
                        it.식품명.contains(charString, true)
                    }
                }
                return FilterResults().apply { values = filteredFoodList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredFoodList = results?.values as List<FoodDTO>
                notifyDataSetChanged()
            }
        }
    }
}

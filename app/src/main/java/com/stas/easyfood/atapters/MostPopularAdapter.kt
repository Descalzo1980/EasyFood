package com.stas.easyfood.atapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stas.easyfood.databinding.PopularItemsBinding
import com.stas.easyfood.pojo.CategoryMeals
import com.stas.easyfood.pojo.MealList

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>(){

    private var mealList = ArrayList<CategoryMeals>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealList: ArrayList<CategoryMeals>){
        this.mealList = mealList
        notifyDataSetChanged()
    }


   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class PopularMealViewHolder(private val binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)
}
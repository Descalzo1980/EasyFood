package com.stas.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stas.easyfood.databinding.CategoryItemBinding
import com.stas.easyfood.pojo.Category
import com.stas.easyfood.pojo.CategoryList

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){

    inner class CategoriesViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var categoriesList = ArrayList<Category>()

    fun setCategoryList(categoryList: List<Category>){
        this.categoriesList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}
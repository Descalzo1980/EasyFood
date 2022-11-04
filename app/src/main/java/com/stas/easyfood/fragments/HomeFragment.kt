package com.stas.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stas.easyfood.activites.CategoryMealsActivity
import com.stas.easyfood.activites.MainActivity
import com.stas.easyfood.activites.MealActivity
import com.stas.easyfood.adapters.CategoriesAdapter
import com.stas.easyfood.adapters.MostPopularAdapter
import com.stas.easyfood.databinding.FragmentHomeBinding
import com.stas.easyfood.pojo.MealsByCategory
import com.stas.easyfood.pojo.Meal
import com.stas.easyfood.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter : MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.stas.easyfood.fragment.idMeal"
        const val MEAL_NAME = "com.stas.easyfood.fragment.nameMeal"
        const val MEAL_THUMB = "com.stas.easyfood.fragment.thumbMeal"
        const val CATEGORY_NAME = "com.stas.easyfood.fragment.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        popularItemsAdapter = MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemRecycleView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()


        viewModel.getCategories()
        observeCategoriesLiveData()
        prepareCategoriesRecycleView()

        onCategoryClick()


    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecycleView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemRecycleView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularMealLiveData().observe(
            viewLifecycleOwner,
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)

        }
    }


    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }

    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,
            Observer{ meal ->
                Glide.with(this@HomeFragment)
                    .load(meal.strMealThumb)
                    .into(binding.imgRandomMeal)
                this.randomMeal = meal
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
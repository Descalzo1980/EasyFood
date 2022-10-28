package com.stas.easyfood.activites

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stas.easyfood.R
import com.stas.easyfood.databinding.ActivityMealBinding
import com.stas.easyfood.fragments.HomeFragment
import com.stas.easyfood.pojo.Meal
import com.stas.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm : MealViewModel
    private lateinit var youtubelink : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()

        setInformationInView()
        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()
        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubelink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this,object : Observer<Meal>{
            @SuppressLint("SetTextI18n")
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t

                binding.tvCategory.text = "Category: ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.tvInstructionsStep.text = meal.strInstructions
                youtubelink = meal.strYoutube
            }
        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(applicationContext,R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(applicationContext,R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFavorites.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFavorites.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}
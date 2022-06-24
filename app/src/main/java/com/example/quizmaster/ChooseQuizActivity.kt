package com.example.quizmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.quizmaster.databinding.ActivityChooseQuizBinding


class ChooseQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = listOf("General Knowledge","Computer Science","Movie","Geography", "Sports")
        val level = listOf("Easy","Medium","Hard")
        val numOfQuestion = listOf("5","10")

        val categoryAdapter = ArrayAdapter(this,R.layout.dropdown_item, category)
        val levelAdapter = ArrayAdapter(this, R.layout.dropdown_item, level)
        val numOfQuestionAdapter = ArrayAdapter(this, R.layout.dropdown_item, numOfQuestion)

        binding.dropdownField.setAdapter(categoryAdapter)
        binding.dropdownField2.setAdapter(levelAdapter)
        binding.dropdownField3.setAdapter(numOfQuestionAdapter)

    }
}
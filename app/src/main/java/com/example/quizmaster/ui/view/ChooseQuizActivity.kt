package com.example.quizmaster.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import com.example.quizmaster.R
import com.example.quizmaster.data.model.OpentdbAPI.Questions
import com.example.quizmaster.data.model.OpentdbAPI.Result
import com.example.quizmaster.databinding.ActivityChooseQuizBinding
import com.example.quizmaster.ui.viewmodel.QuestionsViewModel
import com.example.quizmaster.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

@AndroidEntryPoint
class ChooseQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseQuizBinding
    val vm: QuestionsViewModel by viewModels()
    val userVM: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar2.setNavigationOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        val category = listOf("General Knowledge","Computer Science","Geography","Movie", "Sports")
        val level = listOf("Easy","Medium","Hard")
        val numOfQuestion = listOf("5","10")

        val categoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, category)
        val levelAdapter = ArrayAdapter(this, R.layout.dropdown_item, level)
        val numOfQuestionAdapter = ArrayAdapter(this, R.layout.dropdown_item, numOfQuestion)

        getUserName()

        binding.dropdownField.setAdapter(categoryAdapter)
        binding.dropdownField2.setAdapter(levelAdapter)
        binding.dropdownField3.setAdapter(numOfQuestionAdapter)

        binding.button.setOnClickListener{
            var cat: String = binding.dropdownField.text.toString()
            var difficulty: String = binding.dropdownField2.text.toString()
            var numOfQuestions: String = binding.dropdownField3.text.toString()
            var type = "multiple"



            vm.getQuestions(numOfQuestions,cat,difficulty,type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext ={
                    sendResults(it.results)
//                    binding.textView1.text=it.toString()
//                  Html.fromHtml(it.results[0].question)
                },
                onError = {e -> println("this is error $e")}
            )

//
        }

    }

    private fun sendResults(result: List<Result>){
        var questionsIntent = Intent(this, QuestionPageFrameActivity::class.java)
        questionsIntent.putExtra("results", result as Serializable)
        startActivity(questionsIntent)
    }

    private fun getUserName(){
        var username = userVM.username

        username.observe(this){
            binding.textView4.text = it
        }

    }
}
package com.example.quizmaster.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizmaster.R
import com.example.quizmaster.data.model.SubmittedQuestions
import com.example.quizmaster.databinding.ActivityDetailedResultsPageBinding
import com.example.quizmaster.ui.adapter.HistoryAdapter
import com.example.quizmaster.ui.adapter.ResultAdapter

class DetailedResultsPage : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedResultsPageBinding
    var submittedQuestions = ArrayList<SubmittedQuestions>()
    var adapter = ResultAdapter(submittedQuestions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedResultsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.homeBtn.setOnClickListener{
            var mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }

        binding.resultRecycler.layoutManager = LinearLayoutManager(this)
        binding.resultRecycler.adapter = adapter

        submittedQuestions.addAll(intent.extras?.get("submittedQuestions") as List<SubmittedQuestions>)

        adapter.setItems(submittedQuestions)




    }
}
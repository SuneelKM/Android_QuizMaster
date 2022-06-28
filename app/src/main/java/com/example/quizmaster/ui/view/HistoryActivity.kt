package com.example.quizmaster.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmaster.ui.adapter.HistoryAdapter
import com.example.quizmaster.R
import com.example.quizmaster.data.model.UserData.UserScores
import com.example.quizmaster.databinding.ActivityHistoryBinding
import com.example.quizmaster.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HistoryActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHistoryBinding
    var historyList: ArrayList<UserScores> = ArrayList()
    var adapter: HistoryAdapter = HistoryAdapter(historyList)
    val vm: UserViewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var dividerItemDecoration : DividerItemDecoration = DividerItemDecoration(binding.recyclerHistory.context, LinearLayoutManager(this).orientation)
        binding.recyclerHistory.addItemDecoration(dividerItemDecoration)
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)

        binding.recyclerHistory.adapter = adapter

        vm.getUserScores(this)

        vm.userScoreList.observe(this){
            adapter.setItems(it)
        }

    }
}
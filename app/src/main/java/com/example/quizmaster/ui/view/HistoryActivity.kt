package com.example.quizmaster.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmaster.HistoryAdapter
import com.example.quizmaster.R
import com.example.quizmaster.data.model.UserData.UserScores

class HistoryActivity : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    lateinit var adapter: HistoryAdapter
    var historyList: ArrayList<UserScores> = ArrayList()
    lateinit var date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_history)

        recycler = findViewById(R.id.recycler_history)

        date = findViewById(R.id.date)
        date.setOnClickListener{
            date.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_arrow_drop_up_24,0)
        }


        var dividerItemDecoration : DividerItemDecoration = DividerItemDecoration(recycler.context, LinearLayoutManager(this).orientation)
        recycler.addItemDecoration(dividerItemDecoration)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = HistoryAdapter(historyList)
        historyList.add(UserScores("2022", "Science", "Difficult", "20", ""))
        historyList.add(UserScores("2022", "Entertainment", "Difficult", "20",""))
        historyList.add(UserScores("2022", "Science", "Difficult", "20",""))
        historyList.add(UserScores("2022", "Science", "Difficult", "20",""))
        historyList.add(UserScores("2022", "Science", "Difficult", "20",""))
        historyList.add(UserScores("2022", "Science", "Difficult", "20",""))
        adapter.setItems(historyList)

        recycler.adapter = adapter


    }
}
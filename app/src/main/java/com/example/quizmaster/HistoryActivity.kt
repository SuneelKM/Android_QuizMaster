package com.example.quizmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmaster.UsersData.Users
import com.example.quizmaster.databinding.ActivityHistoryBinding
import com.example.quizmaster.databinding.ActivityMainBinding

class HistoryActivity : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    lateinit var adapter: HistoryAdapter
    var historyList: ArrayList<Users> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_history)

        recycler = findViewById(R.id.recycler_history)


        var dividerItemDecoration : DividerItemDecoration = DividerItemDecoration(recycler.context, LinearLayoutManager(this).orientation)
        recycler.addItemDecoration(dividerItemDecoration)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = HistoryAdapter(historyList)
        historyList.add(Users("", "2022", "Science", "Difficult", "20"))
        historyList.add(Users("", "2022", "Entertainment", "Difficult", "20"))
        historyList.add(Users("", "2022", "Science", "Difficult", "20"))
        historyList.add(Users("", "2022", "Science", "Difficult", "20"))
        historyList.add(Users("", "2022", "Science", "Difficult", "20"))
        historyList.add(Users("", "2022", "Science", "Difficult", "20"))
        adapter.setItems(historyList)

        recycler.adapter = adapter


    }
}
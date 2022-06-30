package com.example.quizmaster.ui.view

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_history)

        recycler = findViewById(R.id.recycler_history)


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
=======
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
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

        binding.toolbar2.setNavigationOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        var dividerItemDecoration : DividerItemDecoration = DividerItemDecoration(binding.recyclerHistory.context, LinearLayoutManager(this).orientation)
        binding.recyclerHistory.addItemDecoration(dividerItemDecoration)
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)

        binding.recyclerHistory.adapter = adapter

        vm.getUserScores(this)

        vm.userScoreList.observe(this){
            adapter.setItems(it)
        }

        val sorting = arrayOf(binding.category, binding.level, binding.score, binding.date)

        for(sort in sorting){
            sort.setOnClickListener {
                var arrowSwitchSort = arrowSwitch(sort)
                when(sort){
                    binding.category -> adapter.sortCategory(arrowSwitchSort)
                    binding.level -> adapter.sortLevel(arrowSwitchSort)
                    binding.score -> adapter.sortScore(arrowSwitchSort)
                    else -> adapter.sortDate(arrowSwitchSort)
                }
            }
        }

    }

    private fun arrowSwitch(field: TextView): Boolean{
        var arrowDown = if(field.compoundDrawables[3].equals(ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_drop_down_24))) true else false
        if (arrowDown)
            field.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0)
        else
            field.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0)

        return !arrowDown
    }
>>>>>>> master
}
package com.example.quizmaster.ui.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizmaster.ui.adapter.HistoryAdapter
import com.example.quizmaster.R
import com.example.quizmaster.data.model.UserData.UserScores
import com.example.quizmaster.databinding.ActivityHistoryBinding
import com.example.quizmaster.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHistoryBinding
    var historyList: ArrayList<UserScores> = ArrayList()
    var adapter: HistoryAdapter = HistoryAdapter(historyList)
    val vm: UserViewModel by viewModels()
    var arrowDownArray = arrayOf(false, true, true, true)//Date, Category, Level, Points

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

        vm.getUserScores()

        vm.userScoreList.observe(this){
            adapter.setItems(it.reversed())
        }

        val sorting = arrayOf(binding.category, binding.level, binding.score, binding.date)

        for(sort in sorting){
            sort.setOnClickListener {
                for(s in sorting){
                    s.setTextColor(Color.WHITE)
                }
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
        val arrowDown = when(field.text.toString()){
            "Category" -> {
                arrowDownArray[1]  = !arrowDownArray[1]
                arrowDownArray[1]
            }
            "Level" -> {
                arrowDownArray[2]  = !arrowDownArray[2]
                arrowDownArray[2]
            }
            "Score" -> {
                arrowDownArray[3]  = !arrowDownArray[3]
                arrowDownArray[3]
            }
            else -> {
                arrowDownArray[0]  = !arrowDownArray[0]
                arrowDownArray[0]
            }
        }
        field.setTextColor(resources.getColor(R.color.choice_incorrect))
        if (!arrowDown)
            field.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0)
        else
            field.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0)

        return arrowDown
    }
}
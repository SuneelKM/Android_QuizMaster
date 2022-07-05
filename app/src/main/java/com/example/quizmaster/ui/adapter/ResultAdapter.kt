package com.example.quizmaster.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmaster.R
import com.example.quizmaster.data.model.SubmittedQuestions


import com.example.quizmaster.data.model.UserData.UserScores
import com.google.android.material.internal.ContextUtils.getActivity

class ResultAdapter(private var resultList: List<SubmittedQuestions>, val context: Context) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        // inflate a view and return it
        var viewInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.result_item, parent, false)

        return ViewHolder(viewInflater)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // add current item to the holder
        val result = resultList[position]

        holder.questionView.text = result.question
        holder.userAnswerView.text = result.userAnswer
        holder.correctAnswerView.text = result.correctAnswer

    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var questionView: TextView = view.findViewById(R.id.question_r)
        var userAnswerView: TextView = view.findViewById(R.id.userAnswer_r)
        var correctAnswerView: TextView = view.findViewById(R.id.correctAnswer_r)
    }

    fun setItems(itemList: List<SubmittedQuestions>){
        this.resultList = itemList
        notifyDataSetChanged()
    }


}
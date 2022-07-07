package com.example.quizmaster.ui.adapter


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmaster.R
import com.example.quizmaster.data.model.UserData.UserScores
import com.example.quizmaster.ui.view.DetailedResultsPage
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import java.text.SimpleDateFormat
import java.util.*


class HistoryAdapter(private var userHistory: List<UserScores>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {

        // inflate a view and return it
        var viewInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)

        return ViewHolder(viewInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // add current item to the holder


        var date = ""
        val history = userHistory[position]
        try{
            val dateFormat = SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy")
            val dateObj = dateFormat.parse(history.date)
            val newDateFormat = SimpleDateFormat("MMM d yyyy\nhh:mm a")
            date = newDateFormat.format(dateObj)
        }
        catch (e:Exception){
            Log.e("History Adapter","Error with date formating: $e")
            date = history.date
        }
        holder.dateView.text = date
        holder.categoryView.text = history.category
        holder.levelView.text = history.level
        holder.scoreView.text = history.points
        val context = holder.dateView.context

        holder.itemView.setOnClickListener {
            val dateTime = history.date
            val resultActivityIntent = Intent(context, DetailedResultsPage::class.java)
            resultActivityIntent.putExtra("dateTime", dateTime)
            context.startActivity(resultActivityIntent)
        }
    }

    override fun getItemCount(): Int {
        return userHistory.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var dateView: TextView = view.findViewById(R.id.date_item)
        var categoryView: TextView = view.findViewById(R.id.cat_item)
        var levelView: TextView = view.findViewById(R.id.level_item)
        var scoreView: TextView = view.findViewById(R.id.score_item)

    }

    fun setItems(itemList: List<UserScores>) {
        this.userHistory = itemList
        notifyDataSetChanged()
    }

    fun sortCategory(arrowDown: Boolean) {
        if (arrowDown)
            this.userHistory.sortedBy { it.category }
        else
            this.userHistory.sortedBy { it.category }.reversed()

        notifyDataSetChanged()
    }

    fun sortLevel(arrowDown: Boolean) {
        if (arrowDown)
            this.userHistory.sortedBy { it.level }
        else
            this.userHistory.sortedBy { it.level }.reversed()

        notifyDataSetChanged()
    }

    fun sortScore(arrowDown: Boolean) {
        if (arrowDown)
            this.userHistory.sortedBy { it.points }
        else
            this.userHistory.sortedBy { it.points }.reversed()
        notifyDataSetChanged()
    }

    fun sortDate(arrowDown: Boolean) {
        // sort by date
    }
}
package com.example.quizmaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.quizmaster.UsersData.Users

class HistoryAdapter(private var userHistory: List<Users>) :
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
        val history = userHistory[position]
        holder.dateView.text = history.date
        holder.categoryView.text = history.category
        holder.levelView.text = history.level
        holder.scoreView.text = history.points
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

    fun setItems(itemList: List<Users>){
        this.userHistory = itemList
        notifyDataSetChanged()
    }
}
package com.example.brainbuster.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brainbuster.R
import com.example.brainbuster.model.QuestionModel

class OptionAdapter(var context: Context, var questions:QuestionModel):RecyclerView.Adapter<OptionAdapter.ViewHolder>() {

    private var optionsList:List<String> = listOf(questions.option1,questions.option2,questions.option3,questions.option4)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.row_option,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_options.text = optionsList[position]

        holder.itemView.setOnClickListener {
            questions.userAnswer = optionsList[position]
            notifyDataSetChanged()
        }
        if (questions.userAnswer == optionsList[position]){
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var tv_options:TextView = itemView.findViewById(R.id.tv_options)
    }

}
package com.example.brainbuster.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brainbuster.R
import com.example.brainbuster.activity.QuestionsActivity
import com.example.brainbuster.helper.ColorPicker
import com.example.brainbuster.helper.MyIconPicker
import com.example.brainbuster.model.QuizModel

class QuizAdapter(var context: Context,var quiz_list: List<QuizModel>):RecyclerView.Adapter<QuizAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_quiz,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_quiz_title.text = quiz_list[position].title
        holder.lv_quizrow.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.iv_quiz_thumbnail.setImageResource(MyIconPicker.getIcon())

        holder.itemView.setOnClickListener {
            val intent = Intent(context,QuestionsActivity::class.java)
            intent.putExtra("DATE",quiz_list[position].title)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return quiz_list.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var lv_quizrow:LinearLayout = itemView.findViewById(R.id.lv_quiz_row)
        var iv_quiz_thumbnail:ImageView = itemView.findViewById(R.id.iv_quizThumbnail)
        var tv_quiz_title:TextView = itemView.findViewById(R.id.tv_quiz_title)
    }

}
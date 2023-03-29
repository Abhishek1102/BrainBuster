package com.example.brainbuster.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brainbuster.R
import com.example.brainbuster.adapter.OptionAdapter
import com.example.brainbuster.databinding.ActivityQuestionsBinding
import com.example.brainbuster.model.QuestionModel
import com.example.brainbuster.model.QuizModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionsActivity : AppCompatActivity() {

    lateinit var binding: ActivityQuestionsBinding
    lateinit var optionAdapter: OptionAdapter
    lateinit var rv_options: RecyclerView
    lateinit var firestore: FirebaseFirestore
    var quizList: MutableList<QuizModel>? = null
    var questionList: MutableMap<String, QuestionModel>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFirestore()
        setupEventListener()

        binding.rvOptions.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvOptions.setHasFixedSize(true)


    }

    private fun bindViews() {
        binding.cardNext.visibility = View.GONE
        binding.cardSubmit.visibility = View.GONE
        binding.cardPrevious.visibility = View.GONE

        if (index == 1) {
            binding.cardNext.visibility = View.VISIBLE
        } else if (index == questionList!!.size) {
            binding.cardPrevious.visibility = View.VISIBLE
            binding.cardSubmit.visibility = View.VISIBLE
        }
        //in the middle
        else {
            binding.cardNext.visibility = View.VISIBLE
            binding.cardPrevious.visibility = View.VISIBLE
        }

        val question = questionList!!["question$index"]
        question?.let {
            binding.tvDescription.text = it.description
            optionAdapter = OptionAdapter(this,it)
            binding.rvOptions.adapter = optionAdapter
        }

    }

    private fun setupFirestore() {

        var date: String? = intent.getStringExtra("DATE")
        if (date != null) {
            firestore = FirebaseFirestore.getInstance()
            firestore.collection("quizes")
                .whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
//                        Log.d("Data", it.toObjects(QuizModel::class.java).toString())
                        quizList = it.toObjects(QuizModel::class.java)
                        questionList = quizList!![0].questions
                        bindViews()

                    }
                }

        }
    }

    private fun setupEventListener() {
        binding.cardPrevious.setOnClickListener {
            index--
            bindViews()
        }
        binding.cardNext.setOnClickListener {
            index++
            bindViews()
        }
        binding.cardSubmit.setOnClickListener {
            Log.d("FinalQuiz",questionList.toString())

            val intent = Intent(this,ResultActivity::class.java)
            val json:String = Gson().toJson(quizList!![0])
            intent.putExtra("Quiz",json)
            startActivity(intent)


        }

    }

}
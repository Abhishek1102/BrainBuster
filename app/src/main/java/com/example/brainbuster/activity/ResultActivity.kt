package com.example.brainbuster.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.example.brainbuster.R
import com.example.brainbuster.databinding.ActivityResultBinding
import com.example.brainbuster.model.QuestionModel
import com.example.brainbuster.model.QuizModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {

    lateinit var quiz: QuizModel
    lateinit var binding: ActivityResultBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!

        setupViews()

    }

    private fun setupViews() {
        val quizData: String? = intent.getStringExtra("Quiz")
        quiz = Gson().fromJson<QuizModel>(quizData, QuizModel::class.java)

        if (firebaseUser.photoUrl!=null) {
            Glide.with(applicationContext).load(firebaseUser.photoUrl).into(binding.ivUser)
        }
        else{
            Glide.with(applicationContext).load(R.drawable.app_icon_64).into(binding.ivUser)
        }
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            binding.tvAnswer.text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        var score = 0
        for (entry: MutableMap.MutableEntry<String, QuestionModel> in quiz.questions.entries) {
            val questionModel: QuestionModel = entry.value
            if (questionModel.answer == questionModel.userAnswer) {
                score += 10
            }
        }
        binding.tvYourscore.text = "Your Score : $score"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}
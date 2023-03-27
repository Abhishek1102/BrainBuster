package com.example.brainbuster.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainbuster.R
import com.example.brainbuster.adapter.QuizAdapter
import com.example.brainbuster.databinding.FragmentHomeBinding
import com.example.brainbuster.model.QuizModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var quizAdapter: QuizAdapter
    private var quizList = mutableListOf<QuizModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initView()

        //dummy data for testing
        quizList.add(QuizModel("1","Science QUiz"))
        quizList.add(QuizModel("1","Science QUiz"))
        quizList.add(QuizModel("1","Science QUiz"))
        quizList.add(QuizModel("1","Science QUiz"))
        quizList.add(QuizModel("1","Science QUiz"))

        return binding.root
    }

    private fun initView() {

        binding.rvQuiz.setHasFixedSize(true)
        binding.rvQuiz.layoutManager = GridLayoutManager(context,2)
        quizAdapter= QuizAdapter(requireContext(),quizList)
        binding.rvQuiz.adapter = quizAdapter

    }
}
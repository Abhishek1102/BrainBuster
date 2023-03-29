package com.example.brainbuster.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainbuster.R
import com.example.brainbuster.activity.QuestionsActivity
import com.example.brainbuster.adapter.QuizAdapter
import com.example.brainbuster.databinding.FragmentHomeBinding
import com.example.brainbuster.model.QuizModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var quizAdapter: QuizAdapter
    private var quizList = mutableListOf<QuizModel>()
    lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initView()
        setFirestore()

        //dummy data for testing
        quizList.add(QuizModel("1","Science QUiz"))
        quizList.add(QuizModel("1","Science QUiz"))
        quizList.add(QuizModel("1","Science QUiz"))
        quizList.add(QuizModel("1","Science QUiz"))
        quizList.add(QuizModel("1","Science QUiz"))

        return binding.root
    }

    private fun setFirestore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference:CollectionReference = firestore.collection("quizes")
        collectionReference.addSnapshotListener { value, error ->
            if (value==null || error != null){
                d("Tag","error $error")
                toast("error","Error fetching data")
                return@addSnapshotListener
            }
            Log.d("Data",value.toObjects(QuizModel::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(QuizModel::class.java))
            quizAdapter.notifyDataSetChanged()
        }
    }

    private fun initView() {

        binding.rvQuiz.setHasFixedSize(true)
        binding.rvQuiz.layoutManager = GridLayoutManager(context,2)
        quizAdapter= QuizAdapter(requireContext(),quizList)
        binding.rvQuiz.adapter = quizAdapter


//        binding.cardAdd.setOnClickListener {
//            setupdatapicker()
//        }

    }

    private fun setupdatapicker() {

        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(requireFragmentManager(),"DatePicker")
        datePicker.addOnPositiveButtonClickListener {

            d("DatePicker",datePicker.headerText)

            //changing date to suit to our format of dd-mm-yyyy
            val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
            val date = dateFormatter.format(Date(it))

            val intent = Intent(context,QuestionsActivity::class.java)
            intent.putExtra("DATE",date)
            startActivity(intent)
        }
        datePicker.addOnNegativeButtonClickListener {
            d("DatePicker",datePicker.headerText)
        }
        datePicker.addOnCancelListener {
            d("DatePicker","Date Picker was Cancelled")
        }

    }


    private fun toast(type: String, desc: String) {
        val inflater = layoutInflater
        val layout: View =
            inflater.inflate(
                R.layout.custom_toast,
                requireView().findViewById(R.id.lv_customtoast)
            )
        val iv_messageimage = layout.findViewById<ImageView>(R.id.iv_messageimage)
        val tv_messagetitle = layout.findViewById<TextView>(R.id.tv_messagetitle)
        val tv_messagedesc = layout.findViewById<TextView>(R.id.tv_messagedesc)
        val lv_message = layout.findViewById<LinearLayout>(R.id.lv_message)
        if (type.equals("success", ignoreCase = true)) //success
        {
            lv_message.setBackgroundColor(resources.getColor(R.color.green2))
            //            iv_messageimage.setBackground(getResources().getDrawable(R.drawable.gradient_green));
            iv_messageimage.setImageResource(R.drawable.tick)
            tv_messagetitle.text = "Success"
            tv_messagedesc.text = desc
            tv_messagedesc.text = desc
        } else if (type.equals("info", ignoreCase = true)) //info
        {
            lv_message.setBackgroundColor(resources.getColor(R.color.blue))
            iv_messageimage.setImageResource(R.drawable.info)
            tv_messagetitle.text = "Info"
            tv_messagedesc.text = desc
            tv_messagedesc.text = desc
        } else if (type.equals("error", ignoreCase = true)) {
            lv_message.setBackgroundColor(resources.getColor(R.color.red_2))
            iv_messageimage.setImageResource(R.drawable.danger)
            tv_messagetitle.text = "Danger"
            tv_messagedesc.text = desc
        } else if (type.equals("warning", ignoreCase = true)) {
            lv_message.setBackgroundColor(resources.getColor(R.color.orange4))
            iv_messageimage.setImageResource(R.drawable.exclamation)
            tv_messagetitle.text = "Warning"
            tv_messagedesc.text = desc
        }
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(layout)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

}
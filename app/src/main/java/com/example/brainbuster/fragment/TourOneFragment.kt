package com.example.brainbuster.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brainbuster.R
import com.example.brainbuster.activity.DashboardActivity
import com.example.brainbuster.databinding.FragmentTourOneBinding

class TourOneFragment : Fragment() {

    lateinit var binding: FragmentTourOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTourOneBinding.inflate(inflater, container, false)


        binding.lvNext.setOnClickListener {
            addfragment(TourTwoFragment(), "TourTwoFragment")
        }

        binding.tvSkip.setOnClickListener {
            startActivity(Intent(context, DashboardActivity::class.java))
            requireActivity().finish()
        }

        return binding.root
    }

    private fun addfragment(fragment: Fragment, tag: String) {
        val fragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction.add(R.id.frame_tour, fragment, tag).addToBackStack(null)
        fragmentTransaction.commit()
    }
}
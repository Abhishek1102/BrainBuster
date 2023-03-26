package com.example.brainbuster.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brainbuster.R
import com.example.brainbuster.databinding.FragmentAuthenticateBinding
import io.grpc.InternalChannelz.id

class AuthenticateFragment : Fragment() {

    lateinit var binding: FragmentAuthenticateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAuthenticateBinding.inflate(inflater, container, false)

        binding.cardLogin.setOnClickListener {
            replacefragment(LoginFragment(),"LoginFragment")
        }

        binding.cardSignup.setOnClickListener {
            replacefragment(SignUpFragment(),"SignUpFragment")
        }

        return binding.root
    }

    fun replacefragment(fragment1: Fragment?, tag: String?) {
        val ft = requireFragmentManager().beginTransaction()
        ft.replace(R.id.frame_authentication, fragment1!!, tag).addToBackStack(null).commit()
    }
}
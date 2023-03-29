package com.example.brainbuster.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.brainbuster.R
import com.example.brainbuster.activity.AuthenticationActivity
import com.example.brainbuster.databinding.FragmentProfileBinding
import com.example.brainbuster.helper.AppConstant
import com.example.brainbuster.helper.SecurePreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        firebaseAuth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(
            requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN
        )
        binding.tvUserEmail.text = firebaseAuth.currentUser!!.email

        binding.cardLogout.setOnClickListener {

            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.custom_dialog)
            val title = dialog.findViewById<TextView>(R.id.txt_dialog_title)
            val msg = dialog.findViewById<TextView>(R.id.txt_dialog_msg)
            val yes = dialog.findViewById<TextView>(R.id.txt_positive)
            val no = dialog.findViewById<TextView>(R.id.txt_negative)


            title.setText(R.string.logout)
            msg.setText(getString(R.string.sure_to_logout))
            yes.setText(getString(R.string.yes))
            no.setText(getString(R.string.no))

            yes.setOnClickListener {

                AppConstant.showProgressDialog(context)
                Handler().postDelayed({
                    // Sign out from google
                    googleSignInClient.signOut().addOnCompleteListener { task ->
                        // Check condition
                        if (task.isSuccessful) {
                            // Sign out from firebase
                            firebaseAuth.signOut()

                            // Display Toast
                            Toast.makeText(activity, "Logout successful", Toast.LENGTH_SHORT).show()

                            // Finish activity
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    AuthenticationActivity::class.java
                                )
                            )
                            requireActivity().finish()
                        }
                    }
                }, 2000)
            }
            no.setOnClickListener {
                dialog.dismiss()
            }
            //Creating dialog box
            //Creating dialog box
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }

    }

}
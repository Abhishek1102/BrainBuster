package com.example.brainbuster.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.brainbuster.R
import com.example.brainbuster.activity.DashboardActivity
import com.example.brainbuster.activity.TourActivity
import com.example.brainbuster.databinding.FragmentSignUpBinding
import com.example.brainbuster.helper.AppConstant
import com.example.brainbuster.helper.SecurePreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var firestore: FirebaseFirestore
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    lateinit var ref: DocumentReference
    lateinit var username: String
    lateinit var email: String
    lateinit var password: String

    var validUserName: Boolean = false
    var validEmail: Boolean = false
    var validPassword: Boolean = false

    var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        initView()
        gAuthenticate()
        onTypeValidation()

        return binding.root
    }

    private fun initView() {
        binding.tvGoToLogin.setOnClickListener {
            replacefragment(LoginFragment(), "LoginFragment")
        }

        binding.lvSignupGoogle.setOnClickListener {
            // Initialize sign in intent
            val intent = googleSignInClient.signInIntent
            // Start activity for result
            startActivityForResult(intent, 100)
        }

        binding.lvSignup.setOnClickListener {
            if (validUserName && validEmail && validPassword) {
                addUser()
            } else {
                Toast.makeText(context, "please enter valid data to register", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.ivSeenviolet.setOnClickListener {
            binding.ivSeenviolet.visibility = View.GONE
            binding.ivUnseenviolet.visibility = View.VISIBLE
            binding.edtSignupPassword.setTransformationMethod(null)
        }
        binding.ivUnseenviolet.setOnClickListener {
            binding.ivSeenviolet.visibility = View.VISIBLE
            binding.ivUnseenviolet.visibility = View.GONE
            binding.edtSignupPassword.setTransformationMethod(PasswordTransformationMethod())
        }

    }

    private fun addUser() {
        username = binding.edtSignupName.text.toString().trim()
        email = binding.edtSignupEmail.text.toString().trim()
        password = binding.edtSignupPassword.text.toString().trim()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    SecurePreferences.savePreferences(context, AppConstant.USER_NAME, username)
                    SecurePreferences.savePreferences(context, AppConstant.USER_EMAIL, email)

                    toast("success", "Registration Successfull")

                    //making user info entry in firestore database
                    userId = firebaseAuth.currentUser!!.uid
                    ref = firestore.collection("user_info").document(userId)
                    val reg_entry: MutableMap<String, Any> = HashMap()
                    reg_entry["Name"] = username
                    reg_entry["Email"] = email
                    reg_entry["Password"] = password

                    ref.set(reg_entry).addOnSuccessListener {
                        Log.d("success", "User Registered")
                    }
                        .addOnFailureListener {
                            Log.d("failure", it.localizedMessage)
                            toast("error", "Something went wrong")
                        }

                    Handler().postDelayed({
                        SecurePreferences.savePreferences(activity, AppConstant.IS_LOGIN, true)
                        startActivity(Intent(activity, TourActivity::class.java))
                        requireActivity().finish()
                    }, 1000)
                } else {
                    toast("error", "Registration Failed\nEmail used by another account")
                    Log.d("TAG", task.exception?.message.toString())
                }
            }

    }

    private fun gAuthenticate() {
        //SHA1 Key is nescessary in this type of authenctication

        // Initialize sign in options
        // the client-id is copied form
        // google-services.json file

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("925114036452-84u0l5pg4knqpnrjqfbhffr6gv24moag.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(
            requireActivity(), googleSignInOptions
        )

//        // Initialize firebase auth
//        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize firebase user
        val firebaseUser = firebaseAuth.currentUser
        // Check condition
        if (firebaseUser != null) {
            // When user already sign in
            // redirect to profile activity
            startActivity(
                Intent(activity, DashboardActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            requireActivity().finish()
        } else {
            Log.d("TAG", "not null")
        }

    }

    private fun onTypeValidation() {
        binding.edtSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val password: String = binding.edtSignupPassword.text.toString().trim()
                if (password.length >= 8) {
                    binding.tvPasswordLengthValidation.visibility = View.GONE
                    validPassword = true
                } else {
                    binding.tvPasswordLengthValidation.visibility = View.VISIBLE
                    validPassword = false
                }
                if (password.matches(".*[A-Z].*".toString().toRegex())) {
                    binding.tvPasswordUppercaseValidation.visibility = View.GONE
                    validPassword = true
                } else {
                    binding.tvPasswordUppercaseValidation.visibility = View.VISIBLE
                    validPassword = false
                }
                if (password.matches(".*[a-z].*".toString().toRegex())) {
                    binding.tvPasswordLowercaseValidation.visibility = View.GONE
                    validPassword = true
                } else {
                    binding.tvPasswordLowercaseValidation.visibility = View.VISIBLE
                    validPassword = false
                }
                if (password.matches(".*[0-9].*".toString().toRegex())) {
                    binding.tvPasswordDigitValidation.visibility = View.GONE
                    validPassword = true
                } else {
                    binding.tvPasswordDigitValidation.visibility = View.VISIBLE
                    validPassword = false
                }
                if (password.matches(".*[!@#$%^&*+=/?].*".toRegex())) {
                    binding.tvPasswordSymbolValidation.visibility = View.GONE
                    validPassword = true
                } else {
                    binding.tvPasswordSymbolValidation.visibility = View.VISIBLE
                    validPassword = false
                }
            }
        })

        binding.edtSignupName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.edtSignupName.text.toString()
                        .trim().length < 4 || binding.edtSignupName.text.trim()
                        .toString().length > 20
                ) {
                    binding.tvUsernameValidation.visibility = View.VISIBLE
                    validUserName = false
                } else {
                    binding.tvUsernameValidation.visibility = View.GONE
                    validUserName = true
                }
            }
        })

        binding.edtSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!binding.edtSignupEmail.text.toString().trim().matches(emailPattern.toRegex())) {
                    binding.tvEmailValidation.text = "please enter valid email"
                    binding.tvEmailValidation.visibility = View.VISIBLE
                    validEmail = false
                } else if (binding.edtSignupEmail.text.toString().trim().isEmpty()) {
                    binding.tvEmailValidation.text = "please enter email to continue"
                    binding.tvEmailValidation.visibility = View.VISIBLE
                    validEmail = false
                } else {
                    binding.tvEmailValidation.visibility = View.GONE
                    validEmail = true
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100
            // Initialize task
            val signInAccountTask = GoogleSignIn
                .getSignedInAccountFromIntent(data)

            // check condition
            if (signInAccountTask.isSuccessful) {
                // When google sign in successful
                // Initialize string
                val s = "Google sign in successful"
                // Display Toast
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    val googleSignInAccount = signInAccountTask
                        .getResult(ApiException::class.java)
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null
                        // Initialize auth credential
                        val authCredential = GoogleAuthProvider
                            .getCredential(
                                googleSignInAccount.idToken, null
                            )
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential)
                            .addOnCompleteListener(
                                requireActivity()
                            ) { task ->
                                // Check condition
                                if (task.isSuccessful) {
                                    // When task is successful
                                    // Redirect to profile activity
                                    firebaseUser = firebaseAuth.currentUser!!
                                    SecurePreferences.savePreferences(
                                        context,
                                        AppConstant.USER_IMAGE, firebaseUser.photoUrl.toString()
                                    )
                                    SecurePreferences.savePreferences(
                                        context,
                                        AppConstant.USER_NAME,
                                        firebaseUser.displayName.toString()
                                    )
                                    SecurePreferences.savePreferences(
                                        context,
                                        AppConstant.IS_LOGIN,
                                        true
                                    )
                                    startActivity(
                                        Intent(activity, TourActivity::class.java)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    )
                                    requireActivity().finish()
                                    // Display Toast
                                    Toast.makeText(
                                        context,
                                        "Firebase authentication successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    // When task is unsuccessful
                                    // Display Toast
                                    Toast.makeText(
                                        context,
                                        "Authentication Failed" + task.exception!!.localizedMessage,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(context, "Unsuccessful task", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun replacefragment(fragment1: Fragment?, tag: String?) {
        val ft = requireFragmentManager().beginTransaction()
        ft.replace(R.id.frame_authentication, fragment1!!, tag).addToBackStack(null).commit()
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
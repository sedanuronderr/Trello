package com.seda.trello.Login

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.seda.trello.ObjectClass
import com.seda.trello.R
import com.seda.trello.databinding.FragmentSignInBinding
import com.seda.trello.fragments.MainActivity2
import com.seda.trello.model.User


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.toolbar2.setNavigationIcon(R.drawable.back)
        binding.toolbar2.setNavigationOnClickListener { view->

            val action = SignInFragmentDirections.actionSignInFragmentToLoginScreen()
            view.findNavController().navigate(action)


        }
        binding.btnSignIn.setOnClickListener {
            signInRegisteredUser(it)
        }

    }
    private fun popBackStackToA() {
        if (!findNavController().popBackStack()) {
//            Call finish on your Activity
            requireActivity().finish()
        }
    }
    private fun signInRegisteredUser(view: View){
        var email:String = binding.etEmail1.text.toString().trim(){ it <= ' '}
        var password:String = binding.etPassword1.text.toString().trim(){ it <= ' '}
        if (validateForm(email, password,view )){
                 showProgress()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    hideProgressDialog()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        ObjectClass.registerGet(this,null)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
         }

    }

    private fun validateForm(email:String,password:String,view: View):Boolean{

        return when{

            TextUtils.isEmpty(email)->{
                ObjectClass.showErrorSnackBar("Please enter a email",view)
                false
            }
            TextUtils.isEmpty(password)->{
                ObjectClass.showErrorSnackBar("Please enter a password",view)
                false
            }
            else->{
                true
            }
        }

    }

    fun showProgress(){

        mProgressDialog= Dialog(requireActivity())
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    @SuppressLint("SuspiciousIndentation")
    fun signInSuccess(dataUser: User) {
      hideProgressDialog()
      val intent = Intent(requireContext(),MainActivity2::class.java)
        startActivity(intent)
    }
}
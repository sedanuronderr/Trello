package com.seda.trello.Login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.seda.trello.ObjectClass
import com.seda.trello.R

import com.seda.trello.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
private lateinit var binding: FragmentSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.toolbar.setNavigationIcon(R.drawable.back)
       binding.toolbar.setNavigationOnClickListener { view->

           val action = SignUpFragmentDirections.actionSignUpFragmentToLoginScreen()
           view.findNavController().navigate(action)


       }
        binding.btnSignUp.setOnClickListener {
            registerUser(it)
        }


    }

    private fun registerUser(view: View){

        val name:String =binding.etName.text.toString().trim(){
            it <= ' '
        }
        val email:String =binding.etEmail.text.toString().trim(){
            it <= ' '
        }
        val password:String =binding.etPassword.text.toString().trim(){
            it <= ' '
        }
    if(validateForm(name,email,password,view)){
         ObjectClass.showProgressDialog("Please wait",requireContext())
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
            ObjectClass.hideProgressDialog()
            if (task.isSuccessful){
                val firebasE
            }
        }

    }
    }

    private fun validateForm(name:String,email:String,password:String,view: View):Boolean{

        return when{
            TextUtils.isEmpty(name)->{
                ObjectClass.showErrorSnackBar("Please enter a name",view)
                   false
            }
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
}
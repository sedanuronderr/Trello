package com.seda.trello.Login

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seda.trello.ObjectClass
import com.seda.trello.R

import com.seda.trello.databinding.FragmentSignUpBinding
import com.seda.trello.model.User

class SignUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
private lateinit var binding: FragmentSignUpBinding
    private lateinit var mProgressDialog: Dialog
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
        auth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {
            registerUser(it)
        }


    }
    fun userRegisteredSuccess(){
        Toast.makeText(requireContext()," you have"+
                " successfully registered the email"+
                " address ",Toast.LENGTH_SHORT).show()
        hideProgressDialog()
        auth.signOut()
    }

    private fun registerUser(view: View){

        var name:String =binding.etName.text.toString().trim(){
            it <= ' '
        }
        var email:String =binding.etEmail.text.toString().trim(){
            it <= ' '
        }
        var password:String =binding.etPassword.text.toString().trim(){
            it <= ' '
        }
    if(validateForm(name,email,password,view)){
           showProgress()
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
          if (task.isSuccessful){
                val firebaseUser: FirebaseUser = task.result.user!!
                val registeredEmail = firebaseUser.email!!
                val user= User(firebaseUser.uid,name,registeredEmail)
              ObjectClass.registerUser(this,user)
                            binding.etEmail.setText("")
                            binding.etName.setText("")
                            binding.etPassword.setText("")

            }else{
                Toast.makeText(requireContext(),task.exception?.message,Toast.LENGTH_SHORT).show()
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
}
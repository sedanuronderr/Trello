package com.seda.trello.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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

    }
}
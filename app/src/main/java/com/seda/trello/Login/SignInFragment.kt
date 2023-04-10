package com.seda.trello.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.seda.trello.R
import com.seda.trello.databinding.FragmentSignInBinding
import com.seda.trello.databinding.FragmentSignUpBinding


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
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

        binding.toolbar2.setNavigationIcon(R.drawable.back)
        binding.toolbar2.setNavigationOnClickListener { view->

            val action = SignInFragmentDirections.actionSignInFragmentToLoginScreen()
            view.findNavController().navigate(action)


        }
    }
}
package com.seda.trello.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.seda.trello.ObjectClass
import com.seda.trello.R
import com.seda.trello.databinding.ActivityProfileBinding
import com.seda.trello.fragments.MainActivity2
import com.seda.trello.model.User

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ObjectClass.registerGet(null,null,this)


     binding.apply {
          setSupportActionBar(binding.toolbarr)
          toolbarr.title= resources.getString(R.string.profile)
          toolbarr.setNavigationIcon(R.drawable.back)
          toolbarr.setNavigationOnClickListener {
         val intent = Intent(this@ProfileActivity,MainActivity2::class.java)
          startActivity(intent)
          finish()
    }
}


        binding.navImage1.setOnClickListener{
            if()
        }

    }

    fun setUserData(dataUser: User?) {
        Glide
            .with(this)
            .load(dataUser?.image)
            .centerCrop()
            .placeholder(R.drawable.loading)
            .into(binding.navImage1)

        binding.name.setText(dataUser?.name)
        binding.email.setText(dataUser?.email)
        if(dataUser?.mobile != 0L){
            binding.mobile.setText(dataUser?.mobile.toString())
        }

    }

}
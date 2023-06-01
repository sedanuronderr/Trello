package com.seda.trello.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.seda.trello.Login.MainActivity
import com.seda.trello.ObjectClass
import com.seda.trello.databinding.ActivitySplashScreenBinding
import com.seda.trello.fragments.MainActivity2

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
    var currentUserID= ObjectClass.getCurrentUserID()
    if(currentUserID.isNotEmpty()){
        startActivity(Intent(this, MainActivity2::class.java))
        finish()
    }else{
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

},2500)
        with(binding.animationView){
            setMinAndMaxFrame(20,50)
        }
    }
}
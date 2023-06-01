package com.seda.trello.fragments

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.seda.trello.Login.MainActivity
import com.seda.trello.ObjectClass
import com.seda.trello.R
import com.seda.trello.adapters.BoardItemsAdapter
import com.seda.trello.databinding.ActivityMain2Binding
import com.seda.trello.databinding.NavHeaderMainBinding
import com.seda.trello.model.User
import com.seda.trello.activitys.CreateBoardActivity
import com.seda.trello.activitys.ProfileActivity
import com.seda.trello.utils.Constants

class MainActivity2 : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private  lateinit var mUserName:String
    private lateinit var boardItemsAdapter: BoardItemsAdapter

    private lateinit var mProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.appBarMain.toolbar4)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)



        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.trelloPageFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

        ObjectClass.registerGet(null,this,null,true)



    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun onPopularItemLongClickListener() {

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.nav_account->{
                        Toast.makeText(this,"My Profile",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,ProfileActivity::class.java)
                        startActivity(intent)

                    }

                    R.id.nav_logout->{
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        true
                    }
                }
        return true

    }

    fun updateNavigationUserDetails(dataUser: User?, readBoardList: Boolean?) {
        mUserName = dataUser!!.name

        val headerView = binding.navView.getHeaderView(0)
        val headerBinding = NavHeaderMainBinding.bind(headerView)
        Glide
            .with(this)
            .load(dataUser?.image)
            .centerCrop()
            .placeholder(R.drawable.loading)
            .into(headerBinding.navImage)

        headerBinding.textView4.text = dataUser?.name


    }
    fun showProgress(){

        mProgressDialog= Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
}
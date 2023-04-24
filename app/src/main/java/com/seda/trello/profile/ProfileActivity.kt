package com.seda.trello.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.bumptech.glide.Glide
import com.seda.trello.Manifest
import com.seda.trello.ObjectClass
import com.seda.trello.R
import com.seda.trello.databinding.ActivityProfileBinding
import com.seda.trello.fragments.MainActivity2
import com.seda.trello.model.User
import com.seda.trello.utils.Constants

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var mSelectedImageFileUri : Uri?=null

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
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                      showImageChooser()

            }else{
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showImageChooser()
            }
        }else{
            Toast.makeText(this,"Opps,you just denied the permission for storage.You can also allow it from settings",Toast.LENGTH_SHORT).show()


        }
    }

    private fun showImageChooser(){
        var galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
         startActivityForResult(galleryIntent,Constants.PICK_IMAGE_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE && data!!.data != null){
            mSelectedImageFileUri = data.data
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
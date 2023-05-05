package com.seda.trello.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.seda.trello.ObjectClass
import com.seda.trello.R
import com.seda.trello.databinding.ActivityCreateBoardBinding
import com.seda.trello.model.Board
import com.seda.trello.utils.Constants

class CreateBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBoardBinding
    private var mSelectedImageFileUri : Uri?=null
    private lateinit var mProgressDialog: Dialog
private var mBoardImageUrl:String=""

    private lateinit var mUserName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

         mUserName = intent.getStringExtra(Constants.NAME).toString()
        binding.apply {
            setSupportActionBar(binding.toolbarrr)

            toolbarrr.setNavigationIcon(R.drawable.back)
            toolbarrr.setNavigationOnClickListener {
                val intent = Intent(this@CreateBoardActivity, MainActivity2::class.java)
                startActivity(intent)
                finish()
            }


        }
        binding.createBtn.setOnClickListener {
                   if(mSelectedImageFileUri != null){
                       uploadBoardImage()
                   }else{
                       showProgress()
                       createBoard()
                   }
        }


        binding.ivBoardImage.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
               Constants.showImageChooser(this)
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
                Constants.showImageChooser(this)

            }
        }else{
            Toast.makeText(this,"Opps,you just denied the permission for storage.You can also allow it from settings",
                Toast.LENGTH_SHORT).show()


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE && data!!.data != null){
            mSelectedImageFileUri = data.data

            Glide
                .with(this)
                .load(mSelectedImageFileUri)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(binding.ivBoardImage)


        }



    }

    private fun createBoard(){
        val assignedUsersArrayList:ArrayList<String> = ArrayList()
        assignedUsersArrayList.add(ObjectClass.getCurrentUserID())

        val board = Board(binding.boardName.text.toString(),mBoardImageUrl,mUserName,assignedUsersArrayList)
        ObjectClass.createBoard(this,board)
    }

    private fun uploadBoardImage(){
        showProgress()
        val sRef : StorageReference = FirebaseStorage.getInstance().reference.child("BOARD_IMAGE"+ System.currentTimeMillis() + "." + Constants.getFileExtension(this,mSelectedImageFileUri))

        sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener {
                taskSnapshot->
            Log.e("Firebase Board URL",taskSnapshot.metadata!!.reference!!.downloadUrl.toString())
            taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    uri->
                Log.i("Downloadable Board URL",uri.toString())
                mBoardImageUrl = uri.toString()
               boardCreateSuccessfully()

             createBoard()
            }
        }.addOnFailureListener{
            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            hideProgressDialog()
        }
    }

    fun  boardCreateSuccessfully(){
        hideProgressDialog()
        finish()
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
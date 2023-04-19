package com.seda.trello


import android.graphics.Color
import android.util.Log

import android.view.View
import androidx.fragment.app.Fragment

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.seda.trello.Login.SignInFragment
import com.seda.trello.Login.SignUpFragment
import com.seda.trello.model.User
import com.seda.trello.utils.Constants

class ObjectClass {
companion object  BaseActivity{
  private val mFireStoreDb = FirebaseFirestore.getInstance()
 var currentId=""

fun registerUser(fragment:SignUpFragment,userInfo:User){
mFireStoreDb.collection(Constants.USERS)
    .document(getCurrentUserID())
    .set(userInfo, SetOptions.merge())
    .addOnSuccessListener {
        fragment.userRegisteredSuccess()
    }.addOnFailureListener {
        Log.e(
            fragment.javaClass.simpleName, "Error", it
        )
    }

}

    fun registerGet(fragment:SignInFragment){
        mFireStoreDb.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get().addOnSuccessListener { documentSnapshot->
                val dataUser = documentSnapshot.toObject(User::class.java)

                if (dataUser != null) {
                    fragment.signInSuccess(dataUser)
                }
            }.addOnFailureListener {
                Log.e(
                    "Firestore Error", "Error", it
                )
            }
    }


    fun getCurrentUserID(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
            currentId= currentUser.uid
            Log.e("söyle", currentId)
        }
        return currentId
    }


    fun showErrorSnackBar(message: String,view: View) {
        val snackBar =
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
           Color.LTGRAY
        )

        snackBar.show()
    }
}



}

// END
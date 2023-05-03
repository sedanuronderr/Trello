package com.seda.trello


import android.app.Activity
import android.graphics.Color
import android.util.Log

import android.view.View
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.seda.trello.Login.SignInFragment
import com.seda.trello.Login.SignUpFragment
import com.seda.trello.fragments.CreateBoardActivity
import com.seda.trello.fragments.MainActivity2
import com.seda.trello.model.Board
import com.seda.trello.model.User
import com.seda.trello.profile.ProfileActivity
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

    fun registerGet(fragment:SignInFragment?, activity2: MainActivity2?,profile:ProfileActivity?){

        mFireStoreDb.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get().addOnSuccessListener { documentSnapshot->
                val dataUser = documentSnapshot.toObject(User::class.java)
                profile?.setUserData(dataUser)
                activity2?.updateNavigationUserDetails(dataUser)

                if (dataUser != null) {
                    fragment?.signInSuccess(dataUser)
                }

            }.addOnFailureListener {
           fragment?.hideProgressDialog()

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

    fun createBoard(activity:CreateBoardActivity,board: Board){
        mFireStoreDb.collection(Constants.BOARD)
            .document()
            .set(board,SetOptions.merge())
            .addOnSuccessListener {
                Log.e(
                    activity.javaClass.simpleName, "Success"
                )
                Toast.makeText(activity,"board created successfully.",Toast.LENGTH_SHORT).show()
                activity.hideProgressDialog()
            }

    }

     fun updateUserProfileData(activity: ProfileActivity,
                               userHashMap: HashMap<String,Any>){
         mFireStoreDb.collection(Constants.USERS)
             .document(getCurrentUserID())
             .update(userHashMap)
             .addOnSuccessListener {
                 Log.e(
                     activity.javaClass.simpleName, "Success"
                 )
                 Toast.makeText(activity," update successfuly",Toast.LENGTH_SHORT).show()
                 activity.profileUpdateSuccess()
             }.addOnFailureListener{
                 activity.hideProgressDialog()
             }

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
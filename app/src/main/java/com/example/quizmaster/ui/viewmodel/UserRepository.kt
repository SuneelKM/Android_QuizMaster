package com.example.quizmaster.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.quizmaster.R
import com.example.quizmaster.data.model.UserData.UserScores
import com.example.quizmaster.data.model.UserData.UserSignup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class UserRepository @Inject constructor(
    private val context: Context,
    private val firebaseStorage: FirebaseStorage,
    private val myRef: DatabaseReference
) {

    var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var userScoreList = MutableLiveData<List<UserScores>>()
    var username = MutableLiveData<String>()
    var image = MutableLiveData<String>()

    fun getUserName() {
        myRef.get().addOnSuccessListener { 
            username.postValue(it.child("username").value.toString())
        }
            .addOnFailureListener {
            Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun getUserScores(){

        val usList = ArrayList<UserScores>()
        loading.postValue(true)
        myRef.get()
            .addOnSuccessListener {

                val scores = it.child("scores").children
                for(score in scores){
                    val date = score.child("date").value.toString()
                    val category = score.child("category").value.toString()
                    val level = score.child("level").value.toString()
                    val points = score.child("points").value.toString()
                    //val profile = score.child("profile").value.toString()

//                    usList.add(UserScores(date,category,level,points,profile))
                    usList.add(UserScores(date,category,level,points))
                    loading.postValue(false)
                }
                userScoreList.postValue(usList)
            }
            .addOnFailureListener {
                loading.postValue(false)
                Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
            }

    }

    fun getPicture(){
        var pic: String? =null
        myRef.get().addOnSuccessListener {
            pic = it.child("imageUrl").value.toString()
            image.postValue(pic)
        }
            .addOnFailureListener {
                Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    fun uploadImageToStorage(imageUri: Uri, file: String){
        
        val reference = firebaseStorage.getReference("images/$file")
        reference.putFile(imageUri)
            .addOnSuccessListener { it ->
                Timber.tag("Upload Photo").d("Successfully loaded: %s", it.metadata?.path)

                reference.downloadUrl.addOnSuccessListener {
                    Timber.tag("Upload Photo to Firebase").d("File Location: %s", it)
                    saveImageToDatabase(it.toString())
                }
                    .addOnFailureListener {
                        Timber.tag("No Upload").v("File not found")
                    }
            }
    }

    fun saveImageToDatabase(imageUri: String){
        
        myRef.child("imageUrl").setValue(imageUri)
    }

    fun setUserScores(date:String, category: String, level: String, points: String) {
        val userScores = UserScores(date,category,level, points)
        myRef.child("scores").child("${Date()}").setValue(userScores)
    }
}
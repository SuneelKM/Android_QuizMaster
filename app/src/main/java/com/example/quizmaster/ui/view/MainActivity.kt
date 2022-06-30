package com.example.quizmaster.ui.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.net.toFile
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quizmaster.ui.viewmodel.QuestionsViewModel
import com.example.quizmaster.R
import com.example.quizmaster.data.model.UserData.UserScores
import com.example.quizmaster.databinding.ActivityMainBinding
import com.example.quizmaster.databinding.NavHeaderBinding
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    val vm: QuestionsViewModel by viewModels()
    var allScores = ArrayList<UserScores>()
    lateinit var picture: ShapeableImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var drawer: DrawerLayout
    lateinit var nav_view: NavigationView
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

//        binding.appLogout.setOnClickListener {
//            firebaseAuth.signOut()
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//            finish()
////            firebaseAuth.currentUser?.delete()
//        }
//
//        vm.getQuestions("5","9","medium", "multiple")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onNext ={
//                    println(it)
//                    binding.textView1.text=it.toString()
////                  Html.fromHtml(it.results[0].question)
//                },
//                onError = {e -> println("this is error $e")}
//            )
//
//        val date = SimpleDateFormat("dd MMM").format(Date())
//        setUserScores(date,"Science", "hard", "8/10", "avatar")
//
//        getUserScores()
//    }
//
//    private fun setUserScores(date:String, category: String, level: String, points: String, profile:String) {
//        val userScores = UserScores(date,category,level, points, profile)
//        val database = Firebase.database
//        val uid = firebaseAuth.uid
//        val myRef = database.getReference("/Users/$uid/scores/${Date()}")
//        myRef.setValue(userScores)
//    }
//
//    private fun getUserScores(){
//        val database = Firebase.database
//        val uid = firebaseAuth.uid
//        val myRef = database.getReference("/Users/$uid")
//
//        myRef.get()
//            .addOnSuccessListener {
//                val username = it.child("username").value.toString()
//                val email = it.child("email").value.toString()
//
//                val scores = it.child("scores").children
//                for(score in scores){
//                    val date = score.child("date").value.toString()
//                    val category = score.child("category").value.toString()
//                    val level = score.child("level").value.toString()
//                    val points = score.child("points").value.toString()
//                    val profile = score.child("profile").value.toString()
//
//                    // Added into an array so to be used in recycler view
//                    allScores.add(UserScores(date,category,level,points,profile))
//
//                }
//                println(allScores)
//                println(username)
//                println(email)
//                binding.userName.text = "User: $username"
//                binding.userEmail.text = "Email: $email"
//                binding.userScore.text = "Past Scores: $allScores"
//
//
//        }
//            .addOnFailureListener {
//                Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
//            }

        drawer = binding.drawer
        nav_view = binding.navMenu
        toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        nav_view.bringToFront()
        var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        binding.quizSetupButton.setOnClickListener{
            var quizSetupIntent = Intent(this, ChooseQuizActivity::class.java)
            startActivity(quizSetupIntent)
        }

        binding.historySetupButton.setOnClickListener {
            var historySetupIntent = Intent(this, HistoryActivity::class.java)
            startActivity(historySetupIntent)
        }

        getUserName()
        getPicture()

    }

    private fun getUserName(){
        val database = Firebase.database
        val uid = firebaseAuth.uid
        val myRef = database.getReference("/Users/$uid")
        var username = ""

        var navbarUserName: TextView = nav_view.getHeaderView(0).findViewById(R.id.user_name)

        myRef.get().addOnSuccessListener {
            username = it.child("username").value.toString()
            navbarUserName?.text = username
            binding.textView4.text = username
        }


    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer((GravityCompat.START))
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                firebaseAuth.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
        }
        return true
    }

    private fun getPicture(){
        picture = nav_view.getHeaderView(0).findViewById(R.id.imageView4)

        picture.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage && data != null) {
            imageUri = data.data
            picture.setImageURI(imageUri)
            uploadImage()
        }
    }

    fun uploadImage(){
        val fileName = UUID.randomUUID().toString()
        val reference = FirebaseStorage.getInstance().getReference("images/$fileName")
        reference.putFile(imageUri!!)
            .addOnSuccessListener {
                Timber.tag("Upload Photo").d("Successfully loaded: %s", it.metadata?.path)
                Log.v("Upload Photo", "${it.metadata?.path}")
            }
        reference.downloadUrl.addOnSuccessListener {
            Timber.tag("Upload Photo to Firebase").d("File Location: %s", it)
            Log.v("Upload", "File location ${it.toString()}")
            saveImageToDatabase(it.toString())
        }
    }

    fun saveImageToDatabase(imageUri: String){
        val database = Firebase.database
        val uid = firebaseAuth.uid
        val myRef = database.getReference("/Users/$uid")
        myRef.get().addOnSuccessListener {
            myRef.child("imageUrl").setValue(imageUri)
        }
    }
}
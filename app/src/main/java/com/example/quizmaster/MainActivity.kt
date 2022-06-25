package com.example.quizmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quizmaster.UserData.UserScores
import com.example.quizmaster.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    val vm: QuestionsViewModel by viewModels()
    var allScores = ArrayList<UserScores>()

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
        nav_view = findViewById(R.id.nav_menu)
        toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        nav_view.bringToFront()
        var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

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
}

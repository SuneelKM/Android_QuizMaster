package com.example.quizmaster.ui.view

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quizmaster.ui.viewmodel.QuestionsViewModel
import com.example.quizmaster.R
import com.example.quizmaster.data.model.UserData.UserScores
import com.example.quizmaster.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.example.quizmaster.ui.viewmodel.AuthViewModel
import com.example.quizmaster.ui.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity1 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var binding: ActivityMainBinding
    val vm: QuestionsViewModel by viewModels()
    private val userVM: UserViewModel by viewModels()
    private val authVM: AuthViewModel by viewModels()
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
        drawer = binding.drawer
        nav_view = binding.navMenu
        toolbar = binding.toolbar


//        val spinner: Spinner = findViewById(R.id.languageSpinner)
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter.createFromResource(
//            this,
//            R.array.languages,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinner.adapter = adapter
//        }
//        spinner.onItemSelectedListener = this




//        (nav_view.menu.findItem(R.id.language).actionView as Spinner)
//            .onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//
//                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    val selectedLanguage = adapterView?.getItemAtPosition(position).toString()
//
////                    setLanguage(selectedLanguage)
//                    var l = ""
//                    if (selectedLanguage == "English"){
//                        l="en"
//                        c=1
//                    }
//
//                    else if (selectedLanguage == "हिन्दी")
//                        l = "hi"
//                    println("hello     $l")
//                    val locale = Locale(l)
//                    val config: Configuration = baseContext.resources.configuration
//                    config.setLocale(locale)
//                    baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//                    if(c==0){
//                        recreate()
//                    }
//
//                }
//
//            private fun setLanguage(lang: String) {
//                var l = ""
//                if (lang == "English")
//                    l="en"
//                else if (lang == "हिन्दी")
//                    l = "hi"
//                    println("hello     $l")
//                    val locale = Locale(l)
//                    val config: Configuration = baseContext.resources.configuration
//                    config.setLocale(locale)
//                    baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
////                    recreate()
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                    // we don't need this as we always have an item selected
//                }
//            }


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
            overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
            finish()
        }

        binding.historySetupButton.setOnClickListener{
            historyStartActivity()
        }


        getUserName()
        getPicture()

    }

    private fun setLanguage1(lang:String) {
        var l = ""
        if (lang == "English1")
            l="en"
        else if (lang == "Hindi") {
            l = "hi"
            println("hello     $l")
            val locale = Locale(l)
            val config: Configuration = baseContext.resources.configuration
            config.setLocale(locale)
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            recreate()
        }

    }


    private fun getUserName(){
        var username = userVM.username

        var navbarUserName: TextView = nav_view.getHeaderView(0).findViewById(R.id.user_name)

        username.observe(this){
            navbarUserName?.text = it
            binding.textView4.text = it
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
                authVM.handleSignOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.history ->{
                historyStartActivity()
                true
            }
        }
        return true
    }



    private fun historyStartActivity(){
        val hisIntent = Intent(this, HistoryActivity::class.java)
        startActivity(hisIntent)
        finish()
    }

    private fun getPicture(){
        picture = nav_view.getHeaderView(0).findViewById(R.id.imageView4)

        var image = userVM.image

        image.observe(this){ it ->
            if(it.isNullOrEmpty()){
                picture.setContentPadding(40, 40, 40, 40)
                picture.setImageResource(R.drawable.ic_baseline_camera_alt_24)
                Timber.tag("Picture").v("Picture not found")

            }else {
                picture.scaleType = ImageView.ScaleType.FIT_XY
                image.observe(this){
                    Picasso.get().load(it).into(picture)
                    Timber.tag("Picture").v("Picture attached")
                }
            }
        }


        picture.setOnClickListener{
            MaterialAlertDialogBuilder(this)
                .setTitle("Alert")
                .setMessage("Do you want to upload photo from gallery?")
                .setPositiveButton("Open gallery") { _, _ ->
                    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, pickImage)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage && data != null) {
            imageUri = data.data
            picture.setImageURI(imageUri)
            imageUri?.let { uploadImage(it) }
        }
    }

    private fun uploadImage(imageUri: Uri){
        val fileName = UUID.randomUUID().toString()
        val content = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(imageUri))
        val file = "$fileName.$content"
        userVM.uploadImageToStorage(imageUri, file)
    }

//    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
//        // An item was selected. You can retrieve the selected item using
//         var lang = parent.getItemAtPosition(pos).toString()
//                var l = ""
//                if (lang == "English")
//                    l="en"
//                else if (lang == "हिन्दी")
//                    l = "hi"
//                    println("hello     $l")
//                    val locale = Locale(l)
//                    val config: Configuration = baseContext.resources.configuration
//                    config.setLocale(locale)
//                    baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//                    recreate()
//
//    }
//
//    override fun onNothingSelected(parent: AdapterView<*>) {
//        // Another interface callback
//    }

}
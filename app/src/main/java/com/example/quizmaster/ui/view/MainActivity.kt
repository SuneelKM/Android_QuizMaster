package com.example.quizmaster.ui.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quizmaster.ui.viewmodel.QuestionsViewModel
import com.example.quizmaster.R
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
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    val vm: QuestionsViewModel by viewModels()
    private val userVM: UserViewModel by viewModels()
    private val authVM: AuthViewModel by viewModels()
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

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        nav_view.bringToFront()
        var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        binding.quizSetupButton.setOnClickListener {
            var quizSetupIntent = Intent(this, ChooseQuizActivity::class.java)
            startActivity(quizSetupIntent)
            overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
            finish()
        }

        binding.historySetupButton.setOnClickListener {
            historyStartActivity()
        }

        getUserName()
        getPicture()

    }

    private fun getUserName() {
        var username = userVM.username

        var navbarUserName: TextView = nav_view.getHeaderView(0).findViewById(R.id.user_name)

        username.observe(this) {
            navbarUserName?.text = it
            binding.textView4.text = it
        }

    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer((GravityCompat.START))
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                authVM.handleSignOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.history -> {
                historyStartActivity()
                true
            }

            R.id.language -> {
                authVM.setLanguage(baseContext)
                recreate()
                true
            }
        }
        return true
    }


    private fun historyStartActivity() {
        val hisIntent = Intent(this, HistoryActivity::class.java)
        startActivity(hisIntent)
        finish()
    }

    private fun getPicture() {
        picture = nav_view.getHeaderView(0).findViewById(R.id.imageView4)

        var image = userVM.image

        image.observe(this) { it ->
            if (it.isNullOrEmpty()) {
                picture.setContentPadding(40, 40, 40, 40)
                picture.setImageResource(R.drawable.ic_baseline_camera_alt_24)
                Timber.tag("Picture").v("Picture not found")

            } else {
                picture.scaleType = ImageView.ScaleType.FIT_XY
                image.observe(this) {
                    Picasso.get().load(it).into(picture)
                    Timber.tag("Picture").v("Picture attached")
                }
            }
        }


        picture.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Alert")
                .setMessage("Do you want to upload photo from gallery?")
                .setPositiveButton("Open gallery") { _, _ ->
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
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

    private fun uploadImage(imageUri: Uri) {
        val fileName = UUID.randomUUID().toString()
        val content =
            MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(imageUri))
        val file = "$fileName.$content"
        userVM.uploadImageToStorage(imageUri, file)
    }

}
package com.example.quizmaster.ui.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.quizmaster.R
import com.example.quizmaster.data.model.OpentdbAPI.Result
import java.io.Serializable


class QuestionPageFrameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_page_frame)

        val bundle = Bundle()
        val result = intent.extras?.get("results") as Serializable
        bundle.putSerializable("results", result)
        val frag = QuestionPageFragment()
        frag.arguments = bundle
        var fm = supportFragmentManager
        //changing the view is a trasaction
        var ft = fm.beginTransaction()
        ft.replace(R.id.game_fragment, frag)
        ft.commit()
    }
}
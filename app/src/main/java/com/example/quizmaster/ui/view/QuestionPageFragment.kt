package com.example.quizmaster.ui.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.quizmaster.R
import com.example.quizmaster.data.model.OpentdbAPI.Result
import com.example.quizmaster.databinding.FragmentQuestionPageBinding
import com.example.quizmaster.ui.viewmodel.QuizPageViewModel


class QuestionPageFragment : Fragment() {
    lateinit var questions: List<Result>
    private lateinit var binding: FragmentQuestionPageBinding
    private val vm = QuizPageViewModel()
    private val isSubmitted = false
    private var selected: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            questions = it.get("results") as List<Result>
            println("From Fragment $questions")
        }
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_page, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.qpViewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for buttons and textviews.
        binding.submitBtnQp.setOnClickListener { onSubmitQuestion() }
        binding.nextBtnQp.setOnClickListener { onNextQuestion() }
        binding.stopButton.setOnClickListener { stopQuiz()}
        binding.choice1Qp.setOnClickListener { onChoiceClick(1) }
        binding.choice2Qp.setOnClickListener { onChoiceClick(2) }
        binding.choice3Qp.setOnClickListener { onChoiceClick(3) }
        binding.choice4Qp.setOnClickListener { onChoiceClick(4) }
    }

    private fun onChoiceClick(choice: Int){
        if(!isSubmitted){
            when(choice){
                1 -> choiceColourChange(binding.choice1Qp)
                2 -> choiceColourChange(binding.choice2Qp)
                3 -> choiceColourChange(binding.choice3Qp)
                4 -> choiceColourChange(binding.choice4Qp)
            }
        }
    }

    private fun choiceColourChange(choice: TextView){
        val choices = listOf<TextView>(binding.choice1Qp, binding.choice2Qp, binding.choice3Qp, binding.choice4Qp)
        for(c in choices){
            c.setBackgroundColor(requireActivity().getColor(R.color.white))
        }

        choice.setBackgroundColor(requireActivity().getColor(R.color.choice_selection))
        selected = choice
    }


    private fun onSubmitQuestion(){
        if (selected == null){
            Toast.makeText(this.requireContext(), "No Answer Selected!", Toast.LENGTH_LONG).show()
        }
        else{

        }

    }

    private fun onNextQuestion(){
        if(!isSubmitted){
            Toast.makeText(this.requireContext(), "No Answer Submitted!", Toast.LENGTH_LONG).show()
        }
        else{

        }

    }

    private fun stopQuiz(){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setMessage("Are you sure you want to stop the quiz?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { _, _ ->
            var mainActivityIntent = Intent(this.activity, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
        builder.setNegativeButton("No", DialogInterface.OnClickListener
        { dialog, _ -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }




}
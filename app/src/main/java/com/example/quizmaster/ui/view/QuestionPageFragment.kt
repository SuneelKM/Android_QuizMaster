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
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.example.quizmaster.R
import com.example.quizmaster.data.model.OpentdbAPI.Result
import com.example.quizmaster.databinding.FragmentQuestionPageBinding
import com.example.quizmaster.ui.viewmodel.QuizPageViewModel
import java.io.Serializable


class QuestionPageFragment : Fragment() {
    lateinit var questions: List<Result>
    private lateinit var binding: FragmentQuestionPageBinding
    private val vm = QuizPageViewModel()
    private var isSubmitted = false
    private var isFinished = false
    private var selected: TextView? = null
    private lateinit var allChoices: List<TextView>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            questions = it.get("results") as List<Result>
            vm.setQuestions(questions, requireActivity())
        }
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_page, container, false)
        allChoices = listOf<TextView>(binding.choice1Qp, binding.choice2Qp, binding.choice3Qp, binding.choice4Qp)
        vm.setAllOptions(allChoices)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //userVM = ViewModelProvider(this).get(UserViewModel::class.java)

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
        vm.isFinished.observe(viewLifecycleOwner) {
            isFinished = it
        }
        if(!isSubmitted && !isFinished){
            when(choice){
                1 -> choiceColourChange(binding.choice1Qp)
                2 -> choiceColourChange(binding.choice2Qp)
                3 -> choiceColourChange(binding.choice3Qp)
                4 -> choiceColourChange(binding.choice4Qp)
            }
        }
    }

    private fun choiceColourChange(choice: TextView){
        for(c in allChoices){
            c.background = AppCompatResources.getDrawable(requireActivity(),R.drawable.default_option_border_bg)
        }
        choice.background = AppCompatResources.getDrawable(requireActivity(), R.drawable.selected_option_border_bg)
        selected = choice
    }


    private fun onSubmitQuestion(){
        if (selected == null && !isFinished){
            Toast.makeText(this.requireContext(), "No Answer Selected!", Toast.LENGTH_LONG).show()
        }
        else if(!isFinished){
            vm.setStopTime()
            isSubmitted = true
            vm.submitQuestion(selected!!)
        }


    }

    private fun onNextQuestion(){

        if(!isSubmitted && !isFinished){
            Toast.makeText(this.requireContext(), "No Answer Submitted!", Toast.LENGTH_LONG).show()
        }
        else {
            if (vm.quizFinished()){
                var resultIntent = Intent(this.activity, ResultPageActivity::class.java)
                resultIntent.putExtra("category", vm.qCategory)
                resultIntent.putExtra("level", vm.qLevel)
                resultIntent.putExtra("score", vm.score)
                resultIntent.putExtra("numberOfQuestions", vm.totalScore)
                resultIntent.putExtra("submittedQuestions", vm.submittedQuestions as Serializable)
                startActivity(resultIntent)
            }
            else{
                isSubmitted = false
                vm.nextQuestion()
            }

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

//    private fun errorResults(){
//        val builder = AlertDialog.Builder(this.requireContext())
//        builder.setMessage("ERROR: Please choose quiz settings again.")
//        builder.setCancelable(true)
//        builder.setPositiveButton("Okay") { _, _ ->
//            var chooseActivityIntent = Intent(this.activity, ChooseQuizActivity::class.java)
//            startActivity(chooseActivityIntent)
//        }
//
//        val alertDialog: AlertDialog = builder.create()
//        alertDialog.show()
//    }


}
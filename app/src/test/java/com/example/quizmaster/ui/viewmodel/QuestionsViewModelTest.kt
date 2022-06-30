package com.example.quizmaster.ui.viewmodel

import com.example.quizmaster.data.model.OpentdbAPI.Questions
import com.example.quizmaster.data.model.OpentdbAPI.Result
import com.example.quizmaster.data.repo.QuestionsRepository
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations



@RunWith(JUnit4::class)
class QuestionsViewModelTest {

    lateinit var vm: QuestionsViewModel

    @Mock
    lateinit var repo: QuestionsRepository


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        vm = QuestionsViewModel(repo)
    }

    @Test
    fun getQuestionsTest(){
        val fakeQuestions: Questions = Questions(listOf(
            Result("General Knowledge","Steven Erikson","medium",
                listOf("Ian Cameron Esslemont","George R. R. Martin","J. R. R. Tolkien"),
                "Who is the author of the series 'Malazan Book of the Fallen'?","multiple")
        ))


        Mockito.`when`(repo.getQuestions("1","9","medium", "multiple"))
            .thenReturn(Observable.just(fakeQuestions))

        val result = vm.getQuestions("1","General Knowledge","medium", "multiple")


        result.test()
            .assertResult(
                fakeQuestions
            )


    }


}



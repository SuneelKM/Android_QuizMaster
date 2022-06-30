package com.example.quizmaster.data.repo

import com.example.quizmaster.data.api.RetroApiInterface
import com.example.quizmaster.data.model.OpentdbAPI.Questions
import com.example.quizmaster.data.model.OpentdbAPI.Result
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class QuestionsRepositoryTest {

    lateinit var repo: QuestionsRepository


    @Mock
    lateinit var inter: RetroApiInterface

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = QuestionsRepository(inter)
    }

    @Test
    fun getQuestionsTest() {
        val fakeQuestions: Questions = Questions(listOf(
            Result("Entertainment","Steven Erikson","medium",
                listOf("Ian Cameron Esslemont","George R. R. Martin","J. R. R. Tolkien"),
                "Who is the author of the series 'Malazan Book of the Fallen'?","multiple")
        ))

        Mockito.`when`(inter.getQuestions("1","10","medium", "multiple"))
            .thenReturn(Observable.just(fakeQuestions))
        val result = repo.getQuestions("1","10","medium", "multiple")


        result.test()
            .assertResult(
                fakeQuestions
            )


    }
}

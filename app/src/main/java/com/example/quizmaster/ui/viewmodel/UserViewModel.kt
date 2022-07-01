package com.example.quizmaster.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizmaster.data.model.UserData.UserScores
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (var repo: UserRepository) : ViewModel() {

    var loading: MutableLiveData<Boolean> = repo.loading
    var userScoreList: MutableLiveData<List<UserScores>> = repo.userScoreList
    var username: MutableLiveData<String> = repo.username

    init {
        loading.postValue(false)
        getUserName()
    }

    private fun getUserName() {
        repo.getUserName()
    }

    fun getUserScores() {
        repo.getUserScores()
    }

}
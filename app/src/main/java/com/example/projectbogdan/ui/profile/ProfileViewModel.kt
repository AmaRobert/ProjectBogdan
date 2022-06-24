package com.example.projectbogdan.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectbogdan.data.LoginRepository
import com.example.projectbogdan.data.ShoppingRepository

class ProfileViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    fun logOut(){
        loginRepository.logout()
    }
}
package com.example.textify.ui.setting

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.textify.models.User
import com.example.textify.screenstate.ScreenState
import com.example.textify.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsViewModel : ViewModel() {
    private var usersLiveData: MutableLiveData<ScreenState<User?>> = MutableLiveData()
    val userLiveData: LiveData<ScreenState<User?>>
        get() = usersLiveData

    init {
        fetchUserDetails()
    }

    private fun fetchUserDetails() {
        val database = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USER).document(FirebaseAuth.getInstance().uid!!).addSnapshotListener  { document, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed", error)
                return@addSnapshotListener
            } else {
                val user = document?.toObject(User::class.java)
                usersLiveData.postValue(ScreenState.Success(user))
            }
        }
    }
}
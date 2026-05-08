package com.kreedaprerana.scout.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScoutViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<Boolean>(auth.currentUser != null)
    val authState: StateFlow<Boolean> = _authState

    fun logout() {
        auth.signOut()
        _authState.value = false
    }

    // Additional talent tracking logic would go here
}

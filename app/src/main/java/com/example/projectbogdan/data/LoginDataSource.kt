package com.example.projectbogdan.data

import com.example.projectbogdan.data.model.LoggedInUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    val firebaseAuth = FirebaseAuth.getInstance()
    suspend fun login(email: String, password: String): Result<AuthResult> {
        return try {
            val data = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
            Result.Success(data)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun register(email: String, password: String): Result<AuthResult> {
        return try {
            val data = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            Result.Success(data)
        } catch (e: Throwable) {
            Result.Error(IOException("Error trying to register.", e))
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}
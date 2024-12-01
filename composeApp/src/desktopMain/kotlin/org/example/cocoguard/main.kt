package org.example.cocoguard

import com.google.firebase.FirebasePlatform
import android.app.Application
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest

fun main() = application {

    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {

        val storage = mutableMapOf<String, String>()
        override fun clear(key: String) {
            storage.remove(key)
        }

        override fun log(msg: String) = println(msg)

        override fun retrieve(key: String) = storage[key]

        override fun store(key: String, value: String) = storage.set(key, value)
    })

    val options = FirebaseOptions(
        projectId = "coco-guard",
        applicationId = "1:858696594098:web:a8d46b5b27b74d8beccfab",
        apiKey = "AIzaSyBUy9vq40be_lifW0EbqT8r76Pa_OVUhEY"
    )

    Firebase.initialize(Application(), options)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Firestore",
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            width = 1280.dp,
            height = 720.dp
        )
    ) {

            App()

    }
}
//    ) {
//        val repository = FirestoreRepository()
//        val authService = AuthService(repository)
//        val coroutineScope = CoroutineScope(Dispatchers.IO)
//        var isLoading by remember { mutableStateOf(false) } // Track loading state
//        var message by remember { mutableStateOf("") } // Track success/error messages
//
//        Column(
//            modifier = Modifier.fillMaxSize().padding(16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            SignInScreen(
//                authService = authService,
//                isLoading = isLoading,
//                message = message,
//                onSignIn = { email, password ->
//                    if (email.isNotBlank() && password.isNotBlank()) {
//                        coroutineScope.launch {
//                            try {
//                                isLoading = true // Show loading
//                                message = ""
//                                val isAuthenticated = authService.signIn(email, password)
//                                withContext(Dispatchers.Main) {
//                                    isLoading = false // Hide loading
//                                    message = if (isAuthenticated) {
//                                        "Sign-In Successful!"
//                                    } else {
//                                        "Invalid Email or Password"
//                                    }
//                                }
//                            } catch (e: Exception) {
//                                withContext(Dispatchers.Main) {
//                                    isLoading = false // Hide loading
//                                    message = "Error: ${e.message}" // Error message
//                                }
//                            }
//                        }
//                    } else {
//                        message = "Please fill in both fields." // Validation message
//                    }
//                }
//            )
//            SaveUserScreen(
//                isLoading = isLoading,
//                message = message,
//                onSave = { email, password ->
//                    if (email.isNotBlank() && password.isNotBlank()) {
//                        coroutineScope.launch {
//                            try {
//                                isLoading = true // Show loading
//                                message = ""
//                                val encryptedPassword = encryptPassword(password)
//                                val result = repository.addUser(User(email, encryptedPassword))
//                                withContext(Dispatchers.Main) {
//                                    isLoading = false // Hide loading
//                                    if (result.isSuccess) {
//                                        message = "User added successfully!" // Success message
//                                    } else {
//                                        message = "Error: ${result.exceptionOrNull()?.message}" // Error message
//                                    }
//                                }
//                            } catch (e: Exception) {
//                                withContext(Dispatchers.Main) {
//                                    isLoading = false // Hide loading
//                                    message = "Error: ${e.message}" // Error message
//                                }
//                            }
//                        }
//                    } else {
//                        message = "Please fill in both fields." // Validation message
//                    }
//                }
//            )
//        }
//    }




fun encryptPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(password.toByteArray())
    return hashedBytes.joinToString("") { "%02x".format(it) }
}

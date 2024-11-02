//package org.example.cocoguard.utils
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//object AuthUtil {
//    private val auth: FirebaseAuth = FirebaseAuth.auth
//
//    fun signIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                auth.signInWithEmailAndPassword(email, password)
//                onResult(true, null)
//            } catch (e: Exception) {
//                onResult(false, e.message)
//            }
//        }
//    }
//
//    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                auth.createUserWithEmailAndPassword(email, password)
//                onResult(true, null)
//            } catch (e: Exception) {
//                onResult(false, e.message)
//            }
//        }
//    }
//
//    fun getCurrentUser(): FirebaseUser? = auth.currentUser
//}

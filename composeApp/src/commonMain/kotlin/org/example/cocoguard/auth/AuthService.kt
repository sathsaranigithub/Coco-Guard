package org.example.cocoguard.auth


import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow

class AuthServiceImpl(
    val auth: FirebaseAuth,
    val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
): AuthService {
    val currentUserId: String
        get() = auth.currentUser?.uid.toString()

    val isAuthenticated: Boolean
        get() = auth.currentUser != null && auth.currentUser?.isAnonymous == false
    val currentUser: Flow<User> = auth.authStateChanged.map { it?.let {User(it.uid, it.isAnonymous)} ?: Uer()}

    override suspend fun authenticate(email: String, password: String)
    override suspend fun createUser(email: String, password: String)
    override suspend fun signOut(){
        
    }
}
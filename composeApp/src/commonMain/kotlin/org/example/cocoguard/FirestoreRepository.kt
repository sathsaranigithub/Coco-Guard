package org.example.cocoguard

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()


    suspend fun addUser(user: User): Result<String> {
        return try {

            val existingUser = db.collection("users")
                .whereEqualTo("email", user.email)
                .get()
                .await()

            if (!existingUser.isEmpty) {

                Result.failure(Exception("User with this email is already registered."))
            } else {

                val document = db.collection("users").add(user).await()
                Result.success(document.id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getUserByEmail(email: String): User? {
        val snapshot = db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .await()
        return if (!snapshot.isEmpty) {
            snapshot.documents.first().toObject(User::class.java)
        } else null
    }
}

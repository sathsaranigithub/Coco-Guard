package org.example.cocoguard

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()


    suspend fun addUser(user: User): Result<String> {
        return try {
            // Check if the email is already registered
            val existingEmail = db.collection("users")
                .whereEqualTo("email", user.email)
                .get()
                .await()

            if (!existingEmail.isEmpty) {
                Result.failure(Exception("This email is already registered."))
            } else {
                // Check if the username is already taken
                val existingUname = db.collection("users")
                    .whereEqualTo("uname", user.uname)
                    .get()
                    .await()

                if (!existingUname.isEmpty) {
                    Result.failure(Exception("This username is already taken."))
                } else {
                    // Add the new user
                    val document = db.collection("users").add(user).await()
                    Result.success(document.id)
                }
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

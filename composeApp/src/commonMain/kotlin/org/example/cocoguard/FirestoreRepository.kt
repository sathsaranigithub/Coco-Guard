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
    suspend fun addDemand(userEmail: String, demand: Demand): Result<String> {
        return try {
            // Reference the user's document using email as the document ID in the demand_forecasting collection
            val documentRef = db.collection("demand_forecasting")
                .document(userEmail) // Use the user's email as the document ID

            // Save the demand result under the 'demand' subcollection of the user's document
            val document = documentRef
                .collection("demand")
                .add(demand) // Add the demand document
                .await()

            Result.success(document.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun addYield(userEmail: String, yield: Yield): Result<String> {
        return try {
            // Reference the user's document using email as the document ID in the demand_forecasting collection
            val documentRef = db.collection("yield_recode")
                .document(userEmail) // Use the user's email as the document ID

            // Save the demand result under the 'demand' subcollection of the user's document
            val document = documentRef
                .collection("yield")
                .add(yield) // Add the demand document
                .await()

            Result.success(document.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Retrieve Demands for a User
    suspend fun getDemands(userEmail: String): Result<List<Demand>> {
        return try {
            val snapshot = db.collection("demand_forecasting")
                .document(userEmail)
                .collection("demand")
                .get()
                .await()

            if (!snapshot.isEmpty) {
                val demands = snapshot.documents.map { it.toObject(Demand::class.java)!! }
                Result.success(demands)
            } else {
                Result.failure(Exception("No demands found for the given user."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getYield(userEmail: String): Result<List<Yield>> {
        return try {
            val snapshot = db.collection("yield_recode")
                .document(userEmail)
                .collection("yield")
                .get()
                .await()

            if (!snapshot.isEmpty) {
                val yields = snapshot.documents.map { it.toObject(Yield::class.java)!! }
                Result.success(yields)
            } else {
                Result.failure(Exception("No demands found for the given user."))
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

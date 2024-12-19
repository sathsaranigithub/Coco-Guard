package org.example.cocoguard.controller

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.example.cocoguard.model.Demand
import org.example.cocoguard.model.Treatment
import org.example.cocoguard.model.User
import org.example.cocoguard.model.Yield
import org.example.cocoguard.util.encryptPassword

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
                .document(userEmail)
            val document = documentRef
                .collection("demand")
                .add(demand)
                .await()
            Result.success(document.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun addYield(userEmail: String, yield: Yield): Result<String> {
        return try {
            // Reference the user's document using email as the document ID in the yield collection
            val documentRef = db.collection("yield_recode")
                .document(userEmail)
            val document = documentRef
                .collection("yield")
                .add(yield)
                .await()
            Result.success(document.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun addTreatment(userEmail: String, treatment: Treatment): Result<String> {
        return try {
            // Reference the document in the treatment_recode collection
            val documentRef = db.collection("treatment_recode")
                .document(userEmail)
            documentRef.set(treatment).await()
            Result.success(documentRef.id)
        } catch (e: Exception) {
            println("FirestoreError: ${e.localizedMessage}")
            Result.failure(e)
        }
    }
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
    suspend fun getTreatment(userEmail: String): Result<String> {
        return try {
            val documentRef = db.collection("treatment_recode").document(userEmail)
            val snapshot = documentRef.get().await()
            if (snapshot.exists()) {
                val treatment = snapshot.toObject(Treatment::class.java)
                Result.success(treatment?.text ?: "No treatment found.")
            } else {
                Result.failure(Exception("No document found for email: $userEmail"))
            }
        } catch (e: Exception) {
            println("FirestoreError: ${e.localizedMessage}")
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
    suspend fun signIn(email: String, password: String): Boolean {
        val user = getUserByEmail(email)
        val hashedInputPassword = encryptPassword(password)
        return user?.password == hashedInputPassword
    }
}

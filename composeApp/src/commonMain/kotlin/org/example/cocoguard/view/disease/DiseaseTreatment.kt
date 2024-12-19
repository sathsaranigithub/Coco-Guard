package org.example.cocoguard.view.disease

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.first
import org.example.cocoguard.controller.FirestoreRepository
import org.example.cocoguard.component.HeaderCardOne
import org.jetbrains.compose.resources.painterResource

@Composable
fun DiseaseTreatmentScreen(navController: NavController, userEmail: String) {
    val repository = remember { FirestoreRepository() }
    var treatmentText by remember { mutableStateOf("Fetching treatment data...") }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(userEmail) {
        try {
            loading = true
            val result = repository.getTreatment(userEmail)
            treatmentText = result.getOrElse { "First you need to detect disease and then you can create treatment plan for disease: ${it.localizedMessage}" } // Set text
        } catch (e: Exception) {
            errorMessage = e.message ?: "Unexpected error occurred"
        } finally {
            loading = false
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderCardOne(
            title = "Coconut Trees Diseases\n",
            subtitle = "Planer",
            sentence = "Ensure the health of your coconut plantation with regular monitoring and AI-assisted insights",
            navController = navController,
            painter = painterResource(Res.drawable.first),
            onBackClick = {
                navController.navigate("home")
            }
        )
        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp), shape = RoundedCornerShape(10.dp), elevation = 4.dp) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth().verticalScroll(scrollState)
            ) {
                Text(
                    text = when {
                        loading -> "Loading..."
                        errorMessage.isNotEmpty() -> errorMessage
                        else -> treatmentText
                    },
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}

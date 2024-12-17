package org.example.cocoguard.view.disease

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.gallery
import coco_guard.composeapp.generated.resources.second
import coco_guard.composeapp.generated.resources.uploadimage
import org.example.cocoguard.controller.FirestoreRepository
import org.example.cocoguard.component.DropdownQuestion
import org.example.cocoguard.component.HeaderCardTwo
import org.example.cocoguard.controller.Diseases
import org.example.cocoguard.util.parseDiseaseFromResponse

@Serializable
data class DetectionResult(val `class`: String)

@Composable
fun ImageUploadScreen(navController: NavController, email: String) {
    val scope = rememberCoroutineScope()
    val context = com.mohamedrejeb.calf.core.LocalPlatformContext.current
    val byteArray = remember { mutableStateOf(ByteArray(0)) }
    val platformSpecificFilePath = remember { mutableStateOf("") }
    val responseText = remember { mutableStateOf<String?>(null) }
    val isLoading = remember { mutableStateOf(false) }
    val detectedDisease = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val selectedDuration = remember { mutableStateOf("") }
    val repository = FirestoreRepository()
    val diseases = Diseases()
    val pickerLauncher = rememberFilePickerLauncher(
        onResult = { files ->
            scope.launch{
                files.firstOrNull()?.let {
                    byteArray.value = it.readByteArray(context)
                    platformSpecificFilePath.value = it.getPath(context)?:""
                }
            }
        },
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single
    )

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderCardTwo(
            navController = navController,
            title = "Coconut Trees Diseases\n",
            subtitle = "Identification",
            description = "Upload an image to detect coconut tree diseases and receive AI-driven treatment suggestions",
            buttonText = "Treatment Plan",
            buttonAction = { navController.navigate("diseaseTreatmentScreen/$email") },
            painter = painterResource(Res.drawable.second),
            isPressed = isPressed
        )
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Card(modifier = Modifier.size(180.dp).clip(RoundedCornerShape(16.dp)), backgroundColor = Color.Gray, elevation = 4.dp) {
                // Check if byteArray has any data, if not show the static image
                if (byteArray.value.isNotEmpty()) {
                    AsyncImage(model = byteArray.value, modifier = Modifier.size(170.dp), contentScale = ContentScale.Crop, contentDescription = null)
                } else {
                    Image(painter = painterResource(Res.drawable.uploadimage), contentDescription = "Upload Image Placeholder", modifier = Modifier.fillMaxSize())
                }
            }
            Column(modifier = Modifier.padding(start = 16.dp).align(Alignment.CenterVertically), verticalArrangement = Arrangement.Center) {
                Text(text = "Upload Image", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "You can select an existing photo to upload.", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(Res.drawable.gallery), contentDescription = "Gallery", modifier = Modifier.size(40.dp).clickable {
                            pickerLauncher.launch()
                        }
                    )
                }
                Button(
                    onClick = {
                        if (byteArray.value.isNotEmpty()) {
                            scope.launch {
                                isLoading.value = true
                                val response = diseases.uploadImage(byteArray.value)
                                responseText.value = response
                                detectedDisease.value = parseDiseaseFromResponse(response)
                                isLoading.value = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    modifier = Modifier
                        .width(300.dp)
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Upload", color = Color.White, fontWeight = FontWeight.Bold)
                }
                if (isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(2.dp),
                        color = Color(0xFF4CAF50))
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp).weight(1f)) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (detectedDisease.value.isNotEmpty()) {
                            if(detectedDisease.value != "Unknown disease" && detectedDisease.value != "Unknown Class"){
                                Text(text = "Detected disease: ${detectedDisease.value}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Description", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(
                                    text = when (detectedDisease.value) {
                                        "bud root dropping" -> "Bud rot dropping is a devastating disease that affects coconut palms, caused by fungal pathogens like Phytophthora palmivora or bacterial infections. It begins with the rotting of the growing tip or bud, leading to the palm's eventual death if untreated. The disease often occurs in humid, wet climates or areas with poor drainage. It can spread through contaminated soil, water, or plant debris, making early identification and management crucial to prevent widespread damage."
                                        "bud rot" -> "Bud rot is a severe disease that affects the growing point or bud of coconut trees, primarily caused by fungal pathogens like Phytophthora palmivora or bacterial infections. It is prevalent in humid and wet conditions, especially during prolonged rainy periods. The disease begins with discoloration and softening of the young bud tissue, eventually leading to its decay. If untreated, bud rot can cause the death of the palm as the bud is the tree's only growing point. The disease spreads through soil, water, and infected plant debris, necessitating prompt management. Preventive measures and timely treatment are crucial to protect affected trees and prevent the spread to healthy ones."
                                        "gray leaf spot" -> "Gray leaf spot is a fungal disease that affects coconut trees, caused by Pestalotiopsis palmarum. The disease primarily targets the leaves, creating distinctive grayish spots with dark brown margins. Over time, these spots expand, leading to leaf blight and reduced photosynthetic activity, which can weaken the tree and reduce nut yield. Gray leaf spot thrives in humid, wet environments and spreads through wind, rain, and infected plant debris. Severe infections can result in premature defoliation, exposing the tree to further stress and secondary infections. Prompt treatment and proper management practices are vital to prevent its spread and safeguard the health of the coconut palms."
                                        "leaf rot" -> "Leaf rot is a common disease in coconut palms caused by fungal pathogens like Colletotrichum gloeosporioides or Exserohilum rostratum. It primarily affects the leaflets, causing discoloration, necrosis, and eventual rotting of the tissue. The disease thrives in humid and wet conditions, especially during the rainy season or in areas with poor drainage. Early symptoms include small yellow or brown spots, which gradually enlarge and lead to severe damage to the leaves. If left untreated, leaf rot can weaken the tree, reduce its photosynthetic efficiency, and result in lower yields. Prompt identification and treatment are crucial to minimize the spread and damage."
                                        "stembleeding" -> "Stem bleeding is a serious disease in coconut trees caused by the fungus Thielaviopsis paradoxa. It is characterized by the exudation of a dark, sticky liquid from cracks or lesions on the stem. The disease typically begins at the base of the trunk and gradually spreads upward. Prolonged infection weakens the tree, causing reduced nut yield and, in severe cases, death. It is commonly associated with waterlogging, injuries to the stem, and poor maintenance practices. Stem bleeding affects the vascular tissues, disrupting nutrient and water flow. Early detection and proper management are crucial to prevent its spread and save the affected palms."
                                        else -> "Description of the diseases"
                                    },
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Symptoms", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(
                                    text =  when (detectedDisease.value) {
                                        "bud root dropping" -> "Discoloration and rotting of the central bud.\n" + "Yellowing of young leaves.\n" + "Drooping or falling fronds.\n" + "Foul odor from the infected bud area.\n" + "Formation of black or brown lesions on the bud."
                                        "bud rot" ->"Yellowing of young leaves.\n" + "Discoloration and softening of the bud.\n" + "Foul odor from the bud area.\n" + "Stunted growth of new leaves.\n" + "Premature nut shedding."
                                        "gray leaf spot" -> "Small grayish spots with dark brown edges on leaves.\n" + "Expansion of spots into larger blotches.\n" + "Premature yellowing of leaves.\n" + "Blighting and drying of affected leaves.\n" + "Reduced nut production."
                                        "leaf rot" ->"Yellowish spots on young leaflets.\n" + "Dark brown lesions on affected areas.\n" + "Premature drying and falling of leaflets.\n" + "Rotting of the leaf tissue.\n" + "Fungal growth on the leaf surface."
                                        "stembleeding" -> "Dark brown or black exudation from trunk cracks.\n" + "Sticky liquid with a foul odor.\n" + "Formation of longitudinal cracks on the stem.\n" + "Discoloration and rotting of underlying tissues.\n" + "Yellowing and wilting of leaves."
                                        else -> "Symptoms of the diseases"
                                    }, fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Treatment", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(
                                    text = when (detectedDisease.value) {
                                        "bud root dropping" -> "1. Remove and destroy infected plant material.\n" + "2. Improve drainage around the palm.\n" + "3. Apply copper-based fungicides to the bud.\n" + "4. Spray Bordeaux mixture (1%) on affected and surrounding trees.\n" + "5. Use Trichoderma-based biofungicides for soil treatment.\n" + "6. Ensure proper spacing for airflow to reduce humidity.\n" + "7. Avoid water stagnation near the tree.\n" + "8. Use resistant coconut varieties, if available.\n" + "9. Maintain regular fertilization with balanced nutrients.\n" + "10. Conduct regular tree health inspections to detect symptoms early."
                                        "bud rot" -> "1. Remove and burn infected plant parts.\n" + "2. Apply Bordeaux mixture (1%) or copper oxychloride to the affected bud.\n" + "3. Use Trichoderma-based biofungicides in the soil around the palm.\n" + "4. Improve drainage to prevent water stagnation.\n" + "5. Spray systemic fungicides like Metalaxyl.\n" + "6. Ensure adequate spacing for ventilation.\n" + "7. Avoid injury to the palm's trunk and bud.\n" + "8. Apply balanced fertilizers to strengthen plant health.\n" + "9. Treat the soil with lime to reduce pathogen proliferation.\n" + "10 Use resistant or tolerant coconut varieties."
                                        "gray leaf spot" ->"1. Prune and destroy infected leaves.\n" + "2. Spray copper-based fungicides on affected trees.\n" + "3. Apply Bordeaux mixture (1%) to leaves.\n" + "4. Use systemic fungicides like Mancozeb or Propiconazole.\n" + "5. Improve air circulation by maintaining proper tree spacing.\n" + "6. Remove and burn plant debris from the area.\n" + "7. Avoid overwatering and ensure proper drainage.\n" + "8. Enhance soil health with organic manure.\n" + "9. Apply balanced fertilizers, particularly potassium and magnesium.\n" + "10. Monitor regularly and take preventive measures during wet seasons."
                                        "leaf rot" -> "1. Remove and destroy infected leaves to prevent spread.\n" + "2. Apply Bordeaux mixture (1%) on affected areas.\n" + "3. Use systemic fungicides like Carbendazim or Mancozeb.\n" + "4. Ensure proper drainage around the tree.\n" + "5. Maintain balanced soil nutrients, including potassium and magnesium.\n" + "6. Spray neem oil or biofungicides as a preventive measure.\n" + "7. Improve air circulation by trimming nearby vegetation.\n" + "8. Regularly monitor trees for early symptoms.\n" + "9. Avoid injury to leaves and trunk during maintenance.\n" + "10. Provide organic compost to strengthen tree immunity."
                                        "stembleeding" -> "1. Scrape and clean the affected area thoroughly.\n" + "2. Apply Bordeaux paste (10%) on the cleaned wounds.\n" + "3. Use systemic fungicides like Carbendazim.\n" + "4. Ensure proper drainage to avoid waterlogging.\n" + "5. Remove and burn severely infected trees to prevent spread.\n" + "6. Apply organic manure to improve soil health.\n" + "7. Treat soil around the tree with lime to reduce fungal activity.\n" + "8. Avoid injuries to the stem during maintenance.\n" + "9. Maintain proper spacing to reduce humidity around the tree.\n" + "10. Regularly monitor and promptly treat early signs of the disease."
                                        else -> "Treatment options for the diseases"
                                    },   fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(text = "Create treatment plan for  ${detectedDisease.value}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Spacer(modifier = Modifier.height(8.dp))
                                DropdownQuestion(
                                    question = "Select your preferred durations for treatment",
                                    options = listOf("Regular", "3 months", "One year"),
                                    hint = "Select answer",
                                    onSelect = { selectedDuration.value = it }
                                )
                                Button(
                                    onClick = {
                                        if (detectedDisease.value.isNotEmpty() && selectedDuration.value.isNotEmpty()) {
                                            scope.launch {
                                                isLoading.value = true
                                                val queryText = "My coconut tree has ${detectedDisease.value} disease. Give me a ${selectedDuration.value} treatment plan."
                                                val result = diseases.callGeminiAPI(queryText,email,repository,navController)
                                                responseText.value = result
                                                isLoading.value = false
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                                    modifier = Modifier.width(160.dp)
                                ) {
                                    Text(text = "Create plan", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }
                                if (isLoading.value) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.padding(2.dp),
                                        color = Color(0xFF4CAF50)
                                    )
                                }
                            }
                            else{
                                Text(text = "Invalid image", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(
                                    text = "This image can't be identified by the AI model. Please upload a clear and valid image and try again.",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


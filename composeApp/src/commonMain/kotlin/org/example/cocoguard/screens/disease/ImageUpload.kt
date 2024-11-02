package org.example.cocoguard.screens.disease


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.cam
import cocoguard.composeapp.generated.resources.cardone
import cocoguard.composeapp.generated.resources.cardthree
import cocoguard.composeapp.generated.resources.cardtwo
import cocoguard.composeapp.generated.resources.gallery
import cocoguard.composeapp.generated.resources.homemain
import cocoguard.composeapp.generated.resources.logout
import cocoguard.composeapp.generated.resources.uploadimage
import coil3.Uri
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import org.example.cocoguard.screens.ImageCard
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageUploadScreen() {
    val scope = rememberCoroutineScope()
    val context = com.mohamedrejeb.calf.core.LocalPlatformContext.current
    val byteArray = remember { mutableStateOf(ByteArray(0)) }


    // Use a lambda to configure the file picker launcher
    val pickerLauncher = rememberFilePickerLauncher(
        onResult = { files ->
            scope.launch{
                files.firstOrNull()?.let {
                    byteArray.value = it.readByteArray(context)
                }
            }

        },
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single
    )

    Column(modifier = Modifier.fillMaxSize()) {

        // Existing top card layout
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(0.dp),
            shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp),
            backgroundColor = Color(0xFF024A1A),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Title
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Coconut Trees Diseases")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                            append(" Identification")
                        }
                    },
                    fontSize = 30.sp,
                    fontFamily = workSansBoldFontFamily(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upload an image to detect coconut tree diseases and receive AI-driven treatment suggestions.",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = workSansBoldFontFamily(),
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(Res.drawable.homemain),
                        contentDescription = "Main",
                        modifier = Modifier
                            .fillMaxWidth(1 / 3f)
                            .aspectRatio(154f / 114f)
                            .padding(start = 0.dp)
                    )
                }
            }
        }

        // Row layout with image card and upload button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Image card
            Card(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                backgroundColor = Color.Gray,
                elevation = 4.dp
            ) {
                // Check if byteArray has any data, if not show the static image
                if (byteArray.value.isNotEmpty()) {
                    // If the byteArray has data, display it as an image
                    AsyncImage(
                        model = byteArray.value,
                        modifier = Modifier.size(170.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                } else {
                    // If no image has been uploaded, show the static image
                    Image(
                        painter = painterResource(Res.drawable.uploadimage), // Replace with your actual resource
                        contentDescription = "Upload Image Placeholder",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Text area with upload button
            Column(
               modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Upload Image",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "You can use your camera or select an existing photo to upload.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.cam),
                        contentDescription = "Camera",
                        modifier = Modifier.size(40.dp)
                    )
                    Image(
                        painter = painterResource(Res.drawable.gallery),
                        contentDescription = "Gallery",
                        // Add click listener to select image from gallery
                        modifier = Modifier.size(40.dp).clickable {
                            pickerLauncher.launch()
                        }
                    )
                }

                Button(
                    onClick = {
                        // Upload action
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    modifier = Modifier
                        .width(300.dp)
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Upload", color = Color.White)
                }
            }
        }

        // Scrollable Card View below the existing row
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f) // Ensures it fills the remaining space in Column
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Detected disease: Lethal Yellowing",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Description",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Lethal Yellowing is a devastating disease that affects coconut trees, causing premature fruit drop, yellowing of leaves, and, eventually, the death of the tree. The disease is caused by a phytoplasma (a type of bacteria without a cell wall) that affects the vascular system of the tree, impeding the flow of water and nutrients.",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Symptoms",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Premature fruit drop from all maturity stages\n" +
                                    "Yellowing of older leaves, eventually spreading to the entire canopy\n" +
                                    "Deterioration of flower spikes\n" +
                                    "Collapse of the crown, leading to the tree's death within a few months if untreated",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Treatment",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Antibiotic Treatment: Injecting the trunk with oxytetracycline (OTC) antibiotic can suppress the phytoplasma in the early stages. However, this is a temporary solution and must be repeated every 4–5 months.\n" +
                                    "Resistant Varieties: Planting resistant varieties like the Malayan Dwarf or Maypan hybrids has proven to be one of the most effective long-term strategies.\n" +
                                    "Tree Removal: If a tree is severely affected, it should be removed and destroyed to prevent the spread of the disease to healthy palms nearby.",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

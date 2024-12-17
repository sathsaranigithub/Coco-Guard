import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.register
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.cocoguard.controller.FirestoreRepository
import org.example.cocoguard.model.User
import org.example.cocoguard.component.AppFooter
import org.example.cocoguard.component.HeaderText
import org.example.cocoguard.component.InputField
import org.example.cocoguard.component.LoadingAndErrorState
import org.example.cocoguard.component.SideImage
import org.example.cocoguard.ui.theme.workSansFontFamily
import org.example.cocoguard.util.encryptPassword
import org.jetbrains.compose.resources.painterResource

@Composable
fun RegisterPage(onNavigateToHome: () -> Unit, onNavigateToLogin: () -> Unit, onEmailLoggedIn: (String) -> Unit) {
    var uname by remember { mutableStateOf(("")) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(("")) }
    var loading by remember { mutableStateOf(false) } // Loading state
    var errorMessage by remember { mutableStateOf<String?>(null) } // Error state
    val coroutineScope = rememberCoroutineScope()
    val repository = FirestoreRepository()

    Row(modifier = Modifier.fillMaxSize().background(Color.White)){
        SideImage(painter = painterResource(Res.drawable.register), contentDescription = "Coconut background")
        Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            HeaderText(
                title = "Let's Create your account",
                subtitle = "Join CocoGuard and protect your farm and increase yield and market with AI"
            )
            Card(shape = RoundedCornerShape(10.dp), elevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InputField(value = uname, onValueChange = { uname = it }, label = "User name")
                    Spacer(modifier = Modifier.height(10.dp))
                    InputField(value = email, onValueChange = { email = it }, label = "E-mail")
                    Spacer(modifier = Modifier.height(10.dp))
                    InputField(value = password, onValueChange = { password = it }, label = "Password")
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            loading = true
                            errorMessage = null
                            if (uname.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                                coroutineScope.launch {
                                    try {
                                        val encryptedPassword = encryptPassword(password)
                                        val result = repository.addUser(
                                            User(
                                                uname,
                                                email,
                                                encryptedPassword
                                            )
                                        )
                                        withContext(Dispatchers.Main) {
                                            loading = false // Hide loading
                                            if (result.isSuccess) {
                                                onNavigateToHome()
                                                onEmailLoggedIn(email)
                                            } else {
                                                errorMessage =
                                                    "Error: ${result.exceptionOrNull()?.message}"
                                            }
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            loading = false
                                            errorMessage = "Error: ${e.message}"
                                        }
                                    }
                                }
                            } else {
                                loading = false
                                errorMessage = "All fields are required."
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).padding(vertical = 10.dp)
                    ) {
                        Text(text = "Register", color = Color.White, style = TextStyle(fontSize = 18.sp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LoadingAndErrorState(isLoading = loading, errorMessage = errorMessage)
            AppFooter()
            TextButton(onClick = onNavigateToLogin) {
                Text(text = "Already have an account? Login", style = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = workSansFontFamily(), textAlign = TextAlign.Center),)
            }
        }
      }
    }


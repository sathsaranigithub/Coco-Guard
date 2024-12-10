import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.logo
import coco_guard.composeapp.generated.resources.register
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.cocoguard.FirestoreRepository
import org.example.cocoguard.User
import org.example.cocoguard.ui.theme.lemonadaFontFamily
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.example.cocoguard.ui.theme.workSansFontFamily
import org.example.cocoguard.ui.theme.workSansSemiBoldFontFamily
import org.jetbrains.compose.resources.painterResource
import java.security.MessageDigest
@Composable
fun RegisterPage(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onEmailLoggedIn: (String) -> Unit
) {
    var uname by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var loading by remember { mutableStateOf(false) } // Loading state
    var errorMessage by remember { mutableStateOf<String?>(null) } // Error state
    val coroutineScope = rememberCoroutineScope()
    val repository = FirestoreRepository()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(Res.drawable.register),
            contentDescription = "Coconut background",
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.3f),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Let's Create your account",
                style = TextStyle(
                    fontFamily = workSansBoldFontFamily(),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Join CocoGuard and protect your farm with AI",
                style = TextStyle(
                    fontFamily = workSansSemiBoldFontFamily(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = uname,
                        onValueChange = { uname = it },
                        label = { Text("User name") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = Color(0xFF4CAF50)
                        )
                    } else {
                        Button(
                            onClick = {
                                loading = true
                                errorMessage = null
                                if (uname.text.isNotBlank() && email.isNotBlank() && password.text.isNotBlank()) {
                                    coroutineScope.launch {
                                        try {
                                            val encryptedPassword = encryptPassword(password.text)
                                            val result = repository.addUser(
                                                User(
                                                    uname.text,
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = "Register",
                                color = Color.White,
                                style = TextStyle(fontSize = 18.sp)
                            )
                        }
                    }
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            style = TextStyle(fontSize = 14.sp),
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 8.dp)
                )
            Text(
                text = "Coco Guard",
                style = TextStyle(
                    fontFamily = lemonadaFontFamily(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            )
            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                Text(
                    text = "Already have an account? Login",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = workSansFontFamily(),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
        }
    }
}
fun encryptPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(password.toByteArray())
    return hashedBytes.joinToString("") { "%02x".format(it) }
}

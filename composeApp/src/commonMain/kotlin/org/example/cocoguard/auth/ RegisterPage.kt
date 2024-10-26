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
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.logo
import cocoguard.composeapp.generated.resources.register
import org.example.cocoguard.ui.theme.lemonadaFontFamily
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.example.cocoguard.ui.theme.workSansFontFamily
import org.example.cocoguard.ui.theme.workSansSemiBoldFontFamily
import org.jetbrains.compose.resources.painterResource

@Composable
fun RegisterPage(onNavigateToLogin: () -> Unit) {
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
                modifier = Modifier.padding(bottom = 40.dp).align(Alignment.CenterHorizontally)
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

            TextField(
                value = "",
                onValueChange = { /* Handle email input */ },
                label = { Text("E-mail") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = "",
                onValueChange = { /* Handle password input */ },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = "",
                onValueChange = { /* Handle confirm password input */ },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* Handle registration action */ },
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
                onClick = onNavigateToLogin, // Navigate back to LoginPage
                modifier = Modifier.padding(bottom = 30.dp)
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

package org.example.cocoguard.screens.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.cocoguard.ui.theme.workSansBoldFontFamily

@Composable
fun ResultDialog(
    title: String,
    resultText: String,
    detailedText: String,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                title,
                fontFamily = workSansBoldFontFamily(),
                fontSize = 25.sp,
                color = Color(0xFF024A1A)
            )
        },
        text = {
            Column {
                Text(
                    text = detailedText,
                    fontFamily = workSansBoldFontFamily(),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = resultText,
                    fontFamily = workSansBoldFontFamily(),
                    fontSize = 20.sp,
                    color = Color(0xFF024A1A)
                )
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B)),
                onClick = {
                    onSave()
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B))
            ) {
                Text("Close")
            }
        }
    )
}

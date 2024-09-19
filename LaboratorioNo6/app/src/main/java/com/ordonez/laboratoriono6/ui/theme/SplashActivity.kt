package com.ordonez.laboratoriono6.ui.theme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ordonez.laboratoriono6.MainActivity
import com.ordonez.laboratoriono6.ui.theme.LaboratorioNo6Theme
import androidx.compose.ui.tooling.preview.Preview


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaboratorioNo6Theme {
                SplashScreen(
                    onSplashScreenClick = {

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}


@Composable
fun SplashScreen(onSplashScreenClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onSplashScreenClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Emoji de chef
            Text(
                text = "👨‍🍳",
                fontSize = 100.sp, // Tamaño grande para el emoji
                modifier = Modifier.padding(16.dp)
            )

            // Texto "Chef Recipes" en grande
            Text(
                text = "Chef Recipes",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 48.sp,        // Tamaño del texto grande
                    fontWeight = FontWeight.Bold,
                    color = Color.Black     // Color blanco para el texto
                ),
                modifier = Modifier.padding(top = 16.dp) // Ajustar espaciado entre emoji y texto
            )
        }
    }
}

// Función para el Preview
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    LaboratorioNo6Theme {
        // Muestra solo la interfaz, sin lógica de navegación
        SplashScreen(onSplashScreenClick = {})
    }
}

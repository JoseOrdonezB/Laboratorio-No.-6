package com.ordonez.laboratoriono6.ui.theme

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation. background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.ordonez.laboratoriono6.MainActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ordonez.laboratoriono6.ui.theme.LaboratorioNo6Theme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaboratorioNo6Theme {
                SplashScreen()
            }
        }

        // Mantener la pantalla de splash durante 2 segundos y luego iniciar MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000) // Duración del splash en milisegundos
    }
}

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE57373)) // Fondo rojo
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Texto "Chef Recipes" en grande
            Text(
                text = "Chef Recipes",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 48.sp,        // Tamaño del texto grande
                    fontWeight = FontWeight.Bold,
                    color = Color.White      // Color blanco para el texto
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

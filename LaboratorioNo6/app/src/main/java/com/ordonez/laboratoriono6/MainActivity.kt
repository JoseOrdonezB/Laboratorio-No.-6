package com.ordonez.laboratoriono6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ordonez.laboratoriono6.ui.theme.LaboratorioNo6Theme

// Clase principal que define la actividad de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicar el tema y el diseño general
            LaboratorioNo6Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(), // Rellenar toda la pantalla
                    color = Color.White // Color de fondo
                ) {
                    // Columna que contiene la barra superior y el carrusel de imágenes
                    Column {
                        TopBar()
                        CarruselDeImagenes()
                    }
                }
            }
        }
    }
}

// Composable para la barra superior de la aplicación
@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth() // Rellenar el ancho total
            .padding(horizontal = 16.dp, vertical = 8.dp) // Espaciado interno
            .background(MaterialTheme.colorScheme.primary), // Color de fondo de la barra
        verticalAlignment = Alignment.CenterVertically, // Alinear verticalmente
        horizontalArrangement = Arrangement.SpaceBetween // Espaciar los elementos
    ) {
        // Icono del menú
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = stringResource(id = R.string.menu_icon),
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        // Título de la aplicación
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
            modifier = Modifier.weight(1f), // Ocupa el espacio disponible
            textAlign = TextAlign.Center
        )
        // Icono de búsqueda
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search_icon),
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
    }
}

// Composable para el carrusel de imágenes
@Composable
fun CarruselDeImagenes() {
    // Lista de imágenes y descripciones
    val imagenes = listOf(
        Triple(R.drawable.imagen1, "Ensalada de atún", "Descripción de la ensalada de atún..."),
        Triple(R.drawable.imagen2, "Caldo Tlalpeño", "Descripción del caldo tlalpeño..."),
        Triple(R.drawable.imagen3, "Sopa de papa", "Descripción de la sopa de papa..."),
        Triple(R.drawable.imagen4, "Alitas al pastor", "Descripción de las alitas al pastor..."),
        Triple(R.drawable.imagen5, "Tacos de carne asada con salsa de piña", "Descripción de los tacos de carne asada con salsa de piña..."),
        Triple(R.drawable.imagen6, "Pastel de arroz con leche", "Descripción del pastel de arroz con leche...")
    )

    // Extender la lista de imágenes para crear un carrusel continuo
    val extendedImages = listOf(
        imagenes.last()
    ) + imagenes + listOf(
        imagenes.first()
    )

    // Estado de la lista para el carrusel
    val listState = rememberLazyListState()
    var textoActual by remember { mutableStateOf(imagenes.first().second) }
    var descripcionActual by remember { mutableStateOf(imagenes.first().third) }

    // Actualizar el texto y descripción cuando el índice del primer ítem visible cambia
    LaunchedEffect(listState.firstVisibleItemIndex) {
        val viewportWidth = listState.layoutInfo.viewportSize.width
        val visibleItems = listState.layoutInfo.visibleItemsInfo

        val centerIndex = visibleItems
            .filter { it.offset <= viewportWidth / 2 && it.offset + it.size >= viewportWidth / 2 }
            .firstOrNull()?.index ?: 0

        val adjustedIndex = when {
            centerIndex == 0 -> imagenes.lastIndex
            centerIndex == extendedImages.lastIndex -> 0
            else -> centerIndex - 1
        }

        val imageData = imagenes.getOrElse(adjustedIndex) { imagenes.first() }
        textoActual = imageData.second
        descripcionActual = imageData.third
    }

    // Composable principal del carrusel
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Composable para el carrusel de imágenes
        ImageCarousel(images = extendedImages, listState = listState)

        Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre el carrusel y la caja de información

        // Composable para mostrar la información de la imagen
        InformationBox(title = textoActual, description = descripcionActual)
    }
}

// Composable para el carrusel de imágenes
@Composable
fun ImageCarousel(images: List<Triple<Int, String, String>>, listState: LazyListState) {
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth() // Rellenar el ancho total
            .padding(vertical = 0.dp),
        contentPadding = PaddingValues(horizontal = 32.dp) // Espaciado horizontal del carrusel
    ) {
        itemsIndexed(images) { _, image ->
            // Mostrar cada imagen en el carrusel
            Image(
                painter = painterResource(id = image.first),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp) // Tamaño de las imágenes
                    .padding(horizontal = 8.dp) // Espaciado entre imágenes
                    .clip(MaterialTheme.shapes.medium) // Bordes redondeados
            )
        }
    }
}

// Composable para la caja de información
@Composable
fun InformationBox(title: String, description: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp) // Espaciado interno
            .fillMaxWidth() // Rellenar el ancho total
    ) {
        // Caja para el título
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background) // Fondo de la caja
                .padding(16.dp) // Espaciado interno
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre el título y la descripción

        // Caja para la descripción
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background) // Fondo de la caja
                .padding(16.dp) // Espaciado interno
                .fillMaxWidth()
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray), // Color del texto
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Visible, // Mostrar todo el texto
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun RecipeDetail(imageResId: Int, title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Mostrar la imagen
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el título de la receta
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar la descripción completa de la receta
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray),
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetail() {
    LaboratorioNo6Theme {
        RecipeDetail(
            imageResId = R.drawable.imagen1,
            title = "Ensalada de atún",
            description = "Descripción detallada de la ensalada de atún. Esta ensalada contiene atún fresco, vegetales variados y una deliciosa mezcla de aderezos..."
        )
    }
}


// Vista previa de la aplicación
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LaboratorioNo6Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column {
                TopBar()
                CarruselDeImagenes()
            }
        }
    }
}


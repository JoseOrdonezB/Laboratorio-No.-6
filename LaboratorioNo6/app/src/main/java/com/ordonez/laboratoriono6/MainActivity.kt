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
        Triple(R.drawable.imagen1, "Ensalada de atún", "Esta ensalada contiene atún fresco con vegetales variados..."),
        Triple(R.drawable.imagen2, "Caldo Tlalpeño", "Sopa tradicional mexicana, reconfortante y picante, hecha con pollo, garbanzos y verduras."),
        Triple(R.drawable.imagen3, "Sopa de papa", "Plato sencillo y reconfortante, con papas tiernas cocidas en un caldo cremoso y lleno de sabor."),
        Triple(R.drawable.imagen4, "Alitas al pastor", "Alitas de pollo marinadas en una salsa de chiles y especias inspirada en el clásico taco al pastor."),
        Triple(R.drawable.imagen5, "Tacos de carne asada con salsa de piña", "Se combina el sabor jugoso de la carne a la parrilla con el dulzor tropical y fresco de la piña."),
        Triple(R.drawable.imagen6, "Pastel de arroz con leche", "Este pastel es una reinvención del tradicional postre de arroz con leche, convertido en una rica y cremosa delicia horneada.")
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
fun RecipeDetail(imageResId: Int, title: String, description: String, title2: String, ingredients: String, title3: String, preparation: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Mostrar la imagen
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.height(12.dp))

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
        Spacer(modifier = Modifier.height(12.dp))

        // Mostrar el título "Ingredientes"
        Text(
            text = title2,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar la lista de ingredientes
        Text(
            text = ingredients,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Justify
                )
        Spacer(modifier = Modifier.height(12.dp))

        // Mostrar el título de Pasos de preparación
        Text(
            text = title3,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar los pasos de preparación
        Text(
            text = preparation,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetail6() {
    LaboratorioNo6Theme {
        RecipeDetail(
            imageResId = R.drawable.imagen6,
            title = "Pastel de Arroz con Leche",
            description = "Este pastel es una reinvención del tradicional postre de arroz con leche, convertido en una rica y cremosa delicia horneada.",
            title2 = "Ingredientes",
            ingredients = "1. 1 taza de arroz blanco\n" +
                    "2. 4 tazas de leche entera\n" +
                    "3. 1 taza de azúcar\n" +
                    "4. 1 rama de canela\n" +
                    "5. 1 cucharadita de vainilla\n" +
                    "6. 1 taza de crema para batir\n" +
                    "7. 1/2 taza de leche condensada\n" +
                    "8. 3 huevos\n" +
                    "9. 1 cucharadita de canela en polvo",
            title3 = "Pasos de preparación",
            preparation = "1. Enjuaga el arroz bajo agua fría. En una olla grande, mezcla el arroz, 4 tazas de leche, la rama de canela y una pizca de sal. Cocina a fuego medio, removiendo ocasionalmente, hasta que el arroz esté tierno y haya absorbido la mayor parte de la leche, unos 20-25 minutos.\n" +
                    "2. En un tazón grande, bate los huevos junto con la crema para batir, la leche condensada y la canela en polvo. Incorpora el arroz con leche frío a esta mezcla.\n" +
                    "3. Vierte la mezcla en el molde preparado y hornea durante 40-50 minutos."
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetail5() {
    LaboratorioNo6Theme {
        RecipeDetail(
            imageResId = R.drawable.imagen5,
            title = "Tacos de Carne Asada con Salsa de Piña",
            description = "Se combina el sabor jugoso de la carne a la parrilla con el dulzor tropical y fresco de la piña.",
            title2 = "Ingredientes",
            ingredients = "1. 500 g de carne asada (arrachera, bistec o falda)\n" +
                    "2. 12 tortillas de maíz (o de harina)\n" +
                    "3. 1 cucharada de aceite vegetal\n" +
                    "4. 1 taza de piña fresca, cortada en cubitos\n" +
                    "5. 1/4 de cebolla morada, picada finamente",
            title3 = "Pasos de preparación",
            preparation = "1. Sazona la carne con sal, pimienta y un poco de aceite vegetal. Déjala reposar por 10 minutos a temperatura ambiente.\n" +
                    "2. Asa la carne durante 3-5 minutos por lado (dependiendo del grosor) hasta que esté bien cocida pero aún jugosa. Deja reposar la carne por unos minutos antes de cortarla en tiras finas o pequeños trozos.\n" +
                    "3. Mezcla los cubos de piña con la cebolla morada.\n" +
                    "3. Calienta las tortillas de maíz en una sartén o comal hasta que estén suaves y calientes.\n" +
                    "4. Coloca una porción de carne asada en el centro de cada tortilla. Agrega una cucharada de la salsa de piña por encima."
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetail4() {
    LaboratorioNo6Theme {
        RecipeDetail(
            imageResId = R.drawable.imagen4,
            title = "Alitas al pastor",
            description = "Alitas de pollo marinadas en una salsa de chiles y especias inspirada en el clásico taco al pastor.",
            title2 = "Ingredientes",
            ingredients = "1. 1 kg de alitas de pollo\n" +
                    "2. 2 chiles guajillo, desvenados y sin semillas\n" +
                    "3. 1 chile chipotle en adobo\n" +
                    "4. 2 dientes de ajo\n" +
                    "5. 1/4 de cebolla\n" +
                    "6. 2 cucharadas de vinagre blanco\n" +
                    "7. 1 cucharadita de orégano\n" +
                    "8. 2 cucharadas de achiote",
            title3 = "Pasos de preparación",
            preparation = "1. En una olla pequeña, hierve agua y agrega los chiles guajillo y ancho. Cocina durante 5 minutos para ablandarlos. Escúrrelos y colócalos en una licuadora junto con los demás ingredientes.\n" +
                    "2. Coloca las alitas de pollo en un tazón grande o una bolsa con cierre hermético. Vierte la salsa sobre las alitas, asegurándote de que estén bien cubiertas. Deja marinar en el refrigerador durante al menos 2 horas.\n" +
                    "3. Precalienta la parrilla o el horno a 200°C (390°F). Si prefieres hornearlas, colócalas en una bandeja para hornear forrada con papel aluminio y hornea por 25-30 minutos."
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetail3() {
    LaboratorioNo6Theme {
        RecipeDetail(
            imageResId = R.drawable.imagen3,
            title = "Sopa de Papa",
            description = "Plato sencillo y reconfortante, con papas tiernas cocidas en un caldo cremoso y lleno de sabor.",
            title2 = "Ingredientes",
            ingredients = "1. 4 papas medianas, peladas y cortadas en cubos\n" +
                    "2. 1 litro de caldo de pollo o vegetales\n" +
                    "3. 1 cebolla, picada finamente\n" +
                    "4. 2 dientes de ajo, picados\n" +
                    "5. 1 taza de crema\n" +
                    "6. 1 cucharada de mantequilla\n" +
                    "6. Sal y pimienta al gusto",
            title3 = "Pasos de preparación",
            preparation = "1. En una olla grande, derrite la mantequilla a fuego medio. Añade la cebolla y el ajo, y sofríelos hasta que estén dorados y fragantes.\n" +
                    "2. Agrega las papas y el caldo a la olla. Lleva a ebullición, luego reduce el fuego y cocina a fuego lento hasta que las papas estén tiernas, aproximadamente 20 minutos.\n" +
                    "3. Una vez que las papas estén cocidas, retira la olla del fuego y añade la crema. Mezcla bien y sazona con sal y pimienta al gusto."
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetail2() {
    LaboratorioNo6Theme {
        RecipeDetail(
            imageResId = R.drawable.imagen2,
            title = "Caldo Tlalpeño",
            description = "Sopa tradicional mexicana, reconfortante y picante, hecha con pollo, garbanzos y verduras.",
            title2 = "Ingredientes",
            ingredients = "1. 2 pechugas de pollo deshuesadas y cocidas\n" +
                    "2. 1 taza de garbanzos cocidos\n" +
                    "3. 2 zanahorias, cortadas en tiras\n" +
                    "4. 2 calabacitas, cortadas en tiras\n" +
                    "5. 1 cebolla picada\n" +
                    "6. 2 dientes de ajo picados\n" +
                    "7. 2 chiles chipotles en adobo\n" +
                    "8. 6 tazas de caldo de pollo\n" +
                    "9. Jugo de 2 limones\n" +
                    "10. Aguacate y queso fresco para servir",
            title3 = "Pasos de preparación",
            preparation = "1. En una olla grande, sofríe la cebolla y el ajo hasta que estén dorados. Agrega las zanahorias, las calabacitas y los chiles chipotles.\n" +
                    "2. Vierte el caldo de pollo en la olla y añade las pechugas cocidas y desmenuzadas junto con los garbanzos. Cocina a fuego lento durante 20 minutos.\n" +
                    "3. Retira del fuego y exprime el jugo de los limones en el caldo.\n" +
                    "4. Sirve caliente con rodajas de aguacate, queso fresco y más limón al gusto. ¡Disfruta!"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetail1() {
    LaboratorioNo6Theme {
        RecipeDetail(
            imageResId = R.drawable.imagen1,
            title = "Ensalada de atún",
            description = "Esta ensalada contiene atún fresco con vegetales variados...",
            title2 = "Ingredientes",
            ingredients = "1. 2 latas de atún (en agua o aceite, escurridas)\n" +
                    "2. 1/2 taza de mayonesa\n" +
                    "3. 1 cucharada de mostaza\n" +
                    "4. 1/4 de cebolla morada, picada finamente\n" +
                    "5. 1 tallo de apio, picado\n" +
                    "6. 1 zanahoria, rallada\n" +
                    "7. 1 tomate, picado en cubos pequeños\n" +
                    "8. 1 aguacate, en cubos (opcional)\n" +
                    "9. Jugo de 1/2 limón\n" +
                    "10. Sal y pimienta al gusto\n" +
                    "11. 1/4 taza de cilantro fresco, picado (opcional)",
            title3 = "Pasos de preparación",
            preparation = "1. En un tazón grande, mezcla el atún escurrido con la mayonesa y la mostaza.\n" +
                    "2. Agrega la cebolla morada, el apio, la zanahoria rallada, el tomate y el aguacate (si lo usas).\n" +
                    "3. Exprime el jugo de limón sobre la mezcla y sazona con sal y pimienta al gusto.\n" +
                    "4. Mezcla todos los ingredientes hasta que estén bien combinados.\n" +
                    "5. Añade el cilantro picado para dar un toque de frescura (opcional)."
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


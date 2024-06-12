package com.example.vamz

import android.content.Context
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vamz.ui.theme.VAMZTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VAMZTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "menu"
                ) {
                    composable("menu") {//entry ->
                        MenuNahlad(navController)
                    }
                    composable("mapa") {
                        PrvyNahlad(navController)
                    }
                    composable("vyhladaj") {
                        VyhladajNahlad(navController)
                    }
                    composable("vysledokHladania/{arg1}", arguments = listOf(
                        navArgument("arg1") { type = NavType.StringType}
                    )){entry ->
                        vysledokHladania(navController = navController, entry.arguments?.getString("arg1"))
                    }
                }
            }
        }
    }
}
val farbaPozadia = Color(144,238,144)


@Composable
fun HornaListaMain(navController: NavController) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(BorderStroke(2.dp, Color.Black))
    ){
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = null )
        }
        Text(
            text = "Chenyu Vale",
            color = Color.Black,
            fontSize = 25.sp,
            )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(30.dp) )
        }
    }
}

@Composable
fun strednaCastMain() {
    var scale by remember {
        mutableStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1117f / 925f)

    ){
        val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
            scale = (scale * zoomChange).coerceIn(1f, 5f)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )
        }
        Image(
            painter = painterResource(id = R.drawable.mapchenyun),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state)
            )
    }

}

@Composable
fun PrvyNahlad(navController: NavController){
    Column (
        modifier = Modifier.background(farbaPozadia)
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        HornaListaMain(navController)
        strednaCastMain()
    }

}

@Composable
fun MenuNahlad(navController: NavController){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(farbaPozadia)
    ) {

        IconButton(
            onClick = { navController.navigate("mapa") },
            modifier = Modifier
                .size(64.dp)
        )
        {
            Icon(
                Icons.Filled.Place,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
        Text(text = "Mapa")
        Spacer(modifier = Modifier.size(16.dp))

        IconButton(
            onClick = { navController.navigate("vyhladaj") },
            modifier = Modifier
                .size(64.dp)
        )
        {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
        Text(text = "Vyhladaj")
        Spacer(modifier = Modifier.size(16.dp))

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(64.dp)
        )
        {
            Icon(
                Icons.Filled.List,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
        Text(text = "Historia")
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(64.dp)){
        Text(
            "ORECH",
            fontSize = 78.sp,
            modifier = Modifier.align(Alignment.TopCenter),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            //color = Color(161,98,67)
        )
    }
}

@Composable
fun VyhladajNahlad(navController: NavController) {
    val viewModel = viewModel<SearchViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val materialy by viewModel.materialy.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(farbaPozadia)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null)},
            value = searchText,
            label = { Text("Vyhladaj") },
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedPlaceholderColor = Color.White,
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            var predosly = ""

            items(materialy) { material ->
                if (material.nazovMaterialu != predosly)
                {
                    Row(modifier = Modifier
                        .fillMaxSize()) {
                        OutlinedButton(
                            onClick = {
                                /*navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("hladanyMaterial", material.nazovMaterialu)
                                navController.popBackStack()*/
                                      navController.navigate("vysledokHladania/${material.nazovMaterialu}")
                                },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = material.nazovMaterialu)
                            Icon(Icons.Filled.Face, contentDescription = null)
                        }
                        predosly = material.nazovMaterialu
                    }
                }
            }
        }
    }
}

@Composable
fun vysledokHladania(navController: NavController, material: String?) {
    val viewModel = viewModel<SearchViewModel>()
    val materialy by viewModel.materialy.collectAsState()

    if (material != null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                IconButton(onClick = { navController.navigate("menu") }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Menu")
                }
                Text(
                    text = material,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(materialy) { _material ->
                    if (_material.nazovMaterialu == material) {
                        Text(
                            text = "Lokácia: ${_material.lokacia}",
                            modifier = Modifier.padding(8.dp)
                        )
                        Image(
                            painter = painterResource(id = _material.imageRes),
                            contentDescription = _material.nazovMaterialu,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}



@Preview(
    showBackground = true,
    //showSystemUi = true,
    )
@Composable
fun GreetingPreview() {
    VAMZTheme {
        //PrvyNahlad()
        //MenuNahlad()
        //VyhladajNahlad()
    }
}
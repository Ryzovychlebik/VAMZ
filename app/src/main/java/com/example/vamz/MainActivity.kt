package com.example.vamz

import android.os.Bundle
import android.text.Layout
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.panBy
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                    composable("menu") {
                        MenuNahlad(navController)
                    }
                    composable("mapa") {
                        PrvyNahlad(navController)
                    }
                }
            }
        }
    }
}



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
        modifier = Modifier.background(Color(144,238,144))
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
            .background(Color(144, 238, 144))
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
            onClick = { /*TODO*/ },
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
fun VyhladajNahlad() {
    val viewModel = viewModel<SearchViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val persons by viewModel.persons.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Vyhladaj")}
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            items(persons) {person ->
                Text(
                    text = "${person.firstName} ${person.lastName}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
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
    }
}
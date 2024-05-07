package com.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android.ui.theme.AndroidTheme

@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTheme {
                Surface(
                    Modifier
                        .padding(top = 32.dp)
                        .fillMaxSize()) {
                    SharedTransitionLayout {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController ,
                            startDestination = "list"
                        ){
                            composable(route = "list"){
                                ListScreen(
                                    onItemClick = {resId, title ->
                                        navController.navigate("detail/$resId/$title")
                                    },
                                    animatedVisibilityScope = this
                                )
                            }
                            composable(
                                route = "detail/{resId}/{title}",
                                arguments = listOf(
                                    navArgument("resId"){
                                        type = NavType.IntType
                                    },
                                    navArgument("title"){
                                        type = NavType.StringType
                                    }
                                )
                            ){
                                val resId = it.arguments?.getInt("resId")?:0
                                val title = it.arguments?.getString("title")?:""
                                DetailScreen(
                                    resId = resId,
                                    title = title,
                                    animatedVisibilityScope = this
                                )
                            }

                        }
                    }

                }
            }
        }
    }
}

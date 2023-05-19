package com.example.animatednavcontrollerbackpressedissues

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.example.animatednavcontrollerbackpressedissues.ui.theme.AnimatedNavControllerBackPressedIssuesTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimatedNavControllerBackPressedIssuesTheme {
                val outerController = rememberAnimatedNavController()

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedNavHost(
                        navController = outerController,
                        startDestination = "innerDestinationHost/{page}"
                    ) {
                        composable(
                            route = "innerDestinationHost/{page}",
                            content = {
                                InnerNavigation(navigateToOuterRoute = {
                                    outerController.navigate(
                                        "firstRoute"
                                    )
                                })
                            }
                        )
                        composable(
                            route = "firstRoute",
                            content = { OuterRoute(name = "firstRoute") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InnerNavigation(navigateToOuterRoute: () -> Unit) {
    val innerController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = innerController,
        startDestination = "innerDestinationHost/firstRoute"
    ) {
        composable(
            route = "innerDestinationHost/firstRoute",
            content = {
                InnerRoute(
                    name = "firstInnerRoute",
                    navigateToOuterRoute = navigateToOuterRoute,
                    navigateToInnerRoute = { innerController.navigate("innerDestinationHost/secondRoute") }
                )
            }
        )
        composable(
            route = "innerDestinationHost/secondRoute",
            content = {
                InnerRoute(
                    name = "secondInnerRoute",
                    navigateToOuterRoute = navigateToOuterRoute,
                    navigateToInnerRoute = null
                )
            }
        )
    }
}

@Composable
fun InnerRoute(
    name: String,
    navigateToOuterRoute: () -> Unit,
    navigateToInnerRoute: (() -> Unit)? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Hello $name")
        Button(
            onClick = navigateToOuterRoute
        ) {
            Text(text = "Navigate to outer route")
        }
        if (navigateToInnerRoute != null) {
            Button(onClick = navigateToInnerRoute) {
                Text(text = "Navigate to inner route")
            }
        }
    }
}

@Composable
fun OuterRoute(name: String) {
    Text(text = "Hello $name!")
}

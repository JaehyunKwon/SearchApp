package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.search.navigation.ROUTE_SEARCH_SCREEN
import com.example.myapplication.navigation.AppNavHost
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.bookmark.navigation.BOOKMARK_ROUTE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                MainScreen(navController)
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController
) {
    var topBarTitle by remember { mutableIntStateOf(items[0].title) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        val selectedTitle = items.find { it.screenRoute == currentRoute }
        topBarTitle = selectedTitle?.title ?: items[0].title
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(title = stringResource(id = topBarTitle))
        },
        bottomBar = {
            BottomNavigation(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                currentRoute = currentRoute,
                navController = navController
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            AppNavHost(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    currentRoute: String?,
    navController: NavHostController
) {
    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = true,
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        style = TextStyle(
                            fontSize = 12.sp
                        )
                    )
                },
                icon = {},
                onClick = {
                    navController.navigate(item.screenRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

data class BottomNavItem(val title: Int, val screenRoute: String)

val items = listOf(
    BottomNavItem(R.string.search_screen, ROUTE_SEARCH_SCREEN),
    BottomNavItem(R.string.bookmark_screen, BOOKMARK_ROUTE)
)


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        val navController = rememberNavController()
        MainScreen(navController)
    }
}
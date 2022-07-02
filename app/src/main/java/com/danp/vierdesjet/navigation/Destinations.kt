package com.danp.vierdesjet.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Home: Destinations("home", "Home", Icons.Filled.Home)
    object Login: Destinations("login", "Login", Icons.Filled.Home)
    object SignUp: Destinations("signup", "Sign Up", Icons.Filled.Home)
    object Plants: Destinations("plants", "Plants", Icons.Filled.Home)
    object PlantsCreator: Destinations("plantscreator", "Plants Creator", Icons.Filled.Home)
    object PlantsDetails: Destinations("plantsdetails", "Plants Details", Icons.Filled.Home)
}
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
    object Config: Destinations("config", "Config", Icons.Filled.Home)
    object PlantsActions: Destinations("plantsactions", "Plants Actions", Icons.Filled.Home)
    object PlantsDetails: Destinations(
        "plantsdetails/?plantId={plantId}&plantType={plantType}&plantPrice={plantPrice}&plantDescription={plantDescription}&plantA={plantA}&plantB={plantB}&plantC={plantC}&plantCode={plantCode}&plantDate={plantDate}&plantStatus={plantStatus}",
        "Plants Details", Icons.Filled.Home){
        fun createRoute(
            plantId: Int,
            plantType: String,
            plantPrice: String,
            plantDescription: String,
            plantA: Boolean,
            plantB: Boolean,
            plantC: Boolean,
            plantCode: String,
            plantDate: String,
            plantStatus: Boolean
        ) = "plantsdetails/?plantId=$plantId&plantType=$plantType&plantPrice=$plantPrice&plantDescription=$plantDescription&plantA=$plantA&plantB=$plantB&plantC=$plantC&plantCode=$plantCode&plantDate=$plantDate&plantStatus=$plantStatus"
    }
}
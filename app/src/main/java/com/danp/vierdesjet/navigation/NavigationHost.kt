package com.danp.vierdesjet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.danp.vierdesjet.*
import com.danp.vierdesjet.navigation.Destinations.*
import com.danp.vierdesjet.screens.*

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) {
            Home(
                navLogin = {
                    navController.navigate(Login.route)
                },
                navSignUp = {
                    navController.navigate(SignUp.route)
                }
            )
        }
        composable(Login.route) {
            Login(
                navPlants = {
                    navController.navigate(Plants.route)
                }
            )
        }

        composable(SignUp.route){
            SignUp(
                navPlants = { navController.navigate(Plants.route) }
            )
        }

        composable(Plants.route){
            Plants(
                navPlantsCreator = { navController.navigate(PlantsCreator.route) },
                navPlantsDetails = { navController.navigate(PlantsDetails.route) }
            )
        }

        composable(PlantsCreator.route){
            PlantsCreator(
                navPlants = { navController.navigate(Plants.route) }
            )
        }

        composable(PlantsDetails.route){
            PlantsDetails(
                navPlants = { navController.navigate(Plants.route) }
            )
        }
    }
}
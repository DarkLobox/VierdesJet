package com.danp.vierdesjet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.danp.vierdesjet.navigation.Destinations.*
import com.danp.vierdesjet.screens.*

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) {
            Home(
                navLogin = { navController.navigate(Login.route) },
                navPlants = { navController.navigate(Plants.route) },
                navSignUp = { navController.navigate(SignUp.route) }
            )
        }
        composable(Login.route) {
            Login(
                navPlants = { navController.navigate(Plants.route) },
                navSignUp = { navController.navigate(SignUp.route) },
                navHome = { navController.navigate(Home.route) }
            )
        }

        composable(SignUp.route){
            SignUp(
                navPlants = { navController.navigate(Plants.route) },
                navHome = { navController.navigate(Home.route) }
            )
        }

        composable(Plants.route){
            Plants(
                navPlantsCreator = { navController.navigate(PlantsCreator.route) },
                navPlantsDetails = {
                        plantId,
                        plantType,
                        plantPrice,
                        plantDescription,
                        plantA,
                        plantB,
                        plantC,
                        plantCode,
                        plantDate,
                        plantStatus ->
                    navController.navigate(PlantsDetails.createRoute(
                        plantId,
                        plantType,
                        plantPrice,
                        plantDescription,
                        plantA,
                        plantB,
                        plantC,
                        plantCode,
                        plantDate,
                        plantStatus)
                    )},
                navConfig = { navController.navigate(Config.route) },
                navHome = { navController.navigate(Home.route) },
                navPlantsActions = { navController.navigate(PlantsActions.route) }
            )
        }

        composable(PlantsCreator.route){
            PlantsCreator(
                navPlants = { navController.navigate(Plants.route) },
                navHome = { navController.navigate(Home.route) }
            )
        }

        composable(PlantsDetails.route,
                arguments = listOf(
                    navArgument("plantId"){ defaultValue = 0},
                    navArgument("plantType"){ defaultValue = "margarita"},
                    navArgument("plantPrice"){ defaultValue = "0.0"},
                    navArgument("plantDescription"){ defaultValue = "Descripcion por defecto"},
                    navArgument("plantA"){ defaultValue = false},
                    navArgument("plantB"){ defaultValue = false},
                    navArgument("plantC"){ defaultValue = false},
                    navArgument("plantCode"){ defaultValue = "codigo por defecto"},
                    navArgument("plantDate"){ defaultValue = "00/00/0000"},
                    navArgument("plantStatus"){ defaultValue = true}
                )
            ){ navBackStackEntry ->
                var plantId = navBackStackEntry.arguments?.getInt("plantId")
                var plantType = navBackStackEntry.arguments?.getString("plantType")
                var plantPrice = navBackStackEntry.arguments?.getString("plantPrice")
                var plantDescription = navBackStackEntry.arguments?.getString("plantDescription")
                var plantA = navBackStackEntry.arguments?.getBoolean("plantA")
                var plantB = navBackStackEntry.arguments?.getBoolean("plantB")
                var plantC = navBackStackEntry.arguments?.getBoolean("plantC")
                var plantCode = navBackStackEntry.arguments?.getString("plantCode")
                var plantDate = navBackStackEntry.arguments?.getString("plantDate")
                var plantStatus = navBackStackEntry.arguments?.getBoolean("plantStatus")

                requireNotNull(plantId)
                requireNotNull(plantType)
                requireNotNull(plantPrice)
                requireNotNull(plantDescription)
                requireNotNull(plantA)
                requireNotNull(plantB)
                requireNotNull(plantC)
                requireNotNull(plantCode)
                requireNotNull(plantDate)
                requireNotNull(plantStatus)

                PlantsDetails(
                    plantId,
                    plantType,
                    plantPrice,
                    plantDescription,
                    plantA,
                    plantB,
                    plantC,
                    plantCode,
                    plantDate,
                    plantStatus,
                    navPlants = { navController.navigate(Plants.route) },
                    navHome = { navController.navigate(Home.route) }
                )
        }

        composable(Config.route){
            Config(
                navPlants = { navController.navigate(Plants.route) },
                navHome = { navController.navigate(Home.route) }
            )
        }

        composable(PlantsActions.route){
            PlantsActions(
                navPlants = { navController.navigate(Plants.route) },
                navHome = { navController.navigate(Home.route) }
            )
        }

    }
}
package com.example.gameclock.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.screens.AlarmDetailsScreen
import com.example.gameclock.screens.HomeScreen
import com.example.gameclock.screens.RingtoneSelectionScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    val alarmViewModel: AlarmViewModel = viewModel()

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController, alarmViewModel = alarmViewModel)
        }

        composable(
            route = Screen.AlarmDetailsScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ) { backStackEntry ->
            AlarmDetailsScreen(
                navController = navController,
                alarmId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                alarmViewModel = alarmViewModel
            )
        }
        composable(
            route = Screen.RingtoneScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ) {backStackEntry ->
            RingtoneSelectionScreen(
                navController = navController,
                alarmId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                alarmViewModel = alarmViewModel)
        }

    }
}
package com.example.gameclock.navigation

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.ViewModels.AlarmViewModelFactory
import com.example.gameclock.helper.AlarmManagerHelper
import com.example.gameclock.screens.AlarmDetailsScreen
import com.example.gameclock.screens.HomeScreen

import com.example.gameclock.screens.PuzzleSelectionScreen
import com.example.gameclock.screens.RingtoneSelectionScreen

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    val factory = AlarmViewModelFactory(context.applicationContext as Application)
    val alarmViewModel: AlarmViewModel = viewModel(factory = factory)
    val alarmManagerHelper = remember { AlarmManagerHelper(context) }


    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                alarmViewModel = alarmViewModel,
                alarmManagerHelper = alarmManagerHelper
            )
        }

        composable(
            route = Screen.AlarmDetailsScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            AlarmDetailsScreen(
                navController = navController,
                alarmId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                alarmViewModel = alarmViewModel,
                context = context.applicationContext,
                alarmManagerHelper = alarmManagerHelper
            )
        }

        composable(
            route = Screen.RingtoneScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            RingtoneSelectionScreen(
                navController = navController,
                alarmId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                alarmViewModel = alarmViewModel,
                context = context.applicationContext
            )
        }

        composable(
            route = Screen.PuzzleSelectionScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            PuzzleSelectionScreen(
                navController = navController,
                alarmId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                alarmViewModel = alarmViewModel,
                context = context.applicationContext
            )
        }

//        composable(
//            route = Screen.PuzzleScreen.route,
//            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) { type = NavType.StringType })
//        ) { backStackEntry ->
//            val alarmId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY) ?: return@composable
//
//            PuzzleScreen(
//                alarmId = alarmId,
//                onPuzzleSolved = {
//                    // Define what happens when the puzzle is solved
//                    alarmViewModel.removeAlarmById(alarmId)
//                    navController.navigate(Screen.HomeScreen.route)
//                },
//                onEmergencyStop = {
//                    // Define what happens when the emergency stop is pressed
//                    alarmViewModel.removeAlarmById(alarmId)
//                    navController.navigate(Screen.HomeScreen.route)
//                }
//            )
//        }
    }
}
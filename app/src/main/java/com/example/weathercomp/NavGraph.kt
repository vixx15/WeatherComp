package com.example.weathercomp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController,viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),){
    NavHost(navController = navController, startDestination = Screens.MainScreen.route){
        composable(route=Screens.MainScreen.route){
            MainScreen(viewModel=viewModel,navyController = navController)
        }
        composable(route=Screens.DetailScreen.route){
            WeatherDetails(viewModel = viewModel)
        }

    }
}
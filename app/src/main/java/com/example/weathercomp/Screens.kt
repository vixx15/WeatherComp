package com.example.weathercomp

sealed class Screens(val route:String) {
    object MainScreen: Screens("main_screen")
    object DetailScreen: Screens("detail_screen")
}
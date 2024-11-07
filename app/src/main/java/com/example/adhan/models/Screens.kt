package com.example.adhan.models

sealed class Screen(val route: String) {
    object App : Screen("app")
    object NoPermission : Screen("no_permission")
    object Compass : Screen("compass")
    object PrayerTimes : Screen("prayer_times")
    object Settings : Screen("settings")
}
package com.example.weathercomp

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weathercomp.ui.theme.WeatherCompTheme
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherCompTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //MainScreen()
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navyController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Permission granted, update the location
                    getCurrentLocation(context) { lat, long ->
                        viewModel.locationText = "Latitude: $lat, Longitude: $long"
                    }
                }
            })




    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Temperature:",
            modifier = Modifier.padding(10.dp),

            )
        Text(
            text = viewModel.weatherTime?.currentWeather?.temperature.toString(),
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = "Insert longitude and latitude:",
            modifier = Modifier.padding(10.dp)
        )


        TextField(
            value = viewModel.lat,
            onValueChange = { newText -> viewModel.lat = newText },
            label = { Text("Latitude") },
            modifier = Modifier.padding(10.dp)
        )

        TextField(
            value = viewModel.long,
            onValueChange = { newText -> viewModel.long = newText },
            label = { Text("Longitude") },
            modifier = Modifier.padding(10.dp)
        )

        Button(
            onClick = {
                if (viewModel.lat.text != "" && viewModel.long.text != "" && viewModel.lat.text
                        .toDouble() <= 90 && viewModel.long.text.toDouble() < 180
                ) {
                    viewModel.forecast(
                        viewModel.lat.text.toDouble(),
                        viewModel.long.text.toDouble()
                    )
                    // viewModel.temperature =
                    viewModel.weatherTime?.currentWeather?.temperature.toString()
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "SUBMIT")

        }

        Button(
            onClick = {
                if (hasLocationPermission(context)) {
                    // Permission already granted, update the location
                    getCurrentLocation(context) { lat, long ->
                        viewModel.locationText = "Latitude: $lat, Longitude: $long"
                        viewModel.lat = TextFieldValue(lat.toString())
                        viewModel.long = TextFieldValue(long.toString())
                    }
                } else {
                    // Request location permission
                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {

            Text(text = "LOCATION")

        }
        Text(text = viewModel.locationText)

        Button(
            onClick = { navyController.navigate(Screens.DetailScreen.route) },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "DETAILS")
        }
    }
}

private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@Composable
fun LocationScreen() {
    val context = LocalContext.current
    var location by remember { mutableStateOf("Your location") }

    // Create a permission launcher
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Permission granted, update the location
                    getCurrentLocation(context) { lat, long ->
                        location = "Latitude: $lat, Longitude: $long"
                    }
                }
            })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (hasLocationPermission(context)) {
                    // Permission already granted, update the location
                    getCurrentLocation(context) { lat, long ->
                        location = "Latitude: $lat, Longitude: $long"

                    }
                } else {
                    // Request location permission
                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        ) {
            Text(text = "Allow")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = location)
    }
}

private fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val long = location.longitude
                    callback(lat, long)
                }
            }
            .addOnFailureListener { exception ->
                // Handle location retrieval failure
                exception.printStackTrace()
            }
    } catch (e: SecurityException) {

    }
}

@Composable
fun WeatherDetails() {
    Column {
        Text(text = "Latitude:")
        Text(text = "Longitude:")
        Text(text = "Generation Time (ms):")
        Text(text = "UTC Offset (sec):")
        Text(text = "Timezone:")
        Text(text = "Timezone Abbreviation:")
        Text(text = "Elevation:")
        Text(text = "Temperature:")
        Text(text = "Wind speed:")
        Text(text = "Wind direction:")
        Text(text = "Weather code:")
        Text(text = "Is Day:")
        Text(text = "Time:")

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherCompTheme {
        //MainScreen()
    }
}



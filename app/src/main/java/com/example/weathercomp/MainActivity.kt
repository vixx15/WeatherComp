package com.example.weathercomp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.dto.WeatherTime
import com.example.weathercomp.ui.theme.WeatherCompTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    MainScreen("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier) {
    var lat by remember { mutableStateOf(TextFieldValue("")) }
    var long by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Temperature:",
            modifier = Modifier.padding(10.dp),

            )
        Text(
            text = "No temperatue",
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = "Insert longitude and latitude:",
            modifier = Modifier.padding(10.dp)
        )


        TextField(
            value = lat,
            onValueChange = { newText -> lat = newText },
            label = { Text("Latitude") },
            modifier = Modifier.padding(10.dp)
        )

        TextField(
            value = long,
            onValueChange = { newText -> long = newText },
            label = { Text("Latitude") },
            modifier = Modifier.padding(10.dp)
        )

        Button(
            onClick = {},
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "SUBMIT")

        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(10.dp)
        ) {

            Text(text = "LOCATION")
        }
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
        MainScreen("Android")
    }
}



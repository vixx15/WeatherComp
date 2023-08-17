package com.example.weathercomp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.api.NetworkClient
import com.example.weather.dto.WeatherTime
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    var lat by mutableStateOf(TextFieldValue(""))
    var long by mutableStateOf(TextFieldValue(""))
    var temperature by mutableStateOf("No temperature")
    var locationText by mutableStateOf("Your location")
    var weatherTime by mutableStateOf<WeatherTime?>(null)


    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var client = NetworkClient()

    //public var weatherTime: WeatherTime? = null
    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData

    //private var jsonWeatherTime: JSONObject? = null
    fun forecast(latitude: Double, longitude: Double) {
        // You need to replace 'client' with your actual Retrofit client instance
        client.getForecast(latitude, longitude)
            .enqueue(object : Callback<WeatherTime> {
                override fun onResponse(
                    call: Call<WeatherTime>,
                    response: Response<WeatherTime>
                ) {
                    if (response.isSuccessful) {
                        weatherTime = response.body()
                        //temperature = weatherTime?.currentWeather?.temperature.toString()
                    } else {
                        _errorLiveData.postValue("Response code: ${response.code()}, ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<WeatherTime>, t: Throwable) {
                    _errorLiveData.postValue(t.localizedMessage)
                }
            })
    }
}
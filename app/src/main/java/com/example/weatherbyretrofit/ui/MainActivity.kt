package com.example.weatherbyretrofit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.weatherbyretrofit.api.RetrofitInstance
import com.example.weatherbyretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main) {
            val response = try {
                RetrofitInstance.api.getWeather()
            } catch (e: IOException) {
                return@launch
            }
            binding.apply {


                response.body()?.apply {
                    val sunsetRes = this.sys.sunset.toLong()
                    val sunriseRes = this.sys.sunrise.toLong()
                    address.text = this.name
                    temp.text = this.main.temp.toString()+"°C"
                    tempMax.text =  "Max Temp: "+this.main.temp_max.toString()
                    tempMin.text = "Min Temp: "+this.main.temp_min.toString()
                    sunrise.text =  SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunriseRes*1000))
                    sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunsetRes*1000)).toString()


                    humidity.text = response.body()?.main?.humidity.toString()
                    pressure.text = response.body()?.main?.pressure.toString()
                    windx.text = response.body()?.wind?.speed.toString()
                    val updateAt = response.body()?.dt?.toLong()

                    if (updateAt != null) {
                        updatedAt.text  = SimpleDateFormat(
                            "dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updateAt*1000)).toString()
                    }

                    status.text = response.body()?.weather?.first()?.description
                }

                }

            }

    }
}





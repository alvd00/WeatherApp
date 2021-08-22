package ru.geekbrains.androidwithkotlin.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.DetailsFragmentBinding
import com.example.myapplication.model.dto.WeatherDTO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.details_fragment.*
import ru.geekbrains.androidwithkotlin.model.data.Weather
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


private const val API_KEY = BuildConfig.WEATHER_API_KEY
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle : Weather

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherBundle = arguments?.getParcelable<Weather>(BUNDLE_EXTRA) ?: Weather()
        binding.main.hide()
        binding.loadingLayout.show()
        loadWeather()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadWeather(){
        try {   val uri =
            URL("https://api.weather.yandex.ru/v2/informers?lat=${weatherBundle.city.latitude}&lon=${weatherBundle.city.longitude}")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "X-Yandex-API-Key",
                        API_KEY
                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    // преобразование ответа от сервера (JSON) в модель данных (WeatherDTO)
                    val weatherDTO: WeatherDTO =
                        Gson().fromJson(getLines(bufferedReader), WeatherDTO::class.java)
                    handler.post { displayWeather(weatherDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    //Обработка ошибки
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            //Обработка ошибки
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun displayWeather(weatherData: WeatherDTO) {
        with(binding) {
            weatherData.apply {
                main.show()
                loadingLayout.hide()
                weatherBundle.city.also {
                    city -> cityName.text = city.city

                }
                weatherData.fact?.let {
                    fact ->
                   /* Glide
                        .with(this@DetailsFragment)
                        .load(fact.icon)
                        .fitCenter()
                        .into(weatherIcon)*/
//
                   // weather_icon.setImageResource(fact.icon)
                    temperatureValue.text = fact.temperature.toString()
                    feelsLikeValue.text = fact.feels_like.toString()
                    weatherCondition.text = fact.condition
                    //pressure.text = fact.pressure.toString()
                }

            }
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
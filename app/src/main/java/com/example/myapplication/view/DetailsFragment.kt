package ru.geekbrains.androidwithkotlin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.DetailsFragmentBinding
import kotlinx.android.synthetic.main.details_fragment.*
import ru.geekbrains.androidwithkotlin.model.data.Weather

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let {
            setdata(it)
        }
    }

    private fun setdata(weatherData: Weather) {
        with(binding) {
            weatherData.apply {
                cityName.text = city.city
                weather_icon.setImageResource(icon)
                temperatureValue.text = temperature.toString()
                feelsLikeValue.text = feelsLike.toString()

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
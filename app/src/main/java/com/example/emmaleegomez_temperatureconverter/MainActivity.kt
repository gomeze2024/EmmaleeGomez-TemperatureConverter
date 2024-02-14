package com.example.emmaleegomez_temperatureconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var fahrenheitView : TextView
    private lateinit var celsiusView : TextView
    private lateinit var fahrenheitSeekBar : SeekBar
    private lateinit var celsiusSeekBar : SeekBar
    private lateinit var interestingMessage : TextView
    private val FAHRENHEIT_LOW : Int = 32

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fahrenheitView = findViewById<TextView>(R.id.fahrenheit)
        celsiusView = findViewById<TextView>(R.id.celsius)
        interestingMessage = findViewById<TextView>(R.id.interesting_message)
        fahrenheitSeekBar = findViewById<SeekBar>(R.id.fahrenheit_seekbar)
        celsiusSeekBar = findViewById<SeekBar>(R.id.celsius_seekbar)

        fahrenheitSeekBar.progress = 106
        celsiusSeekBar.progress = 16

        fahrenheitSeekBar.min = 0
        fahrenheitSeekBar.max = 212
        celsiusSeekBar.min = 0
        celsiusSeekBar.max = 100

        fahrenheitSeekBar.setOnSeekBarChangeListener(object :
        SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seek: SeekBar,
                                       progress: Int, fromUser: Boolean) {
            val fahrenheit : Int = seek.progress
            fahrenheitView.text = fahrenheit.toString()

            val celsius : Int = fahrenheitToCelsius(fahrenheit)
            celsiusSeekBar.progress = celsius
            celsiusView.text = celsius.toString()

            if (celsius < celsiusSeekBar.min) {
                celsiusView.text = celsiusSeekBar.min.toString()
                celsiusSeekBar.progress = celsiusSeekBar.min
            } else {
                celsiusSeekBar.progress = celsius
                celsiusView.text = celsius.toString()

                if (celsius <= 20) {
                    interestingMessage.text = getString(R.string.message_cold)
                } else {
                    interestingMessage.text = getString(R.string.message_warm)

                }            }
        }

        override fun onStartTrackingTouch(seek: SeekBar) {

        }

        override fun onStopTrackingTouch(seek: SeekBar) {
            val fahrenheit : Int = seek.progress

            if (fahrenheit < FAHRENHEIT_LOW) {
                fahrenheitView.text = FAHRENHEIT_LOW.toString()
                fahrenheitSeekBar.progress = FAHRENHEIT_LOW
            }
        }
        })

        celsiusSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                val celsius : Int = seek.progress
                celsiusView.text = celsius.toString()

                val fahrenheit : Int = celsiusToFahrenheit(celsius)
                fahrenheitSeekBar.progress = fahrenheit
                fahrenheitView.text = fahrenheit.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })

    }
    private fun fahrenheitToCelsius(f: Int) : Int {
        val c : Double = (f - 32) * (5.0 / 9)
        return round(c).toInt()
    }

    private fun celsiusToFahrenheit(c: Int) : Int {
        val f : Double = (c * (9.0 / 5)) + 32
        return round(f).toInt()
    }
}
package com.example.emmaleegomez_temperatureconverter

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import org.w3c.dom.Text
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var fahrenheitView : TextView
    private lateinit var celsiusView : TextView
    private lateinit var fahrenheitSeekBar : SeekBar
    private lateinit var celsiusSeekBar : SeekBar
    private lateinit var interestingMessage : TextView
    private val fahrenheitLow : Int = 32

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fahrenheitView = findViewById<TextView>(R.id.fahrenheit)
        celsiusView = findViewById<TextView>(R.id.celsius)
        interestingMessage = findViewById<TextView>(R.id.interesting_message)
        fahrenheitSeekBar = findViewById<SeekBar>(R.id.fahrenheit_seekbar)
        celsiusSeekBar = findViewById<SeekBar>(R.id.celsius_seekbar)

        //Set the temperature bars progress, min, and max
        initializeSeekBars()
        setBackground()

        fahrenheitSeekBar.setOnSeekBarChangeListener(object :
        SeekBar.OnSeekBarChangeListener {

        //Used as reference: https://www.geeksforgeeks.org/seekbar-in-kotlin/

        override fun onProgressChanged(seek: SeekBar,
                                       progress: Int, fromUser: Boolean) {
            val fahrenheit : Int = seek.progress
            val celsius : Int = fahrenheitToCelsius(fahrenheit)

            //Fahrenheit can go below 32, but Celsius will not go below 0.
            updateFahrenheit(fahrenheit)
            updateCelsius(celsius)
        }

        override fun onStartTrackingTouch(seek: SeekBar) {}

        override fun onStopTrackingTouch(seek: SeekBar) {
            val fahrenheit : Int = seek.progress
            if (fahrenheit < fahrenheitLow) {
                val fDegrees : String = fahrenheitLow.toString() +
                        getString(R.string.degrees_fahrenheit)
                fahrenheitView.text = fDegrees
                fahrenheitSeekBar.progress = fahrenheitLow
            }
            updateInterestingMessage(celsiusSeekBar.progress)
        }
        })

        celsiusSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                val celsius : Int = seek.progress
                val fahrenheit : Int = celsiusToFahrenheit(celsius)

                updateCelsius(celsius)
                updateFahrenheit(fahrenheit)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {
                updateInterestingMessage(seek.progress)
            }
        })
    }

    private fun initializeSeekBars() {
        fahrenheitSeekBar.apply {
            progress = 106
            min = 0
            max = 212
        }

        celsiusSeekBar.apply {
            progress = 16
            min = 0
            max = 100
        }
    }

    private fun fahrenheitToCelsius(f: Int) : Int {
        val c : Double = (f - 32) * (5.0 / 9)
        return round(c).toInt()
    }

    private fun celsiusToFahrenheit(c: Int) : Int {
        val f : Double = (c * (9.0 / 5)) + 32
        return round(f).toInt()
    }

    private fun updateFahrenheit(f : Int) {
        fahrenheitSeekBar.progress = f
        val fDegrees : String = f.toString() +
                getString(R.string.degrees_fahrenheit)
        fahrenheitView.text = fDegrees

    }
    private fun updateCelsius(c: Int) {
        val cDegrees : String;
        if (c < celsiusSeekBar.min) {
            celsiusView.text = celsiusSeekBar.min.toString()
            celsiusSeekBar.progress = celsiusSeekBar.min
            cDegrees= celsiusSeekBar.min.toString() + getString(R.string.degrees_celsius)
            celsiusView.text = cDegrees
        } else {
            celsiusSeekBar.progress = c
            celsiusView.text = c.toString()
            cDegrees= c.toString() + getString(R.string.degrees_celsius)
            celsiusView.text = cDegrees
        }
    }

    private fun updateInterestingMessage(c: Int) {
        interestingMessage.text = getString(
            if (c <= 20) R.string.message_cold
            else R.string.message_warm
        )
    }

    private fun setBackground() {
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(0xFF295F97.toInt(),
                0xFF5C97D2.toInt())
        )

        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_layout)
        constraintLayout.background = gradientDrawable
    }
}
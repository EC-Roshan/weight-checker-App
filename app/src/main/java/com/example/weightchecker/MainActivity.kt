package com.example.weightchecker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Create planet array
    private val planets = arrayOf("","Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Moon" )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Define UI elements
        val btnWeight = findViewById<Button>(R.id.btnCalcWeight)
        val spinner = findViewById<Spinner>(R.id.spinnerPlanets)
        val weightInput = findViewById<EditText>(R.id.etWeight)
        val result = findViewById<TextView>(R.id.tvResult)
        val radioKilograms = findViewById<RadioButton>(R.id.radioButton1)
        val radioPounds = findViewById<RadioButton>(R.id.radioButton2)
        val radioKilograms1 = findViewById<RadioButton>(R.id.radioButton3)
        val radioPounds1 = findViewById<RadioButton>(R.id.radioButton4)


        val adapter = ArrayAdapter(this, R.layout.spinner_item, planets)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = adapter

        btnWeight.setOnClickListener {
            val selectedPlanet = spinner.selectedItem.toString()
            val weightText = weightInput.text.toString()

            val weight = weightText.toDoubleOrNull()

            if (weight != null) {
                val isKgInput = radioKilograms.isChecked
                val isLbInput = radioPounds.isChecked
                val isKgOutput = radioKilograms1.isChecked
                val isLbOutput = radioPounds1.isChecked

                if (!isKgInput && !isLbInput) {
                    result.text = "Please select the input unit (kg or lbs)."
                    return@setOnClickListener
                }

                if (!isKgOutput && !isLbOutput) {
                    result.text = "Please select the output unit (kg or lbs)."
                    return@setOnClickListener
                }

                // Convert input weight to both formats
                val inputKg = if (isKgInput) weight else weight * 0.453592
                val inputLb = if (isKgInput) weight * 2.20462 else weight

                val (gravityFactor, explanation) = getGravityFactorAndExplanation(weight ,selectedPlanet)

                if (gravityFactor == 0.0) {
                    result.text = "Please select a valid planet."
                    return@setOnClickListener
                }

                // Calculate planetary weight
                val calculatedWeightInKg = inputKg * gravityFactor
                val calculatedWeightInLbs = inputLb * gravityFactor

                val finalWeight = if (isKgOutput) calculatedWeightInKg else calculatedWeightInLbs
                val unitLabel = if (isKgOutput) "Kg" else "lbs"

                 val formattedWeight = String.format("%.2f", finalWeight)
                result.text = "Your Weight on $selectedPlanet: $formattedWeight $unitLabel\n\nDid you know? $explanation"

            } else {
                result.text = "Please enter a valid weight."
            }
        }
    }
    private fun getGravityFactorAndExplanation(weight: Double, planet: String): Pair<Double, String> {
        val weightOnPlanet: Double
        val description: String

        when (planet) {
            "Mercury" -> {
                weightOnPlanet =  0.38
                description = "Mercury has weak gravity due to its small size—about 38% of Earth's."
            }
            "Venus" -> {
                weightOnPlanet =  0.91
                description = "Venus' gravity is 91% of Earth's, due to its similar size."
            }
            "Earth" -> {
                weightOnPlanet = 1.0
                description = "Earth's gravity defines your normal weight."
            }
            "Mars" -> {
                weightOnPlanet = 0.38
                description = "Mars' gravity is much weaker than Earth's, at only 38%."
            }
            "Jupiter" -> {
                weightOnPlanet =  2.34
                description = "Jupiter is massive, making its gravity 2.34 times stronger than Earth's."
            }
            "Saturn" -> {
                weightOnPlanet =  0.92
                description = "Saturn’s gravity is slightly weaker than Earth's, at 92%."
            }
            "Uranus", "Neptune" -> {
                weightOnPlanet =  1.19
                description = "$planet has stronger gravity due to its large size—1.19 times Earth's."
            }
            "Moon" -> {
                weightOnPlanet =  0.165
                description = "The Moon's gravity is very weak—only 16.5% of Earth's."
            }
            else -> {
                weightOnPlanet = 0.0
                description = "Unknown planet."
            }
        }
        return Pair(weightOnPlanet, description)
    }
    
}

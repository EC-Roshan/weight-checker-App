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
    private val planets = arrayOf(" ","Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Moon" )

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


        val adapter = ArrayAdapter(this, R.layout.spinner_item, planets)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = adapter

        btnWeight.setOnClickListener {
            val selectedPlanet = spinner.selectedItem.toString()
            val weightText = weightInput.text.toString()

            val weight = weightText.toDoubleOrNull()

            if (weight != null) {
                val (calculatedWeight, explanation) = calculateWeightOnPlanet(weight, selectedPlanet)

                var finalWeight = calculatedWeight

                if (radioPounds.isChecked) {
                    finalWeight *= 2.20462 // Convert kg to lbs
                    result.text = "Your Weight on $selectedPlanet: $finalWeight lbs \n\nDo you know that?: $explanation"
               } else if (radioKilograms.isChecked){
                    finalWeight = finalWeight
                   result.text = "Your Weight on $selectedPlanet: $finalWeight Kg \n\nDo you know that?: $explanation"
             }else{
                     result.text= "No Option Selected"
                }
            }
        }
    }

    private fun calculateWeightOnPlanet(weight: Double, planet: String): Pair<Double, String> {
        val weightOnPlanet: Double
        val description: String

        when (planet) {
            "Mercury" -> {
                weightOnPlanet = weight * 0.38
                description = "Mercury has weak gravity due to its small size—about 38% of Earth's."
            }
            "Venus" -> {
                weightOnPlanet = weight * 0.91
                description = "Venus' gravity is 91% of Earth's, due to its similar size."
            }
            "Earth" -> {
                weightOnPlanet = weight
                description = "Earth's gravity defines your normal weight."
            }
            "Mars" -> {
                weightOnPlanet = weight * 0.38
                description = "Mars' gravity is much weaker than Earth's, at only 38%."
            }
            "Jupiter" -> {
                weightOnPlanet = weight * 2.34
                description = "Jupiter is massive, making its gravity 2.34 times stronger than Earth's."
            }
            "Saturn" -> {
                weightOnPlanet = weight * 0.92
                description = "Saturn’s gravity is slightly weaker than Earth's, at 92%."
            }
            "Uranus", "Neptune" -> {
                weightOnPlanet = weight * 1.19
                description = "$planet has stronger gravity due to its large size—1.19 times Earth's."
            }
            "Moon" -> {
                weightOnPlanet = weight * 0.165
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
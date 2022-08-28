package khamdamov.dilmurod.a7minsworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import khamdamov.dilmurod.a7minsworkout.Utils.Companion.changeStatusBarColor
import khamdamov.dilmurod.a7minsworkout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiactivityBinding

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBarColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            ), false
        )

        setSupportActionBar(binding.toolbarBmiActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }

        binding.btnCalculateUnits.setOnClickListener {
            if (currentVisibleView.equals(METRIC_UNITS_VIEW)) {
                if (validateMetricUnits()) {
                    val heightValue: Float =
                        binding.etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue: Float = binding.etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                if (validateUSUnits()) {
                    val usUnitHeightValueFeet: String = binding.etUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch: String = binding.etUsUnitHeightInch.text.toString()
                    val usUnitWeightValue: Float = binding.etUsUnitWeight.text.toString().toFloat()
                    val heightValue: Float =
                        usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))
                    displayBMIResult(bmi)

                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        makeVisibleMetricUnitsView()
        binding.rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }

        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilMetricUnitHeight.visibility = View.VISIBLE
        binding.tilMetricUnitWeight.visibility = View.VISIBLE

        binding.etMetricUnitHeight.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()

        binding.tilUsUnitWeight.visibility = View.GONE
        binding.llUsUnitsHeight.visibility = View.GONE

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding.tilMetricUnitHeight.visibility = View.GONE
        binding.tilMetricUnitWeight.visibility = View.GONE

        binding.etUsUnitWeight.text!!.clear()
        binding.etUsUnitHeightFeet.text!!.clear()
        binding.etUsUnitHeightInch.text!!.clear()

        binding.tilUsUnitWeight.visibility = View.VISIBLE
        binding.llUsUnitsHeight.visibility = View.VISIBLE

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding.llDisplayBMIResult.visibility = View.VISIBLE
        /*binding.tvYourBMI.visibility = View.VISIBLE
        binding.tvBMIValue.visibility = View.VISIBLE
        binding.tvBMIType.visibility = View.VISIBLE
        binding.tvBMIDescription.visibility = View.VISIBLE*/

        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBMIValue.text = bmiValue // Value is set to TextView
        binding.tvBMIType.text = bmiLabel // Label is set to TextView
        binding.tvBMIDescription.text = bmiDescription // Description is set to TextView

    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true
        if (binding.etUsUnitHeightFeet.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etUsUnitHeightInch.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

}
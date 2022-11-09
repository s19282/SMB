package com.example.smb_p01

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.smb_p01.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sp.edit()
        getSettingsFromSP()
        when (sp.getString("backgroundColor", "#FF03A9F4")) {
            "#FF03A9F4" -> binding.radioGroup.check(R.id.radioButtonBlue)
            "#FF8BC34A" -> binding.radioGroup.check(R.id.radioButtonGreen)
            "#FFF6675D" -> binding.radioGroup.check(R.id.radioButtonRed)
        }
        binding.saveButton.setOnClickListener {
            editor.putString("fontSize", binding.fontSizeValue.text.toString())
            when (binding.radioGroup.checkedRadioButtonId) {
                R.id.radioButtonBlue -> editor.putString("backgroundColor", "#FF03A9F4")
                R.id.radioButtonGreen -> editor.putString("backgroundColor", "#FF8BC34A")
                R.id.radioButtonRed -> editor.putString("backgroundColor", "#FFF6675D")
            }
            editor.apply()
            getSettingsFromSP()
        }


    }

    private fun getSettingsFromSP() {
        binding.constraintLayout.setBackgroundColor(
            Color.parseColor(
                sp.getString(
                    "backgroundColor",
                    "#50F1A0"
                )
            )
        )
        binding.settingsMainText.textSize = sp.getString("fontSize", "20")!!.toFloat()
        binding.fontSize.textSize = sp.getString("fontSize", "20")!!.toFloat()
        binding.backgroundColor.textSize = sp.getString("fontSize", "20")!!.toFloat()
    }
}
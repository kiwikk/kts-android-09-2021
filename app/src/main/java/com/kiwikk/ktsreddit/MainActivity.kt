package com.kiwikk.ktsreddit

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var btn_lang: Button
    private lateinit var imageView: ImageView
    private lateinit var tv: TextView
    private var color = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn_hello)
        btn_lang = findViewById(R.id.btn_hello_change_lang)
        imageView = findViewById(R.id.iv_hello)
        tv = findViewById(R.id.tv_hello)

        setLayoutColor(savedInstanceState)

        var visibility = savedInstanceState?.getBoolean(BTN_CHANGE_KEY) ?: false
        setVisibility(imageView, tv, visibility)

        btn.setOnClickListener {
            visibility = imageView.visibility == View.GONE && tv.visibility == View.GONE
            setVisibility(imageView, tv, visibility)
        }

        btn_lang.setOnClickListener {
            changeLocale(savedInstanceState)
        }
    }

    private fun setLayoutColor(savedInstanceState: Bundle?) {
        val layout = findViewById<ConstraintLayout>(R.id.constraint_hello)
        if (savedInstanceState != null) {
            when(savedInstanceState.getInt(COLOR_KEY)){
                Color.BLACK -> {
                    color = Color.WHITE
                    tv.setTextColor(Color.BLACK)
                    Log.i(TAG, "COLOR NOW = BLACK")
                }
                else -> {
                    color = Color.BLACK
                    tv.setTextColor(Color.WHITE)
                    Log.i(TAG, "COLOR NOW = WHITE")
                }
            }
        } else{
            color = Color.WHITE
        }
        layout.setBackgroundColor(color)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val visibility = imageView.visibility == View.VISIBLE && tv.visibility == View.VISIBLE
        val locale = Locale.getDefault().language

        outState.putBoolean(BTN_CHANGE_KEY, visibility)
        outState.putString(LANGUAGE_KEY, locale)
        outState.putInt(COLOR_KEY, color)

        Log.i(TAG, "$BTN_CHANGE_KEY = $visibility")
        Log.i(TAG, "$LANGUAGE_KEY = $locale")
    }

    fun setVisibility(imageView: ImageView, textView: TextView, visibility: Boolean) {
        imageView.isVisible = visibility
        textView.isVisible = visibility
    }

    fun changeLocale(savedInstanceState: Bundle?) {
        val rnd = Random.nextDouble(1.0)

        if (rnd > 0.1) {
            var langCode =
                savedInstanceState?.getString(LANGUAGE_KEY) ?: Locale.getDefault().language
            Log.i(TAG, "language = $langCode, locale = ${Locale.getDefault().language}")

            when (langCode) {
                "en" -> {
                    langCode = "ru"
                }
                "ru" -> {
                    langCode = "en"
                }
            }

            val locale = Locale(langCode)
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            Log.i(TAG, "change language to $langCode")
            recreate()
        } else {
            Toast.makeText(this, getString(R.string.rest), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val LANGUAGE_KEY = "LANGUAGE"
        private const val BTN_CHANGE_KEY = "BUTTON_CHANGED"
        private const val COLOR_KEY = "HELLO_LAYOUT_COLOR"
        private val TAG = MainActivity::class.simpleName.toString()
    }
}
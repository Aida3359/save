package kz.kolesateam.confapp

import kz.kolesateam.confapp.R
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

private const val DEFAULT_NAME = "Мир"

class TestHelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_hello)

        val nameText: TextView = findViewById(R.id.activity_test_hello)
        nameText.text = getSavedUserName()
    }

    private fun getSavedUserName(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            APPLICATION_SHARED_PREFERENCES,

            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(USER_NAME_KEY, DEFAULT_NAME) ?: DEFAULT_NAME
    }
}
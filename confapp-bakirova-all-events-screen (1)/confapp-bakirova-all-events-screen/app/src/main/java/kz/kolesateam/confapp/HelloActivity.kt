package kz.kolesateam.confapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.presentation.common.AbstractTextWatcher

const val APPLICATION_SHARED_PREFERENCES = "memory"
const val USER_NAME_KEY = "user_name"

class HelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val openHelloButton: Button = findViewById(R.id.activity_hello_open_hello_button)
        val nameEditText: EditText = findViewById(R.id.activity_hello_name_edit_text)

        nameEditText.addTextChangedListener(AbstractTextWatcher { text ->
            openHelloButton.isEnabled = text.toString().isNotBlank()
        })

        openHelloButton.setOnClickListener {
            saveUserName(nameEditText.text.toString())
            navigateToUpcomingEventsActivity()
        }
    }

    private fun saveUserName(userName: String) {

        val sharedPreferences: SharedPreferences = getSharedPreferences(
            APPLICATION_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(USER_NAME_KEY, userName)
        editor.apply()
    }

    private fun navigateToUpcomingEventsActivity() {
        val upcomingEventsActivityIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(upcomingEventsActivityIntent)
    }
}
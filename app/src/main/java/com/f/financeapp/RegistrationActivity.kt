package com.f.financeapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.f.financeapp.ui.activities.FinanceActivity

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        title = "Регистрация"

        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPass = findViewById<EditText>(R.id.etPass)
        val etPassRepeat = findViewById<EditText>(R.id.etPassRepeat)

        val btnReg = findViewById<Button>(R.id.btnReg)

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preferences_file), Context.MODE_PRIVATE
        )

        btnReg.setOnClickListener {
            val login = etLogin.text.toString()
            val pass = etPass.text.toString()
            val passRepeat = etPassRepeat.text.toString()

            if (login != "" && pass != "" && passRepeat != "") {
                if (pass == passRepeat) {
                    with(sharedPref.edit()) {
                        putString(getString(R.string.prefs_login), login)
                        putString(getString(R.string.prefs_pass), pass)
                        apply()
                    }
                    Toast.makeText(this, "Регистрация пройдена успешно!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, FinanceActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Пароль и повтор пароля должны совпадать!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
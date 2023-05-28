package com.f.financeapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.f.financeapp.ui.activities.FinanceActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = "Авторизация"

        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPass = findViewById<EditText>(R.id.etPass)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnReg = findViewById<Button>(R.id.btnReg)

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preferences_file), Context.MODE_PRIVATE)

        btnLogin.setOnClickListener{
            val login = etLogin.text.toString()
            val pass = etPass.text.toString()

            if (login != "" && pass != ""){
                val prefsLogin = sharedPref.getString(getString(R.string.prefs_login), "")
                val prefsPass = sharedPref.getString(getString(R.string.prefs_pass), "")
                if (prefsLogin == login){
                    if (prefsPass == pass){
                        val intent = Intent(this, FinanceActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Неверный пароль!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Нет пользователя с таким логином!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Поля логин и пароль должны быть заполнены!", Toast.LENGTH_SHORT).show()
            }
        }

        btnReg.setOnClickListener{
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
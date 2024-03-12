package com.example.gadgetsfuture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Registrarse : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtApellidos: EditText
    private lateinit var txtNum: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtContras: EditText
    private lateinit var txtConfirmContra: EditText
    private lateinit var nameError: TextView
    private lateinit var apellidosError: TextView
    private lateinit var numError: TextView
    private lateinit var emailError: TextView
    private lateinit var contraError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        txtNombre = findViewById(R.id.txtNombre)
        txtApellidos = findViewById(R.id.txtApellidos)
        txtNum = findViewById(R.id.txtNum)
        txtEmail = findViewById(R.id.txtEmail)
        txtContras = findViewById(R.id.txtContras)
        txtConfirmContra = findViewById(R.id.txtConfirmContra)

        nameError = findViewById(R.id.nameError)
        apellidosError = findViewById(R.id.apellidosError)
        numError = findViewById(R.id.numError)
        emailError = findViewById(R.id.emailError)
        contraError = findViewById(R.id.contraError)

        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        btnRegistrarse.setOnClickListener {
            validarCampos()
        }
    }
    fun  inicioS(view: View) {
        val intent = Intent(this, InicioSesion::class.java)
        startActivity(intent)
        this@Registrarse.overridePendingTransition(
            R.anim.animate_slide_in_left,
            R.anim.animate_slide_out_right
        )
    }

    private fun validarCampos() {
        val nombre = txtNombre.text.toString()
        val apellidos = txtApellidos.text.toString()
        val num = txtNum.text.toString()
        val email = txtEmail.text.toString()
        val contras = txtContras.text.toString()
        val confirmContra = txtConfirmContra.text.toString()

        var isValid = true

        if (nombre.isEmpty()) {
            nameError.visibility = View.VISIBLE
            nameError.text = "El nombre es requerido"
            isValid = false
        } else {
            nameError.visibility = View.VISIBLE
            nameError.text =""
        }

        if (apellidos.isEmpty()) {
            apellidosError.visibility = View.VISIBLE
            apellidosError.text = "Los apellidos son requeridos"
            isValid = false
        } else {
            apellidosError.visibility = View.VISIBLE
            apellidosError.text = ""
        }

        if (num.isEmpty()) {
            numError.visibility = View.VISIBLE
            numError.text = "El número es requerido"
            isValid = false
        } else {
            numError.visibility = View.VISIBLE
            numError.text =""
        }

        if (email.isEmpty()) {
            emailError.visibility = View.VISIBLE
            emailError.text = "El correo electrónico es requerido"
            isValid = false
        } else {
            emailError.visibility = View.VISIBLE
            emailError.text = ""
        }

        if (contras.isEmpty()) {
            contraError.visibility = View.VISIBLE
            contraError.text = "La contraseña es requerida"
            isValid = false
        } else {
            contraError.visibility = View.VISIBLE
            contraError.text = ""
        }

        if (confirmContra.isEmpty()) {
            contraError.visibility = View.VISIBLE
            contraError.text = "La confirmación de contraseña es requerida"
            isValid = false
        } else if (contras != confirmContra) {
            contraError.visibility = View.VISIBLE
            contraError.text = "Las contraseñas no coinciden"
            isValid = false
        } else {
            contraError.visibility = View.VISIBLE
            contraError.text = ""
        }

        if (isValid) {
            // Aquí podré realizar el registro del usuario
            // llamar a un método en el ViewModel o Presenter
        }
    }
}
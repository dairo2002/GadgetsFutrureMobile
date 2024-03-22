package com.example.gadgetsfuture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gadgetsfuture.config.config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

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
            /*validarCampos()
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    peticionSignup()
                } catch (error: Exception) {
                    Toast.makeText(this@Registrarse, "Error en la petición de inicio de sesión: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }*/

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

    suspend fun peticionSignup(){
        var url=config().urlBase+"/cuenta/api/signup/v1/"
        val queue=Volley.newRequestQueue(this)
        val parametros=JSONObject()
        parametros.put("nombre", txtNombre.text)
        parametros.put("apellido", txtApellidos.text)
        parametros.put("correo_electronico", txtEmail.text)
        parametros.put("telefono", txtNum.text)
        parametros.put("password", txtContras.text)
        // Falta el campo de confirmar contraseña


        val request= JsonObjectRequest(
            Request.Method.POST,
            url,
            parametros,
            {response ->
                Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show()
                // Corregir
                //val fragmento=Home_fragment()
                //supportFragmentManager.beginTransaction().replace(R.id.container, fragmento).addToBackStack(null).commit()
            },
            {error ->
                Toast.makeText(this, "Error en la solicitud: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }



}
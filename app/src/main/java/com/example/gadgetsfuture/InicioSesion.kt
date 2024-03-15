package com.example.gadgetsfuture

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap.Config
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.ActivityNavigator
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

class InicioSesion : AppCompatActivity() {
    lateinit var correoEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    lateinit var passwordError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
        setContentView(R.layout.activity_inicio_sesion)

        correoEditText = findViewById(R.id.txtCorreo)
        passwordEditText = findViewById(R.id.txtContra)

        loginButton = findViewById(R.id.btnIniciarS)
        passwordError = findViewById(R.id.passwordError)

        // Verificar si las credenciales están guardadas
        /*if (checkCredentials()) {
            // Si las credenciales están guardadas, iniciar la actividad principal
            val intent = Intent(this, FrmPrincipal::class.java)
            startActivity(intent)
            finish()
        }*/

        // Forzar el modo claro
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()


        loginButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    peticionLogin()
                } catch (error: Exception) {
                    Toast.makeText(this@InicioSesion, "Error en la petición de inicio de sesión: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }


    } // Cierra onCreate

    /*fun olvideContra(view: View) {
        val olvideContra = Intent(this, Recuperar_Contrasena::class.java)
        startActivity(olvideContra)
        this@InicioSesion.overridePendingTransition(
            R.anim.animate_slide_left_enter,
            R.anim.animate_slide_left_exit
        )
    }

    fun registrarse(view: View) {
        val regis = Intent(this, Registrarse::class.java)
        startActivity(regis)
        this@InicioSesion.overridePendingTransition(
            R.anim.animate_slide_left_enter,
            R.anim.animate_slide_left_exit
        )
    }*/

    /*fun inicioSesion(view: View) {
        val username = correoEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username == "admin@gmail.com" && password == "admin") {
            // Inicio de sesión exitoso
            saveCredentials(username, password) // Guardar las credenciales
            val intent = Intent(this, FrmPrincipal::class.java)
            startActivity(intent)
            finish()
        } else {
            passwordError.visibility = View.VISIBLE
            passwordError.text = "Correo o contraseña incorrecta"
        }
    }*/

    // Función para guardar las credenciales
    /*private fun saveCredentials(username: String, password: String) {
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    // Función para verificar si las credenciales están guardadas
    private fun checkCredentials(): Boolean {
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null)
        val password = sharedPref.getString("password", null)
        return !username.isNullOrEmpty() && !password.isNullOrEmpty()
    }
 */

    suspend fun peticionLogin(){
        var url=config().urlBase+"cuenta/api/login/v1/"
        val queue=Volley.newRequestQueue(this)
        val parametros=JSONObject()
        parametros.put("correo_electronico", correoEditText.text)
        parametros.put("password", passwordEditText.text)

        val request=JsonObjectRequest(
            Request.Method.POST,
            url,
            parametros,
            {response ->
                //val token = response.getString("token")
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                // Corregir
                val fragmento=Home_fragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragmento).addToBackStack(null).commit()
            },
            {error ->
                // Corregir esta parte
                if (error is AuthFailureError) {
                    Toast.makeText(this, "Error de autenticación: {$error}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error en la solicitud: ${error.message}", Toast.LENGTH_SHORT).show()

                }
            }
        )
        queue.add(request)

    }


}

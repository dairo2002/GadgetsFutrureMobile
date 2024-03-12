package com.example.gadgetsfuture

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import de.hdodenhof.circleimageview.CircleImageView

class UserFragment : Fragment() {

    private lateinit var imgProfile: CircleImageView
    private lateinit var btnEditarImg: ImageButton
    private var defaultDrawableResId: Int = R.drawable.userprofile
    private var selectedImageUri: Uri? = null

    private val REQUEST_IMAGE_CODE = 1001
    private val REQUEST_PERMISSION_CODE = 1002

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        imgProfile = view.findViewById(R.id.imgProfile)
        btnEditarImg = view.findViewById(R.id.btnEditarImg)
        val cardCerrarSesion = view.findViewById<CardView>(R.id.cardCerrarSesion)

        // Cargar la imagen de perfil guardada en SharedPreferences
        loadProfileImageFromPrefs()

        btnEditarImg.setOnClickListener {
            checkPermissionAndOpenGallery()
        }

        cardCerrarSesion.setOnClickListener {
            // Aquí va la lógica para cerrar sesión
            // Por ejemplo, borrar las credenciales de inicio de sesión almacenadas en SharedPreferences
            // Luego, redirigir al usuario a la actividad de inicio de sesión

            // Suponiendo que utilizas SharedPreferences para almacenar el estado de inicio de sesión
            val sharedPref = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            // Redirigir al usuario a la actividad de inicio de sesión
            val intent = Intent(requireContext(), InicioSesion::class.java)
            startActivity(intent)
            requireActivity().finish() // Cierra la actividad actual (FrmPrincipal)
        }



        return view
    }

    private fun checkPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION_CODE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            selectedImageUri = selectedImage
            imgProfile.setImageURI(selectedImage)

            // Guardar la URI de la imagen seleccionada en SharedPreferences
            saveProfileImageToPrefs(selectedImageUri)
        }
    }

    private fun saveProfileImageToPrefs(uri: Uri?) {
        val sharedPref = activity?.getSharedPreferences("MyPrefs", 0)
        val editor = sharedPref?.edit()
        editor?.putString("profileImageUri", uri.toString())
        editor?.apply()
    }

    private fun loadProfileImageFromPrefs() {
        val sharedPref = activity?.getSharedPreferences("MyPrefs", 0)
        val uriString = sharedPref?.getString("profileImageUri", null)
        uriString?.let { uri ->
            selectedImageUri = Uri.parse(uri)
            imgProfile.setImageURI(selectedImageUri)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                // Permiso denegado, mostrar un mensaje al usuario o realizar alguna otra acción
            }
        }
    }
}



package com.example.gadgetsfuture

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.gadgetsfuture.config.config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detalle_producto_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class detalle_producto_fragment : Fragment() {

    private lateinit var view: View
    private var id_producto: Int = 0
    //private var categoriaSlug = ""
    //private var productoSlug = ""

    lateinit var lblNombre: TextView
    lateinit var lblPrecio: TextView
    lateinit var imgProducto: ImageView
    lateinit var lbldescripcion: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            //Obtner le id
            id_producto=it.getInt("id_productoH")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        view = inflater.inflate(R.layout.fragment_detalle_producto_fragment, container, false)

        lblNombre=view.findViewById(R.id.lblNombreDetalleProducto)
        lblPrecio=view.findViewById(R.id.lblPrecioDetalleProducto)
        imgProducto=view.findViewById(R.id.imgDetalleProducto)
        lbldescripcion=view.findViewById(R.id.lblDetalleDescripcion)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                peticionDetalleProducto()
            } catch (error: Exception)    {
                Toast.makeText(activity, "Error en la petición: {$error}", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detalle_producto_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detalle_producto_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    suspend fun peticionDetalleProducto(){
        /*
            http://192.168.1.6:8000/tienda/categoria/api/detail_product/v1/portatiles/potatil-chuwi-larkbook/

           - Por slug
            "categoria/api/detail_product/v1/<slug:category_slug>/<slug:product_slug>/"
            "categoria/api/detail_product/v1/"

           - Por ids categoria y producto
            recuerde que por id lleva la s al final PRODUCTS

            "categoria/api/detail_products/v1/<int:category_id>/<int:product_id>/
            "categoria/api/detail_products/v1/"

           - Por id de producto
            "categoria/api/detail_products/v1/<int:product_id>/
         */

        var url=config().urlBase+"tienda/categoria/api/detail_products/v1/$id_producto/"
        var queue = Volley.newRequestQueue(activity)
        var request= JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val nombre = response.getString("nombre")
                    val precio = response.getDouble("precio")
                    val descripcion = response.getString("descripcion")
                    val imgURL ="http://192.168.1.6:8000/"+ response.getString("imagen")

                    lblNombre.text = nombre
                    lblPrecio.text = "$precio"
                    lbldescripcion.text = descripcion
                    Glide.with(this).load(imgURL).into(imgProducto)


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(activity, "Error en la solicitud: $error", Toast.LENGTH_SHORT).show()
                Log.e("PeticionDetalleProducto", "Error en la solicitud", error)
            }
        )
        queue.add(request)
    }


}
package com.example.gadgetsfuture;

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.gadgetsfuture.R
import com.example.gadgetsfuture.adapter.adaterCateogriaStore
import com.example.gadgetsfuture.config.config
import com.example.gadgetsfuture.model.Categoria
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.lang.Exception


class store_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var view: View
    //lateinit var lblNombre: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        view = inflater.inflate(R.layout.fragment_store_fragment, container, false)

        recyclerView=view.findViewById(R.id.RVCategorias)
        //lblNombre=view.findViewById(R.id.lblNombreCategoria)


        GlobalScope.launch(Dispatchers.Main) {
            try {
                peticionListaCategoria()
            } catch (error: Exception)    {
                Toast.makeText(activity, "Error en la petición: {$error}", Toast.LENGTH_SHORT).show()
            }
        }

        return view

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            store_fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    suspend fun peticionListaCategoria(){
        var url= config().urlBase+"tienda/categorias/api/category/v1/"
        var queue= Volley.newRequestQueue(activity)
        var request= JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response->
                try {
                    //val categoriasList = mutableListOf<Categoria>()
                    /*for (i in 0 until  response.length()){
                        val categoria = response.getJSONObject(i)
                        val nombre = categoria.getString("nombre")
                        //lblNombre.text=nombre
                    }
                    */

                    recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
                    val adapter = adaterCateogriaStore(activity, listaCategoria = response)
                    recyclerView.adapter = adapter

                } catch (e: JSONException){
                    e.printStackTrace()
                }
            },
            {error->
                Toast.makeText(activity, "Error en la solicitud: {$error}", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(request)
    }


}
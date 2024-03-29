package com.example.gadgetsfuture;

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.gadgetsfuture.R
import com.example.gadgetsfuture.adapter.adapterHome
import com.example.gadgetsfuture.adapter.adapterProductoStore
import com.example.gadgetsfuture.adapter.adaterCateogriaStore
import com.example.gadgetsfuture.config.config
import com.example.gadgetsfuture.model.Categoria
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class store_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var view: View
    private var  id_producto_categoria = 0

    lateinit var lblNombre:TextView
    lateinit var imgProduStore: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            id_producto_categoria=it.getInt("id_producto_categoria")

        }
    }

    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        view = inflater.inflate(R.layout.fragment_store_fragment, container, false)

        // Lista de categorias
        recyclerView=view.findViewById(R.id.RVCategorias)
        // Lista de productos
        recyclerView=view.findViewById(R.id.RVProductosStore)


        GlobalScope.launch(Dispatchers.Main) {
            try {
                peticionListaCategoria()
            } catch (error: Exception)    {
                Toast.makeText(activity, "Error en la petición: {$error}", Toast.LENGTH_SHORT).show()
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                peticionListaProductosStore()
            } catch (error: Exception)    {
                Toast.makeText(activity, "Error en la petición: {$error}", Toast.LENGTH_SHORT).show()
            }
        }


        GlobalScope.launch(Dispatchers.Main) {
            try {
                // CORREGIR
               //peticionProductosUnaCategoria()
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




    suspend fun peticionProductosUnaCategoria(){
        var url= config().urlBase+"tienda/categoria/api/store/v1/$id_producto_categoria/"
        var queue= Volley.newRequestQueue(activity)
        var request= JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response->
                try {

                    recyclerView.layoutManager = GridLayoutManager(activity, 2)
                    val adater = adapterProductoStore(activity, listaProdStore = response)
                    //val adater = adapterProductoStore(requireContext(), listaProdStore = response)
                    adater.onclick={
                        val bundle=Bundle()
                        bundle.putInt("id_producto_categoria",it.getInt("id"))
                        val transaction=requireFragmentManager().beginTransaction()
                        var fragmento=store_fragment()
                        fragmento.arguments=bundle
                        transaction.replace(R.id.container, fragmento).commit()
                        transaction.addToBackStack(null)
                    }
                    recyclerView.adapter=adater

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


    suspend fun peticionListaCategoria(){
        var url= config().urlBase+"tienda/categorias/api/category/v1/"
        var queue= Volley.newRequestQueue(activity)
        var request= JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response->
                try {
                    // CODIGO DE ID

                    recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    val adapter = adaterCateogriaStore(activity, listaCategoria = response)
                    recyclerView.adapter = adapter


                   /* recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    val adapter = adaterCateogriaStore(activity, listaCategoria = response)
                    adapter.onclick={
                        val bundle=Bundle()
                        bundle.putInt("id_producto_categoria",it.getInt("id"))
                        val transaction=requireFragmentManager().beginTransaction()
                        var fragmento=store_fragment()
                        fragmento.arguments=bundle
                        transaction.replace(R.id.container, fragmento).commit()
                        transaction.addToBackStack(null)
                    }*/
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


    suspend fun peticionListaProductosStore(){
        var url=config().urlBase+"api/list_product/v1/"
        var queue= Volley.newRequestQueue(activity)
        var request= JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response->
                try {
                    recyclerView.layoutManager = GridLayoutManager(activity, 2)
                    val adater = adapterProductoStore(activity, listaProdStore = response)
                    //val adater = adapterProductoStore(requireContext(), listaProdStore = response)
                    adater.onclick={
                        val bundle=Bundle()
                        bundle.putInt("id_productoH",it.getInt("id"))
                        val transaction=requireFragmentManager().beginTransaction()
                        var fragmento=detalle_producto_fragment()
                        fragmento.arguments=bundle
                        transaction.replace(R.id.container, fragmento).commit()
                        transaction.addToBackStack(null)
                    }
                    recyclerView.adapter=adater
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


    /*fun cargarLista(listaProductos:JSONArray){
        recycler.layoutManager= LinearLayoutManager(activity)
        var adapter= adapterHome(activity, listaProductos)
        // Cambio de fragmento desde otro
        adapter.onclick= {
            val bundle=Bundle()
            //bundle.putInt("id_productoH",it.getInt("id"))
            bundle.putInt("id_productoH",it.getInt("id"))
            val transaction=requireFragmentManager().beginTransaction()
            var fragmento=detalle_producto_fragment()
            fragmento.arguments=bundle
            transaction.replace(R.id.container, fragmento).commit()
            //transaction.replace(R.id.contenedor_view_home, fragmento).commit()
            transaction.addToBackStack(null)
        }
        recycler.adapter=adapter
    }*/

}
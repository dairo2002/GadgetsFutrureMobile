package com.example.gadgetsfuture.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gadgetsfuture.R
import com.example.gadgetsfuture.model.Categoria
import org.json.JSONArray
import org.json.JSONObject

class adaterCateogriaStore(var context: Context?, var listaCategoria:JSONArray)
    :RecyclerView.Adapter<adaterCateogriaStore.MyHolder>(){

        inner class MyHolder(ItemCategoria : View):RecyclerView.ViewHolder(ItemCategoria) {
            lateinit var btnNomListCategoria: TextView

            init {
                btnNomListCategoria=itemView.findViewById(R.id.btnNomListCategoria)
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaterCateogriaStore.MyHolder {
        var itemView= LayoutInflater.from(context).inflate(R.layout.item_categoria,parent,false)
        return MyHolder(itemView)
    }

    var onclick:((JSONObject)->Unit)?=null
    override fun onBindViewHolder(holder: adaterCateogriaStore.MyHolder, position: Int) {
        val categoria = listaCategoria.getJSONObject(position)

        // Tremos la categoiras
        holder.btnNomListCategoria.text=categoria.getString("nombre")

        // Accion para que nos lleve a los productos de una categoria
        holder.btnNomListCategoria.setOnClickListener {
            onclick?.invoke(categoria)
        }



    }

    override fun getItemCount(): Int {
        return listaCategoria.length()
    }


}
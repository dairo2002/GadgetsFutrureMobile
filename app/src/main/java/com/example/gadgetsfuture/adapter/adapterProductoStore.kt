package com.example.gadgetsfuture.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gadgetsfuture.R
import org.json.JSONArray
import org.json.JSONObject


class adapterProductoStore(var context: Context?, var listaProdStore:JSONArray)
    :RecyclerView.Adapter<adapterProductoStore.MyHolder>(){

    inner class MyHolder(ItemCategoria : View):RecyclerView.ViewHolder(ItemCategoria) {
        lateinit var lblNombre: TextView
        lateinit var imgView : ImageView
        lateinit var btnDetalleProducto: Button

        init {
            lblNombre=itemView.findViewById(R.id.lblNombreT)
            imgView=itemView.findViewById(R.id.imgProdTienda)
            btnDetalleProducto=itemView.findViewById(R.id.btnDetalleProdT)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterProductoStore.MyHolder {
        var itemView= LayoutInflater.from(context).inflate(R.layout.item_prod_store,parent,false)
        return MyHolder(itemView)
    }

    var onclick:((JSONObject)->Unit)?=null
    override fun onBindViewHolder(holder: adapterProductoStore.MyHolder, position: Int) {
        val producto = listaProdStore.getJSONObject(position)

        val nombre=producto.getString("nombre")
        val imgProductoUrl= "http://192.168.1.6:8000/"+producto.getString("imagen")

        holder.lblNombre.text = nombre
        Glide.with(holder.itemView.context).load(imgProductoUrl).into(holder.imgView)

        holder.btnDetalleProducto.setOnClickListener {
            onclick?.invoke(producto)
        }

    }

    override fun getItemCount(): Int {
        return listaProdStore.length()
    }

}


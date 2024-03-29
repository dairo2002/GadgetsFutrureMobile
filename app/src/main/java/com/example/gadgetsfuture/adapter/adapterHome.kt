package com.example.gadgetsfuture.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gadgetsfuture.R
import org.json.JSONArray
import org.json.JSONObject

class adapterHome (var context: Context?, var  listaProductoH:JSONArray)
    :RecyclerView.Adapter<adapterHome.MyHolder>() {

    inner class MyHolder(Item: View):RecyclerView.ViewHolder(Item){
        lateinit var lblnombre:TextView
        lateinit var lblprecio:TextView
        lateinit var lblprecioDescunto:TextView
        lateinit var lblporcentajeDescunto:TextView
        lateinit var btnImgProducto:ImageButton
        lateinit var btnImgCarrito:ImageButton

        init {
            lblnombre=itemView.findViewById(R.id.lblNombreH)
            lblprecio=itemView.findViewById(R.id.lblPrecioH)
            lblprecioDescunto=itemView.findViewById(R.id.lblDescuentoPrecioH)
            lblporcentajeDescunto=itemView.findViewById(R.id.lblPorcenDescuentoH)
            btnImgProducto=itemView.findViewById(R.id.btnImgProductoH)
            btnImgCarrito=itemView.findViewById(R.id.btnImgCarritoH)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterHome.MyHolder {
        var itemView=LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        return MyHolder(itemView)
    }

    // Variable que almacena la funcion onclick
    var onclick:((JSONObject)->Unit)?=null

    override fun onBindViewHolder(holder: adapterHome.MyHolder, position: Int) {
        val producto = listaProductoH.getJSONObject(position)
        val nombre=producto.getString("nombre")
        val precio=producto.getDouble("precio")
        val btnImgProductoUrl= "http://192.168.1.6:8000/"+producto.getString("imagen")
        //val precioDescunto=producto.getDouble()
        //val porcentajeDescunto=producto.getDouble()

        //Variable para ir al carrito de compras
        //val btnImgCarrito

        holder.lblnombre.text = nombre
        holder.lblprecio.text = "$precio"
        Glide.with(holder.itemView.context).load(btnImgProductoUrl).into(holder.btnImgProducto)

        // Imagen button nos lleva a detalle del producto
        holder.btnImgProducto.setOnClickListener {
            onclick?.invoke(producto)
        }


    }

    override fun getItemCount(): Int {
        return  listaProductoH.length()
    }


}
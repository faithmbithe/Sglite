package com.faith.sglite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

val rvProducts:RecyclerView=findViewById(R.id.recyclerViewProducts)

        val db = DBHelper(this,null)

        val productsList=ArrayList<Product>()

        val cursor = db.getProducts()

        while (cursor!!.moveToNext()){
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val quantity=cursor.getInt(2)
            val price=cursor.getInt(3)
            val product=Product(id,name,quantity, price)
            productsList.add(product)
        }
        rvProducts.layoutManager=LinearLayoutManager(this)

        val adapter =CustomAdapter(productsList)

        rvProducts.adapter=adapter
    }
}
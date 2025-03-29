package com.example.torushoppingapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.torushoppingapp.Activity.OrderDetailActivity
import com.example.torushoppingapp.Domain.OrderModel
import com.example.torushoppingapp.databinding.ViewholderOrderListingBinding

class OrderListAdapter (val orders:MutableList<OrderModel>)
    : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
        lateinit var context: Context
    class ViewHolder(val binding: ViewholderOrderListingBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderOrderListingBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderListAdapter.ViewHolder, position: Int) {
        val order = orders[position]
        fun bindCommonData(
            orderIdText:String,
            totalPriceText: String,
            statusText:String
        ){
            holder.binding.orderIdText.text = orderIdText
            holder.binding.totalPriceText.text = totalPriceText.toString()
            holder.binding.statusText.text = statusText

            holder.itemView.setOnClickListener {
                val intent = Intent(context, OrderDetailActivity::class.java)
                intent.putExtra("object", orders[position])
                context.startActivity(intent)
            }
        }

        Toast.makeText(context, order.totalPrice.toString() , Toast.LENGTH_SHORT).show()
        bindCommonData(order.id, "${order.totalPrice}", order.status)
    }

    override fun getItemCount(): Int = orders.size
}
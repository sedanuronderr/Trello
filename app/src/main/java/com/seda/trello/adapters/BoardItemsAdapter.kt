package com.seda.trello.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seda.trello.R
import com.seda.trello.databinding.ItemBoardBinding
import com.seda.trello.model.Board

class BoardItemsAdapter(private val context: Context,val board: ArrayList<Board>,private val clickListener:(Board)->Unit):
    RecyclerView.Adapter<BoardItemsAdapter.MyViewHolder>() {


 class MyViewHolder(val binding: ItemBoardBinding):RecyclerView.ViewHolder(binding.root){


}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

             return  MyViewHolder(ItemBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
val model = board[position]

        Glide
            .with(context)
            .load(model.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.bordImage)

        holder.binding.apply {
            boardName.text= model.name
            createdBy.text="Created by:  ${model.createBy}"
        }
holder.itemView.setOnClickListener{
    clickListener?.let {it1->

            it1(board[position])

    }
}
    }

    override fun getItemCount(): Int {
        return board.size   }
}
package com.seda.trello.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seda.trello.model.Board

class BoardItemsAdapter(private val context: Context,
                        private var list: ArrayList<Board>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
private class MyViewHolder(view: View):RecyclerView.ViewHolder(view){


}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
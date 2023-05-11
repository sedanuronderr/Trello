package com.seda.trello.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.seda.trello.ObjectClass
import com.seda.trello.R
import com.seda.trello.adapters.BoardItemsAdapter
import com.seda.trello.databinding.FragmentSignUpBinding
import com.seda.trello.databinding.FragmentTrelloPageBinding
import com.seda.trello.model.Board
import com.seda.trello.utils.Constants


class TrelloPageFragment : Fragment() {
    private lateinit var binding: FragmentTrelloPageBinding
private lateinit var boardItemsAdapter: BoardItemsAdapter
  lateinit var board: ArrayList<Board>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrelloPageBinding.inflate(layoutInflater,container,false)

        return binding.root




    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ObjectClass.getBoardList(this)


    }
    private fun onPopularItemLongClickListener() {
        boardItemsAdapter.onLongClickListener = {

            Toast.makeText(requireContext(),"Favorilere Eklendi", Toast.LENGTH_SHORT).show()
        }
    }
     fun setupRecyclerView(board: ArrayList<Board>){
         Log.d("gelen", "Cached document data: ${board}")

         if(board.size >0){

             binding.rvBoardsList.visibility = View.VISIBLE
             binding.tvNoBoardsAvailable.visibility = View.GONE
             binding.rvBoardsList.apply {
                 layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)

                 boardItemsAdapter = BoardItemsAdapter(requireContext(),board)
                 adapter = boardItemsAdapter
         }





}




    }



}
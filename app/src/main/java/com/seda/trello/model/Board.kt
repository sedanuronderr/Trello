package com.seda.trello.model



data class Board(
    val name:String ="",
    val image:String ="",
    val createBy:String ="",
    var documentedId: String ="" ,
    val assignedTo:ArrayList<String> = ArrayList()
):java.io.Serializable


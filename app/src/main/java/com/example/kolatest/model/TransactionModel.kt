package com.example.kolatest.model

class TransactionModel( var id:String?=null,
                       var purpose:String?=null,
                       var wallet:String?=null,
                       var note:String?=null,
                       var image:String?=null,
                       var date:String?=null,
                       var frequency:String?=null,
                       var amount:Double?=null,
                       var currency:String?=null){
    override fun toString(): String {
        return "TransactionModel(id=$id, purpose=$purpose, wallet=$wallet, note=$note, image=$image, date=$date, frequency=$frequency, amount=$amount, currency=$currency)"
    }
}

package com.example.kolatest.model

class WalletModel(  var id:String?=null, var walletName:String?=null, var walletdescription:String?=null){





    override fun toString(): String {
        return "WalletModel(id=$id, name=$walletName, description=$walletdescription)"
    }


}

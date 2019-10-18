package com.example.kolatest.ui.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kolatest.R
import com.example.kolatest.activities.NewWallet
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var walletName:TextView?=null
    var walletDetails:LinearLayout?=null
    var walletAmount:TextView?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        //my buttons
        var newWallet:CardView
        var newBanc:CardView

        //my texts
        walletName=root.findViewById(R.id.walletDislayedName)
        walletDetails=root.findViewById(R.id.walletdetails)
        walletName!!.text=getAllTheWallets()



        newWallet=root.findViewById(R.id.add_cash_wallet)
        newBanc=root.findViewById(R.id.connect_banc)

        newWallet.setOnClickListener(View.OnClickListener {
            val intent=Intent(root.context,NewWallet::class.java)
            startActivity(intent)
        })

        walletDetails!!.setOnClickListener(View.OnClickListener {
            val intent=Intent(root.context,Transactions::class.java)
            startActivity(intent)
        })

        return root
    }

    open fun getAllTheWallets():String{
        // Access a Cloud Firestore instance from your Activity
        val db = FirebaseFirestore.getInstance()
        db.collection("wallets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}"+" nouveau")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return " argent de poche"
    }

}
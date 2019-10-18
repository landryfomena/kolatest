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
import com.example.kolatest.activities.TransactionDetailsActivity
import com.example.kolatest.adapters.CustomAdapter
import com.example.kolatest.model.DataModel
import com.example.kolatest.model.TransactionModel
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    private lateinit var homeViewModel: HomeViewModel
    var walletName: TextView? = null
    var walletDetails: LinearLayout? = null
    var walletAmount: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        //my buttons
        var newWallet: CardView
        var newBanc: CardView

        //my texts
        walletName = root.findViewById(R.id.walletDislayedName)
        walletDetails = root.findViewById(R.id.walletdetails)
        walletName!!.text = getAllTheWallets()
        walletAmount = root.findViewById(R.id.wallAmount)



        newWallet = root.findViewById(R.id.add_cash_wallet)
        newBanc = root.findViewById(R.id.connect_banc)

        newWallet.setOnClickListener(View.OnClickListener {
            val intent = Intent(root.context, NewWallet::class.java)
            startActivity(intent)
        })

        walletDetails!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(root.context, TransactionDetailsActivity::class.java)
            startActivity(intent)
        })
        getAllUsers()

        return root
    }

    open fun getAllTheWallets(): String {
        // Access a Cloud Firestore instance from your Activity

        db.collection("transactions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}" + " nouveau")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return " argent de poche"
    }

    open fun getAllUsers() {
        val wallets = ArrayList<TransactionModel>()
        var totalAmount: Double = 0.0
        db.collection("transactions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var wallet = document.toObject(TransactionModel::class.java)
                    wallet.id = document.id
                    wallets.add(wallet)

                    if (wallet.note != null && wallet.amount != null && wallet.date != null && wallet.currency != null) {
                        var dataModel = DataModel(
                            wallet.note!!,
                            wallet.date!!,
                            wallet.amount.toString(),
                            feature = wallet.currency!!
                        )
                        totalAmount += wallet.amount!!
                    }
                    Log.e("data model", "" + wallet.toString())

                }
                walletAmount!!.text=totalAmount.toString()
                for (walletModel in wallets) {
                    Log.e("wallet", "" + walletModel)
                }
                Log.e("taille des elements", "" + wallets.size)

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

}
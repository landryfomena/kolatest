package com.example.kolatest.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.kolatest.R
import com.example.kolatest.adapters.CustomAdapter
import com.example.kolatest.model.DataModel
import com.google.android.material.snackbar.Snackbar
import android.widget.AdapterView
import com.example.kolatest.model.TransactionModel
import com.example.kolatest.model.WalletModel
import com.google.firebase.firestore.FirebaseFirestore


class TransactionDetailsActivity : AppCompatActivity() {
    var dataModels: ArrayList<DataModel>? = null
    var listView: ListView? = null
    private val RC_SIGN_IN: Int = 5
    val db = FirebaseFirestore.getInstance()
    private var adapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)

        listView = findViewById(R.id.list)

        dataModels = ArrayList()


        getAllUsers()



        listView!!.onItemClickListener =
            (AdapterView.OnItemClickListener { parent, view, position, id ->
                val dataModel = dataModels!![position]

                Snackbar.make(
                    view,
                    dataModel.name + "\n" + dataModel.type + " API: " + dataModel.version_number,
                    Snackbar.LENGTH_LONG
                )
                    .setAction("No action", null).show()
            })
    }

    open fun getAllUsers() {
        val wallets = ArrayList<TransactionModel>()
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
                        dataModels!!.add(dataModel)
                        adapter = CustomAdapter(dataModels!!, applicationContext)
                        listView!!.adapter = adapter
                    }
                    Log.e("data model", "" + wallet.toString())

                }
                for (walletModel in wallets) {
                    Log.e("wallet", "" + walletModel)
                }
                Log.e("taille des elements", "" + wallets.size)

            }
            .addOnFailureListener { exception ->
                Log.w(applicationContext.toString(), "Error getting documents.", exception)
            }
    }
}

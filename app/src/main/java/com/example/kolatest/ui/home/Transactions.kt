package com.example.kolatest.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.kolatest.model.TransactionModel

import com.google.firebase.firestore.FirebaseFirestore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kolatest.adapters.MyListAdapter

import androidx.recyclerview.widget.RecyclerView


import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.example.kolatest.R


class Transactions : AppCompatActivity() {
    private val RC_SIGN_IN: Int = 5
    val transactions = ArrayList<TransactionModel>()
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)
        getAllUsers()
        val recyclerView = findViewById<View>(R.id.list) as ListView
        if (transactions.size >= 0) {
            val adapter = MyListAdapter(this, transactions)
            recyclerView.adapter = adapter
        } else {
            Toast.makeText(this, "you don't have any transaction", Toast.LENGTH_LONG).show()
        }


    }

    open fun getAllUsers() {

        db.collection("transactions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var transaction = document.toObject(TransactionModel::class.java)
                    transaction.id = document.id
                    transactions.add(transaction)
                }
                for (walletModel in transactions) {
                    Log.e("wallet", "" + walletModel)
                }
                Log.e("taille des elements", "" + transactions.size)

            }
            .addOnFailureListener { exception ->
                Log.w(applicationContext.toString(), "Error getting documents.", exception)
            }
    }
}

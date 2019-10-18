package com.example.kolatest.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.kolatest.Acceuil
import com.example.kolatest.R
import com.google.firebase.firestore.FirebaseFirestore





class NewWallet : AppCompatActivity() {
    var nameWallet:EditText?=null

    var descriptionWallet:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_wallet)

        nameWallet=findViewById(R.id.nameWalletText)
        descriptionWallet=findViewById(R.id.descriptionWalletText)
        var  send:CardView
        send=findViewById(R.id.creerwallet)
        send.setOnClickListener(View.OnClickListener {

            var nameWalletString:String
            var descriptionWalletString:String
            if (nameWallet!=null&&descriptionWallet!=null){
                if (nameWallet!!.text.toString().equals("",true)
                    &&descriptionWallet!!.text.toString().equals("",true)){
                    Toast.makeText(applicationContext,resources.getString(R.string.empty_fields),Toast.LENGTH_LONG).show()

                }else{
                    nameWalletString=nameWallet!!.text.toString()
                    descriptionWalletString=descriptionWallet!!.text.toString()
                    createWallet(nameWalletString,descriptionWalletString)
                }

            }

        })
    }

    open fun createWallet(name:String,description:String){
        val db = FirebaseFirestore.getInstance()
        val wallet = hashMapOf(
            "walletName" to name,
            "walletdescription" to description
        )

        // Add a new document with a generated ID
        db.collection("wallets")
            .add(wallet)
            .addOnSuccessListener { documentReference ->
                Log.e("insertion", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(applicationContext,resources.getString(R.string.wallet_created),Toast.LENGTH_LONG).show()
                val intent= Intent(applicationContext,Acceuil::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.e("insertion", "Error adding document", e)

                Toast.makeText(applicationContext,resources.getString(R.string.error_sending_document),Toast.LENGTH_LONG).show()
            }


    }
}

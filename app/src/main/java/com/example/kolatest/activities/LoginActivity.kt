package com.example.kolatest.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kolatest.Acceuil
import com.example.kolatest.R
import com.example.kolatest.model.WalletModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 5
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        createAccount()

    }

    open fun createAccount() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )


// Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .setLogo(R.drawable.logo) // Set logo drawable
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                getAllUsers()
                intent = Intent(applicationContext, Acceuil::class.java)
                startActivity(intent)
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    open fun createUser() {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.e(
                    applicationContext.toString(),
                    "DocumentSnapshot added with ID: ${documentReference.id}"
                )

            }
            .addOnFailureListener { e ->
                Log.e(applicationContext.toString(), "Error adding document", e)
            }
    }

    open fun getAllUsers() {
        val wallets = ArrayList<WalletModel>()
        db.collection("wallets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var wallet = document.toObject(WalletModel::class.java)
                    wallet.id = document.id
                    wallets.add(wallet)


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

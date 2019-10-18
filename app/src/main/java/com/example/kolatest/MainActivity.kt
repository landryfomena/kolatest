package com.example.kolatest

import android.app.Activity
import android.app.Activity.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth?=null
    private var login:EditText?=null
    private var password:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var connect:Button
        login=findViewById(R.id.email)
        password=findViewById(R.id.password)
        connect=findViewById(R.id.test)
        connect.setOnClickListener(){

           // if(valisateFields(login!!.text.toString(),password!!.text.toString())) createAccount(login!!.text.toString(),password!!.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth?.getCurrentUser()

    }



}

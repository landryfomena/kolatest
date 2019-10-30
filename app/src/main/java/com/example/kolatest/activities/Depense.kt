package com.example.kolatest.activities

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.kolatest.Acceuil
import com.example.kolatest.R
import com.example.kolatest.model.DataModel
import com.example.kolatest.model.TransactionModel
import com.example.kolatest.model.WalletModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.nightonke.boommenu.BoomButtons.BoomButton
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.OnBoomListener
import kotlinx.android.synthetic.main.activity_depense.*
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ArrayAdapter





class Depense : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    var bmb: BoomMenuButton? = null
    var rating: Spinner? = null
    var wallets: Spinner? = null
    var price: TextView? = null
    var note: EditText? = null
    var image: TextView? = null
    var country = ArrayList<String>()
    var send: FloatingActionButton? = null
    var date: EditText? = null
    var ratingTxt: EditText? = null
    var purpuse: String? = null

    private val RC_SIGN_IN: Int = 5
    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depense)
        //Binding with xml
        bmb = findViewById(R.id.bmb)
        rating = findViewById(R.id.frequency)

        rating!!.onItemSelectedListener = this


        price = findViewById(R.id.amount)
        note = findViewById(R.id.noteTXT)
        image = findViewById(R.id.selectImage)
        send = findViewById(R.id.sendTransaction)
        date = findViewById(R.id.dateTXT)
        ratingTxt = findViewById(R.id.frequencyTXt)
        wallets = findViewById(R.id.WalletIdSpinner)
        var images = arrayListOf<Int>()
        images.add(R.drawable.gender_neutral_user_filled_24px)
        images.add(R.drawable.home_page_24px)
        images.add(R.drawable.shopping_bag_filled_24px)
        images.add(R.drawable.stack_of_money_24px)
        images.add(R.drawable.restaurant_filled_24px)
        images.add(R.drawable.car_filled_24px)
        images.add(R.drawable.subway_24px)
        images.add(R.drawable.subway_filled_24px)
        images.add(R.drawable.theatre_mask_24px)


        for (i in 0 until bmb!!.getPiecePlaceEnum().pieceNumber()) {
            val builder = TextOutsideCircleButton.Builder()
                .normalImageRes(images.get(i))
                .rotateImage(true)
                .normalText("menu " + i)
            bmb!!.addBuilder(builder)
        }
        // Use OnBoomListener to listen all methods
        bmb!!.setOnBoomListener(object : OnBoomListener {
            override fun onClicked(index: Int, boomButton: BoomButton) {
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
                Toast.makeText(applicationContext, "Click background!!!" + index, Toast.LENGTH_LONG)
                    .show()
                purpuse = "" + index
            }


            override fun onBackgroundClick() {
                //Toast.makeText(applicationContext,"Click background!!!",Toast.LENGTH_LONG).show()

            }

            override
            fun onBoomWillHide() {
                //Toast.makeText(applicationContext,"Will hide!!!",Toast.LENGTH_LONG).show()
            }

            override fun onBoomDidHide() {
                //Toast.makeText(applicationContext,"Did RE-BOOM!!!",Toast.LENGTH_LONG).show()

            }

            override fun onBoomWillShow() {
                //Toast.makeText(applicationContext,"Will BOOM!!!",Toast.LENGTH_LONG).show()

            }

            override fun onBoomDidShow() {
                //Toast.makeText(applicationContext,"Did BOOM!!!",Toast.LENGTH_LONG).show()

            }
        })


        //rating list adapter
        ArrayAdapter.createFromResource(
            this,
            R.array.country_arrays,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            rating!!.adapter = adapter
        }
        getAllWallets()

        //date picker
        date!!.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    date!!.setText(
                        day.toString() + "/" + (month + 1) + "/" + year
                    )
                }, year, month, dayOfMonth
            )
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
            datePickerDialog.show()
        })

        //send data
        send!!.setOnClickListener(View.OnClickListener {
            var transaction: TransactionModel = TransactionModel(
                id = null,
                purpose = purpuse,
                note = note!!.text.toString(),
                date = dateTXT.text.toString(),
                amount = amount!!.text.toString().toDouble(),
                currency = "XAF",
                frequency = frequencyTXt!!.text.toString()
            )

            validatefielsTransaction(
                purpose = "taxi",
                wallet = "52sVQsEAPuFRuS4bJePt",
                note = transaction.note!!,
                image = "52sVQsEAPuFRuS4bJePt",
                date = transaction.date!!,
                frequency = transaction.frequency!!,
                amount = transaction.amount!!,
                currency = transaction.currency!!
            )
        })
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        // On selecting a spinner item
        val item = parent.getItemAtPosition(position).toString()

        if(view==rating){
            ratingTxt!!.setText(item)
        }else{
            var wallet:TextView?=null
            wallet = findViewById(R.id.walletIdText)
            wallet.text= item
        }


        // Showing selected spinner item
        Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
        // TODO Auto-generated method stub
    }

    open fun validatefielsTransaction(
        purpose: String,
        wallet: String,
        note: String,
        image: String,
        date: String,
        frequency: String,
        amount: Double,
        currency: String
    ) {
        if (0 >= amount!! && frequency == null && note == null && date == null) {
            Toast.makeText(
                applicationContext,
                "missing amount, freequency or date",
                Toast.LENGTH_LONG
            ).show()
        } else {

            createTransaction(purpose, wallet, note, image, date, frequency, amount, currency)

        }


    }


    open fun createTransaction(
        purpose: String,
        wallet: String,
        note: String,
        image: String,
        date: String?,
        frequency: String,
        amount: Double,
        currency: String
    ) {
        val db = FirebaseFirestore.getInstance()
        val transaction = hashMapOf(
            "walletName" to purpose,
            "wallet" to wallet,
            "note" to note,
            "image" to image,
            "date" to date,
            "frequency" to frequency,
            "amount" to amount,
            "currency" to currency
        )

        // Add a new document with a generated ID
        db.collection("transactions")
            .add(transaction)
            .addOnSuccessListener { documentReference ->
                Log.e("insertion", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.transaction_created),
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(applicationContext, Acceuil::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.e("insertion", "Error adding document", e)

                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.error_sending_document),
                    Toast.LENGTH_LONG
                ).show()
            }


    }

    open fun getAllWallets() {
        val wallets = ArrayList<WalletModel>()
        var totalAmount: Double = 0.0
        db.collection("wallets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var wallet = document.toObject(WalletModel::class.java)
                    wallet.id = document.id
                    wallets.add(wallet)

                    for (wallet in wallets) {
                        country.add(wallet.walletName!!)
                    }

                    //Creating the ArrayAdapter instance having the country list
                    val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, country)
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    //Setting the ArrayAdapter data on the Spinner
                    this.wallets!!.setAdapter(aa)

                    Log.e("data model", "" + wallet.toString())

                }

                Log.e("taille des elements", "" + wallets.size)

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }


}



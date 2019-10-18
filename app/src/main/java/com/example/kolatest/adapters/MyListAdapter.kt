package com.example.kolatest.adapters
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.app.Activity
import android.widget.ArrayAdapter
import com.example.kolatest.R
import com.example.kolatest.model.TransactionModel
import java.util.*
import kotlin.collections.ArrayList


class MyListAdapter(
    private val context: Activity,
private val transactions:ArrayList<TransactionModel>
)// TODO Auto-generated constructor stub
    : ArrayAdapter<TransactionModel>(context, R.layout.list_item, transactions) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item, null, true)

        val titleText = rowView.findViewById(R.id.amount) as TextView
        val currency = rowView.findViewById(R.id.currency) as TextView
        val subtitleText = rowView.findViewById(R.id.dateTXT) as TextView

        titleText.text = transactions[position].amount.toString()
        currency.text=transactions[position].currency
        subtitleText.text = transactions[position].date

        return rowView

    }
}
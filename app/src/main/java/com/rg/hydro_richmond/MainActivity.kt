package com.rg.hydro_richmond

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.rg.hydro_richmond.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // using the binding feature
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.binding.btnCalculate.setOnClickListener(this)
        this.binding.btnReset.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_calculate -> {
                this.calculate()
            }

            R.id.btn_reset -> {
                this.reset()
            }
        }

    }

    // function that makes calculate
    private fun calculate() {

        // Condition to check if fields are empty
        if (this.binding.etMorningUsage.text.isNullOrEmpty() ||
            this.binding.etEveningUsage.text.isNullOrEmpty()){
                this.binding.tvErrorMsg.isVisible = true

                // clearing previous information displayed
                this.binding.tvRcptInfo.text = ""
                this.binding.tvRcptTitle.text = ""
                this.binding.tvRcptAmt.text = ""

                return
        } else {

            // clearing previous error messages
            this.binding.tvErrorMsg.isVisible = false

            val mrnUsageUI = this.binding.etMorningUsage.text.toString()
            val evnUsageUI = this.binding.etEveningUsage.text.toString()

            this.binding.tvRcptTitle.text = "Charge Breakdown"

            val mrnUsage = mrnUsageUI.toDouble()
            val evnUsage = evnUsageUI.toDouble()


            val mrnUsageCost = mrnUsage * 0.132
            val evnUsageCost = evnUsage * 0.094
            val totalUsageCost = mrnUsageCost + evnUsageCost
            val rebate: Double


            if (this.binding.swRenewable.isChecked) {
                rebate = totalUsageCost * 0.09
            } else {
                rebate = 0.0
            }

            val subtotal = totalUsageCost - rebate
            val salesTax = subtotal * 0.13
            val paymentAmt = subtotal + salesTax


            // converting values to two decimal places
            val mrnUsageCostUI = "%,.2f".format(Locale.ENGLISH, mrnUsageCost)
            val evnUsageCostUI = "%,.2f".format(Locale.ENGLISH, evnUsageCost)
            val totalUsageCostUI = "%,.2f".format(Locale.ENGLISH, totalUsageCost)
            val rebateUI = "%,.2f".format(Locale.ENGLISH, rebate)
            val subtotalUI = "%,.2f".format(Locale.ENGLISH, subtotal)
            val salesTaxUI = "%,.2f".format(Locale.ENGLISH, salesTax)
            val paymentAmtUI = "%,.2f".format(Locale.ENGLISH, paymentAmt)

            var rcptOutput = "Morning usage charge: $$mrnUsageCostUI " +
                    "\nEvening Usage charge: $$evnUsageCostUI" +
                    "\nTotal Usage charge: $$totalUsageCostUI" +
                    "\nEnvironmental rebate: -$$rebateUI" +
                    "\nSubtotal: $$subtotalUI" +
                    "\nSales Tax: $$salesTaxUI"

            this.binding.tvRcptInfo.text = rcptOutput

            // making layout for payment amount visible
            this.binding.ltRcptFooter.isVisible = true
            this.binding.tvRcptAmt.text = "YOU MUST PAY: $$paymentAmtUI"
        }

    }

    // function to reset values
    private fun reset() {

        // clearing all input fields
        this.binding.etMorningUsage.text = null
        this.binding.etEveningUsage.text = null


        // clearing all display messages
        this.binding.tvErrorMsg.isVisible = false
        this.binding.ltRcptFooter.isVisible = false
        this.binding.tvRcptInfo.text = ""
        this.binding.tvRcptTitle.text = ""
        this.binding.tvRcptAmt.text = ""
    }
}
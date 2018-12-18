package ir.farshid_roohi.sdkzainpal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener
import com.zarinpal.ewallets.purchase.PaymentRequest
import com.zarinpal.ewallets.purchase.ZarinPal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            request()
            btn.isEnabled = false
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) {
            return
        }
        intent.data?.let {

            ZarinPal.getPurchase(this).verificationPayment(intent.data!!) { isPaymentSuccess, refID, paymentRequest ->

                progressBar.visibility = View.INVISIBLE
                btn.isEnabled = true

                if (isPaymentSuccess) {

                    Log.d("TAG", "your payment is success : $refID")
                } else {
                    Log.d("TAG", "your payment is field :( $refID")
                }
            }

        }
    }

    fun request() {
        progressBar.visibility = View.VISIBLE
        val purchase = ZarinPal.getPurchase(this)
        val paymentRequest = ZarinPal.getPaymentRequest()

        paymentRequest.merchantID = "c464ade0-fd2e-11e8-8d75-005056a205be"
        paymentRequest.mobile = "09121212121"
        paymentRequest.description = "test description for app test"
        paymentRequest.email = "farshid.roohi.a@gmail.com"
        paymentRequest.amount = 100
        paymentRequest.setCallbackURL("farshidroohi://app")

        purchase.startPayment(paymentRequest) { status, authority, paymentGatewayUri, intent ->

            if (status == 100) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }


        }
    }
}

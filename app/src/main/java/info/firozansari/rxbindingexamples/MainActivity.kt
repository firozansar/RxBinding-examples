package info.firozansari.rxbindingexamples

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import butterknife.OnClick

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    protected override val layoutId: Int
        protected get() = R.layout.activity_main

    override fun onViewCreated(savedInstanceState: Bundle?) {}
    @OnClick(R.id.btn_rx_view, R.id.btn_rx_text_view, R.id.btn_rx_adapter_view, R.id.btn_rx_compound, R.id.btn_count_down, R.id.btn_form_validation)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.btn_rx_view -> startActivity(Intent(this, RxViewActivity::class.java))
            R.id.btn_rx_text_view -> startActivity(Intent(this, RxTextViewActivity::class.java))
            R.id.btn_rx_adapter_view -> startActivity(Intent(this, RxAdapterViewActivity::class.java))
            R.id.btn_rx_compound -> startActivity(Intent(this, RxCompoundButtonActivity::class.java))
            R.id.btn_count_down -> startActivity(Intent(this, CountDownActivity::class.java))
            R.id.btn_form_validation -> startActivity(Intent(this, FormValidationActivity::class.java))
        }
    }
}
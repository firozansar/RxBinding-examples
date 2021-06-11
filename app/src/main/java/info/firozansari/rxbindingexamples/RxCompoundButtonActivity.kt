package info.firozansari.rxbindingexamples

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import butterknife.BindView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.jakewharton.rxbinding2.widget.RxTextView
import info.firozansari.rxbindingexamples.RxCompoundButtonActivity
import java.util.concurrent.TimeUnit

class RxCompoundButtonActivity : BaseActivity() {
    @JvmField
    @BindView(R.id.btn_login)
    var btnLogin: Button? = null

    @JvmField
    @BindView(R.id.cb_contract)
    var cbContract: CheckBox? = null
    protected override val layoutId: Int
        protected get() = R.layout.activity_rx_compound_button

    override fun onViewCreated(savedInstanceState: Bundle?) {
        checkedChanges()
        compoundButtonOperate()
    }

    private fun checkedChanges() {
        btnLogin!!.isEnabled = false
        addDisposable(RxCompoundButton.checkedChanges(cbContract!!)
                .subscribe { aBoolean: Boolean ->
                    RxView.enabled(btnLogin!!).accept(aBoolean)
                    btnLogin!!.setBackgroundResource(if (aBoolean) R.color.colorPrimary else R.color.colorAccent)
                    RxTextView.color(btnLogin!!).accept(if (aBoolean) Color.parseColor("#ffffff") else Color.parseColor("#000000"))
                })
        addDisposable(RxView.clicks(btnLogin!!)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { o: Any? ->
                    Toast.makeText(this@RxCompoundButtonActivity, "Hello",
                            Toast.LENGTH_SHORT).show()
                })
    }

    private fun compoundButtonOperate() {
        addDisposable(RxView.clicks(btnLogin!!)
                .subscribe { o: Any? ->
                    RxCompoundButton.checked(cbContract!!).accept(true)
                    RxCompoundButton.toggle(cbContract!!).accept(null)
                })
    }
}
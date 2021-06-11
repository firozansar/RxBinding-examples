package info.firozansari.rxbindingexamples

import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import butterknife.BindView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import info.firozansari.rxbindingexamples.FormValidationActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.regex.Pattern

class FormValidationActivity : BaseActivity() {
    @JvmField
    @BindView(R.id.text_phone)
    var phone: EditText? = null

    @JvmField
    @BindView(R.id.text_pwd)
    var password: EditText? = null

    @JvmField
    @BindView(R.id.btn_submit)
    var login: Button? = null
    private var result: ValidationResult? = null
    protected override val layoutId: Int
        protected get() = R.layout.activity_form_validation

    override fun onViewCreated(savedInstanceState: Bundle?) {
        val ObservablePhone: Observable<CharSequence> = RxTextView.textChanges(phone!!)
        val ObservablePassword: Observable<CharSequence> = RxTextView.textChanges(password!!)
        addDisposable(Observable.combineLatest(ObservablePhone, ObservablePassword, { o1: CharSequence, o2: CharSequence ->
            val result = ValidationResult()
            if (o1.length == 0) {
                result.flag = false
                result.message = "Please provide phone number"
            } else if (o1.length < 10) {
                result.flag = false
                result.message = "Phone number length is not valid!"
            } else if (!isPhoneNumber(o1.toString())) {
                result.flag = false
                result.message = "Phone number is not valid!"
            } else if (o2.length == 0) {
                result.flag = false
                result.message = "Please provide password!"
            }
            result
        }).subscribe { validationResult: ValidationResult? -> result = validationResult })
        addDisposable(RxView.clicks(login!!)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { o: Any? ->
                    if (result == null) return@subscribe
                    if (result!!.flag) {
                        Toast.makeText(this@FormValidationActivity, result!!.message, Toast.LENGTH_SHORT).show()
                    } else if (!TextUtils.isEmpty(result!!.message)) {
                        Toast.makeText(this@FormValidationActivity, result!!.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }

    companion object {
        fun isPhoneNumber(phoneNumber: String): Boolean {
            var isValid = false
            val inputStr: CharSequence = phoneNumber
            val phone = "^1[34578]\\d{9}$"
            val pattern = Pattern.compile(phone)
            val matcher = pattern.matcher(inputStr)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }
    }
}
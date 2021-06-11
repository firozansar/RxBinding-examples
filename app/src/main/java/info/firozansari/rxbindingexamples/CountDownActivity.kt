package info.firozansari.rxbindingexamples

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class CountDownActivity : BaseActivity() {
    @JvmField
    @BindView(R.id.btn_get_code)
    var btnGetCode: Button? = null

    @JvmField
    @BindView(R.id.increment_button)
    var incrementButton: Button? = null

    @JvmField
    @BindView(R.id.my_text_view)
    var myTextView: TextView? = null
    protected override val layoutId: Int
        protected get() = R.layout.activity_count_down

    override fun onViewCreated(savedInstanceState: Bundle?) {
        addDisposable(RxView.clicks(btnGetCode!!)
                .throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { o: Any? -> RxView.enabled(btnGetCode!!).accept(false) }
                .subscribe { o: Any? -> }
        )

        //broadcast clicks into a cumulative increment, and display in TextView
        addDisposable(RxView.clicks(incrementButton!!)
                .map { o: Any? -> 1 }
                .scan(0, { total: Int, next: Int -> total + next })
                .subscribe { i: Int -> myTextView!!.text = i.toString() })
    }
}
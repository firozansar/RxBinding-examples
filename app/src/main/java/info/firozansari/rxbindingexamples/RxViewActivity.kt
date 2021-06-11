package info.firozansari.rxbindingexamples

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import butterknife.BindView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
import info.firozansari.rxbindingexamples.RxViewActivity
import java.util.concurrent.TimeUnit

class RxViewActivity : BaseActivity() {
    @JvmField
    @BindView(R.id.btn_click)
    var btnClick: Button? = null

    @JvmField
    @BindView(R.id.btn_layout)
    var btnLayout: Button? = null

    @JvmField
    @BindView(R.id.btn_change)
    var btnChange: Button? = null

    @JvmField
    @BindView(R.id.btn_draw)
    var btnDraw: Button? = null

    @JvmField
    @BindView(R.id.view_canvas)
    var viewCanvas: View? = null

    @JvmField
    @BindView(R.id.btn_scroll_layout)
    var btnScrollLayout: Button? = null

    @JvmField
    @BindView(R.id.btn_scroll)
    var btnScroll: Button? = null
    private var x = 0
    protected override val layoutId: Int
        protected get() = R.layout.activity_rx_view

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onViewCreated(savedInstanceState: Bundle?) {
        clicks()
        draws()
        drags()
        layoutChanges()
        scrollChange()
        //        viewOperate();
    }

    private fun clicks() {
        addDisposable(RxView.clicks(btnClick!!)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { o: Any? -> Log.e("rx_binding_test", "clicks") })
        addDisposable(RxView.longClicks(btnClick!!)
                .subscribe { o: Any? ->
                    Log.e("rx_binding_test", "longClicks")
                    Toast.makeText(this@RxViewActivity, "Long click", Toast.LENGTH_SHORT).show()
                })
    }

    private fun draws() {
        addDisposable(RxView.clicks(btnDraw!!)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { o: Any? -> })
        addDisposable(RxView.draws(viewCanvas!!)
                .subscribe { o: Any? ->
                    Log.e("rx_binding_test", "draws:viewCanvas")
                    Toast.makeText(this@RxViewActivity, "viewCanvas drawn", Toast.LENGTH_SHORT).show()
                })
    }

    private fun drags() {
        addDisposable(RxView.drags(btnDraw!!)
                .subscribe { o: DragEvent? ->
                    Log.e("rx_binding_test", "drags:btnDraw")
                    Toast.makeText(this@RxViewActivity, "btnDraw dragged", Toast.LENGTH_SHORT).show()
                })
    }

    private fun layoutChanges() {
        addDisposable(RxView.clicks(btnChange!!)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { o: Any? ->
                    btnLayout!!.layout(btnLayout!!.left - 20, btnLayout!!.top,
                            btnLayout!!.right - 20, btnLayout!!.bottom)
                })
        addDisposable(RxView.layoutChanges(btnLayout!!)
                .subscribe { o: Any? ->
                    Log.e("rx_binding_test", "layoutChanges:btnLayout")
                    Toast.makeText(this@RxViewActivity, "btnLayout changed", Toast.LENGTH_SHORT).show()
                })
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun scrollChange() {
        addDisposable(RxView.clicks(btnScroll!!)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { o: Any? ->
                    x += 10
                    if (x == 100) {
                        x = 0
                    }
                    btnScrollLayout!!.scrollTo(x, 0)
                })
        addDisposable(RxView.scrollChangeEvents(btnScrollLayout!!)
                .subscribe { viewScrollChangeEvent: ViewScrollChangeEvent ->
                    Log.e("rx_binding_test", "scrollChangeEvents:btnScrollLayout:$viewScrollChangeEvent")
                    Toast.makeText(this@RxViewActivity, "btnScroll Layout changed", Toast.LENGTH_SHORT).show()
                })
    }

    private fun viewOperate() {
        addDisposable(RxView.clicks(btnClick!!)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { o: Any? ->
                    RxView.visibility(btnClick!!).accept(true)
                    RxView.clickable(btnClick!!).accept(true)
                    RxView.enabled(btnClick!!).accept(true)
                })
    }
}
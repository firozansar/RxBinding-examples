package info.firozansari.rxbindingexamples

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import com.jakewharton.rxbinding2.widget.TextViewBeforeTextChangeEvent
import com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent
import info.firozansari.rxbindingexamples.RxTextViewActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class RxTextViewActivity : BaseActivity() {
    @JvmField
    @BindView(R.id.et_rx_text_view)
    var etRxTextView: EditText? = null
    protected override val layoutId: Int
        protected get() = R.layout.activity_rx_text_view

    override fun onViewCreated(savedInstanceState: Bundle?) {
        textChanges()
        //        beforeTextChangeEvents();
//        afterTextChangeEvents();
        editorActions()
        //        textViewOperate();
    }

    private fun textChanges() {
        //textChanges
        addDisposable(RxTextView.textChanges(etRxTextView!!)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map { obj: CharSequence -> obj.toString() }
                .subscribe { s: String ->
                    Log.e("rx_binding_test", "textChanges:etRxTextView:$s")
                    Toast.makeText(this@RxTextViewActivity, s, Toast.LENGTH_SHORT).show()
                })
        //textChangeEvents
//        addDisposable(RxTextView.textChangeEvents(etRxTextView)
//                .subscribe(textViewTextChangeEvent -> {
//                    Log.e("rx_binding_test", "textChanges:etRxTextView:" + "before:" +
//                            textViewTextChangeEvent.before() + ",start:" + textViewTextChangeEvent.start() +
//                            ",text:" + textViewTextChangeEvent.text() + ",count:" + textViewTextChangeEvent.count());
//                    Toast.makeText(RxTextViewActivity.this, "etRxTextView", Toast.LENGTH_SHORT).show();
//                }));
    }

    private fun beforeTextChangeEvents() {
        addDisposable(RxTextView.beforeTextChangeEvents(etRxTextView!!)
                .subscribe { textViewBeforeTextChangeEvent: TextViewBeforeTextChangeEvent ->
                    Log.e("rx_binding_test", "beforeTextChangeEvents:etRxTextView:" + textViewBeforeTextChangeEvent.text())
                    Toast.makeText(this@RxTextViewActivity, textViewBeforeTextChangeEvent.text(), Toast.LENGTH_SHORT).show()
                })
    }

    private fun afterTextChangeEvents() {
        addDisposable(RxTextView.afterTextChangeEvents(etRxTextView!!)
                .subscribe { textViewAfterTextChangeEvent: TextViewAfterTextChangeEvent ->
                    Log.e("rx_binding_test", "afterTextChangeEvents:etRxTextView")
                    Toast.makeText(this@RxTextViewActivity, textViewAfterTextChangeEvent.view().text, Toast.LENGTH_SHORT).show()
                })
    }

    private fun editorActions() {
        addDisposable(RxTextView.editorActions(etRxTextView!!)
                .subscribe { integer: Int ->
                    Log.e("rx_binding_test", "editorActions::")
                    Toast.makeText(this@RxTextViewActivity, "click no.$integer", Toast.LENGTH_SHORT).show()
                })
        addDisposable(RxTextView.editorActionEvents(etRxTextView!!)
                .subscribe { textViewEditorActionEvent: TextViewEditorActionEvent ->
                    val keyEvent = textViewEditorActionEvent.keyEvent()
                    if (keyEvent!!.keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_UP) {
                        Log.e("rx_binding_test", "editorActionEvents:" + textViewEditorActionEvent.keyEvent())
                        Toast.makeText(this@RxTextViewActivity, "Pressed enter", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun textViewOperate() {
        addDisposable(RxTextView.editorActions(etRxTextView!!)
                .subscribe { integer: Int? ->
                    RxTextView.text(etRxTextView!!).accept("Hello")
                    RxTextView.hint(etRxTextView!!).accept("click")
                    RxTextView.color(etRxTextView!!).accept(Color.parseColor("#00ff00"))
                })
    }
}
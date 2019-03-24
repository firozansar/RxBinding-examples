package info.firozansari.rxbindingexamples;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RxTextViewActivity extends BaseActivity {

    @BindView(R.id.et_rx_text_view)
    EditText etRxTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_text_view;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        textChanges();
//        beforeTextChangeEvents();
//        afterTextChangeEvents();
        editorActions();
//        textViewOperate();
    }


    private void textChanges() {
        //textChanges
        addDisposable(RxTextView.textChanges(etRxTextView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .subscribe(s -> {
                    Log.e("rx_binding_test", "textChanges:etRxTextView:" + s);
                    Toast.makeText(RxTextViewActivity.this, "etRxTextView", Toast.LENGTH_SHORT).show();
                }));
        //textChangeEvents
//        addDisposable(RxTextView.textChangeEvents(etRxTextView)
//                .subscribe(textViewTextChangeEvent -> {
//                    Log.e("rx_binding_test", "textChanges:etRxTextView:" + "before:" +
//                            textViewTextChangeEvent.before() + ",start:" + textViewTextChangeEvent.start() +
//                            ",text:" + textViewTextChangeEvent.text() + ",count:" + textViewTextChangeEvent.count());
//                    Toast.makeText(RxTextViewActivity.this, "etRxTextView", Toast.LENGTH_SHORT).show();
//                }));
    }


    private void beforeTextChangeEvents() {
        addDisposable(RxTextView.beforeTextChangeEvents(etRxTextView)
                .subscribe(
                        textViewBeforeTextChangeEvent -> {
                            Log.e("rx_binding_test", "beforeTextChangeEvents:etRxTextView:" + textViewBeforeTextChangeEvent.text());
                            Toast.makeText(RxTextViewActivity.this, "etRxTextView", Toast.LENGTH_SHORT).show();
                        }));
    }

    private void afterTextChangeEvents() {
        addDisposable(RxTextView.afterTextChangeEvents(etRxTextView)
                .subscribe(textViewAfterTextChangeEvent -> {
                    Log.e("rx_binding_test", "afterTextChangeEvents:etRxTextView");
                    Toast.makeText(RxTextViewActivity.this, "etRxTextView", Toast.LENGTH_SHORT).show();
                }));
    }


    private void editorActions() {
        addDisposable(RxTextView.editorActions(etRxTextView)
                .subscribe(integer -> {
                    Log.e("rx_binding_test", "editorActions::");
                    Toast.makeText(RxTextViewActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                }));
        addDisposable(RxTextView.editorActionEvents(etRxTextView)
                .subscribe(textViewEditorActionEvent -> {
                    KeyEvent keyEvent = textViewEditorActionEvent.keyEvent();
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        Log.e("rx_binding_test", "editorActionEvents:" + textViewEditorActionEvent.keyEvent());
                        Toast.makeText(RxTextViewActivity.this, "enter", Toast.LENGTH_SHORT).show();
                    }
                }));
    }


    private void textViewOperate() {
        addDisposable(RxTextView.editorActions(etRxTextView)
                .subscribe(integer -> {
                    RxTextView.text(etRxTextView).accept("Hello");
                    RxTextView.hint(etRxTextView).accept("click");
                    RxTextView.color(etRxTextView).accept(Color.parseColor("#00ff00"));
                }));
    }
}

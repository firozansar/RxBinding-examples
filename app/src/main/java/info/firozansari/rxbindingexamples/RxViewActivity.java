package info.firozansari.rxbindingexamples;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class RxViewActivity extends BaseActivity {

    @BindView(R.id.btn_click)
    Button btnClick;
    @BindView(R.id.btn_layout)
    Button btnLayout;
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.btn_draw)
    Button btnDraw;
    @BindView(R.id.view_canvas)
    View viewCanvas;
    @BindView(R.id.btn_scroll_layout)
    Button btnScrollLayout;
    @BindView(R.id.btn_scroll)
    Button btnScroll;
    private int x;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        clicks();
        draws();
        drags();
        layoutChanges();
        scrollChange();
//        viewOperate();
    }


    private void clicks() {
        addDisposable(RxView.clicks(btnClick)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    Log.e("rx_binding_test", "clicks");
                    //Toast.makeText(RxViewActivity.this, "", Toast.LENGTH_SHORT).show();
                }));
        addDisposable(RxView.longClicks(btnClick)
                .subscribe(o -> {
                    Log.e("rx_binding_test", "longClicks");
                    Toast.makeText(RxViewActivity.this, "", Toast.LENGTH_SHORT).show();
                }));
    }


    private void draws() {
        addDisposable(RxView.clicks(btnDraw)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    //viewCanvas.getViewTreeObserver().dispatchOnDraw();
                }));
        addDisposable(RxView.draws(viewCanvas)
                .subscribe(o -> {
                    Log.e("rx_binding_test", "draws:viewCanvas");
                    Toast.makeText(RxViewActivity.this, "viewCanvas drawn", Toast.LENGTH_SHORT).show();
                }));
    }

    private void drags() {
        addDisposable(RxView.drags(btnDraw)
                .subscribe(o -> {
                    Log.e("rx_binding_test", "drags:btnDraw");
                    Toast.makeText(RxViewActivity.this, "btnDraw dragged", Toast.LENGTH_SHORT).show();
                }));
    }


    private void layoutChanges() {
        addDisposable(RxView.clicks(btnChange)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> btnLayout.layout(btnLayout.getLeft() - 20, btnLayout.getTop(),
                        btnLayout.getRight() - 20, btnLayout.getBottom())
                ));
        addDisposable(RxView.layoutChanges(btnLayout)
                .subscribe(o -> {
                    Log.e("rx_binding_test", "layoutChanges:btnLayout");
                    Toast.makeText(RxViewActivity.this, "btnLayout changed", Toast.LENGTH_SHORT).show();
                }));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void scrollChange() {
        addDisposable(RxView.clicks(btnScroll)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    x += 10;
                    if (x == 100) {
                        x = 0;
                    }
                    btnScrollLayout.scrollTo(x, 0);
                }));
        addDisposable(RxView.scrollChangeEvents(btnScrollLayout)
                .subscribe(viewScrollChangeEvent -> {
                    Log.e("rx_binding_test", "scrollChangeEvents:btnScrollLayout:" + viewScrollChangeEvent.toString());
                    Toast.makeText(RxViewActivity.this, "btnScroll Layout changed", Toast.LENGTH_SHORT).show();
                }));
    }


    private void viewOperate() {
        addDisposable(RxView.clicks(btnClick)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    RxView.visibility(btnClick).accept(true);
                    RxView.clickable(btnClick).accept(true);
                    RxView.enabled(btnClick).accept(true);
                }));
    }
}

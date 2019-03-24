package info.firozansari.rxbindingexamples;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class CountDownActivity extends BaseActivity {

    @BindView(R.id.btn_get_code)
    Button btnGetCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_count_down;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {

        addDisposable(RxView.clicks(btnGetCode)
                .throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(o -> RxView.enabled(btnGetCode).accept(false))
                .subscribe(o -> {

                            addDisposable(Flowable.intervalRange(0, 11, 0, 1, TimeUnit.SECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doOnNext(aLong -> {
                                        RxTextView.text(btnGetCode).accept(" " + (10 - aLong) + " ");
                                    })
                                    .doOnComplete(() -> {
                                        RxView.enabled(btnGetCode).accept(true);
                                        RxTextView.text(btnGetCode).accept("Done");
                                    })
                                    .subscribe());
                        }
                )
        );
    }
}

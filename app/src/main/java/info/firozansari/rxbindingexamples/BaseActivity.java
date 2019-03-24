package info.firozansari.rxbindingexamples;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {
    public CompositeDisposable mCompositeDisposable;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mCompositeDisposable = new CompositeDisposable();
        onViewCreated(savedInstanceState);
    }


    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposable();
        mUnbinder.unbind();
    }

    protected abstract int getLayoutId();

    protected abstract void onViewCreated(Bundle savedInstanceState);
}

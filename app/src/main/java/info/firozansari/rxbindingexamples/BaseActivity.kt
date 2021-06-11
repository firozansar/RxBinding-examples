package info.firozansari.rxbindingexamples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {
    var mCompositeDisposable: CompositeDisposable? = null
    private var mUnbinder: Unbinder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        mUnbinder = ButterKnife.bind(this)
        mCompositeDisposable = CompositeDisposable()
        onViewCreated(savedInstanceState)
    }

    fun addDisposable(mDisposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(mDisposable!!)
    }

    fun clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDisposable()
        mUnbinder!!.unbind()
    }

    protected abstract val layoutId: Int
    protected abstract fun onViewCreated(savedInstanceState: Bundle?)
}
package info.firozansari.rxbindingexamples

import android.os.Bundle
import android.util.Log
import android.widget.*
import butterknife.BindView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import info.firozansari.rxbindingexamples.RxAdapterViewActivity
import java.util.*
import java.util.concurrent.TimeUnit

class RxAdapterViewActivity : BaseActivity() {
    @JvmField
    @BindView(R.id.lv_list)
    var lvList: ListView? = null
    private var list: MutableList<String>? = null
    protected override val layoutId: Int
        protected get() = R.layout.activity_rx_adapter_view

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initData()
        itemClicks()
        itemLongClicks()
        itemSelections()
        itemViewOperate()
    }

    private fun initData() {
        list = ArrayList()
        for (i in 0..99) {
            (list as ArrayList<String>).add("Hello : $i")
        }
        //adapter
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        adapter.addAll(list as ArrayList<String>)
        lvList!!.adapter = adapter
    }

    private fun itemClicks() {
        addDisposable(RxAdapterView.itemClicks(lvList)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { integer: Int ->
                    Toast.makeText(this@RxAdapterViewActivity,
                            "value" + integer + "：" + list!![integer], Toast.LENGTH_SHORT).show()
                })
    }

    private fun itemLongClicks() {
        addDisposable(RxAdapterView.itemLongClicks(lvList)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { integer: Int? ->
                    Toast.makeText(this@RxAdapterViewActivity,
                            "Long clicked " + list!![integer!!], Toast.LENGTH_SHORT).show()
                })
    }

    private fun itemSelections() {
        addDisposable(RxAdapterView.itemSelections(lvList)
                .subscribe { integer: Int -> Log.e("rx_binding_test", "itemSelections：$integer") })
    }

    private fun itemViewOperate() {
        addDisposable(RxAdapterView.itemClicks(lvList)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe { integer: Int? -> RxAdapterView.selection(lvList).accept(0) })
    }
}
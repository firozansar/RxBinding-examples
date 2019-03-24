package info.firozansari.rxbindingexamples;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class RxAdapterViewActivity extends BaseActivity {

    @BindView(R.id.lv_list)
    ListView lvList;
    private List<String> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_adapter_view;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        initData();
        itemClicks();
        itemLongClicks();
        itemSelections();
        itemViewOperate();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("Hello :" + i);
        }
        //adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(list);
        lvList.setAdapter(adapter);
    }


    private void itemClicks() {
        addDisposable(RxAdapterView.itemClicks(lvList)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(integer -> Toast.makeText(RxAdapterViewActivity.this,
                        "value" + integer + "：" + list.get(integer), Toast.LENGTH_SHORT).show()));
    }


    private void itemLongClicks() {
        addDisposable(RxAdapterView.itemLongClicks(lvList)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(integer -> Toast.makeText(RxAdapterViewActivity.this,
                        "value" + integer + "：" + list.get(integer), Toast.LENGTH_SHORT).show()));

    }


    private void itemSelections() {
        addDisposable(RxAdapterView.itemSelections(lvList)
                .subscribe(integer -> Log.e("rx_binding_test", "itemSelections：" + integer)));
    }


    private void itemViewOperate() {
        addDisposable(RxAdapterView.itemClicks(lvList)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(integer -> RxAdapterView.selection(lvList).accept(0)));
    }
}

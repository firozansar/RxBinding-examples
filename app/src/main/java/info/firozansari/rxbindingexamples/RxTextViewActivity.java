package info.firozansari.rxbindingexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RxTextViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_text_view);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {

    }
}

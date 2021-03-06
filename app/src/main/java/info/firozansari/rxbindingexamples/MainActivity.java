package info.firozansari.rxbindingexamples;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn_rx_view, R.id.btn_rx_text_view, R.id.btn_rx_adapter_view, R.id.btn_rx_compound,
            R.id.btn_count_down, R.id.btn_form_validation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rx_view:
                startActivity(new Intent(this, RxViewActivity.class));
                break;
            case R.id.btn_rx_text_view:
                startActivity(new Intent(this, RxTextViewActivity.class));
                break;
            case R.id.btn_rx_adapter_view:
                startActivity(new Intent(this, RxAdapterViewActivity.class));
                break;
            case R.id.btn_rx_compound:
                startActivity(new Intent(this, RxCompoundButtonActivity.class));
                break;
            case R.id.btn_count_down:
                startActivity(new Intent(this, CountDownActivity.class));
                break;
            case R.id.btn_form_validation:
                startActivity(new Intent(this, FormValidationActivity.class));
                break;
        }
    }

}

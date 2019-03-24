package info.firozansari.rxbindingexamples;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class RxCompoundButtonActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_contract)
    CheckBox cbContract;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_compound_button;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        checkedChanges();
        compoundButtonOperate();
    }


    private void checkedChanges() {
        btnLogin.setEnabled(false);
        addDisposable(RxCompoundButton.checkedChanges(cbContract)
                .subscribe(aBoolean -> {
                    RxView.enabled(btnLogin).accept(aBoolean);
                    btnLogin.setBackgroundResource(aBoolean ? R.color.colorPrimary : R.color.colorAccent);
                    RxTextView.color(btnLogin).accept(aBoolean ? Color.parseColor("#ffffff") :
                            Color.parseColor("#000000"));
                }));
        addDisposable(RxView.clicks(btnLogin)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> Toast.makeText(RxCompoundButtonActivity.this, "Hello",
                        Toast.LENGTH_SHORT).show()));
    }


    private void compoundButtonOperate() {
        addDisposable(RxView.clicks(btnLogin)
                .subscribe(o -> {
                    RxCompoundButton.checked(cbContract).accept(true);
                    RxCompoundButton.toggle(cbContract).accept(null);
                }));
    }
}

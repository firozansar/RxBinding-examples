package info.firozansari.rxbindingexamples;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FormValidationActivity extends BaseActivity {

    @BindView(R.id.text_phone)
    EditText phone;

    @BindView(R.id.text_pwd)
    EditText password;

    @BindView(R.id.btn_submit)
    Button login;

    private ValidationResult result;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_form_validation;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        Observable<CharSequence> ObservablePhone = RxTextView.textChanges(phone);
        Observable<CharSequence> ObservablePassword = RxTextView.textChanges(password);

        addDisposable(Observable.combineLatest(ObservablePhone, ObservablePassword, (o1, o2) -> {

            ValidationResult result = new ValidationResult();
            if (o1.length() == 0) {
                result.flag = false;
                result.message = "Please provide phone number";
            } else if (o1.length() < 10) {
                result.flag = false;
                result.message = "Phone number length is not valid!";
            } else if (!isPhoneNumber(o1.toString())) {
                result.flag = false;
                result.message = "Phone number is not valid!";
            } else if (o2.length() == 0) {
                result.flag = false;
                result.message = "Please provide password!";
            }

            return result;
        }).subscribe(validationResult -> result = validationResult));

        addDisposable(RxView.clicks(login)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (result == null) return;
                    if (result.flag) {
                        Toast.makeText(FormValidationActivity.this, result.message,Toast.LENGTH_SHORT).show();
                    } else if (!TextUtils.isEmpty(result.message)){
                        Toast.makeText(FormValidationActivity.this,result.message,Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        boolean isValid = false;
        CharSequence inputStr = phoneNumber;
        String phone="^1[34578]\\d{9}$" ;

        Pattern pattern = Pattern.compile(phone);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}

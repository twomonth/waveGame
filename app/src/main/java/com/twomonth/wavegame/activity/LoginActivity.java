package com.twomonth.wavegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.twomonth.wavegame.MessageEvent;
import com.twomonth.wavegame.R;
import com.twomonth.wavegame.bean.Register;
import com.twomonth.wavegame.util.IpAddress;
import com.twomonth.wavegame.util.LogUtil;
import com.twomonth.wavegame.util.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_tv_account)
    TextView loginTvAccount;
    @BindView(R.id.login_et_account)
    EditText loginEtAccount;
    @BindView(R.id.login_tv_password)
    TextView loginTvPassword;
    @BindView(R.id.login_et_password)
    EditText loginEtPassword;
    @BindView(R.id.login_bt_login)
    Button loginBtLogin;
    @BindView(R.id.login_tv_2register)
    TextView loginTv2register;
    @BindView(R.id.login_forget)
    TextView loginForget;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        gson = new Gson();
    }

    @OnClick({R.id.login_bt_login, R.id.login_tv_2register, R.id.login_forget})
    public void onViewClicked(View view) {
        final String userName = loginEtAccount.getText().toString().trim();
        final String passWord = loginEtPassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.login_bt_login:
                if (userName.isEmpty()||userName==""){
                    Toast.makeText(this,"账号不能为空",Toast.LENGTH_SHORT).show();
                }else if (passWord.isEmpty()||passWord==""){
                    Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    OkGo.<String>post(IpAddress.LOGIN)
                            .params("username",userName)
                            .params("password",passWord)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Register register = gson.fromJson(response.body(),Register.class);
                                    if (register.getCode()==200){
                                        PreferencesUtils.putBoolean(LoginActivity.this,"haveUser",true);
                                        PreferencesUtils.putString(LoginActivity.this,"username",userName);
                                        PreferencesUtils.putString(LoginActivity.this,"password",passWord);
                                        MessageEvent messageEvent = new MessageEvent("login_success");
                                        EventBus.getDefault().post(messageEvent);
                                        finish();
                                    }else {
                                        Toast.makeText(LoginActivity.this,register.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                break;
            case R.id.login_tv_2register:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_forget:
                break;
        }
    }
}

package com.yxh115.guild.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yxh115.guild.R;
import com.yxh115.guild.bean.Register;
import com.yxh115.guild.util.IpAddress;
import com.yxh115.guild.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_tv_account)
    TextView registerTvAccount;
    @BindView(R.id.register_et_account)
    EditText registerEtAccount;
    @BindView(R.id.register_tv_password)
    TextView registerTvPassword;
    @BindView(R.id.register_et_password)
    EditText registerEtPassword;
    @BindView(R.id.register_tv_passwordagen)
    TextView registerTvPasswordagen;
    @BindView(R.id.register_et_passwordagen)
    EditText registerEtPasswordagen;
    @BindView(R.id.register_bt_register)
    Button registerBtRegister;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        gson = new Gson();
    }

    @OnClick(R.id.register_bt_register)
    public void onViewClicked() {
        final String userName = registerEtAccount.getText().toString().trim();
        final String passWord = registerEtPassword.getText().toString().trim();
        String passWordAgen = registerEtPasswordagen.getText().toString().trim();

        if (userName.isEmpty()||userName==""){
            Toast.makeText(this,"账号不能为空",Toast.LENGTH_SHORT).show();
        }else if (passWord.isEmpty()||passWord==""){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (!passWord.equals(passWordAgen)){
            Toast.makeText(this,"您两次输入的密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
        }
        else {
            OkGo.<String>post(IpAddress.REGISTER)
                    .params("username",userName)
                    .params("password",passWord)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Register register = gson.fromJson(response.body(),Register.class);
                            LogUtil.logE(register.toString());
                            if (register.getCode()==200){
//                                PreferencesUtils.putBoolean(RegisterActivity.this,"haveUser",true);
//                                PreferencesUtils.getString(RegisterActivity.this,"username",userName);
//                                PreferencesUtils.getString(RegisterActivity.this,"password",passWord);
                                Toast.makeText(RegisterActivity.this,"注册成功赶快登陆吧。。。",Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this,register.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}

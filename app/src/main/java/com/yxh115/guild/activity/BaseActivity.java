package com.yxh115.guild.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;



public abstract class BaseActivity extends AppCompatActivity {

    /**
     * @date on 2019/3/16 0016
     * @author yxh115
     * @describe 完成初始化布局和数据操作
     *
     */


    public abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

}

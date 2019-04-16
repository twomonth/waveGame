package com.yxh115.guild.net;

import com.yxh115.guild.bean.UpDate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    //检测更新接口
    @GET("/update")
    Call<UpDate> getUpdate();
}

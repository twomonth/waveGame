package com.twomonth.wavegame.net;

import com.twomonth.wavegame.bean.UpDate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    //检测更新接口
    @GET("/update")
    Call<UpDate> getUpdate();
}

package com.twomonth.wavegame.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.twomonth.wavegame.R;
import com.twomonth.wavegame.bean.UpDate;
import com.twomonth.wavegame.util.AppUtil;
import com.twomonth.wavegame.util.IpAddress;
import com.twomonth.wavegame.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * @author twomonth
 * @date on 2019/3/15 0015
 * @describe 闪屏页面用于程序启动初始化操作、app更新检测等功能
 */
public class SplashActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    Gson gson = new Gson();
    @BindView(R.id.splash_iv)
    ImageView splashIv;
    @BindView(R.id.versionname_tv)
    TextView versionnameTv;
    int code;
    String apkUrl;
    UpDate upDate = null;
    AlertDialog.Builder builder = null;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            toHomeActivity();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        versionnameTv.setText(AppUtil.getVersionName(this));

        //闪屏页面广告
        OkGo.<String>get(IpAddress.IV_SPLASH).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    AppUtil.setImageForView(SplashActivity.this,jsonObject.getString("data"),splashIv);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SplashActivity.this,"广告地址错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppUtil.setImageForView(this,IpAddress.IV_SPLASH,splashIv);
        //检测更新
        OkGo.<String>get(IpAddress.UPDATE).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                upDate = gson.fromJson(response.body(),UpDate.class);
                LogUtil.logE(upDate.toString());
                if (upDate.getData().getVersioncode()>AppUtil.getVersionCode(getApplicationContext())){
                  showMyDialog();
                }else {
                    handler.sendEmptyMessageDelayed(1,2000);

                }
            }
        });

    }

    private void toHomeActivity(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void showMyDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(upDate.getData().getMessage());
        builder.setNegativeButton("再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (upDate.getData().isMust()){

                }else {
                    toHomeActivity();
                }
            }
        });
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //更新操作写到下载的时候再说
                requestPermissions();
            }
        });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                toHomeActivity();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 去申请权限
     */
    private void requestPermissions() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        //判断有没有权限
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 如果有权限了, 就做你该做的事情
            downloadApk();
        } else {
            // 如果没有权限, 就去申请权限
            // this: 上下文
            // Dialog显示的正文
            // RC_CAMERA_AND_RECORD_AUDIO 请求码, 用于回调的时候判断是哪次申请
            // perms 就是你要申请的权限
            EasyPermissions.requestPermissions(this, "下载游戏安装包需要获取您的存储卡读写权限，不然无法下载。", 1, perms);
        }
    }
    /**
     * 权限申请成功的回调
     *
     * @param requestCode 申请权限时的请求码
     * @param perms 申请成功的权限集合
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        code = requestCode;
        downloadApk();
    }

    /**
     * 权限申请拒绝的回调
     *
     * @param requestCode 申请权限时的请求码
     * @param perms 申请拒绝的权限集合
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    private void downloadApk() {
        // 创建下载请求
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(upDate.getData().getUpdate_url()));

        /*
         * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
         *    VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
         *    VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
         *    VISIBILITY_HIDDEN:                    始终不显示通知
         */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // 设置通知的标题和描述
        request.setTitle("115");
        request.setDescription("正在下载");

        /*
         * 设置允许使用的网络类型, 可选值:
         *     NETWORK_MOBILE:      移动网络
         *     NETWORK_WIFI:        WIFI网络
         *     NETWORK_BLUETOOTH:   蓝牙网络
         * 默认为所有网络都允许
         */
        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

        // 添加请求头
        // request.addRequestHeader("User-Agent", "Chrome Mozilla/5.0");

        // 设置下载文件的保存位置
        File saveFile = new File(Environment.getExternalStorageDirectory(), upDate.getData().getUpdate_url());
        request.setDestinationUri(Uri.fromFile(saveFile));

        /*
         * 2. 获取下载管理器服务的实例, 添加下载任务
         */
        DownloadManager manager = (DownloadManager) SplashActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

        // 将下载请求加入下载队列, 返回一个下载ID
        long downloadId = manager.enqueue(request);
    }
}

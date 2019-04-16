package com.yxh115.guild.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yxh115.guild.R;
import com.yxh115.guild.bean.GameInfo;
import com.yxh115.guild.util.IpAddress;
import com.yxh115.guild.util.LogUtil;

import java.io.File;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class GameActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    int gid;
    @BindView(R.id.iv_game_back)
    ImageView ivGameBack;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_game)
    AppBarLayout appBarGame;
    @BindView(R.id.iv_gameinfo_icon)
    ImageView ivGameinfoIcon;
    @BindView(R.id.tv_gameinfo_size)
    TextView tvGameinfoSize;
    @BindView(R.id.tv_gameinfo_downloadcount)
    TextView tvGameinfoDownloadcount;
    @BindView(R.id.tv_gameinfo_label)
    TextView tvGameinfoLabel;
    @BindView(R.id.tv_gameinfo_duction)
    TextView tvGameinfoDuction;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_gameinfo_gamets)
    TextView tvGameinfoGamets;
    @BindView(R.id.iv_game_back2)
    ImageView ivGameBack2;
    @BindView(R.id.tv_gameinfo_qq)
    TextView tvGameinfoQq;
    @BindView(R.id.tv_gameinfo_qq_qun)
    TextView tvGameinfoQqQun;
    @BindView(R.id.fa_gameinfo_qq)
    FloatingActionButton faGameinfoQq;
    @BindView(R.id.fa_gameinfo_qqq)
    FloatingActionButton faGameinfoQqq;
    @BindView(R.id.fa_gameinfo_download)
    FloatingActionButton faGameinfoDownload;
    @BindView(R.id.tv_game_title)
    TextView tvGameTitle;
    @BindView(R.id.nestedScrollView2)
    NestedScrollView nestedScrollView2;
    private Gson gson = null;

    GameInfo gameInfo;
    Uri uri_qq;
    Uri uri_qqq;
    String downloadUrl;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        gid = getIntent().getIntExtra("gid", 0);
        gson = new Gson();
        faGameinfoQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, "qq", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri_qq);
                startActivity(intent);
            }
        });
        faGameinfoQqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, "qqq", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri_qqq);
                startActivity(intent);
            }
        });
        faGameinfoDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (downloadUrl.contains(".apk")){
//                    requestPermissions();
//                }else {
//                    Intent intent = new Intent(GameActivity.this,GameWebActivity.class);
//                    intent.putExtra("url",downloadUrl);
//                    startActivity(intent);
//                }
                requestPermissions();
                Intent intent = new Intent(GameActivity.this, GameWebActivity.class);
                intent.putExtra("url", downloadUrl);
                startActivity(intent);
            }
        });
        initData();
    }

    private void downloadApk() {
        // 创建下载请求
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        /*
         * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
         *    VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
         *    VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
         *    VISIBILITY_HIDDEN:                    始终不显示通知
         */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // 设置通知的标题和描述
        request.setTitle(gameInfo.getData().getData().getGamename());
        request.setDescription(gameInfo.getData().getData().getGamesize());

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
        File saveFile = new File(Environment.getExternalStorageDirectory(), gameInfo.getData().getData().getGamename() + ".apk");
        request.setDestinationUri(Uri.fromFile(saveFile));

        /*
         * 2. 获取下载管理器服务的实例, 添加下载任务
         */
        DownloadManager manager = (DownloadManager) GameActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

        // 将下载请求加入下载队列, 返回一个下载ID
        long downloadId = manager.enqueue(request);
    }

    private void initData() {
        OkGo.<String>get(IpAddress.GAMEINFO)
                .params("gid", gid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        gameInfo = gson.fromJson(response.body(), GameInfo.class);
                        LogUtil.logE(gameInfo.getData().getData().getAndroiddownload() + "---");
                        setSupportActionBar(toolBar);
                        //设置返回按钮
                        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setTitle("");
                        tvGameTitle.setText(gameInfo.getData().getData().getGamename());
                        Glide.with(GameActivity.this).load(gameInfo.getData().getData().getIcon()).into(ivGameinfoIcon);
                        Glide.with(GameActivity.this).load(gameInfo.getData().getData().getNew_img().split(",")[0]).into(ivGameBack);
                        Glide.with(GameActivity.this).load(gameInfo.getData().getData().getNew_img().split(",")[1]).into(ivGameBack2);
                        tvGameinfoSize.setText(gameInfo.getData().getData().getGamesize());
                        tvGameinfoDownloadcount.setText(gameInfo.getData().getData().getDownloadcount() + "次下载");
                        tvGameinfoDuction.setText(gameInfo.getData().getData().getDuction());
                        tvGameinfoLabel.setText(gameInfo.getData().getData().getLabel());
                        tvGameinfoQq.setText(gameInfo.getData().getData().getKfqq());
                        tvGameinfoQqQun.setText(gameInfo.getData().getData().getKfqq_qun());
                        tvDetails.setText(gameInfo.getData().getData().getDetails());
                        tvGameinfoGamets.setText(Html.fromHtml(gameInfo.getData().getData().getGamets()));
                        uri_qq = Uri.parse(gameInfo.getData().getData().getKfqq());
                        uri_qqq = Uri.parse(gameInfo.getData().getData().getKfqq_qun());
                        downloadUrl = gameInfo.getData().getData().getAndroiddownload();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
     * @param perms       申请成功的权限集合
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
     * @param perms       申请拒绝的权限集合
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

}

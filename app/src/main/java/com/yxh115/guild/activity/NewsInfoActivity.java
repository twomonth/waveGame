package com.yxh115.guild.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yxh115.guild.R;
import com.yxh115.guild.bean.NewsInfo;
import com.yxh115.guild.util.IpAddress;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_newsinfo)
    Toolbar toolbarNewsinfo;
    @BindView(R.id.appbar_newsinfo)
    AppBarLayout appbarNewsinfo;
    @BindView(R.id.tv_newsinfo)
    TextView tvNewsinfo;
    Gson gson = null;
    int nid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_news_info);
        ButterKnife.bind(this);
        nid = getIntent().getIntExtra("nid",0);
        gson = new Gson();
        setSupportActionBar(toolbarNewsinfo);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("新闻");
        initData();

    }

    private void initData() {
        OkGo.<String>get(IpAddress.NEWSINFO)
                .params("news_id",nid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        NewsInfo newsInfo = gson.fromJson(response.body(),NewsInfo.class);
                        setTitle(newsInfo.getData().getTitle());
                        tvNewsinfo.setText(Html.fromHtml(newsInfo.getData().getContent()));
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
}

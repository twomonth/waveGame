package com.yxh115.guild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yxh115.guild.R;
import com.yxh115.guild.adapter.NewsAdapter;
import com.yxh115.guild.bean.News;
import com.yxh115.guild.util.IpAddress;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_RIGHT;

public class NewsActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_news)
    Toolbar toolbarNews;
    @BindView(R.id.tabLayout_news)
    TabLayout tabLayoutNews;
    @BindView(R.id.appbar_news)
    AppBarLayout appbarNews;
    @BindView(R.id.recycle_news)
    RecyclerView recycleNews;

    Gson gson;
    int type = 0,page=1;
    NewsAdapter adapter= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        gson = new Gson();
        setSupportActionBar(toolbarNews);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("新闻");
        tabLayoutNews.addTab(tabLayoutNews.newTab().setText("全部"),true);
        tabLayoutNews.addTab(tabLayoutNews.newTab().setText("活动 "));
        tabLayoutNews.addTab(tabLayoutNews.newTab().setText("新闻"));
        tabLayoutNews.addTab(tabLayoutNews.newTab().setText("攻略"));
        tabLayoutNews.addTab(tabLayoutNews.newTab().setText("公告"));

        loadNews();

        tabLayoutNews.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition();
                page = 1;
                loadNews();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

    void loadNews(){
        OkGo.<String>get(IpAddress.NEWS)
                .params("type",type)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        News news = gson.fromJson(response.body(),News.class);
                        adapter = new NewsAdapter(R.layout.item_news,news.getData());
                        adapter.openLoadAnimation(SLIDEIN_RIGHT );
                        adapter.isFirstOnly(false);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewsActivity.this);
                        recycleNews.setLayoutManager(linearLayoutManager);
                        recycleNews.setAdapter(adapter);
                        //上啦加载更多
                        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                page++;
                                loadMoreData(adapter);
                            }
                        },recycleNews);

                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                Toast.makeText(getApplicationContext(),((GameItem.DataBean.ListBean)adapter.getData().get(position)).getGamename()+"",Toast.LENGTH_SHORT).show();
                                toNewsInfoActivity(((News.DataBean)adapter.getData().get(position)).getNews_id());
                            }
                        });
                    }
                });
    }


    /**
     * @date on 2019/3/22 0022
     * @author yxh115
     * @describe 上拉加载更多数据
     *
     */
    void loadMoreData(final NewsAdapter adapter){
        OkGo.<String>get(IpAddress.NEWS)
                .params("type",type)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        News news = gson.fromJson(response.body(),News.class);
                        adapter.addData(news.getData());
                        adapter.loadMoreComplete();
                        if (news.getData().size()<10){
                            adapter.loadMoreEnd();
                        }
                    }
                });
    }

    /**
     * @date on 2019/3/22 0022
     * @author yxh115
     * @describe 跳转到游戏详情页面
     *
     */
    void toNewsInfoActivity(int nid){
        Intent intent = new Intent(this,NewsInfoActivity.class);
        intent.putExtra("nid",nid);
        startActivity(intent);
    }
}

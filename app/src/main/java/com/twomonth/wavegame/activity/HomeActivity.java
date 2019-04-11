package com.twomonth.wavegame.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.twomonth.wavegame.MessageEvent;
import com.twomonth.wavegame.R;
import com.twomonth.wavegame.adapter.GameItemAdapter;
import com.twomonth.wavegame.adapter.WebBannerAdapter;
import com.twomonth.wavegame.bean.Banner;
import com.twomonth.wavegame.bean.GameItem;
import com.twomonth.wavegame.util.IpAddress;
import com.twomonth.wavegame.util.LogUtil;
import com.twomonth.wavegame.util.PreferencesUtils;
import com.twomonth.wavegame.view.banner.BannerLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;
import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_RIGHT;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.fl_center)
//    FrameLayout flCenter;
    @BindView(R.id.content_home)
    ConstraintLayout contentHome;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.action_bar)
    AppBarLayout actionBar;
    @BindView(R.id.bannerLayout)
    BannerLayout bannerLayout;
    @BindView(R.id.vertical_tab)
    VerticalTabLayout verticalTab;
    @BindView(R.id.recycle_home)
    RecyclerView recycleHome;

    View headView;
    TextView textView_userName;

    private String[] typeNum = null;
    //    private FragmentManager mFragmentManager = getSupportFragmentManager();
//    private Fragment mCurrentFragment = new BTFragment();
    private Gson gson;
    private int mold = 1,type = 1 , page=1;
    private Banner banner = null;
    private List<String> bannerImageList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        typeNum = getResources().getStringArray(R.array.type);
        ButterKnife.bind(this);
        gson = new Gson();

        EventBus.getDefault().register(this);

        headView = navView.getHeaderView(0);
        textView_userName = headView.findViewById(R.id.tv_username);

        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        //下拉选择spinner
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext()
                , R.array.spinner, android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = new Spinner(getSupportActionBar().getThemedContext());
        spinner.setAdapter(spinnerAdapter);
        spinner.setPopupBackgroundResource(R.drawable.yuanjiaojuxing);
        toolbar.addView(spinner);

        final Switch witch = new Switch(this);
        toolbar.addView(witch);
        witch.setChecked(false);
        witch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    verticalTab.setVisibility(View.GONE);
                }else {
                    verticalTab.setVisibility(View.VISIBLE);
                }
            }
        });

        setTitle("");
        //侧滑菜单绑定
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //bannerlayout 加载轮播图
        initBanner();
        bannerLayout.setFocusable(true);
        bannerLayout.setFocusableInTouchMode(true);
        bannerLayout.requestFocus();
        bannerLayout.requestFocusFromTouch();

        //填充默认fragment
//        switchFragment(ZKFragment.class);

        //spinner监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(Color.WHITE); //设置颜色
                mold = position+1;
                page = 1;
                initRecycle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //tablayout
        initTab();
        verticalTab.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                type = position + 1;
                page = 1;
                initRecycle();
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_download:
                        startActivity(new Intent(HomeActivity.this,SearchActivity.class));
                        break;
                    case R.id.nav_start:
                        startActivity(new Intent(HomeActivity.this,KaifuActivity.class));
                        break;
                    case R.id.nav_news:
                        startActivity(new Intent(HomeActivity.this,NewsActivity.class));
                        break;
                        default:
                            break;
                }
                return false;
            }
        });
//        navView.getHeaderView().setOnClickListener();
        navView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        initRecycle();

    }

    /**
     * @date on 2019/3/19 0019
     * @author twomonth
     * @describe 初始化tablayout
     */
    private void initTab() {
        verticalTab.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return typeNum.length;
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent(typeNum[position])
                        .setTextColor(Color.parseColor("#F6BC29"), Color.WHITE)
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });
    }

    /**
     * @date on 2019/3/17 0017
     * @author twomonth
     * @describe 选择填充的fragment
     */
//    private void switchFragment(Class<?> clazz) {
//        Fragment fragment = FragmentManagerWrapper.getInstance().createFragment(clazz);
//        if (fragment.isAdded()) {
//            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
//        } else {
//            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.fl_center, fragment).commitAllowingStateLoss();
//        }
//        mCurrentFragment = fragment;
//    }

    /**
     * @date on 2019/3/19 0019
     * @author twomonth
     * @describe 轮播图加载
     */
    private void initBanner() {
        OkGo.<String>get(IpAddress.BANNER).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                banner = gson.fromJson(response.body(), Banner.class);
                for (Banner.DataBean dataBean : banner.getData()
                        ) {
                    bannerImageList.add(dataBean.getImage());
                }
                WebBannerAdapter adapter = new WebBannerAdapter(HomeActivity.this, bannerImageList);
                adapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        toGameActivity(banner.getData().get(position).getGid());
                    }
                });
                bannerLayout.setAdapter(adapter);
            }
        });
    }

    /**
     * @date on 2019/3/19 0019
     * @author twomonth
     * @describe 填充recycleview
     *
     */
    void initRecycle(){
        OkGo.<String>post(IpAddress.HOME_GAMES)
                .params("mold",mold)
                .params("type",type)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GameItem gameItem = gson.fromJson(response.body(),GameItem.class);
                        final GameItemAdapter adapter = new GameItemAdapter(R.layout.item_game,gameItem.getData().getList());
                        adapter.openLoadAnimation(SLIDEIN_RIGHT );
                        adapter.isFirstOnly(false);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
                        recycleHome.setLayoutManager(linearLayoutManager);
                        recycleHome.setAdapter(adapter);
                        //上啦加载更多
                        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                page++;
                                loadMoreData(adapter);
                            }
                        },recycleHome);

                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                Toast.makeText(getApplicationContext(),((GameItem.DataBean.ListBean)adapter.getData().get(position)).getGamename()+"",Toast.LENGTH_SHORT).show();
                                toGameActivity(((GameItem.DataBean.ListBean)adapter.getData().get(position)).getGid());
                            }
                        });
                    }
                });
    }

    /**
     * @date on 2019/3/22 0022
     * @author twomonth
     * @describe 上拉加载更多数据
     *
     */
    void loadMoreData(final GameItemAdapter adapter){
        OkGo.<String>post(IpAddress.HOME_GAMES)
                .params("mold",mold)
                .params("type",type)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GameItem gameItem = gson.fromJson(response.body(),GameItem.class);
                        adapter.addData(gameItem.getData().getList());
                        adapter.loadMoreComplete();
                        if (gameItem.getData().getList().size()<10){
                            adapter.loadMoreEnd();
                        }
                    }
                });
    }

    /**
     * @date on 2019/3/22 0022
     * @author twomonth
     * @describe 跳转到游戏详情页面
     *
     */
    void toGameActivity(int gid){
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("gid",gid);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * @创建日期 on 2019/3/28 0028
     * @作者 twomonth
     * @简单描述 判断是否有本地用户记录并显示
     * @param
     * @返回值
     */
    void haveUser(){
        if (PreferencesUtils.getBoolean(HomeActivity.this,"haveUser",false)){
            textView_userName.setText(PreferencesUtils.getString(this,"username"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLoginCallback(MessageEvent messageEvent){
        if (messageEvent.getMessage().equals("login_success")){
            String userName = PreferencesUtils.getString(HomeActivity.this,"username");
            textView_userName.setText(userName);
        }
    }
}

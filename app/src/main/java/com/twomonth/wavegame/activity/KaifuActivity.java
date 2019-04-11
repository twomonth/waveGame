package com.twomonth.wavegame.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.twomonth.wavegame.R;
import com.twomonth.wavegame.adapter.MyViewPagerAdapter;
import com.twomonth.wavegame.fragment.BTFragment;
import com.twomonth.wavegame.fragment.H5Fragment;
import com.twomonth.wavegame.fragment.StartAllreadyFragment;
import com.twomonth.wavegame.fragment.StartTodayFragment;
import com.twomonth.wavegame.fragment.StartTommrroFragment;
import com.twomonth.wavegame.fragment.ZKFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KaifuActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_start)
    Toolbar toolbarStart;
    @BindView(R.id.tabLayout_start)
    TabLayout tabLayoutStart;
    @BindView(R.id.appbar_start)
    AppBarLayout appbarStart;
    @BindView(R.id.viewPager_start)
    ViewPager viewPagerStart;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaifu);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarStart);
        //设置返回按钮
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setTitle("开服");

        List<String> titleList = new ArrayList<>();
        titleList.add("今日");
        titleList.add("明日");
        titleList.add("已开");

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new StartTodayFragment());
        list.add(new StartTommrroFragment());
        list.add(new StartAllreadyFragment());
        viewPagerStart.setAdapter(new MyViewPagerAdapter(this,getSupportFragmentManager(),list,titleList));
        tabLayoutStart.setupWithViewPager(viewPagerStart);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.twomonth.wavegame.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.twomonth.wavegame.R;
import com.twomonth.wavegame.activity.GameActivity;
import com.twomonth.wavegame.adapter.StartItemAdapter;
import com.twomonth.wavegame.bean.Start;
import com.twomonth.wavegame.util.IpAddress;

import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_RIGHT;


public class StartAllreadyFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private Gson gson;
    private int page = 1;
    private int type = 3;
    StartItemAdapter adapter = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_startalready;
    }

    @Override
    protected void initView() {
        recyclerView = mContentView.findViewById(R.id.recycle_start_already);
    }

    @Override
    protected void initData() {
        gson = new Gson();
        OkGo.<String>get(IpAddress.START)
                .params("type",type)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Start start = gson.fromJson(response.body(),Start.class);
                        adapter = new StartItemAdapter(R.layout.item_start,start.getData().getList());
                        adapter.openLoadAnimation(SLIDEIN_RIGHT );
                        adapter.isFirstOnly(false);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                        //上啦加载更多
                        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                page++;
                                loadMoreData(adapter);
                            }
                        },recyclerView);

                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                toGameActivity(((Start.DataBean.ListBean)adapter.getData().get(position)).getGid());
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
    void loadMoreData(final StartItemAdapter adapter){
        OkGo.<String>get(IpAddress.START)
                .params("type",type)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Start start = gson.fromJson(response.body(),Start.class);
                        adapter.addData(start.getData().getList());
                        adapter.loadMoreComplete();
                        if (start.getData().getList().size()<10){
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
        Intent intent = new Intent(getActivity(),GameActivity.class);
        intent.putExtra("gid",gid);
        startActivity(intent);
    }
}

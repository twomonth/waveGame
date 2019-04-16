package com.yxh115.guild.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxh115.guild.R;

import com.yxh115.guild.bean.Start;

import java.util.List;

public class StartItemAdapter extends BaseQuickAdapter<Start.DataBean.ListBean,BaseViewHolder> {

    public StartItemAdapter(int layoutResId, @Nullable List<Start.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Start.DataBean.ListBean item) {
        helper.setText(R.id.game_start,item.getGamename());
        helper.setText(R.id.server,item.getServer());
        helper.setText(R.id.time,item.getKfday());
        helper.setText(R.id.duction,item.getDuction());
        Glide.with(mContext).load(item.getIcon()).into((ImageView) helper.getView(R.id.icon_start));
    }

}

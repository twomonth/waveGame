package com.twomonth.wavegame.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.twomonth.wavegame.R;
import com.twomonth.wavegame.bean.GameItem;

import java.util.List;

public class GameItemAdapter extends BaseQuickAdapter<GameItem.DataBean.ListBean,BaseViewHolder> {

    public GameItemAdapter(int layoutResId, @Nullable List<GameItem.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GameItem.DataBean.ListBean item) {
        helper.setText(R.id.gamename_item_game,item.getGamename());
        helper.setText(R.id.gamemessage_item_game,item.getDuction());
        helper.setText(R.id.tv_lable1,item.getLabel().split(",")[0]);
        helper.setText(R.id.tv_lable2,item.getLabel().split(",")[1]);
        Glide.with(mContext).load(item.getIcon()).into((ImageView) helper.getView(R.id.icon_item_game));
    }
}

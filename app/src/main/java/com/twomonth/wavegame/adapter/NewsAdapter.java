package com.twomonth.wavegame.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.twomonth.wavegame.R;
import com.twomonth.wavegame.bean.News;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<News.DataBean, BaseViewHolder> {


    public NewsAdapter(int layoutResId, @Nullable List<News.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, News.DataBean item) {
        helper.setText(R.id.news_title,item.getTitle());
        Glide.with(mContext).load(item.getBgimg()).into((ImageView) helper.getView(R.id.news_image));
    }
}

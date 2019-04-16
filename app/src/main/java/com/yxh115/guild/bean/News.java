package com.yxh115.guild.bean;

import java.util.List;

public class News {

    /**
     * page : 1
     * data : [{"news_id":22,"title":"《胡来三国》孙权厉害吗-孙权技能分析_金皇朝官网","bgimg":"http://pic5.3733.com/dynamic/201903/eeab1092513557c73e652765a3e7d02a_n.jpeg","create_time":1551959611},{"news_id":21,"title":"多人热血PK战斗手游《暗黑纪元-黑魂觉醒》今日10:00正式开服","bgimg":"http://pic5.3733.com/dynamic/201901/6f03543aced6a445fa4d6785d4e1d5d8_n.jpeg","create_time":1547102562},{"news_id":20,"title":"少年三国志杀神版【新人必看】","bgimg":"http://oss2.339sy.cn/115game/news/20190108205245.jpeg","create_time":1546951966},{"news_id":19,"title":"2018年精美3D回合制手游力作《梦回仙游星耀版》手游评测介绍","bgimg":"http://pic5.3733.com/dynamic/201812/0a751b42d36b3e43ce579b6f3b0c0966_n.jpeg","create_time":1546947461},{"news_id":18,"title":"[新游预告]《Q萌修仙传商城版》上线送满V、20000绑钻、10万绑银","bgimg":"http://pic5.3733.com/dynamic/201901/0dd99cc10a51b4e9a61baa6f0060cf47_n.jpeg","create_time":1546947410},{"news_id":17,"title":"《盛唐-巅峰版》\u200b游戏单人活动盘点","bgimg":"http://pic5.3733.com/dynamic/201901/39bc21f84db17a3d7bc71b52260ff198.png","create_time":1546938193},{"news_id":16,"title":"新手该怎么玩《神奇宝贝日月海量版》\u200b新手攻略分享","bgimg":"http://pic5.3733.com/dynamic/201901/45da61793e1d47e84b63bb3a2f7bf592.png","create_time":1546938161},{"news_id":15,"title":"玩转boss系统《斗罗乾坤》刷boss有小妙招","bgimg":"http://pic5.3733.com/dynamic/201901/76bb3708d9886a9cc3aea86b9f5c24a4.png","create_time":1546938114},{"news_id":14,"title":"[新游预告]《最佳足球经理》上线送VIP5，金球10000，资金50W","bgimg":"http://pic5.3733.com/dynamic/201901/4d03b62108b95cc739ece5a2eaac6504_n.jpeg","create_time":1546938025},{"news_id":13,"title":"《三国高爆版》酒馆玩法攻略一览","bgimg":"http://pic5.3733.com/dynamic/201901/8584188e55c2dfef79811a4e04c9450a.png","create_time":1546937965}]
     */

    private int page;
    private List<DataBean> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * news_id : 22
         * title : 《胡来三国》孙权厉害吗-孙权技能分析_金皇朝官网
         * bgimg : http://pic5.3733.com/dynamic/201903/eeab1092513557c73e652765a3e7d02a_n.jpeg
         * create_time : 1551959611
         */

        private int news_id;
        private String title;
        private String bgimg;
        private int create_time;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBgimg() {
            return bgimg;
        }

        public void setBgimg(String bgimg) {
            this.bgimg = bgimg;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}

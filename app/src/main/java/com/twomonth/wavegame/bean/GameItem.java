package com.twomonth.wavegame.bean;

import java.util.List;

public class GameItem {

    /**
     * code : 200
     * message : success
     * data : {"page":1,"list":[{"gid":23490,"icon":"http://img.sy115.com/Public/Upload/Product/2019-03-15/5c8af762d8bd0.png","gamename":"BT,H5,GM手游私服-麦游盒子-送首冲版本","duction":"麦游盒子,内置GM手游","label":"即时,卡牌"},{"gid":202,"icon":"http://img.sy115.com/Public/Upload/Product/2017-09-19/59c1129532b9b.png","gamename":"10元无限元宝游戏盒子","duction":"115手游GM盒子,10元无限元宝,无限金币","label":"即时,卡牌"},{"gid":141,"icon":"http://img.sy115.com/Public/Upload/Product/2017-08-19/5998391ba932c.png","gamename":"115手游盒子","duction":"内置3733盒子,乐嗨嗨,BT手游盒子10元无限元宝的所有游戏","label":"即时,卡牌"},{"gid":314,"icon":"http://img.sy115.com/Public/Upload/Product/2018-04-12/5acf31b9b94e5.jpg","gamename":" 少年三国志杀神版","duction":"送VIP12,30亿元宝(游戏签到处领取)","label":"卡牌,策略"},{"gid":22953,"icon":"http://www.985sy.com/attachment/syapp/logo/201811151542276248.jpg","gamename":"我不是MT-满V","duction":"送vip11,钻石138888,金币3000W","label":"动作,挂机"},{"gid":502,"icon":"http://img.sy115.com/Public/Upload/Product/2018-03-06/5a9df9fc65328.png","gamename":"天天点西游","duction":"送8888元宝,金币100W","label":"策略,挂机"},{"gid":22159,"icon":"http://www.985sy.com/attachment/syapp/logo/201809131536832270.jpg","gamename":"宝可梦进化","duction":"送vip10,钻石26888,铜钱500W","label":"卡牌,策略"},{"gid":261,"icon":"http://img.sy115.com/Public/Upload/Product/2017-10-25/59f07d1282eb2.png","gamename":"我叫MT变态山口山","duction":"满VIP10,元宝18888,金币100万","label":"卡牌,策略"},{"gid":22816,"icon":"http://pic3.3733.com/d/file/game/2018-10-18/79a93f37b2d18bb7a35d7115a8850006.png","gamename":"仙道回合","duction":"送VIP5,元宝*8888,铜钱*888W","label":"动作,回合"},{"gid":21416,"icon":"http://www.985sy.com/attachment/syapp/logo/201808221534927356.jpg","gamename":"菜鸟大掌门（武娘）","duction":"送vip12,元宝18888,银两1000W","label":"卡牌,策略"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : 1
         * list : [{"gid":23490,"icon":"http://img.sy115.com/Public/Upload/Product/2019-03-15/5c8af762d8bd0.png","gamename":"BT,H5,GM手游私服-麦游盒子-送首冲版本","duction":"麦游盒子,内置GM手游","label":"即时,卡牌"},{"gid":202,"icon":"http://img.sy115.com/Public/Upload/Product/2017-09-19/59c1129532b9b.png","gamename":"10元无限元宝游戏盒子","duction":"115手游GM盒子,10元无限元宝,无限金币","label":"即时,卡牌"},{"gid":141,"icon":"http://img.sy115.com/Public/Upload/Product/2017-08-19/5998391ba932c.png","gamename":"115手游盒子","duction":"内置3733盒子,乐嗨嗨,BT手游盒子10元无限元宝的所有游戏","label":"即时,卡牌"},{"gid":314,"icon":"http://img.sy115.com/Public/Upload/Product/2018-04-12/5acf31b9b94e5.jpg","gamename":" 少年三国志杀神版","duction":"送VIP12,30亿元宝(游戏签到处领取)","label":"卡牌,策略"},{"gid":22953,"icon":"http://www.985sy.com/attachment/syapp/logo/201811151542276248.jpg","gamename":"我不是MT-满V","duction":"送vip11,钻石138888,金币3000W","label":"动作,挂机"},{"gid":502,"icon":"http://img.sy115.com/Public/Upload/Product/2018-03-06/5a9df9fc65328.png","gamename":"天天点西游","duction":"送8888元宝,金币100W","label":"策略,挂机"},{"gid":22159,"icon":"http://www.985sy.com/attachment/syapp/logo/201809131536832270.jpg","gamename":"宝可梦进化","duction":"送vip10,钻石26888,铜钱500W","label":"卡牌,策略"},{"gid":261,"icon":"http://img.sy115.com/Public/Upload/Product/2017-10-25/59f07d1282eb2.png","gamename":"我叫MT变态山口山","duction":"满VIP10,元宝18888,金币100万","label":"卡牌,策略"},{"gid":22816,"icon":"http://pic3.3733.com/d/file/game/2018-10-18/79a93f37b2d18bb7a35d7115a8850006.png","gamename":"仙道回合","duction":"送VIP5,元宝*8888,铜钱*888W","label":"动作,回合"},{"gid":21416,"icon":"http://www.985sy.com/attachment/syapp/logo/201808221534927356.jpg","gamename":"菜鸟大掌门（武娘）","duction":"送vip12,元宝18888,银两1000W","label":"卡牌,策略"}]
         */

        private int page;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * gid : 23490
             * icon : http://img.sy115.com/Public/Upload/Product/2019-03-15/5c8af762d8bd0.png
             * gamename : BT,H5,GM手游私服-麦游盒子-送首冲版本
             * duction : 麦游盒子,内置GM手游
             * label : 即时,卡牌
             */

            private int gid;
            private String icon;
            private String gamename;
            private String duction;
            private String label;

            public int getGid() {
                return gid;
            }

            public void setGid(int gid) {
                this.gid = gid;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getGamename() {
                return gamename;
            }

            public void setGamename(String gamename) {
                this.gamename = gamename;
            }

            public String getDuction() {
                return duction;
            }

            public void setDuction(String duction) {
                this.duction = duction;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }
    }
}

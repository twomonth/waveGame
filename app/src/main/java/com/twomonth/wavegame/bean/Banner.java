package com.twomonth.wavegame.bean;

import java.util.List;

public class Banner {

    /**
     * code : 200
     * message : success
     * data : [{"image":"http://oss2.339sy.cn/115game/tj/20190108170447.png","gid":22954},{"image":"http://img.sy115.com/Public/Upload/Product/2018-10-23/5bcea81474259.jpg","gid":22660},{"image":"http://img.sy115.com/Public/Upload/Product/2018-10-23/5bcea6eda700d.jpg","gid":22157},{"image":"http://img.sy115.com/Public/Upload/Product/2018-08-17/5b7681ce0c723.jpg","gid":956},{"image":"http://img.sy115.com/Public/Upload/Product/2018-05-29/5b0cb37e88f5d.jpg","gid":724}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image : http://oss2.339sy.cn/115game/tj/20190108170447.png
         * gid : 22954
         */

        private String image;
        private int gid;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }
    }
}

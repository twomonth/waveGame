package com.yxh115.guild.bean;

public class UpDate {


    /**
     * data : {"versioncode":4,"versionname":"1.2","update_url":"http://oss2.339sy.cn/box/115yxh.apk","message":"115手游APP全面更新,感谢大家的支持与厚爱,新APP增加登录,注册.后续针对老玩家增加商城系统,如果无法自动更新.请访问我们官网:www.sy115.com进行手动下载更新。","must":true}
     * code : 200
     * message : success
     */

    private DataBean data;
    private int code;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * versioncode : 4
         * versionname : 1.2
         * update_url : http://oss2.339sy.cn/box/115yxh.apk
         * message : 115手游APP全面更新,感谢大家的支持与厚爱,新APP增加登录,注册.后续针对老玩家增加商城系统,如果无法自动更新.请访问我们官网:www.sy115.com进行手动下载更新。
         * must : true
         */

        private int versioncode;
        private String versionname;
        private String update_url;
        private String message;
        private boolean must;

        public int getVersioncode() {
            return versioncode;
        }

        public void setVersioncode(int versioncode) {
            this.versioncode = versioncode;
        }

        public String getVersionname() {
            return versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public String getUpdate_url() {
            return update_url;
        }

        public void setUpdate_url(String update_url) {
            this.update_url = update_url;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isMust() {
            return must;
        }

        public void setMust(boolean must) {
            this.must = must;
        }
    }
}

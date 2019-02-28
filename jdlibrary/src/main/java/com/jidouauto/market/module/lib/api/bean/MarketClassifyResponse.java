package com.jidouauto.market.module.lib.api.bean;

import java.util.List;

/**
 * Created tangxin
 * Time 2018/11/5 11:31 AM
 */
public class MarketClassifyResponse extends MarketBaseResponse {


    private List<DataBean> data;

    /**
     * 分类列表
     */
    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private int id;
        private String name;
        private int channelId;
        private String icon;
        private String createTime;
        private String updateTime;

        /**
         * 分类ID
         */
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        /**
         * 分类名称
         */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 渠道ID
         */
        public int getChannelId() {
            return channelId;
        }

        public void setChannelId(int channelId) {
            this.channelId = channelId;
        }

        /**
         * 分类图标
         */
        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        /**
         * 创建时间
         */
        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
        /**
         * 更新时间
         */
        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}

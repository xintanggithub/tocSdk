package com.jidouauto.market.module.lib.api.bean;

import com.jidouauto.market.module.lib.common.StringUtils;

import java.util.List;

/**
 * Created tangxin
 * Time 2018/10/24 4:50 PM
 */
public class MarketDetailResponse extends MarketBaseResponse{

    private DetailBean detail;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public static class DetailBean {

        private int id;
        private String name;
        private String packageName;
        private String url;
        private String versionName;
        private int versionCode;
        private String checksum;
        private int size;
        private String icon;
        private String updateLog;
        private String description;
        private String publisher;
        private float averageRate;
        private StatisticsBean statistics;
        private MarketCommentsResponse.CommentListBean latestComment;
        private int apiLevel;
        private List<String> pics;

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String formatSize() {
            return StringUtils.getFileSize(size);
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getChecksum() {
            return checksum;
        }

        public void setChecksum(String checksum) {
            this.checksum = checksum;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUpdateLog() {
            return updateLog;
        }

        public void setUpdateLog(String updateLog) {
            this.updateLog = updateLog;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public float getAverageRate() {
            return averageRate;
        }

        public void setAverageRate(float averageRate) {
            this.averageRate = averageRate;
        }

        public StatisticsBean getStatistics() {
            return statistics;
        }

        public void setStatistics(StatisticsBean statistics) {
            this.statistics = statistics;
        }

        public MarketCommentsResponse.CommentListBean getLatestComment() {
            return latestComment;
        }

        public void setLatestComment(MarketCommentsResponse.CommentListBean latestComment) {
            this.latestComment = latestComment;
        }

        public int getApiLevel() {
            return apiLevel;
        }

        public void setApiLevel(int apiLevel) {
            this.apiLevel = apiLevel;
        }

        public static class StatisticsBean {
            /**
             * id : 8
             * versionId : 13
             * channelId : 1
             * packageId : 10
             * currentDownload : 0
             * totalDownload : 0
             */

            private int id;
            private int versionId;
            private int channelId;
            private int packageId;
            private int currentDownload;
            private int totalDownload;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVersionId() {
                return versionId;
            }

            public void setVersionId(int versionId) {
                this.versionId = versionId;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public int getPackageId() {
                return packageId;
            }

            public void setPackageId(int packageId) {
                this.packageId = packageId;
            }

            public int getCurrentDownload() {
                return currentDownload;
            }

            public void setCurrentDownload(int currentDownload) {
                this.currentDownload = currentDownload;
            }

            public int getTotalDownload() {
                return totalDownload;
            }

            public void setTotalDownload(int totalDownload) {
                this.totalDownload = totalDownload;
            }
        }
    }
}

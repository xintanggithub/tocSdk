package com.jidouauto.market.module.lib.api.bean;

import java.util.List;

/**
 * Created tangxin
 * Time 2018/11/5 11:35 AM
 */
public class MarketRecommendResponse extends MarketBaseResponse {

    /**
     * result : true
     * reason : success
     * data : [{"versionId":6,"name":"qq music","packageName":"com.tencent.qqmusiccar","url":"http://localhost:7777/appstore_resource/qqmusiccar_1.7.0.6_android.apk","versionName":"1.7.0.6","versionCode":13,"checksum":"1f4eee95d7898b5edd9c78682bee3ff4","size":9918059,"icon":"http://localhost:7777/appstore_resource/20e72bf01ce904df3e13c1af27d1ae4c.png","apiLevel":0},{"versionId":7,"name":"攀登读书会","packageName":"io.dushu.fandengreader","url":"http://localhost:7777/appstore_resource/pandengdushuhui3914.apk","versionName":"3.9.14","versionCode":3914,"checksum":"9908fdce9c258df7b6b17bdae0e2f69d","size":18737630,"icon":"http://localhost:7777/appstore_resource/63682cacc0029bb1eeb4b8a84a767dbe.png","apiLevel":0},{"versionId":8,"name":"擎话","packageName":"com.guoyi.qinghua","url":"http://localhost:7777/appstore_resource/com.guoyi.qinghua_10.apk","versionName":"1.0.9","versionCode":10,"checksum":"5fe6c0b65e9ca9298a3fb9b410cdc13d","size":25991775,"icon":"http://localhost:7777/appstore_resource/d9a539c38affb24a4ea4284c27ea0e98.jpg","apiLevel":0},{"versionId":9,"name":"车信","packageName":"com.jidouauto.carletter","url":"http://localhost:7777/appstore_resource/changan_carletter_v4.1.4_release_2018-08-27_414_jiagu_sign.apk","versionName":"4.1.4","versionCode":414,"checksum":"e9a74fea62ebd79917aef7ad841758ea","size":19913854,"icon":"http://localhost:7777/appstore_resource/ac159244c8e78df15b29616d78cf134a.jpg","apiLevel":0}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * versionId : 6
         * name : qq music
         * packageName : com.tencent.qqmusiccar
         * url : http://localhost:7777/appstore_resource/qqmusiccar_1.7.0.6_android.apk
         * versionName : 1.7.0.6
         * versionCode : 13
         * checksum : 1f4eee95d7898b5edd9c78682bee3ff4
         * size : 9918059
         * icon : http://localhost:7777/appstore_resource/20e72bf01ce904df3e13c1af27d1ae4c.png
         * apiLevel : 0
         */

        private int versionId;
        private String name;
        private String packageName;
        private String url;
        private String versionName;
        private int versionCode;
        private String checksum;
        private int size;
        private String icon;
        private int apiLevel;

        public int getVersionId() {
            return versionId;
        }

        public void setVersionId(int versionId) {
            this.versionId = versionId;
        }

        public String getName() {
            return name;
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

        public int getApiLevel() {
            return apiLevel;
        }

        public void setApiLevel(int apiLevel) {
            this.apiLevel = apiLevel;
        }
    }
}

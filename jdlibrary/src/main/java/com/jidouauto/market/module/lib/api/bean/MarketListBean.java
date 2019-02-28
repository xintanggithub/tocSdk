package com.jidouauto.market.module.lib.api.bean;

import java.util.List;

/**
 * Created tangxin
 * Time 2018/10/24 3:10 PM
 */
public class MarketListBean {
    private boolean result;
    private String reason;
    private PaginationBean pagination;
    private List<PackageSetBean> package_set;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public List<PackageSetBean> getPackage_set() {
        return package_set;
    }

    public void setPackage_set(List<PackageSetBean> package_set) {
        this.package_set = package_set;
    }

    public static class PaginationBean {
        private int page_size;
        private int total;
        private int page;
        private int page_count;

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }
    }

    public static class PackageSetBean {

        private int id;
        private String name;
        private String package_name;
        private String version_name;
        private String version_code;
        private String url;
        private String checksum;
        private int size;
        private String icon;
        private int apiLevel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

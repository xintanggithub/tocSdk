package com.jidouauto.market.module.lib.api.bean;

import java.io.Serializable;
import java.util.List;


public class PackageListResponse extends MarketBaseResponse implements Serializable {
    /**
     * 请求是否成功
     */
    public List<PackageBean> package_set;
    public PaginationEntity pagination;
}

package com.jidouauto.market.module.lib.api.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PackageResultEntity implements Serializable {

    @SerializedName("detail")
    public PackagerDetailEntity packageX;

    public boolean result;
}

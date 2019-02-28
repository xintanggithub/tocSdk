package com.jidouauto.market.module.lib.api.bean;

import java.io.Serializable;

/**
 * Created tangxin
 * Time 2018/10/13 上午11:48
 */
public class PackagePositionEntity implements Serializable {
    public PackagePositionEntity() {
    }

    public PackagePositionEntity(int position, PackageBean packageEntity) {
        this.position = position;
        this.packageEntity = packageEntity;
    }

    private int position = -1;
    private PackageBean packageEntity;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PackageBean getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageBean packageEntity) {
        this.packageEntity = packageEntity;
    }
}

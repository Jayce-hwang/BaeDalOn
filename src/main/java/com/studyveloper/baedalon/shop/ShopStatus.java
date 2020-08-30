package com.studyveloper.baedalon.shop;

import lombok.Getter;

@Getter
public enum ShopStatus {
    CLOSE("close", false), OPEN("open", true);
    private String statusName;
    private boolean statusValue;

    ShopStatus(String statusName, boolean statusValue) {
        this.statusName = statusName;
        this.statusValue = statusValue;
    }
}

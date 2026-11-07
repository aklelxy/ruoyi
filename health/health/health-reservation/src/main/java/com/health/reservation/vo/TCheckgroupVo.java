package com.health.reservation.vo;

import com.health.reservation.domain.TCheckgroup;

import java.util.List;

public class TCheckgroupVo extends TCheckgroup {
    private List<Long> checkItemIds;

    public List<Long> getCheckItemIds() {
        return checkItemIds;
    }

    public void setCheckItemIds(List<Long> checkItemIds) {
        this.checkItemIds = checkItemIds;
    }

    @Override
    public String toString() {
        return "TCheckgroupDto{" +
                "checkItemIds=" + checkItemIds +
                '}';
    }
}
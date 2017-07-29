package com.rayfay.bizcloud.platforms.apigateway.swagger.utils;

import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by shenfu on 2017/4/26.
 */
public class BizCloudSorter implements Serializable {
    private String sortField;
    private Sort.Direction direction;

    public  BizCloudSorter() {
    }

    public  BizCloudSorter(String sortField, String sortOrder) {
        this.sortField = sortField;
        this.setSortOrder(sortOrder);
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortOrder(String sortOrder) {
        if (sortOrder.equals(Direction.ascend.name())) {
            direction = Sort.Direction.ASC;
        } else if (sortOrder.equals(Direction.descend.name())) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.DEFAULT_DIRECTION;
        }
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    private enum Direction {
        ascend,
        descend
    }
}

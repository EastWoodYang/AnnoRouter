package com.eastwood.common.router;

public interface OnRouterResult {

    void onSuccess();

    void onFailure(Exception e);

}
package com.eastwood.common.router;

public interface OnTaskResult {

    void success();

    void error(String msg);

    void cancel();
}
package com.eastwood.demo.router.task;

import android.content.Context;

import com.eastwood.common.router.RouterInfo;
import com.eastwood.common.router.IRouterTask;
import com.eastwood.common.router.OnTaskResult;

public class PreTask1 implements IRouterTask {

    @Override
    public void execute(Context context, RouterInfo routerInfo, OnTaskResult taskCallback) {
        taskCallback.success();
    }

}
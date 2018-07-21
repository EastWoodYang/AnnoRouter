package com.eastwood.demo.router.task;

import android.content.Context;
import android.content.Intent;

import com.eastwood.common.router.Router;
import com.eastwood.common.router.RouterInfo;
import com.eastwood.common.router.OnActivityResult;
import com.eastwood.common.router.IRouterTask;
import com.eastwood.common.router.OnTaskResult;

public class PreTask3 implements IRouterTask {

    @Override
    public void execute(Context context, RouterInfo routerInfo, final OnTaskResult taskCallback) {
        Router.execute("test://test.winwin.com/temp", new OnActivityResult() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                taskCallback.success();
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

}
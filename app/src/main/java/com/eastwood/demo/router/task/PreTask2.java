package com.eastwood.demo.router.task;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.eastwood.common.router.RouterInfo;
import com.eastwood.common.router.IRouterTask;
import com.eastwood.common.router.OnTaskResult;

public class PreTask2 implements IRouterTask {

    @Override
    public void execute(Context context, RouterInfo routerInfo, final OnTaskResult taskCallback) {
        Toast.makeText(context, "execute task2, will take 2 seconds", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                taskCallback.success();
            }
        }, 2000);
    }

}
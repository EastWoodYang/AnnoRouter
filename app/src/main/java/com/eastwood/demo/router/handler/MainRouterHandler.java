package com.eastwood.demo.router.handler;

import android.content.Context;
import android.content.Intent;

import com.eastwood.common.router.IRouterHandler;
import com.eastwood.common.router.RouterInfo;
import com.eastwood.common.router.OnRouterResult;
import com.eastwood.demo.router.TempActivity;

public class MainRouterHandler implements IRouterHandler {

    @Override
    public void applyRouter(Context context, RouterInfo routerInfo, OnRouterResult routerResult) {
        Intent intent = new Intent(context, TempActivity.class);
        intent.setAction("customService");
        context.startActivity(intent);

        if(routerResult != null) {
            routerResult.onSuccess();
        }
    }
}
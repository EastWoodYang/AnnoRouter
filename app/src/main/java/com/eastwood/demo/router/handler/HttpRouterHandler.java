package com.eastwood.demo.router.handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.eastwood.common.router.ISchemeHandler;
import com.eastwood.common.router.OnRouterResult;

public class HttpRouterHandler implements ISchemeHandler {

    @Override
    public void applyRouter(Context context, String url, OnRouterResult routerResult) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);

        if(routerResult != null) {
            routerResult.onSuccess();
        }
    }
}
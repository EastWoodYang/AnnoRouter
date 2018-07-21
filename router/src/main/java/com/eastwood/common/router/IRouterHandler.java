package com.eastwood.common.router;

import android.content.Context;

public interface IRouterHandler {

    void applyRouter(Context context, RouterInfo routerInfo, OnRouterResult routerResult);

}
package com.eastwood.common.router;

import android.content.Context;

public interface ISchemeHandler {

    void applyRouter(Context context, String url, OnRouterResult routerResult);

}

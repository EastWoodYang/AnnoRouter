package com.eastwood.common.router;

import android.content.Context;

public interface IRouterTask {

    void execute(Context context, RouterInfo routerInfo, OnTaskResult taskCallback);

}
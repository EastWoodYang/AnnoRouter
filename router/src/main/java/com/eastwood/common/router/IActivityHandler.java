package com.eastwood.common.router;

import android.content.Context;
import android.content.Intent;

public interface IActivityHandler {

    void startActivity(Context context, Intent intent, int requestCode, IActivityTransition activityTransition, OnRouterResult routerResult);

}
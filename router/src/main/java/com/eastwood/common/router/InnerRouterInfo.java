package com.eastwood.common.router;

class InnerRouterInfo extends RouterInfo {

    public Class<? extends IRouterHandler> routerHandler;
    public Class<? extends IRouterTask>[] preTasks;
    public Class<? extends android.app.Activity> activity;
    public Class<? extends IActivityTransition> transition;
    public int flags;
    public int requestCode;

}
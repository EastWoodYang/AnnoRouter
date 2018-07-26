package com.eastwood.common.router;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private static ActivityLifecycle instance;

    static ActivityLifecycle init(Application application) {
        if (instance == null) {
            instance = new ActivityLifecycle();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    static void remove(Application application) {
        if (instance == null) {
            application.unregisterActivityLifecycleCallbacks(instance);
            instance = null;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Router.referenceCurrentActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Router.referenceCurrentActivity(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Router.referenceCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
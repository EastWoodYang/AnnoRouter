package com.eastwood.demo.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.eastwood.common.router.IActivityHandler;
import com.eastwood.common.router.IActivityTransition;
import com.eastwood.common.router.IExceptionHandler;
import com.eastwood.common.router.OnRouterResult;
import com.eastwood.common.router.Router;
import com.eastwood.common.router.IRouterUrlFilter;
import com.eastwood.demo.router.api.IRouterAApi;
import com.eastwood.demo.router.api.InnerRouterApi;
import com.eastwood.demo.router.api.PassObjectRouterApi;
import com.eastwood.demo.router.api.TestRouterApi;
import com.eastwood.demo.router.handler.HttpSchemeHandler;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Router.Builder builder = new Router.Builder()
                .application(this)
                .routerUrlFilter(new IRouterUrlFilter() {
                    @Override
                    public String filter(String url) {
                        // 过滤URL
                        return url;
                    }
                })
                .exceptionHandler(new IExceptionHandler() {
                    @Override
                    public void handler(String url, Exception e) {
                        Toast.makeText(App.this, url + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .activityHandler(new IActivityHandler() {
                    @Override
                    public void startActivity(Context context, Intent intent, int requestCode, IActivityTransition activityTransition, OnRouterResult routerResult) {
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
                            if (routerResult instanceof OnActivityResult) {
                                ActivityResultUtil.startForResult(activity, intent, requestCode, (OnActivityResult) routerResult);
                            } else {
                                activity.startActivityForResult(intent, requestCode);
                            }

                            if (activityTransition != null) {
                                    activity.overridePendingTransition(activityTransition.enterAnim(), activityTransition.exitAnim());
                            }
                        } else {
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if (routerResult != null) routerResult.onSuccess();
                    }
                });
        Router.init(builder);

        Router.addRouterIndex(TestRouterApi.class);

        Router.addRouterIndex(IRouterAApi.class);
        Router.addRouterIndex(InnerRouterApi.class);
        Router.addRouterIndex(PassObjectRouterApi.class);

        HttpSchemeHandler httpSchemeHandler = new HttpSchemeHandler();
        Router.addSchemeHandler("https", httpSchemeHandler);
        Router.addSchemeHandler("http", httpSchemeHandler);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Router.destroy();
    }
}
package com.eastwood.demo.router;

import android.app.Application;
import android.widget.Toast;

import com.eastwood.common.router.IExceptionHandler;
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
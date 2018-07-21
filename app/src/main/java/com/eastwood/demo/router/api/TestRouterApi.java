package com.eastwood.demo.router.api;

import android.content.Intent;

import com.eastwood.common.router.annotation.Activity;
import com.eastwood.common.router.annotation.Flags;
import com.eastwood.common.router.annotation.Param;
import com.eastwood.common.router.annotation.Path;
import com.eastwood.common.router.annotation.RouterHost;
import com.eastwood.common.router.annotation.RouterScheme;
import com.eastwood.common.router.annotation.Strict;
import com.eastwood.common.router.OnActivityResult;
import com.eastwood.demo.router.RouterAActivity;
import com.eastwood.demo.router.TempActivity;

@RouterScheme("test")
@RouterHost("test.winwin.com")
public interface TestRouterApi {

    @Path("routerA")
    @Activity(RouterAActivity.class)
    void gotoRouterAActivity(@Param("param1") String param1, @Flags int flags);

    @Strict
    @Path("routerA")
    @Flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    @Activity(RouterAActivity.class)
    void gotoRouterAActivity1(@Param("param1") String param1, @Param("param3") String param3);

    @Activity(RouterAActivity.class)
    @Flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    void gotoRouterAActivity2(@Param("param1") String param1, @Param("param2") String param2, @Flags int flags);


    @Path("temp")
    @Activity(TempActivity.class)
    @Flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    void gotoTempActivity(String param1, @Param("param2") String param2, @Flags int flags, OnActivityResult onActivityResult);

}
package com.eastwood.demo.router.api;

import android.content.Intent;

import com.eastwood.common.router.annotation.Activity;
import com.eastwood.common.router.annotation.Flags;
import com.eastwood.common.router.annotation.Param;
import com.eastwood.common.router.annotation.Path;
import com.eastwood.common.router.annotation.RequestCode;
import com.eastwood.common.router.annotation.RouterHandler;
import com.eastwood.common.router.annotation.RouterHost;
import com.eastwood.common.router.annotation.RouterScheme;
import com.eastwood.common.router.annotation.Strict;
import com.eastwood.common.router.annotation.Task;
import com.eastwood.common.router.annotation.Transition;
import com.eastwood.demo.router.MainRouterTransition;
import com.eastwood.demo.router.RouterAActivity;
import com.eastwood.demo.router.handler.MainRouterHandler;
import com.eastwood.demo.router.task.PreTask1;

@RouterScheme("winA")
@RouterHost("pass.winwin.com")
public interface ExampleRouterApi {


    /*
     * 严格模式
     * 为了避免Path相同，以Scheme+Host+Path+Flags+Param的键值将进行严格匹配，
     * 默认以Scheme+Host+Path进行匹配。
     */
    @Strict

    /*
     * 页面路径，（通过接口调用时，可以不需要）
     */
    @Path("path/*")

    /*
     * 要跳转的Activity
     */
    @Activity(RouterAActivity.class)

    /*
     * 将以startActivityForResult的方式跳转，并设置相应requestCode
     */
    @RequestCode(1001)

    /*
     * 前置任务
     * 存在多个前置任务时，按如下方式并按顺序执行
     * [PreTask1.class, PreTask2.class]
     */
    @Task(PreTask1.class)

    /*
     * 自定义最终的路由的处理
     * 与@Activity并存时，@Activity将失效
     */
    @RouterHandler(MainRouterHandler.class)


    @RouterScheme("winA")

    @RouterHost("pass.winwin.com")


    @Transition(MainRouterTransition.class)

    @Flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

    /*
     * @Param 通过Intent传递的参数，支持的数据类型同Intent
     *
     * @Flags 设置Activity的启动模式Flags，intent.setFlags(...)
     */
    void jump(@Param("paramName") String paramValue);

    //...
}

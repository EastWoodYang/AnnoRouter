package com.eastwood.demo.router.api;

import com.eastwood.common.router.annotation.Activity;
import com.eastwood.common.router.annotation.Bundle;
import com.eastwood.common.router.annotation.Flags;
import com.eastwood.common.router.annotation.Param;
import com.eastwood.common.router.annotation.RequestCode;
import com.eastwood.common.router.annotation.Task;
import com.eastwood.demo.router.RouterAActivity;
import com.eastwood.demo.router.task.PreTask1;
import com.eastwood.demo.router.task.PreTask2;
import com.eastwood.demo.router.task.PreTask3;

//@RouterHost("b.winwin.com")
//@RouterScheme("winB")
public interface InnerRouterApi {

    @Activity(RouterAActivity.class)
    void gotoWithdowActivity(int type);


    @Activity(RouterAActivity.class)
    @Bundle({"type=type_withdraw_remain"})
    void gotoWithdowActivity();

    @Activity(RouterAActivity.class)
    @Bundle({"type=type_recharge_remain"})
    void gotoRechargeActivity();

    @Activity(RouterAActivity.class)
    void method(@Param("param1") String param1);

    @Activity(RouterAActivity.class)
    void method(@Flags int flags);

    @Activity(RouterAActivity.class)
    void method(@Param("param1") String param1, @Flags int flags);

    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void methodForResult();

    @Activity(RouterAActivity.class)
    @Task(PreTask1.class)
    void methodWithTask1();

    @Activity(RouterAActivity.class)
    @Task({PreTask1.class, PreTask2.class})
    void methodWithTask2();

    @Activity(RouterAActivity.class)
    @Task({PreTask1.class, PreTask2.class, PreTask3.class})
    void methodWithTask3();

    @Activity(RouterAActivity.class)
    @Task({PreTask1.class})
    @RequestCode(1001)
//    @RouterHandler(name = "com.eastwood.demo.router.handler.MainRouterHandler")
    void methodWithRouterHandler(@Flags int flags, @Param("count") int param1);
}
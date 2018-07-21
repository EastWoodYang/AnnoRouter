package com.eastwood.demo.router.api;

import com.eastwood.common.router.annotation.Activity;
import com.eastwood.common.router.annotation.Flags;
import com.eastwood.common.router.annotation.Param;
import com.eastwood.common.router.annotation.Path;
import com.eastwood.common.router.annotation.RequestCode;
import com.eastwood.common.router.annotation.RouterHandler;
import com.eastwood.common.router.annotation.RouterHost;
import com.eastwood.common.router.annotation.RouterScheme;
import com.eastwood.common.router.annotation.Task;
import com.eastwood.demo.router.RouterAActivity;
import com.eastwood.demo.router.handler.MainRouterHandler;
import com.eastwood.demo.router.task.PreTask1;
import com.eastwood.demo.router.task.PreTask2;
import com.eastwood.demo.router.task.PreTask3;

@RouterScheme("winA")
@RouterHost("a.winwin.com")
public interface IRouterAApi {

    @Path("startActivity/defaultService/withParams/withTasks/withInnerActivity")
    @Task({com.eastwood.demo.router.task.PreTask1.class, com.eastwood.demo.router.task.PreTask2.class, com.eastwood.demo.router.task.PreTask3.class})
    @Activity(RouterAActivity.class)
    void startActivity_defaultService_withParams_withTasks_withInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivity/defaultService/withParams/withTasks/withoutInnerActivity")
    @Task({PreTask1.class, PreTask2.class})
    @Activity(RouterAActivity.class)
    void startActivity_defaultService_withParams_withTasks_withoutInnerActivity(@Param("count") int count, @Flags int flags, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);


    @Path("startActivity/defaultService/withParams/withoutTasks/withoutInnerActivity")
    @Activity(RouterAActivity.class)
    void startActivity_defaultService_withParams_withoutTasks_withoutInnerActivity(@Param("count") int count, @Param("time") long time, @Flags int flags, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivity/defaultService/withoutParams/withTasks/withInnerActivity")
    @Task({PreTask1.class, PreTask2.class, PreTask3.class})
    @Activity(RouterAActivity.class)
    void startActivity_defaultService_withoutParams_withTasks_withInnerActivity();

    @Path("startActivity/defaultService/withoutParams/withTasks/withoutInnerActivity")
    @Task({PreTask1.class, PreTask2.class})
    @Activity(RouterAActivity.class)
    void startActivity_defaultService_withoutParams_withTasks_withoutInnerActivity();

    @Path("startActivity/defaultService/withoutParams/withoutTasks/withoutInnerActivity")
    @Activity(RouterAActivity.class)
    void startActivity_defaultService_withoutParams_withoutTasks_withoutInnerActivity();

    @Path("startActivity/customService/withParams/withTasks/withInnerActivity")
    @Task({PreTask1.class, PreTask2.class, PreTask3.class})
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    void startActivity_customService_withParams_withTasks_withInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Flags int flags, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivity/customService/withParams/withTasks/withoutInnerActivity")
    @Task({PreTask1.class, PreTask2.class})
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    void startActivity_customService_withParams_withTasks_withoutInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll, @Flags int flags);

    @Path("startActivity/customService/withParams/withoutTasks/withoutInnerActivity")
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    void startActivity_customService_withParams_withoutTasks_withoutInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivity/customService/withoutParams/withTasks/withInnerActivity")
    @Task({PreTask1.class, PreTask2.class, PreTask3.class})
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    void startActivity_customService_withoutParams_withTasks_withInnerActivity();

    @Path("startActivity/customService/withoutParams/withTasks/withoutInnerActivity")
    @Task({PreTask1.class, PreTask2.class})
    @RouterHandler(MainRouterHandler.class)
    void startActivity_customService_withoutParams_withTasks_withoutInnerActivity();

    @Path("startActivity/customService/withoutParams/withoutTasks/withoutInnerActivity")
    @RouterHandler(MainRouterHandler.class)
    void startActivity_customService_withoutParams_withoutTasks_withoutInnerActivity();

    // startActivityForResult

    @Path("startActivityForResult/defaultService/withParams/withTasks/withInnerActivity")
    @Task({PreTask1.class, PreTask2.class, PreTask3.class})
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_defaultService_withParams_withTasks_withInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivityForResult/defaultService/withParams/withTasks/withoutInnerActivity")
    @Task({PreTask1.class, PreTask2.class})
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_defaultService_withParams_withTasks_withoutInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivityForResult/defaultService/withParams/withoutTasks/withoutInnerActivity")
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_defaultService_withParams_withoutTasks_withoutInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivityForResult/defaultService/withoutParams/withTasks/withInnerActivity")
    @Task({PreTask1.class, PreTask2.class, PreTask3.class})
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_defaultService_withoutParams_withTasks_withInnerActivity();

    @Path("startActivityForResult/defaultService/withoutParams/withTasks/withoutInnerActivity")
    @Task({PreTask1.class, PreTask2.class})
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_defaultService_withoutParams_withTasks_withoutInnerActivity();

    @Path("startActivityForResult/defaultService/withoutParams/withoutTasks/withoutInnerActivity")
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_defaultService_withoutParams_withoutTasks_withoutInnerActivity();

    @Path("startActivityForResult/customService/withParams/withTasks/withInnerActivity")
    @Task({PreTask1.class, PreTask2.class, PreTask3.class})
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_customService_withParams_withTasks_withInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivityForResult/customService/withParams/withTasks/withoutInnerActivity")
    @Task({PreTask1.class, PreTask2.class})
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_customService_withParams_withTasks_withoutInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivityForResult/customService/withParams/withoutTasks/withoutInnerActivity")
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_customService_withParams_withoutTasks_withoutInnerActivity(@Param("count") int count, @Param("time") long time, @Param("flag") float flag, @Param("total") double total, @Param("isAll") boolean isAll);

    @Path("startActivityForResult/customService/withoutParams/withTasks/withInnerActivity")
    @Task({PreTask1.class, PreTask2.class, PreTask3.class})
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_customService_withoutParams_withTasks_withInnerActivity();

    @Path("startActivityForResult/customService/withoutParams/withTasks/withoutInnerActivity")
    @Task({PreTask1.class, PreTask2.class})
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_customService_withoutParams_withTasks_withoutInnerActivity();

    @Path("startActivityForResult/customService/withoutParams/withoutTasks/withoutInnerActivity")
    @RouterHandler(MainRouterHandler.class)
    @Activity(RouterAActivity.class)
    @RequestCode(1001)
    void startActivityForResult_customService_withoutParams_withoutTasks_withoutInnerActivity();

}
## Router 初始化

    public class App extends Application {
    
        @Override
        public void onCreate() {
            super.onCreate();
    
            ...
            
            HttpRouterHandler httpRouterHandler = new HttpRouterHandler();
            Router.Builder builder = new Router.Builder()
                    .application(this)
                    .addRouterIndex(new MainRouterIndex()) 
    //              添加各模块生成的RouterIndex 
    //                .addRouterIndex(new ModuleBRouterIndex())
    //                .addRouterIndex(new ModuleCRouterIndex())
    //              自定义处理Scheme
                    .addSchemeHandler("http", httpRouterHandler)
                    .addSchemeHandler("https", httpRouterHandler)
                    .routerUrlFilter(new RouterUrlFilter() {
                        @Override
                        public String filter(String url) {
                            // 过滤URL
                            return url;
                        }
                    })
                    .exceptionHandler(new ExceptionHandler() {
                        @Override
                        public void handler(String s, Exception e) {
                            // 自定义处理异常
                        }
                    });
            Router.init(builder);
            
            ...
        }
        
        @Override
        public void onTerminate() {
            super.onTerminate();
            Router.destroy();
        }
    }

## Router Usage

### 通过URL
- Router.execute(String url)
- Router.execute(String url, RouterResult routerResult)
- Router.execute(Context context, String url)
- Router.execute(Context context, String url, RouterResult routerResult)

#### 通过接口

    InnerRouterApi innerRouterApi = Router.create(InnerRouterApi.class);
    innerRouterApi.methodWithRouterHandler(0, 1000);

## Router 接口配置说明

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
        @Path("path")
     
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
     
        /*
         * @Param 通过Intent传递的参数，支持的数据类型同Intent
         *
         * @Flags 设置Activity的启动模式Flags，intent.setFlags(...)
         */
        void jump(@Param("paramName") String paramValue, @Flags("flagsName") int flagsValue);
     
        //...
    }
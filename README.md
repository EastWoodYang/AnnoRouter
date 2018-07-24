# AnnoRouter
A routing framework to assist with Android Componentization. it's turns your Router API into a Java interface.

<img src='https://github.com/EastWoodYang/AnnoRouter/blob/master/picture/1.png'/>

## Get it
AnnoAdapter is now available on JCentral.

    implementation 'com.eastwood.common:anno-router:1.0.2'

## Usage

### Build Global Router

    Router.Builder builder = new Router.Builder()
            .application(this)
            ...
           
    Router.init(builder);
    
    
**Intercept filter url**

    new Router.Builder()
        .routerUrlFilter(new IRouterUrlFilter() {
            @Override
            public String filter(String url) {
                ...
                return url;
            }
        })
        ...

**Exception error handler**

    new Router.Builder()
        .exceptionHandler(new IExceptionHandler() {
            @Override
            public void handler(String url, Exception e) {
                
            }
        })
        ...

### Define Router Api

use `@RouterScheme`, `@RouterHost`, `@Path` and `@Param` to define a router url.

    @RouterScheme("scheme")
    @RouterHost("host")
    public interface RouterApi {
         
        @Path("path")
        ...
        void jump(@Param("paramName") int paramValue);
            
    }
     
    public interface RouterApi {
         
        @RouterScheme("scheme")
        @RouterHost("host")
        @Path("path")
        ...
        void jump(@Param("paramName") int paramValue);
            
    }

Sometimes, you may define the same `scheme://host/path` but different params. then, need to use `@Strict` to distinguish. 

e.g.
    
    @RouterScheme("scheme")
    @RouterHost("host")
    public interface RouterApi {
         
        @Path("path")
        ...
        void jumpToActivity1(@Param("param1") String param1);
        
        
        @Strict
        @Path("path")
        ...
        void jumpToActivity2(@Param("param1") String param1, @Param("param2") int param2);
            
    }
  
* `scheme://host/path?param1=a` will match method `jumpToActivity1` 
* `scheme://host/path?param1=a&param2=1` will match method `jumpToActivity2`
* `scheme://host/path?param1=a&param2=1&param3=1` will match method `jumpToActivity1`, and `param2=1&param3=1` will be ignore.

**Do some verification or preparation tasks**


    public interface RouterApi {
         
        @Task(CustomRouterTask.class)
        ...
        void jumpToActivity();
            
    }
     
    // ----------------
         
    public class CustomRouterTask implements IRouterTask {
                                         
        @Override
        public void execute(Context context, RouterInfo routerInfo, OnTaskResult onTaskResult) {
            // do something...
            onTaskResult.success();
        }
    
    }
    
    
**Jump to a Activity or handle custom**


    public interface RouterApi {
         
        ...
        @Activity(LoginActivity.class)
        void jumpToLogin();
        
        ...
        @RouterHandler(CustomRouterHandler.class)
        void jumpToLogin();
            
    }
     
    // ----------------
     
    public class CustomRouterHandler implements IRouterHandler {
    
        @Override
        public void applyRouter(Context context, RouterInfo routerInfo, OnRouterResult routerResult) {
            
            // do what you want to do.
     
            if(routerResult != null) {
                routerResult.onSuccess();
            }
        }
    }
    
**Custom Activity transition animation**
    
    public interface RouterApi {
         
        ...
        @Transition(CustomeTransition.class)
        void jumpToLogin();
            
    }
     
    // ----------------
     
    public class CustomeTransition implements IActivityTransition {
                         
        @Override
        public int enterAnim() {
            return R.anim.fade_in;
        }
     
        @Override
        public int exitAnim() {
            return R.anim.fade_out;
        }
    }


**Set Activity launchMode**
    
    public interface RouterApi {
         
        ...
        @Flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        void jump(@Flags int flags);
        
        
        ...
        void jump(@Flags int flags);
            
    }
     
    // ----------------
     
    Router.create(RouterApi.class).jump(Intent.FLAG_ACTIVITY_CLEAR_TOP);

### Add Router Api

    Router.addRouterIndex(RouterApi.class);
    
### Add custom scheme handler

    public class HttpSchemeHandler implements ISchemeHandler {
     
        @Override
        public void applyRouter(Context context, String url, OnRouterResult routerResult) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
     
            if(routerResult != null) {
                routerResult.onSuccess();
            }
        }
    }
    
    // ----------------
     
    HttpSchemeHandler httpSchemeHandler = new HttpSchemeHandler();
    Router.addSchemeHandler("https", httpSchemeHandler);
    Router.addSchemeHandler("http", httpSchemeHandler);
    
### Use Router Api To Jump
    
    // The Router class generates an implementation of the RouterApi interface.
    RouterApi routerApi = Router.create(RouterApi .class);
    routerApi.jump("value");
     
    // or use url instead.
    Router.execute("scheme://host/path?param=value");
         
**Get Activity result**

    @RouterScheme("app")
    @RouterHost("usercenter")
    public interface LoginRouterApi {
         
        @Path("login")
        @Activity(LoginActivity.class)
        @RequestCode(1001)
        void jumpToLogin(@Param("mobile") String mobile);
            
        @Activity(LoginActivity.class)
        @RequestCode(1001)
        void jumpToLogin(@Param("mobile") String mobile, OnActivityResult onActivityResult);
    }
     
    // ----------------
     
    OnActivityResult onActivityResult = new OnActivityResult() {
     
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
             
        }
        
        @Override
        public void onSuccess() {
            
        }
     
        @Override
        public void onFailure(Exception e) {

        }
    };
     
    Router.execute("app://usercenter/login?mobile=0123456789", onActivityResult);
    // or
    Router.create(LoginRouterApi.class).jumpToLogin("0123456789", onActivityResult);



## License

```
   Copyright 2018 EastWood Yang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

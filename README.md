# AnnoRouter
A routing framework to assist with Android componentization. Use interfaces and annotations to define route jump info.

## Get it
AnnoAdapter is now available on JCentral.

    implementation 'com.eastwood.common:anno-router:1.0.0'

## Usage

#### Build Router Global

    Router.Builder builder = new Router.Builder()
            .application(this)
    Router.init(builder);

#### Define A Router Api

    @RouterScheme("scheme")
    @RouterHost("host")
    public interface AppRouterApi {
     
        @Path("path")
        @Activity(RouterAActivity.class)
        void startAActivity(@Param("param") String param);
        
        ...
        
    }

#### Add Router Api

    Router.addRouterIndex(AppRouterApi.class);
    
#### Use Router Api To Jump

    Router.execute("scheme://host/path?param=1")
     
    // OR
    
    AppRouterApi appRouterApi = Router.create(AppRouterApi.class);
    appRouterApi.startAActivity("1");

## Detail

### Router Builder
    

                
#### Router Url Filter

    new Router.Builder()
            .routerUrlFilter(new IRouterUrlFilter() {
                @Override
                public String filter(String url) {
                    return url;
                }
            })

#### Exception Handler

    new Router.Builder()
            .exceptionHandler(new IExceptionHandler() {
                @Override
                public void handler(String message, Exception e) {
                    
                }
            });
                
### Router Annotations

* `@RouterScheme`

* `@RouterHost`

* `@Path`

* `@Activity`

* `@RouterHandler`

* `@Strict`

* `@Transition`

* `@RequestCode`

* `@Task`

* `@Param`

* `@Flags`



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

package com.eastwood.common.router;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;

import com.eastwood.common.router.annotation.Path;
import com.eastwood.common.router.annotation.RouterHost;
import com.eastwood.common.router.annotation.RouterScheme;
import com.eastwood.common.router.annotation.Strict;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Router {

    private Application application;
    private IExceptionHandler exceptionHandler;
    private IRouterUrlFilter routerUrlFilter;
    private SparseArray<ISchemeHandler> schemeHandlerMap;
    protected SparseArray<Class> routerIndexMap;

    private IActivityHandler activityHandler;

    private static Router routerInstance;

    private static WeakReference<Activity> sCurrentActivity;

    public final static String SYMBOL = "://";

    protected static Router getRouterInstance() {
        if (routerInstance == null) {
            synchronized (Router.class) {
                if (routerInstance == null) {
                    routerInstance = new Router();
                }
            }
        }
        return routerInstance;
    }

    public static void init(Builder builder) {
        Router router = getRouterInstance();
        router.application = builder.application;
        router.exceptionHandler = builder.exceptionHandler;
        router.routerUrlFilter = builder.routerUrlFilter;
        router.activityHandler = builder.activityHandler;

        router.schemeHandlerMap = new SparseArray<>();
        router.routerIndexMap = new SparseArray<>();

        ActivityLifecycle.init(router.application);
    }

    public static void destroy() {
        Router router = getRouterInstance();
        ActivityLifecycle.remove(router.application);
        sCurrentActivity = null;
        router.routerIndexMap.clear();
        router.routerIndexMap = null;
    }

    public static void addRouterIndex(Class<?> routerIndexClass) {
        Router router = getRouterInstance();
        String classRouterScheme = null;
        String classRouterHost = null;
        RouterScheme classRouterSchemeAnnotation = routerIndexClass.getAnnotation(RouterScheme.class);
        if (classRouterSchemeAnnotation != null) {
            classRouterScheme = classRouterSchemeAnnotation.value();
        }
        RouterHost classRouterHostAnnotation = routerIndexClass.getAnnotation(RouterHost.class);
        if (classRouterHostAnnotation != null) {
            classRouterHost = classRouterHostAnnotation.value();
        }

        Method[] methods = routerIndexClass.getDeclaredMethods();
        for (Method method : methods) {
            Path pathAnnotation = method.getAnnotation(Path.class);
            if (pathAnnotation == null) {
                continue;
            }

            String path = pathAnnotation.value();

            String methodRouterScheme = classRouterScheme;
            String methodRouterHost = classRouterHost;
            RouterScheme methodRouterSchemeAnnotation = method.getAnnotation(RouterScheme.class);
            if (methodRouterSchemeAnnotation != null) {
                methodRouterScheme = methodRouterSchemeAnnotation.value();
            }
            RouterHost methodRouterHostAnnotation = method.getAnnotation(RouterHost.class);
            if (methodRouterHostAnnotation != null) {
                methodRouterHost = methodRouterHostAnnotation.value();
            }
            if (methodRouterScheme == null || methodRouterHost == null) {
                continue;
            }

            String url;
            Strict strictAnnotation = method.getAnnotation(Strict.class);
            if (strictAnnotation == null) {
                url = methodRouterScheme + SYMBOL + methodRouterHost + Utils.filterPath(path);
            } else {
                String paramNames = Utils.getParamNames(method);
                url = methodRouterScheme + SYMBOL + methodRouterHost + Utils.filterPath(path) + (paramNames.equals("") ? paramNames : "?" + paramNames);
            }

            int key = url.toLowerCase().hashCode();
            if (router.routerIndexMap.get(key) == null) {
                router.routerIndexMap.put(key, routerIndexClass);
            } else {
                RuntimeException exception = new RuntimeException("router url [" + url + "] is already exist.");
                exception.printStackTrace();
                throw exception;
            }
        }
    }

    public static boolean addSchemeHandler(String scheme, ISchemeHandler schemeHandler) {
        Router router = getRouterInstance();
        if (router.schemeHandlerMap == null) {
            throw new RuntimeException("Invoke Router.init first!");
        }

        int key = scheme.toLowerCase().hashCode();
        if (router.schemeHandlerMap.get(key) != null) {
            return false;
        }
        router.schemeHandlerMap.put(key, schemeHandler);
        return true;
    }

    public static void execute(String url) {
        execute(url, null);
    }

    public static void execute(String url, OnRouterResult routerResult) {
        final Context context = getActivityContextPossibly();
        execute(context, url, routerResult);
    }

    public static void execute(Context context, String url) {
        execute(context, url, null);
    }

    public static void execute(Context context, String url, OnRouterResult routerResult) {
        Router router = getRouterInstance();
        if (router.routerUrlFilter != null) {
            url = router.routerUrlFilter.filter(url);
        }

        if (!Utils.isURL(url)) {
            Exception e = new IllegalArgumentException("Invalid url.");
            if (routerResult != null) {
                routerResult.onFailure(e);
            }
            if (router.exceptionHandler != null) {
                router.exceptionHandler.handler(url, e);
            }
            return;
        }

        Uri uri = Uri.parse(url);
        String scheme = uri.getScheme().toLowerCase();
        ISchemeHandler schemeHandler = router.schemeHandlerMap.get(scheme.hashCode());
        if (schemeHandler != null) {
            schemeHandler.applyRouter(context, url, routerResult);
        } else {
            InnerRouterInfo routerInfo = Utils.getRouterInfo(url);
            if (routerInfo != null) {
                execute(context, routerInfo, routerResult);
            } else {
                Exception e = new RuntimeException("Failure to execute.");
                if (routerResult != null) {
                    routerResult.onFailure(e);
                }
                if (router.exceptionHandler != null) {
                    router.exceptionHandler.handler(url, e);
                }
            }
        }
    }

    private static void execute(Context context, final InnerRouterInfo routerInfo, final OnRouterResult routerResult) {
        final Context contextPossibly = context != null ? context : getActivityContextPossibly();
        try {
            final TaskManager taskManager = TaskManager.getTaskList(contextPossibly, routerInfo);
            if (taskManager == null) {
                startRouter(contextPossibly, routerInfo, routerResult);
                return;
            }
            taskManager.start(new OnTaskResult() {

                @Override
                public void success() {
                    try {
                        startRouter(contextPossibly, routerInfo, routerResult);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void error(String msg) {
                    throw new RuntimeException(msg);
                }

                @Override
                public void cancel() {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (routerResult != null) {
                routerResult.onFailure(e);
            }
            IExceptionHandler exceptionHandler = getRouterInstance().exceptionHandler;
            if (exceptionHandler != null) {
                exceptionHandler.handler(routerInfo.originUrl, e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        InnerRouterInfo routerInfo = Utils.buildRouterInfo(null, service, method, args);
                        if (routerInfo == null) return null;

                        OnRouterResult onRouterResult = null;
                        Context context = null;
                        if (args != null && args.length > 0) {
                            for (Object object : args) {
                                if (object instanceof OnRouterResult) {
                                    onRouterResult = (OnRouterResult) object;
                                } else if (object instanceof Context) {
                                    context = (Context) object;
                                }
                            }
                        }

                        execute(context, routerInfo, onRouterResult);
                        return null;
                    }
                });
    }

    private static void startRouter(Context context, InnerRouterInfo routerInfo, OnRouterResult routerResult) throws Exception {
        if (routerInfo.routerHandler == null) {
            Router.applyRouter(context, routerInfo, routerResult);
        } else {
            IRouterHandler routerHandlerImp = Utils.inflectClass(routerInfo.routerHandler);
            if (routerHandlerImp != null) {
                routerHandlerImp.applyRouter(context, routerInfo, routerResult);
            } else {
                if (routerResult != null)
                    routerResult.onFailure(new ClassNotFoundException(routerInfo.routerHandler.toString()));
            }
        }
    }

    private static void applyRouter(Context context, InnerRouterInfo routerInfo, OnRouterResult routerResult) throws Exception {

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, routerInfo.activity));
        if (routerInfo.flags != 0x00000000) {
            intent.setFlags(routerInfo.flags);
        }
        Bundle bundle = new Bundle();
        if (routerInfo.bundleData != null) {
            for (int i = 0; i < routerInfo.bundleData.length; i += 2) {
                bundle.putString(routerInfo.bundleData[i], routerInfo.bundleData[i + 1]);
            }
        }
        ParamHelper.putIntent(bundle, routerInfo);
        intent.putExtras(bundle);

        Router router = getRouterInstance();
        if (router.activityHandler != null) {
            IActivityTransition activityTransition = null;
            if (routerInfo.transition != null) {
                activityTransition = Utils.inflectClass(routerInfo.transition);
            }
            router.activityHandler.startActivity(context, intent, routerInfo.requestCode, activityTransition, routerResult);
        } else {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                activity.startActivityForResult(intent, routerInfo.requestCode);
                if (routerInfo.transition != null) {
                    IActivityTransition routerTransition = Utils.inflectClass(routerInfo.transition);
                    if (routerTransition != null) {
                        activity.overridePendingTransition(routerTransition.enterAnim(), routerTransition.exitAnim());
                    }
                }
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            if (routerResult != null) routerResult.onSuccess();
        }
    }

    private static Context getActivityContextPossibly() {
        Activity activity = null;
        if (sCurrentActivity != null && sCurrentActivity.get() != null) {
            activity = sCurrentActivity.get();
        }
        if (activity != null && activity.isFinishing()) {
            return getRouterInstance().application.getApplicationContext();
        }
        return activity;
    }

    static void referenceCurrentActivity(Activity activity) {
        if (sCurrentActivity == null || sCurrentActivity.get() != activity) {
            sCurrentActivity = new WeakReference<>(activity);
        }
    }

    public static class Builder {

        Application application;
        IExceptionHandler exceptionHandler;
        IRouterUrlFilter routerUrlFilter;
        IActivityHandler activityHandler;

        public Builder application(Application application) {
            this.application = application;
            return this;
        }

        public Builder exceptionHandler(IExceptionHandler exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        public Builder routerUrlFilter(IRouterUrlFilter routerUrlFilter) {
            this.routerUrlFilter = routerUrlFilter;
            return this;
        }

        public Builder activityHandler(IActivityHandler activityHandler) {
            this.activityHandler = activityHandler;
            return this;
        }

        public void init() {
            Router.init(this);
        }

    }

}
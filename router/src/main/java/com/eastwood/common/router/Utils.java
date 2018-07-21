package com.eastwood.common.router;

import android.net.Uri;

import com.eastwood.common.router.annotation.Activity;
import com.eastwood.common.router.annotation.Bundle;
import com.eastwood.common.router.annotation.Flags;
import com.eastwood.common.router.annotation.Param;
import com.eastwood.common.router.annotation.Path;
import com.eastwood.common.router.annotation.RequestCode;
import com.eastwood.common.router.annotation.RouterHost;
import com.eastwood.common.router.annotation.RouterScheme;
import com.eastwood.common.router.annotation.Strict;
import com.eastwood.common.router.annotation.Task;
import com.eastwood.common.router.annotation.Transition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class Utils {

    static InnerRouterInfo getRouterInfo(String originUrl) {
        Uri uri = Uri.parse(originUrl);
        String path = uri.getPath();
        if (path.length() == 0) {
            return null;
        }
        String paramNames = getParamNames(uri);
        String shortUrl = uri.getScheme() + Router.SYMBOL + uri.getHost() + path;

        boolean strict = false;
        Class<?> routerIndexClass = null;
        Router router = Router.getRouterInstance();
        if (!paramNames.equals("")) {
            String strictUrl = shortUrl + "?" + paramNames;
            routerIndexClass = router.routerIndexMap.get(strictUrl.toLowerCase().hashCode());
        }
        if (routerIndexClass == null) {
            routerIndexClass = router.routerIndexMap.get(shortUrl.toLowerCase().hashCode());
            if (routerIndexClass == null) {
                return null;
            }
        } else {
            strict = true;
        }

        Method method = getMethod(routerIndexClass, shortUrl, path, strict, paramNames);
        if (method == null) return null;

        Annotation[][] annotations = method.getParameterAnnotations();
        Object[] params = new Object[annotations.length];
        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof Param) {
                    Param paramAnnotation = (Param) annotation;
                    params[i] = uri.getQueryParameter(paramAnnotation.value());
                }
            }
        }

        InnerRouterInfo routerInfo = buildRouterInfo(originUrl, routerIndexClass, method, params);
        return routerInfo;
    }

    static Method getMethod(Class<?> routerIndexClass, String shortUrl, String path, boolean strict, String params) {
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
            String routerPath = filterPath(pathAnnotation.value());
            if (!routerPath.equalsIgnoreCase(path)) continue;

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
            if (methodRouterScheme == null || methodRouterHost == null) continue;

            String currentShortUrl = methodRouterScheme + Router.SYMBOL + methodRouterHost + routerPath;
            if (!currentShortUrl.equalsIgnoreCase(shortUrl)) continue;

            if (strict) {
                Strict strictAnnotation = method.getAnnotation(Strict.class);
                if (strictAnnotation == null) {
                    continue;
                }
                Annotation[][] annotations = method.getParameterAnnotations();
                List<String> paramNames = new ArrayList<>();
                for (int i = 0; i < annotations.length; i++) {
                    for (Annotation annotation : annotations[i]) {
                        if (annotation instanceof Param) {
                            Param paramAnnotation = (Param) annotation;
                            paramNames.add(paramAnnotation.value());
                        }
                    }
                }
                String[] paramNamesArray = new String[paramNames.size()];
                paramNames.toArray(paramNamesArray);
                String paramNamesStr = "";
                Arrays.sort(paramNamesArray);
                for (int i = 0; i < paramNamesArray.length; i++) {
                    if (paramNamesArray[i] != null) {
                        paramNamesStr = paramNamesStr + "_" + paramNamesArray[i];
                    }
                }

                if (paramNamesStr.equalsIgnoreCase(params)) {
                    return method;
                }
            } else {
                return method;
            }
        }
        return null;
    }

    static String getParamNames(Method method) {
        Annotation[][] annotations = method.getParameterAnnotations();
        List<String> paramNameList = new ArrayList<>();
        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof Param) {
                    Param paramAnnotation = (Param) annotation;
                    paramNameList.add(paramAnnotation.value());
                }
            }
        }
        String[] paramNameArray = new String[paramNameList.size()];
        paramNameList.toArray(paramNameArray);
        Arrays.sort(paramNameArray);
        String paramNames = "";
        for (int i = 0; i < paramNameArray.length; i++) {
            if (paramNameArray[i] != null) {
                paramNames = paramNames + "_" + paramNameArray[i];
            }
        }
        return paramNames.toLowerCase();
    }

    static String getParamNames(Uri uri) {
        Set<String> paramsSet = uri.getQueryParameterNames();
        String[] paramNameArray = new String[paramsSet.size()];
        int currentIndex = 0;
        for (String paramName : paramsSet) {
            paramNameArray[currentIndex] = paramName;
            currentIndex++;
        }
        String paramNames = "";
        Arrays.sort(paramNameArray);
        for (int i = 0; i < paramNameArray.length; i++) {
            if (paramNameArray[i] != null) {
                paramNames = paramNames + "_" + paramNameArray[i];
            }
        }
        return paramNames.toLowerCase();
    }

    static <T> InnerRouterInfo buildRouterInfo(String originUrl, Class<T> routerIndexClass, Method method, Object[] args) {
        InnerRouterInfo routerInfo = new InnerRouterInfo();

        com.eastwood.common.router.annotation.RouterHandler routerHandlerAnnotation = method.getAnnotation(com.eastwood.common.router.annotation.RouterHandler.class);
        if (routerHandlerAnnotation != null) {
            routerInfo.routerHandler = routerHandlerAnnotation.value();
        }

        Activity activityAnnotation = method.getAnnotation(Activity.class);
        if (activityAnnotation != null) {
            routerInfo.activity = activityAnnotation.value();
        }

        if (routerInfo.routerHandler == null && routerInfo.activity == null) {
            throw new RuntimeException("can't find @RouterHandler or @Activity in method " + method.getName() + " of class " + routerIndexClass.getName());
        }

        List<Object> tempList = new ArrayList<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int count = parameterAnnotations.length;
        Object[] tempParamTypes = method.getParameterTypes();
        for (int i = 0; i < count; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            for (Annotation annotation : annotations) {
                if (annotation instanceof Param) {
                    Param param = (Param) annotation;
                    tempList.add(param.value());        // name
                    tempList.add(tempParamTypes[i]);    // type
                    tempList.add(args[i]);              // value
                } else if (annotation instanceof Flags) {
                    if (args[i] != null && args[i] instanceof Integer) {
                        routerInfo.flags = (int) args[i];
                    }
                }
            }
        }

        int size = tempList.size() / 3;
        Object[] paramTypes = new Object[size];
        String[] paramNames = new String[size];
        Object[] paramValues = new Object[size];
        for (int i = 0; i < size; i++) {
            int position = i * 3;
            paramNames[i] = (String) tempList.get(position);
            paramTypes[i] = tempList.get(position + 1);
            paramValues[i] = tempList.get(position + 2);
        }
        routerInfo.paramTypes = paramTypes;
        routerInfo.paramNames = paramNames;
        routerInfo.paramValues = paramValues;

        Task taskAnnotation = method.getAnnotation(Task.class);
        if (taskAnnotation != null) {
            routerInfo.preTasks = taskAnnotation.value();
        }

        if (routerInfo.routerHandler != null) {
            if (originUrl == null) {
                originUrl = getUrl(routerIndexClass, method);
            }
            routerInfo.originUrl = originUrl;
            return routerInfo;
        }

        Bundle bundleAnnotation = method.getAnnotation(Bundle.class);
        if (bundleAnnotation != null) {
            String[] bundle = bundleAnnotation.value();
            if (bundle.length > 0) {
                String[] bundleData = new String[bundle.length * 2];
                for (int i = 0; i < bundle.length; i++) {
                    String[] data = bundle[i].split("=");
                    if (data.length >= 2) {
                        bundleData[i * 2] = data[0];
                        bundleData[i * 2 + 1] = data[1];
                    }
                }
                routerInfo.bundleData = bundleData;
            }
        }

        int requestCode = -1;
        RequestCode requestCodeAnnotation = method.getAnnotation(RequestCode.class);
        if (requestCodeAnnotation != null) {
            requestCode = requestCodeAnnotation.value();
        }
        routerInfo.requestCode = requestCode;


        Transition transitionAnnotation = method.getAnnotation(Transition.class);
        if (transitionAnnotation != null) {
            routerInfo.transition = transitionAnnotation.value();
        }

        Flags flagsAnnotation = method.getAnnotation(Flags.class);
        if (flagsAnnotation != null) {
            routerInfo.flags = flagsAnnotation.value();
        }

        return routerInfo;
    }

    static String getUrl(Class<?> routerIndexClass, Method method) {
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

        Path pathAnnotation = method.getAnnotation(Path.class);
        if (pathAnnotation == null) {
            return null;
        }
        String routerPath = filterPath(pathAnnotation.value());

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
            return null;
        }
        return methodRouterScheme + Router.SYMBOL + methodRouterHost + routerPath;
    }

    static boolean isURL(String url) {
        if (url == null) {
            return false;
        }
        String urlPattern = "^[a-zA-Z0-9]+://[^\\s]*";
        return url.matches(urlPattern);
    }

    static String filterPath(String path) {
        if (path != null && path.startsWith("/")) {
            return path;
        }
        return "/" + path;
    }

    public static <T> T inflectClass(Class<T> className) throws IllegalAccessException, InstantiationException {
        return className.newInstance();
    }
}
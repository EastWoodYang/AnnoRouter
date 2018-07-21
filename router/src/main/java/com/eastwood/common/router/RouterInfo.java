package com.eastwood.common.router;

import android.os.Bundle;

public class RouterInfo {

    public String originUrl;

    public String[] paramNames;
    public Object[] paramTypes;
    public Object[] paramValues;
    public String[] bundleData;

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        ParamHelper.putIntent(bundle, this);
        return bundle;
    }

}
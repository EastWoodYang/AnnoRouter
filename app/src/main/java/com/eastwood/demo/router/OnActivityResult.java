package com.eastwood.demo.router;

import android.content.Intent;

import com.eastwood.common.router.OnRouterResult;

public interface OnActivityResult extends OnRouterResult {

    void onActivityResult(int requestCode, int resultCode, Intent data);

}

package com.eastwood.common.router;

import android.content.Intent;

public interface OnActivityResult extends OnRouterResult {

    void onActivityResult(int resultCode, Intent data);

}

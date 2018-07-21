package com.eastwood.demo.router;

import com.eastwood.common.router.IActivityTransition;

/**
 * @author eastwood
 * createDate: 2018-07-09
 */
public class MainRouterTransition implements IActivityTransition {

    @Override
    public int enterAnim() {
        return R.anim.fade_in;
    }

    @Override
    public int exitAnim() {
        return R.anim.fade_out;
    }
}

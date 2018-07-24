package com.eastwood.common.router;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

class ActivityResultUtil {

    private static final String TAG = "OnResultInnerFragment";

    public static void startForResult(Activity activity, Intent intent, int requestCode, OnActivityResult callback) {
        InnerFragment innerFragment = getFragment(activity);
        innerFragment.startForResult(intent, requestCode, callback);
    }

    private static InnerFragment getFragment(Activity activity) {
        InnerFragment innerFragment = (InnerFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        if (innerFragment == null) {
            innerFragment = new InnerFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(innerFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return innerFragment;
    }

    public static class InnerFragment extends Fragment {

        private SparseArray<OnActivityResult> mCallbacks = new SparseArray<>(2);

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public void startForResult(Intent intent, int requestCode, OnActivityResult callback) {
            mCallbacks.put(requestCode, callback);
            startActivityForResult(intent, requestCode);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            OnActivityResult callback = mCallbacks.get(requestCode);
            if (callback != null) {
                mCallbacks.remove(requestCode);
                callback.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}

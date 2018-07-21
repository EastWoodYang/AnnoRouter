package com.eastwood.common.router;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

class TaskManager {

    private Context mContext;
    private RouterInfo mRouterInfo;
    private List<IRouterTask> mTasks = new ArrayList<>();
    private int mCurrentTaskIndex;

    private OnTaskResult taskCallback;

    public TaskManager(Context context, RouterInfo pageInfo) {
        mContext = context;
        mRouterInfo = pageInfo;
    }

    public void addTask(IRouterTask task) {
        mTasks.add(task);
    }

    public void start(OnTaskResult taskCallback) {
        this.taskCallback = taskCallback;
        if (mTasks.size() == 0) {
            taskCallback.success();
            return;
        }

        executeTask(mTasks.get(0));
    }

    private void executeTask(IRouterTask routerTask) {
        routerTask.execute(mContext, mRouterInfo, new OnTaskResult() {

            @Override
            public void success() {
                mCurrentTaskIndex++;
                if (mCurrentTaskIndex >= mTasks.size()) {
                    taskCallback.success();
                } else {
                    executeTask(mTasks.get(mCurrentTaskIndex));
                }
            }

            @Override
            public void error(String msg) {
                taskCallback.error(msg);
            }

            @Override
            public void cancel() {
                taskCallback.cancel();
            }
        });
    }


    public static TaskManager getTaskList(Context context, InnerRouterInfo routerInfo) throws Exception {
        if (routerInfo.preTasks == null) return null;

        TaskManager taskManager = new TaskManager(context, routerInfo);
        for (Class<? extends IRouterTask> preTask : routerInfo.preTasks) {
            IRouterTask routerTask = Utils.inflectClass(preTask);
            taskManager.addTask(routerTask);
        }
        return taskManager;
    }

}
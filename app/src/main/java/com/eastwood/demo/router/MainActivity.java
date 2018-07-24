package com.eastwood.demo.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.eastwood.common.router.Router;
import com.eastwood.demo.router.api.PassObjectRouterApi;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private int startMethod;
    private boolean openWeb;
    private boolean withTask;
    private boolean withInnerActivity;
    private boolean customService;
    private boolean withParams;

    private boolean callSample;

    private int module;

    private CheckBox withTaskCheckBox;
    private CheckBox withInnerActivityCheckBox;
    private CheckBox customServiceCheckBox;
    private CheckBox withParamsCheckBox;
    private CheckBox callSampleCheckBox;
    private RadioGroup moduleRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        HashMap<String, String> hashMap = new HashMap<>(3);

        ArrayMap<String, String> arrayMap = new ArrayMap<String, String>(50);

        RadioGroup startMethodRadioGroup = (RadioGroup) findViewById(R.id.start_method_radio_group);
        startMethodRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                startMethod = checkedId;
                openWeb = startMethod == R.id.open_web;
                withTaskCheckBox.setEnabled(!openWeb);
                if (!openWeb) {
                    if (withTask) {
                        withInnerActivityCheckBox.setEnabled(true);
                    } else {
                        withInnerActivityCheckBox.setEnabled(false);
                    }
                } else {
                    withInnerActivityCheckBox.setEnabled(false);
                }
                customServiceCheckBox.setEnabled(!openWeb);
                withParamsCheckBox.setEnabled(!openWeb);
                callSampleCheckBox.setEnabled(!openWeb);
                moduleRadioGroup.setEnabled(!openWeb);
            }
        });

        withTaskCheckBox = (CheckBox) findViewById(R.id.with_task_check_box);
        withTaskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                withTask = isChecked;

                if (withTask) {
                    withInnerActivityCheckBox.setEnabled(true);
                    withInnerActivity = withInnerActivityCheckBox.isChecked();
                } else {
                    withInnerActivityCheckBox.setEnabled(false);
                    withInnerActivity = false;
                }
            }
        });

        withInnerActivityCheckBox = (CheckBox) findViewById(R.id.with_inner_activity_check_box);
        withInnerActivityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                withInnerActivity = isChecked;
            }
        });

        customServiceCheckBox = (CheckBox) findViewById(R.id.custom_service_check_box);
        customServiceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customService = isChecked;
            }
        });

        withParamsCheckBox = (CheckBox) findViewById(R.id.with_params_check_box);
        withParamsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                withParams = isChecked;
            }
        });

        callSampleCheckBox = (CheckBox) findViewById(R.id.call_sample_check_box);
        callSampleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callSample = isChecked;
                withTaskCheckBox.setEnabled(!isChecked);
                if (isChecked) {
                    withInnerActivityCheckBox.setEnabled(false);
                } else {
                    withInnerActivityCheckBox.setEnabled(withTaskCheckBox.isChecked());
                }
                customServiceCheckBox.setEnabled(!isChecked);
                withParamsCheckBox.setEnabled(!isChecked);
            }
        });

        moduleRadioGroup = (RadioGroup) findViewById(R.id.module_radio_group);
        moduleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                module = checkedId;
            }
        });

        findViewById(R.id.do_it_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.do_it_button) {
            if (openWeb) {
                Router.execute("http://www.baidu.com?a=1&b=s&c=1002929211119&d=11.1&e=1.11111111111&f=true");
                return;
            }

            if (callSample) {
//                if (module == R.id.b_module) {
//                    IRouterBApi iRouterBApi = Router.create(IRouterBApi.class);
//                    if (startMethod == R.id.start_activity_for_result) {
//                        iRouterBApi.startActivityForResult_defaultService_withoutParams_withoutTasks_withoutInnerActivity();
//                    } else {
//                        iRouterBApi.startActivity_defaultService_withoutParams_withoutTasks_withoutInnerActivity();
//                    }
//                } else if (module == R.id.c_module) {
//                    IRouterCApi iRouterCApi = Router.create(IRouterCApi.class);
//                    if (startMethod == R.id.start_activity_for_result) {
//                        iRouterCApi.startActivityForResult_defaultService_withoutParams_withoutTasks_withoutInnerActivity();
//                    } else {
//                        iRouterCApi.startActivity_defaultService_withoutParams_withoutTasks_withoutInnerActivity();
//                    }
//                } else
                {
//                    TestRouterApi testRouterApi = Router.create(TestRouterApi.class);
//                    testRouterApi.gotoRouterAActivity2("", "", Intent.FLAG_ACTIVITY_NEW_TASK);
//                    call = passObjectRouterApi.method("data1", "data2");
//
                    PassObjectRouterApi passObjectRouterApi = Router.create(PassObjectRouterApi.class);
//                    passObjectRouterApi.startActivity("data");
                    passObjectRouterApi.startActivity(new String[] {"data"});
//                    passObjectRouterApi.startActivity((byte)1, 1, (short)1, 1L, 1f, 1D, true, 'a');
//                    passObjectRouterApi.startActivity(Byte.valueOf("1"), Integer.valueOf(1), Short.valueOf((short)1), Long.valueOf(1L), Float.valueOf(1f), Double.valueOf(1D), Boolean.valueOf(true), Character.valueOf('a'));
//                    passObjectRouterApi.startActivity(new byte[]{(byte)1}, new int[]{1}, new short[]{(short)1}, new long[]{1L}, new float[]{1f}, new double[]{1D}, new boolean[]{true}, new char[]{'a'});
//                    passObjectRouterApi.startActivity(new Byte[]{(byte)1}, new Integer[]{1}, new Short[]{(short)1}, new Long[]{1L}, new Float[]{1f}, new Double[]{1D}, new Boolean[]{true}, new Character[]{'a'});
//                    passObjectRouterApi.startActivity((Byte) null, null, null, null, null, null, null, null);

//                    SerializableObject serializableObject = new SerializableObject();
//                    serializableObject.name = "name";
//                    List<SerializableObject> list = new ArrayList<>();
//                    list.add(serializableObject);
//                    passObjectRouterApi.startActivity(serializableObject);
                }
            } else {
                Router.execute(getUrl());//"winA://pass.winwin.com/basedVariableObject?byte=1&int=1&short=1&long=1&float=1&double=1&boolean=true&char=a"); // getUrl()
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(mContext, "onActivityResult: - requestCode " + requestCode + ", - resultCode " + resultCode, Toast.LENGTH_SHORT).show();
    }

    private String getUrl() {
        String scheme = "winA";
        String host = "a.winwin.com";
        if (module == R.id.b_module) {
            scheme = "winB";
            host = "b.winwin.com";
        } else if (module == R.id.c_module) {
            scheme = "winC";
            host = "c.winwin.com";
        }

        String startMethodStr = "startActivity";
        if (startMethod == R.id.start_activity_for_result) {
            startMethodStr = "startActivityForResult";
        }

        String routerService = "defaultService";
        if (customService) {
            routerService = "customService";
        }

        String params = "withoutParams";
        String query = null;
        if (withParams) {
            params = "withParams";
            query = "?count=1&time=11111111111111&flag=1.1&total=1.222222&isAll=true";
        }
        String task = "withoutTasks";
        if (withTask) {
            task = "withTasks";
        }

        String innerActivity = "withoutInnerActivity";
        if (withInnerActivity) {
            innerActivity = "withInnerActivity";
        }
//        return "winA" + Intent.FLAG_ACTIVITY_CLEAR_TASK;
        return scheme + "://" + host + "/" + startMethodStr + "/" + routerService + "/" + params + "/" + task + "/" + innerActivity + (withParams ? query : "");
    }
}
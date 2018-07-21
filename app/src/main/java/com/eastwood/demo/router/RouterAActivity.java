package com.eastwood.demo.router;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RouterAActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        Intent intent = getIntent();
        if (intent != null) {

            String[] paramNames = new String[] {"byte","int","short","long","float","double","boolean","char"};
            for(String param : paramNames) {
                if (intent.hasExtra(param)) {
                    Log.d("Bundle", "has Extra: " + param);
                }
            }


            int intParam = 0;
            if (intent.hasExtra("count")) {
                intParam = intent.getIntExtra("count", 0);
            }

            long longParam = 0L;
            if (intent.hasExtra("time")) {
                longParam = intent.getLongExtra("time", 0L);
            }

            float floatParam = 0.0f;
            if (intent.hasExtra("flag")) {
                floatParam = intent.getFloatExtra("flag", 0.0f);
            }

            double doubleParam = 0.00d;
            if (intent.hasExtra("total")) {
                doubleParam = intent.getDoubleExtra("total", 0.00d);
            }

            boolean booleanParam = false;
            if (intent.hasExtra("isAll")) {
                booleanParam = intent.getBooleanExtra("isAll", false);
            }

            if (intParam > 0) {
                String params = "intParam: " + intParam + "\nlongParam: " + longParam + "\nfloatParam: " + floatParam +
                        "\ndoubleParam: " + doubleParam + "\nbooleanParam: " + booleanParam;

                TextView paramsTextView = (TextView) findViewById(R.id.params_text_view);
                paramsTextView.setText(params);
                paramsTextView.setVisibility(View.VISIBLE);
            }
        }

        Button finnishButton = (Button) findViewById(R.id.finish_button);
        finnishButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_button:
                finish();
                break;
        }
    }
}
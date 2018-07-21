package com.eastwood.demo.router;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        TextView tipTextView = (TextView) findViewById(R.id.tip_text_view);
        String action = getIntent().getAction();
        if ("customService".equals(action)) {
            tipTextView.setText("this temp activity start redirect by Custom Router Service");
        } else {
            tipTextView.setText("this temp activity start by PreTask3");
            Button startAgainButton = (Button) findViewById(R.id.start_again_button);
            startAgainButton.setOnClickListener(this);
            startAgainButton.setVisibility(View.VISIBLE);
        }
        findViewById(R.id.finish_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_again_button: {
                Intent intent = new Intent(TempActivity.this, TempActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.finish_button: {
                finish();
                break;
            }
        }
    }
}
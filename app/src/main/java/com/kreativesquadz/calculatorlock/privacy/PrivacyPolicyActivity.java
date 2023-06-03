package com.kreativesquadz.calculatorlock.privacy;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.BaseActivity;



public class PrivacyPolicyActivity extends BaseActivity {
    ImageView imgBack;
    Toolbar toolbar;
    WebView txtInformtation;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.privacy_policy);
        findViews();
        initViews();
    }

    private void findViews() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.imgBack = (ImageView) findViewById(R.id.ic_back);
    }

    private void initViews() {
        this.txtInformtation = (WebView) findViewById(R.id.txtInformtation);
        this.txtInformtation.loadUrl("https://www.kreativesquadz.com/apps/privacy-policy/calculator_privacy_policy.html");
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrivacyPolicyActivity.this.finish();
            }
        });
    }

}

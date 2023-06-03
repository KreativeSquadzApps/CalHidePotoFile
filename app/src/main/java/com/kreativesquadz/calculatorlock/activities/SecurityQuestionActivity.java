package com.kreativesquadz.calculatorlock.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;

import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;


public class SecurityQuestionActivity extends BaseActivity {
    public static final String ADD = "add";
    public static final String CHANGE = "change";
    public static final String FORGOT_PASS = "forgot_pass";
    public static final String TYPE = "type";
    private String[] array;
    Button btnSubmit;
    EditText etAnswer;
    LinearLayout nativeAdContainer;
    private int position;
    private String question;
    private String selectedQuestion;
    AppCompatSpinner spinQuestions;
    CenterTitleToolbar toolbar;
    private String type;

    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_security_question);
        ButterKnife.bind(this);
        findViews();
        initViews();
    }

    private void findViews() {
        this.btnSubmit = (Button) findViewById(R.id.btn_submit);
        this.etAnswer = (EditText) findViewById(R.id.et_answer);
        this.nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
        this.spinQuestions = (AppCompatSpinner) findViewById(R.id.spin_questions);
        this.toolbar = (CenterTitleToolbar) findViewById(R.id.toolbar);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = selectedQuestion;
                if (str == null) {
                    Toast.makeText(SecurityQuestionActivity.this, "Please select security question", Toast.LENGTH_SHORT).show();
                } else if (str.equals("Select Your Question")) {
                    Toast.makeText(SecurityQuestionActivity.this, "Please select security question", Toast.LENGTH_SHORT).show();
                } else if (etAnswer.getText().toString().isEmpty()) {
                    Toast.makeText(SecurityQuestionActivity.this, "Please enter security answer", Toast.LENGTH_SHORT).show();
                } else {
                    String str2 = SecurityQuestionActivity.this.type;
                    if (str2 == null || !str2.equals(FORGOT_PASS)) {
                        MainApplication.getInstance().setSecurityQuestion(SecurityQuestionActivity.this.selectedQuestion);
                        MainApplication.getInstance().setSecurityAnswer(SecurityQuestionActivity.this.etAnswer.getText().toString());
                        setBackData();
                    } else if (!SecurityQuestionActivity.this.selectedQuestion.equalsIgnoreCase(MainApplication.getInstance().getSecurityQuestion())) {
                        Toast.makeText(SecurityQuestionActivity.this, "Security question is incorrect!", Toast.LENGTH_SHORT).show();
                    } else if (SecurityQuestionActivity.this.etAnswer.getText().toString().equalsIgnoreCase(MainApplication.getInstance().getSecurityAnswer())) {
                        showPassword();
                    } else {
                        Toast.makeText(SecurityQuestionActivity.this, "Security answer is incorrect!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initViews() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle("Security Question");
        if (getIntent().getExtras() != null) {
            this.type = getIntent().getStringExtra(TYPE);
        }
        this.spinQuestions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                SecurityQuestionActivity.this.selectedQuestion = adapterView.getItemAtPosition(i).toString();
            }
        });
        String str = this.type;
        if (str != null && str.equals(CHANGE)) {
            this.array = getResources().getStringArray(R.array.questions);
            this.question = MainApplication.getInstance().getSecurityQuestion();
            this.etAnswer.setText(MainApplication.getInstance().getSecurityAnswer());
            int i = 0;
            while (true) {
                String[] strArr = this.array;
                if (i < strArr.length) {
                    this.position = i;
                    if (this.question.equals(strArr[i])) {
                        this.spinQuestions.post(new runnable());
                        return;
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }



    public class runnable implements Runnable {
        runnable() {
        }

        @Override
        public void run() {
            SecurityQuestionActivity.this.spinQuestions.setSelection(SecurityQuestionActivity.this.position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }



    private void showPassword() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_security_question);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout(-1, -2);
        }
        ((TextView) dialog.findViewById(R.id.et_file_name)).setText("Your password is \n " + MainApplication.getInstance().getPassword());
        ((ImageView) dialog.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SecurityQuestionActivity.this.finish();
            }
        });
        ((TextView) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SecurityQuestionActivity.this.finish();
            }
        });
        dialog.show();
    }

    private void setBackData() {
        setResult(-1, new Intent());
        String str = this.type;
        if (str == null || !str.equals(CHANGE)) {
            String str2 = this.type;
            if (str2 != null && str2.equals(ADD)) {
                finish();
                startActivity(new Intent(this, HomeActivity.class));
                return;
            }
            return;
        }
        finish();
    }
}

package com.kreativesquadz.calculatorlock.activities;

import static com.kreativesquadz.calculatorlock.Ads.api.ConfigUrl.APPNAME;
import static com.kreativesquadz.calculatorlock.Ads.api.ConfigUrl.AdsString;
import static com.kreativesquadz.calculatorlock.Ads.api.ConfigUrl.DATA;
import static com.kreativesquadz.calculatorlock.Ads.api.ConfigUrl.URL;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonArray;
import com.kreativesquadz.calculatorlock.Ads.api.APIInterface;
import com.kreativesquadz.calculatorlock.R;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.share.Share;
import com.kreativesquadz.calculatorlock.share.share_calc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import net.objecthunter.exp4j.ExpressionBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class CalcActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    boolean aB;
    Double aa;
    private AlphaAnimation click_anim;
    EditText et_main;
    ImageView f6017O;
    Editable f6028Z;
    Button iv_clear;
    ImageView iv_divide;
    Button iv_dot;
    Button iv_eight;
    ImageView iv_equals;
    Button iv_five;
    Button iv_four;
    ImageView iv_minus;
    ImageView iv_multiply;
    Button iv_nine;
    Button iv_one;
    Button iv_percent;
    ImageView iv_plus;
    Button iv_plus_minus;
    Button iv_seven;
    Button iv_six;
    Button iv_sqrt;
    Button iv_three;
    Button iv_two;
    Button iv_zero;
    LinearLayout ll_calc;
    LinearLayout ll_delete;
    RelativeLayout rl_calc_layout;
    EditText tv_Display;
    TextView tv_divide;
    String f6024V = "";
    String f6025W = "";
    String f6026X = "";
    Boolean f6027Y = false;
    boolean aC = false;
    String ab = "";
    String ac = "";
    int ad = 0;
    int ae = 0;
    int af = 0;
    int ag = 0;
    Boolean ah = false;
    Boolean ai = false;
    Boolean aj = false;
    Boolean ak = false;
    Boolean al = false;
    Boolean am = false;
    Boolean an = false;
    Boolean ao = false;
    Boolean ap = false;
    Boolean aq = false;
    Boolean ar = true;
    Boolean as = true;
    Boolean at = true;
    Boolean au = true;
    Boolean av = false;
    Boolean aw = false;
    Boolean az = false;
    private String expressions = "";
    private String firststr = "";
    Boolean f6029n = false;
    Boolean f6030o = false;
    Boolean f6031p = false;
    private String prev = "";
    Boolean f6032q = false;
    Boolean f6033r = false;
    private Double result = Double.valueOf(0.0d);

    @Override

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_calc);
        initviews();
        initlisteners();
        check_tablet();
        if (MainApplication.getInstance().getPassword().isEmpty()) {
            showSetPasswordDialog();
        } else {
            this.click_anim = new AlphaAnimation(1.0f, 0.5f);
        }

        adsstatus(this);
        MangeFile();


    }

    AlertDialog dialog;

    Button ok_click;

    void Show_Diloge(Context con) {
        View alertCustomdialog = LayoutInflater.from(con).inflate(R.layout.mange_file_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(con);
        ok_click = alertCustomdialog.findViewById(R.id.ok_btn);
        alert.setView(alertCustomdialog);
        dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        ok_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });


    }

    void MangeFile() {


        String sdk_version_number = android.os.Build.VERSION.SDK;
        Log.e("sdk_version_number", "" + sdk_version_number);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {

            } else {

                Show_Diloge(CalcActivity.this);
            }


        } else {


        }


    }

    public class C16052 implements TextWatcher {
        final CalcActivity calcActivity;

        @Override
        public void afterTextChanged(Editable editable) {
        }

        C16052(CalcActivity calcActivity) {
            this.calcActivity = calcActivity;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (this.calcActivity.tv_Display.getText().toString().equalsIgnoreCase("") || this.calcActivity.tv_Display.getText().toString().equalsIgnoreCase("0")) {
                this.calcActivity.tv_Display.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!this.calcActivity.tv_Display.getText().toString().equalsIgnoreCase("")) {
                this.calcActivity.tv_Display.setSelection(this.calcActivity.tv_Display.getText().toString().length() - 1);
            }
        }
    }



    public class C16063 implements TextWatcher {
        final CalcActivity calcActivity;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C16063(CalcActivity calcActivity) {
            this.calcActivity = calcActivity;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!this.calcActivity.et_main.getText().toString().equalsIgnoreCase("")) {
                this.calcActivity.et_main.setSelection(this.calcActivity.et_main.getText().toString().length() - 1);
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!this.calcActivity.et_main.getText().toString().equalsIgnoreCase("")) {
                this.calcActivity.et_main.setSelection(this.calcActivity.et_main.getText().toString().length() - 1);
            }
        }
    }

    private void check_tablet() {
        this.f6017O = (ImageView) findViewById(R.id.iv_square_root);
        this.f6017O.setOnClickListener(this);
        if (share_calc.flag_expand.booleanValue()) {
            Log.e("flag_expand", "" + share_calc.flag_expand);
            share_calc.flag_expand = false;
        }
    }

    private void dot_operation() {
        if (this.et_main.getText().length() > 0) {
            this.ad = 0;
            if (this.ai.booleanValue()) {
                this.ah = false;
            }
            if (this.ah.booleanValue()) {
                this.et_main.setText("");
                this.ab = "";
                this.tv_Display.setText("0");
                this.ah = false;
            }
            char[] charArray = this.ab.toCharArray();
            for (int length = charArray.length - 1; length >= 0; length--) {
                if (charArray[length] == '.') {
                    this.ad++;
                }
                if (this.ad == 1) {
                    break;
                }
            }
            if (!(this.ad != 0 || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '.' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '+' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '-' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '/' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '*' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '%')) {
                char charAt = this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1);
                this.tv_Display.setText(this.ab);
                Log.e("dot", "" + charAt);
                if (charAt < '0' || charAt > '9') {
                    Log.e("dot", "else" + charAt);
                    this.et_main.append("* 0.");
                    this.ab += "";
                    this.tv_Display.setText(this.ab);
                    return;
                }
                if (this.av.booleanValue()) {
                    this.ac += ".";
                }
                Log.e("dot", "if" + charAt);
                this.et_main.append(".");
                this.ab += ".";
                this.tv_Display.setText(this.ab);
            }
        }
    }

    private void initlisteners() {
        this.iv_zero.setOnClickListener(this);
        this.iv_one.setOnClickListener(this);
        this.iv_two.setOnClickListener(this);
        this.iv_three.setOnClickListener(this);
        this.iv_four.setOnClickListener(this);
        this.iv_five.setOnClickListener(this);
        this.iv_six.setOnClickListener(this);
        this.iv_seven.setOnClickListener(this);
        this.iv_eight.setOnClickListener(this);
        this.iv_nine.setOnClickListener(this);
        this.iv_plus.setOnClickListener(this);
        this.iv_minus.setOnClickListener(this);
        this.iv_multiply.setOnClickListener(this);
        this.iv_divide.setOnClickListener(this);
        this.iv_percent.setOnClickListener(this);
        this.iv_clear.setOnClickListener(this);
        this.iv_equals.setOnClickListener(this);
        this.iv_sqrt.setOnClickListener(this);
        this.ll_delete.setOnClickListener(this);
        this.iv_plus_minus.setOnClickListener(this);
        this.iv_dot.setOnClickListener(this);
        this.iv_zero.setOnTouchListener(this);
        this.iv_one.setOnTouchListener(this);
        this.iv_two.setOnTouchListener(this);
        this.iv_three.setOnTouchListener(this);
        this.iv_four.setOnTouchListener(this);
        this.iv_five.setOnTouchListener(this);
        this.iv_six.setOnTouchListener(this);
        this.iv_seven.setOnTouchListener(this);
        this.iv_eight.setOnTouchListener(this);
        this.iv_nine.setOnTouchListener(this);
        this.iv_plus.setOnTouchListener(this);
        this.iv_minus.setOnTouchListener(this);
        this.iv_multiply.setOnTouchListener(this);
        this.iv_divide.setOnTouchListener(this);
        this.iv_percent.setOnTouchListener(this);
        this.iv_clear.setOnTouchListener(this);
        this.iv_equals.setOnTouchListener(this);
        this.iv_sqrt.setOnTouchListener(this);
        this.ll_delete.setOnTouchListener(this);
        this.iv_plus_minus.setOnTouchListener(this);
        this.iv_dot.setOnTouchListener(this);
        this.tv_Display.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        this.tv_Display.setSingleLine();
        this.tv_Display.addTextChangedListener(new C16052(this));
        this.et_main.setSingleLine();
        this.tv_Display.setSingleLine();
        this.et_main.addTextChangedListener(new C16063(this));
    }

    private void initviews() {
        this.iv_clear = (Button) findViewById(R.id.iv_clear);
        this.iv_seven = (Button) findViewById(R.id.iv_seven);
        this.iv_four = (Button) findViewById(R.id.iv_four);
        this.iv_one = (Button) findViewById(R.id.iv_one);
        this.iv_zero = (Button) findViewById(R.id.iv_zero);
        this.iv_percent = (Button) findViewById(R.id.iv_percent);
        this.iv_eight = (Button) findViewById(R.id.iv_eight);
        this.iv_five = (Button) findViewById(R.id.iv_five);
        this.iv_two = (Button) findViewById(R.id.iv_two);
        this.iv_dot = (Button) findViewById(R.id.iv_dot);
        this.iv_divide = (ImageView) findViewById(R.id.iv_divide);
        this.iv_nine = (Button) findViewById(R.id.iv_nine);
        this.iv_six = (Button) findViewById(R.id.iv_six);
        this.iv_three = (Button) findViewById(R.id.iv_three);
        this.iv_plus_minus = (Button) findViewById(R.id.iv_plus_minus);
        this.iv_multiply = (ImageView) findViewById(R.id.iv_multiply);
        this.iv_minus = (ImageView) findViewById(R.id.iv_minus);
        this.iv_plus = (ImageView) findViewById(R.id.iv_plus);
        this.iv_equals = (ImageView) findViewById(R.id.iv_equals);
        this.iv_sqrt = (Button) findViewById(R.id.iv_sqrt);
        this.et_main = (EditText) findViewById(R.id.et_main);
        this.tv_Display = (EditText) findViewById(R.id.tv_Display);
        this.tv_divide = (TextView) findViewById(R.id.tv_divide);
        this.ll_delete = (LinearLayout) findViewById(R.id.ll_delete);
        this.ll_calc = (LinearLayout) findViewById(R.id.ll_calc);
        this.rl_calc_layout = (RelativeLayout) findViewById(R.id.rl_calc_layout);
    }

    private void mid_calculation() {
        if (!this.f6026X.equals("/") || !this.f6025W.equals("0")) {
            String valueOf = String.valueOf(this.f6028Z);
            Log.e("string", "" + valueOf);
            try {
                this.aa = Double.valueOf(new ExpressionBuilder(valueOf).build().evaluate());
                Log.e("result", "" + this.aa);
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
            if (String.valueOf(this.aa).contains("E")) {
                Double evaluate = new ExtendedDoubleEvaluator().evaluate(String.valueOf(this.aa).replaceAll("%", "").replace("E", "*10^"));
                String valueOf2 = String.valueOf(evaluate.doubleValue() / 100.0d);
                Log.e("new result", "" + evaluate);
                Log.e("new result", "" + valueOf2);
            }
            this.aa = Double.valueOf(Double.parseDouble(new DecimalFormat(".##########################################").format(this.aa)));
            try {
                new ExtendedDoubleEvaluator();
                if (this.az.booleanValue()) {
                    int i = 0;
                    int i2 = 0;
                    for (int i3 = 0; i3 < valueOf.length(); i3++) {
                        if (valueOf.charAt(i3) == '(') {
                            i++;
                            Log.e("count_left_bracket", "" + i);
                        }
                        if (valueOf.charAt(i3) == ')') {
                            i2++;
                            Log.e("count_right_bracket", "" + i2);
                        }
                    }
                    int i4 = i - i2;
                    Log.e("diff", "" + i4);
                    int i5 = 0;
                    while (i5 < i4) {
                        i5++;
                        String str = ((Object) valueOf) + ")";
                    }
                    this.az = false;
                } else {
                    valueOf = null;
                }
                this.tv_Display.setText(valueOf);
                Log.e("Answer", this.aa + "");
                Double d = this.aa;
                Long valueOf3 = Long.valueOf(new Double(d.doubleValue()).longValue());
                Log.e("Double", d + "");
                Log.e("long", valueOf3 + "");
                Locale locale = Locale.US;
                NumberFormat instance = NumberFormat.getInstance();
                instance.setMaximumIntegerDigits(20);
                instance.setMaximumFractionDigits(20);
                instance.setGroupingUsed(false);
                this.ab = instance.format(this.aa);
                if (this.ab.length() > 16) {
                    this.ab = this.ab.substring(0, 16);
                } else {
                    this.tv_Display.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                }
                if (this.ab.equalsIgnoreCase("-0")) {
                    this.ab = "0";
                }
                this.tv_Display.setText(this.ab);
                this.et_main.setText(this.ab);
                this.f6024V = this.ab;
            } catch (Exception e2) {
                Log.e("TAG", "Toast");
                Toast.makeText(this, "Syntax Error", Toast.LENGTH_SHORT).show();
                this.et_main.setText("");
                this.tv_Display.setText("0");
                this.ab = "";
                this.ac = "";
                this.prev = "";
                this.firststr = "";
                this.aC = false;
                e2.printStackTrace();
                Log.e("Exception", e2 + "");
            }
        } else {
            this.f6033r = true;
            this.tv_Display.setText("0");
            this.et_main.setText("Can't divide by 0");
            this.ab = "";
        }
    }

    private void mid_calculation_new() {
        if (!this.f6026X.equals("/") || !this.f6025W.equals("0")) {
            String valueOf = String.valueOf(this.f6028Z);
            Log.e("string", "" + valueOf);
            try {
                this.aa = Double.valueOf(new ExpressionBuilder(valueOf).build().evaluate());
                Log.e("result", "" + this.aa);
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
            if (String.valueOf(this.aa).contains("E")) {
                Double evaluate = new ExtendedDoubleEvaluator().evaluate(String.valueOf(this.aa).replaceAll("%", "").replace("E", "*10^"));
                String valueOf2 = String.valueOf(evaluate.doubleValue() / 100.0d);
                Log.e("new result", "" + evaluate);
                Log.e("new result", "" + valueOf2);
            }
            this.aa = Double.valueOf(Double.parseDouble(new DecimalFormat(".##########################################").format(this.aa)));
            try {
                new ExtendedDoubleEvaluator();
                if (this.az.booleanValue()) {
                    int i = 0;
                    int i2 = 0;
                    for (int i3 = 0; i3 < valueOf.length(); i3++) {
                        if (valueOf.charAt(i3) == '(') {
                            i++;
                            Log.e("count_left_bracket", "" + i);
                        }
                        if (valueOf.charAt(i3) == ')') {
                            i2++;
                            Log.e("count_right_bracket", "" + i2);
                        }
                    }
                    int i4 = i - i2;
                    Log.e("diff", "" + i4);
                    int i5 = 0;
                    while (i5 < i4) {
                        i5++;
                        String str = ((Object) valueOf) + ")";
                    }
                    this.az = false;
                } else {
                    valueOf = null;
                }
                this.tv_Display.setText(valueOf);
                Log.e("Answer", this.aa + "");
                Double d = this.aa;
                Long valueOf3 = Long.valueOf(new Double(d.doubleValue()).longValue());
                Log.e("Double", d + "");
                Log.e("long", valueOf3 + "");
                Locale locale = Locale.US;
                NumberFormat instance = NumberFormat.getInstance();
                instance.setMaximumIntegerDigits(20);
                instance.setMaximumFractionDigits(20);
                instance.setGroupingUsed(false);
                this.ab = instance.format(this.aa);
                if (this.ab.length() > 16) {
                    this.ab = this.ab.substring(0, 16);
                } else {
                    this.tv_Display.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                }
                if (this.ab.equalsIgnoreCase("-0")) {
                    this.ab = "0";
                }
                this.tv_Display.setText(this.ab);
                this.f6024V = this.ab;
            } catch (Exception e2) {
                Log.e("TAG", "Toast");
                Toast.makeText(this, "Syntax Error", Toast.LENGTH_SHORT).show();
                this.et_main.setText("");
                this.tv_Display.setText("0");
                this.ab = "";
                this.ac = "";
                this.prev = "";
                this.firststr = "";
                this.aC = false;
                e2.printStackTrace();
                Log.e("Exception", e2 + "");
            }
        } else {
            this.f6033r = true;
            this.tv_Display.setText("0");
            this.et_main.setText("Can't divide by 0");
            this.ab = "";
        }
    }

    private void operation() {
        if (this.et_main.getText().toString().length() != 0) {
            if (this.ah.booleanValue()) {
                this.ah = false;
            }
            if (!(this.et_main.getText().toString().equals("Can't divide by 0") || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '+' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '-' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '/' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '*' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '%')) {
                if (this.tv_Display.length() > 0) {
                    if (this.tv_Display.getText().charAt(0) == '-') {
                        Log.e("TAG", "if for loop :");
                        for (int i = 1; i < this.tv_Display.length(); i++) {
                            if (this.tv_Display.getText().charAt(i) == '+' || this.tv_Display.getText().charAt(i) == '-' || this.tv_Display.getText().charAt(i) == '*' || this.tv_Display.getText().charAt(i) == '/') {
                                this.aC = false;
                                break;
                            } else {
                                this.aC = true;
                            }
                        }
                    } else {
                        Log.e("TAG", "else for loop :");
                        for (int i2 = 0; i2 < this.tv_Display.length(); i2++) {
                            if (this.tv_Display.getText().charAt(i2) == '+' || this.tv_Display.getText().charAt(i2) == '-' || this.tv_Display.getText().charAt(i2) == '*' || this.tv_Display.getText().charAt(i2) == '/') {
                                this.aC = false;
                                break;
                            } else {
                                this.aC = true;
                            }
                        }
                    }
                }
                String obj = this.tv_Display.getText().toString();
                if (obj.length() > 16) {
                    obj.substring(0, 16);
                } else {
                    this.tv_Display.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                }
                if (this.aC) {
                    if (this.tv_Display.getText().charAt(0) != '-' || this.tv_Display.length() <= 1) {
                        Log.e("TAG", "display : " + this.ab);
                        Log.e("TAG", "display length : " + this.ab.length());
                        EditText editText = this.et_main;
                        editText.setText(this.et_main.getText().toString().substring(0, this.et_main.getText().toString().length() - this.ab.length()) + "-" + this.ab);
                        EditText editText2 = this.tv_Display;
                        editText2.setText("-" + this.ab);
                        this.ar = false;
                        this.ab = this.tv_Display.getText().toString();
                    } else {
                        Log.e("TAG", "oth pos - in display");
                        String obj2 = this.tv_Display.getText().toString();
                        Log.e("TAG", "s before : " + obj2);
                        Log.e("TAG", "s before replacing string : " + this.tv_Display.getText().toString().substring(1));
                        String replace = obj2.replace(this.tv_Display.getText().toString(), this.tv_Display.getText().toString().substring(1));
                        EditText editText3 = this.tv_Display;
                        editText3.setText(editText3.getText().toString().substring(1));
                        if (this.f6025W.equalsIgnoreCase("")) {
                            this.et_main.setText(replace);
                        } else {
                            EditText editText4 = this.et_main;
                            editText4.setText(this.firststr + ((Object) replace));
                        }
                        this.ab = this.tv_Display.getText().toString();
                    }
                }
                if (this.f6027Y.booleanValue()) {
                    this.f6025W = this.ab;
                    Log.e("str2", this.f6025W);
                    return;
                }
                this.f6024V = this.ab;
                Log.e("str1", this.f6024V);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String str;
        String str2;
        Boolean bool;
        String str3;
        int id = view.getId();
        if (id != R.id.ll_delete) {
            switch (id) {
                case R.id.iv_clear :
                    this.f6029n = false;
                    this.et_main.setText("");
                    this.tv_Display.setText("0");
                    this.ab = "";
                    this.f6025W = "";
                    this.f6024V = "";
                    this.f6026X = "";
                    this.f6033r = false;
                    this.f6031p = false;
                    this.f6032q = false;
                    this.ac = "";
                    this.prev = "";
                    this.firststr = "";
                    this.f6026X = "";
                    this.aC = false;
                    return;
                case R.id.iv_divide :
                    if (this.et_main.getText().toString().equals("Can't divide by 0")) {
                        return;
                    }
                    if (this.et_main.getText().length() == 0) {
                        Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.e("divide", this.et_main.getText().toString());
                    if (!this.f6033r.booleanValue()) {
                        this.f6031p = false;
                        this.f6032q = false;
                        if (this.ah.booleanValue()) {
                            this.et_main.setText(this.ab);
                            this.ab = this.aa + "";
                            this.tv_Display.setText(this.ab);
                            this.ah = false;
                        }
                        this.am = true;
                        this.f6030o = false;
                        if (this.av.booleanValue()) {
                            this.ac += "/";
                        }
                        if (this.et_main.length() > 0) {
                            this.tv_Display.setText(this.ab);
                            if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                this.f6028Z = this.et_main.getText();
                                mid_calculation_new();
                                if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                    this.et_main.append("/");
                                    this.f6029n = true;
                                    this.f6027Y = true;
                                    this.f6026X = "/";
                                    if (this.ap.booleanValue()) {
                                        this.ab += "/";
                                    }
                                } else {
                                    return;
                                }
                            } else if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '+' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '-' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '*' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '/') {
                                EditText editText = this.et_main;
                                editText.setText(editText.getText().toString().substring(0, this.et_main.getText().length() - 1));
                                this.f6028Z = this.et_main.getText();
                                mid_calculation_new();
                                if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                    this.et_main.append("/");
                                    this.f6029n = true;
                                    this.f6027Y = true;
                                    this.f6026X = "/";
                                    if (this.ap.booleanValue()) {
                                        this.ab += "/";
                                    }
                                } else {
                                    return;
                                }
                            }
                            this.prev = this.et_main.getText().toString();
                            this.firststr = this.et_main.getText().toString();
                            return;
                        }
                        Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    return;
                case R.id.iv_dot :
                    if (!this.f6033r.booleanValue()) {
                        try {
                            dot_operation();
                            return;
                        } catch (Exception unused) {
                            return;
                        }
                    } else {
                        return;
                    }
                case R.id.iv_eight :
                    this.f6033r = false;
                    if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("");
                    }
                    if (this.f6032q.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.f6032q = false;
                    }
                    if (this.f6031p.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.f6031p = false;
                    }
                    if (this.ah.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.ah = false;
                    }
                    if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                        if (!this.f6030o.booleanValue()) {
                            this.ab = "";
                            this.f6030o = false;
                        }
                        this.aj = false;
                        this.ak = false;
                        this.am = false;
                        this.al = false;
                        this.an = false;
                        this.ao = false;
                        this.aq = false;
                    }
                    if (this.av.booleanValue()) {
                        this.ac += "8";
                    }
                    if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                        this.et_main.append("8");
                        this.ab += "8";
                        this.tv_Display.setText(this.ab);
                    }
                    if (this.f6027Y.booleanValue()) {
                        this.f6025W = this.ab;
                        Log.e("str2", this.f6025W);
                        return;
                    }
                    this.f6024V = this.ab;
                    Log.e("str1", this.f6024V);
                    return;
                case R.id.iv_equals :
                    if (!MainApplication.getInstance().getPassword().equals("")) {
                        String obj = this.et_main.getText().toString();
                        if (MainApplication.getInstance().getPassword().equalsIgnoreCase(obj)) {
                            String securityQuestion = MainApplication.getInstance().getSecurityQuestion();
                            MainApplication.getInstance().getEmail();
                            if (TextUtils.isEmpty(securityQuestion)) {
                                startActivity(new Intent(this, SecurityQuestionActivity.class).putExtra(SecurityQuestionActivity.TYPE, SecurityQuestionActivity.ADD));
                                finish();
                                return;
                            }
                            startActivity(new Intent(this, HomeActivity.class));
                            finish();
                            return;
                        } else if (obj.equals("11223344")) {
                            startActivity(new Intent(this, SecurityQuestionActivity.class).putExtra(SecurityQuestionActivity.TYPE, SecurityQuestionActivity.FORGOT_PASS));
                            return;
                        } else if (!this.f6033r.booleanValue()) {
                            if (this.ah.booleanValue() && !this.f6026X.equals("") && (str = this.f6025W) != null && !str.equalsIgnoreCase("")) {
                                Log.e("str2equal", this.f6025W);
                                this.f6028Z = new SpannableStringBuilder(this.tv_Display.getText().toString() + this.f6026X + this.f6025W);
                                mid_calculation();
                            }
                            if (this.et_main.getText().length() <= 0) {
                                Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                this.aj = false;
                                this.ak = false;
                                this.am = false;
                                this.al = false;
                                this.an = false;
                                this.ao = false;
                                this.f6028Z = this.et_main.getText();
                                this.ah = true;
                                mid_calculation();
                                this.ac = "";
                                return;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else if (MainApplication.getInstance().getPassword().equals("")) {
                        if (this.et_main.getText().length() == 0) {
                            Toast.makeText(this, "Plz enter 4 digit", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (this.et_main.getText().length() > 4) {
                            Toast.makeText(this, "enter 4 digit pass", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (this.et_main.getText().length() < 4) {
                            Toast.makeText(this, "Plz enter 4 digit pass", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Share.pass = Integer.parseInt(this.et_main.getText().toString());
                            startActivity(new Intent(this, ConfirmCalcActivity.class));
                            return;
                        }
                    } else {
                        return;
                    }
                case R.id.iv_five :
                    this.f6033r = false;
                    if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("");
                    }
                    if (this.f6032q.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.f6032q = false;
                    }
                    if (this.f6031p.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.f6031p = false;
                    }
                    if (this.ah.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.ah = false;
                    }
                    if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                        if (!this.f6030o.booleanValue()) {
                            this.ab = "";
                            this.f6030o = false;
                        }
                        this.aj = false;
                        this.ak = false;
                        this.am = false;
                        this.al = false;
                        this.an = false;
                        this.ao = false;
                        this.aq = false;
                    }
                    if (this.av.booleanValue()) {
                        this.ac += "5";
                    }
                    if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                        this.et_main.append("5");
                        this.ab += "5";
                        this.tv_Display.setText(this.ab);
                        this.f6029n = false;
                    }
                    if (this.f6027Y.booleanValue()) {
                        this.f6025W = this.ab;
                        Log.e("str2", this.f6025W);
                        return;
                    }
                    this.f6024V = this.ab;
                    Log.e("str1", this.f6024V);
                    return;
                case R.id.iv_four :
                    this.f6033r = false;
                    if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("");
                    }
                    if (this.f6032q.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.f6032q = false;
                    }
                    if (this.f6031p.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.f6031p = false;
                    }
                    if (this.ah.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.tv_Display.setText("0");
                        this.ah = false;
                    }
                    if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                        if (!this.f6030o.booleanValue()) {
                            this.ab = "";
                            this.f6030o = false;
                        }
                        this.aj = false;
                        this.ak = false;
                        this.am = false;
                        this.al = false;
                        this.an = false;
                        this.ao = false;
                        this.aq = false;
                    }
                    if (this.av.booleanValue()) {
                        this.ac += "4";
                    }
                    if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                        this.et_main.append("4");
                        this.ab += "4";
                        this.tv_Display.setText(this.ab);
                        this.f6029n = false;
                    }
                    if (this.f6027Y.booleanValue()) {
                        this.f6025W = this.ab;
                        Log.e("str2", this.f6025W);
                        return;
                    }
                    this.f6024V = this.ab;
                    Log.e("str1", this.f6024V);
                    return;
                default:
                    switch (id) {
                        case R.id.iv_minus :
                            if (this.et_main.getText().toString().equals("Can't divide by 0")) {
                                return;
                            }
                            if (this.et_main.getText().length() == 0) {
                                Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.e("minus", this.et_main.getText().toString());
                            if (!this.f6033r.booleanValue()) {
                                this.f6027Y = true;
                                this.f6031p = false;
                                this.f6032q = false;
                                if (this.ah.booleanValue()) {
                                    this.et_main.setText(this.ab);
                                    this.ab = this.aa + "";
                                    this.tv_Display.setText(this.ab);
                                    this.ah = false;
                                }
                                this.ak = true;
                                this.f6030o = false;
                                if (this.av.booleanValue()) {
                                    this.ac += "-";
                                }
                                if (this.et_main.length() > 0) {
                                    this.tv_Display.setText(this.ab);
                                    if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                        this.f6028Z = this.et_main.getText();
                                        mid_calculation_new();
                                        if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                            this.et_main.append("-");
                                            this.f6026X = "-";
                                            this.f6029n = true;
                                            this.f6027Y = true;
                                        } else {
                                            return;
                                        }
                                    } else if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '+' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '-' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '*' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '/') {
                                        EditText editText2 = this.et_main;
                                        editText2.setText(editText2.getText().toString().substring(0, this.et_main.getText().length() - 1));
                                        this.f6028Z = this.et_main.getText();
                                        mid_calculation_new();
                                        if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                            this.et_main.append("-");
                                            this.f6026X = "-";
                                            this.f6029n = true;
                                            this.f6027Y = true;
                                        } else {
                                            return;
                                        }
                                    }
                                    this.prev = this.et_main.getText().toString();
                                    this.firststr = this.et_main.getText().toString();
                                    return;
                                }
                                Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            return;
                        case R.id.iv_multiply :
                            if (this.et_main.getText().toString().equals("Can't divide by 0")) {
                                return;
                            }
                            if (this.et_main.getText().length() == 0) {
                                Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.e("multiply", this.et_main.getText().toString());
                            if (!this.f6033r.booleanValue()) {
                                this.f6031p = false;
                                this.f6032q = false;
                                if (this.ah.booleanValue()) {
                                    this.et_main.setText(this.ab);
                                    this.ab = this.aa + "";
                                    this.tv_Display.setText(this.ab);
                                    this.ah = false;
                                }
                                this.al = true;
                                this.f6030o = false;
                                if (this.av.booleanValue()) {
                                    this.ac += "*";
                                }
                                if (this.et_main.length() > 0) {
                                    this.tv_Display.setText(this.ab);
                                    if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                        this.f6028Z = this.et_main.getText();
                                        mid_calculation_new();
                                        if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                            this.et_main.append("*");
                                            this.f6026X = "*";
                                            this.f6029n = true;
                                            this.f6027Y = true;
                                        } else {
                                            return;
                                        }
                                    } else if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '+' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '-' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '*' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '/') {
                                        EditText editText3 = this.et_main;
                                        editText3.setText(editText3.getText().toString().substring(0, this.et_main.getText().length() - 1));
                                        this.f6028Z = this.et_main.getText();
                                        mid_calculation_new();
                                        if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                            this.et_main.append("*");
                                            this.f6026X = "*";
                                            this.f6029n = true;
                                            this.f6027Y = true;
                                        } else {
                                            return;
                                        }
                                    }
                                    this.prev = this.et_main.getText().toString();
                                    this.firststr = this.et_main.getText().toString();
                                    return;
                                }
                                Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            return;
                        case R.id.iv_nine :
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("");
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.ah = false;
                            }
                            if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                                if (!this.f6030o.booleanValue()) {
                                    this.ab = "";
                                    this.f6030o = false;
                                }
                                this.aj = false;
                                this.ak = false;
                                this.am = false;
                                this.al = false;
                                this.an = false;
                                this.ao = false;
                                this.aq = false;
                            }
                            if (this.av.booleanValue()) {
                                this.ac += "9";
                            }
                            if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.f6029n = false;
                                this.et_main.append("9");
                                this.ab += "9";
                                this.tv_Display.setText(this.ab);
                            }
                            if (this.f6027Y.booleanValue()) {
                                this.f6025W = this.ab;
                                Log.e("str2", this.f6025W);
                                return;
                            }
                            this.f6024V = this.ab;
                            Log.e("str1", this.f6024V);
                            return;
                        case R.id.iv_one :
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("");
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6031p = false;
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6032q = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.ah = false;
                            }
                            if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                                if (!this.f6030o.booleanValue()) {
                                    this.ab = "";
                                    this.f6030o = false;
                                }
                                this.aj = false;
                                this.ak = false;
                                this.am = false;
                                this.al = false;
                                this.an = false;
                                this.ao = false;
                            }
                            if (this.av.booleanValue()) {
                                this.ac += "1";
                            }
                            if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.et_main.append("1");
                                this.ab += "1";
                                this.tv_Display.setText(this.ab);
                                this.f6029n = false;
                            }
                            if (this.f6027Y.booleanValue()) {
                                this.f6025W = this.ab;
                                Log.e("str2", this.f6025W);
                                return;
                            }
                            this.f6024V = this.ab;
                            Log.e("str1", this.f6024V);
                            return;
                        case R.id.iv_percent :
                            if (!this.f6033r.booleanValue()) {
                                try {
                                    if (this.et_main.length() > 0) {
                                        this.az = true;
                                        try {
                                            this.aa = Double.valueOf(new ExpressionBuilder(this.et_main.getText().toString() + "/100").build().evaluate());
                                            Log.e("result", "" + this.aa);
                                        } catch (ArithmeticException unused2) {
                                        }
                                        this.f6026X = "";
                                        NumberFormat instance = NumberFormat.getInstance();
                                        instance.setMaximumIntegerDigits(20);
                                        instance.setMaximumFractionDigits(20);
                                        instance.setGroupingUsed(false);
                                        String format = instance.format(this.aa);
                                        if (format.length() > 16) {
                                            format = format.substring(0, 16);
                                        } else {
                                            this.tv_Display.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                                        }
                                        this.et_main.setText(format + "");
                                        this.ab = format + "";
                                        this.tv_Display.setText(format + "");
                                        this.f6024V = format;
                                        this.f6025W = "";
                                        this.f6032q = true;
                                        return;
                                    }
                                    Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                                    return;
                                } catch (Exception e) {
                                    Log.e("TAG", "Invalid for percentage" + e.getMessage());
                                    return;
                                }
                            } else {
                                return;
                            }
                        case R.id.iv_plus :
                            if (this.et_main.getText().toString().equals("Can't divide by 0")) {
                                return;
                            }
                            if (this.et_main.getText().length() == 0) {
                                Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.e("plus", this.et_main.getText().toString());
                            if (!this.f6033r.booleanValue()) {
                                this.f6031p = false;
                                this.f6032q = false;
                                if (this.ah.booleanValue()) {
                                    this.et_main.setText(this.ab);
                                    this.ab = this.aa + "";
                                    this.tv_Display.setText(this.ab);
                                    this.ah = false;
                                }
                                this.aj = true;
                                this.f6030o = false;
                                if (this.av.booleanValue()) {
                                    this.ac += "+";
                                }
                                if (this.et_main.length() > 0) {
                                    this.tv_Display.setText(this.ab);
                                    if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                        this.f6028Z = this.et_main.getText();
                                        Log.e("if", "if");
                                        mid_calculation_new();
                                        if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                            this.et_main.append("+");
                                            this.f6026X = "+";
                                            this.f6029n = true;
                                            this.f6027Y = true;
                                        } else {
                                            return;
                                        }
                                    } else if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '+' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '-' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '*' || this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '/') {
                                        EditText editText4 = this.et_main;
                                        editText4.setText(editText4.getText().toString().substring(0, this.et_main.getText().length() - 1));
                                        this.f6028Z = this.et_main.getText();
                                        Log.e("else", "else");
                                        mid_calculation_new();
                                        if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                            this.et_main.append("+");
                                            this.f6026X = "+";
                                            this.f6027Y = true;
                                            if (this.tv_Display.getText().toString().length() == 16) {
                                                this.f6029n = true;
                                            }
                                        } else {
                                            return;
                                        }
                                    }
                                    this.prev = this.et_main.getText().toString();
                                    this.firststr = this.et_main.getText().toString();
                                    return;
                                }
                                Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            return;
                        case R.id.iv_plus_minus :
                            if (!this.et_main.getText().toString().equals("0") && !this.et_main.getText().toString().equals("Invalid Input") && !this.et_main.getText().toString().equals("Can't divide by 0")) {
                                try {
                                    operation();
                                    return;
                                } catch (Exception unused3) {
                                    return;
                                }
                            } else {
                                return;
                            }
                        case R.id.iv_seven :
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("");
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.ah = false;
                            }
                            if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                                if (!this.f6030o.booleanValue()) {
                                    this.ab = "";
                                    this.f6030o = false;
                                }
                                this.aj = false;
                                this.ak = false;
                                this.am = false;
                                this.al = false;
                                this.an = false;
                                this.ao = false;
                                this.aq = false;
                            }
                            if (this.av.booleanValue()) {
                                this.ac += "7";
                            }
                            if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.f6029n = false;
                                this.et_main.append("7");
                                this.ab += "7";
                                this.tv_Display.setText(this.ab);
                            }
                            if (this.f6027Y.booleanValue()) {
                                this.f6025W = this.ab;
                                Log.e("str2", this.f6025W);
                                return;
                            }
                            this.f6024V = this.ab;
                            Log.e("str1", this.f6024V);
                            return;
                        case R.id.iv_six :
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("");
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.ah = false;
                            }
                            if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                                if (!this.f6030o.booleanValue()) {
                                    this.ab = "";
                                    this.f6030o = false;
                                }
                                this.aj = false;
                                this.ak = false;
                                this.am = false;
                                this.al = false;
                                this.an = false;
                                this.ao = false;
                                this.aq = false;
                            }
                            if (this.av.booleanValue()) {
                                this.ac += "6";
                            }
                            if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.et_main.append("6");
                                this.ab += "6";
                                this.tv_Display.setText(this.ab);
                                this.f6029n = false;
                            }
                            if (this.f6027Y.booleanValue()) {
                                this.f6025W = this.ab;
                                Log.e("str2", this.f6025W);
                                return;
                            }
                            this.f6024V = this.ab;
                            Log.e("str1", this.f6024V);
                            return;
                        case R.id.iv_sqrt :
                            if (this.f6033r.booleanValue()) {
                                return;
                            }
                            if (this.et_main.length() > 0) {
                                this.f6026X = "";
                                sqrlEquals();
                                return;
                            }
                            Toast.makeText(this, "Select Number First", Toast.LENGTH_SHORT).show();
                            return;
                        case R.id.iv_square_root :
                            if (this.f6033r.booleanValue()) {
                                return;
                            }
                            if (this.ae == 0) {
                                share_calc.flag_expand = true;
                                this.ae = 1;
                                return;
                            }
                            share_calc.flag_expand = false;
                            this.ae = 0;
                            return;
                        case R.id.iv_three :
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.tv_Display.setText("");
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.ah = false;
                            }
                            if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                                if (!this.f6030o.booleanValue()) {
                                    this.ab = "";
                                    this.f6030o = false;
                                }
                                this.aj = false;
                                this.ak = false;
                                this.am = false;
                                this.al = false;
                                this.an = false;
                                this.ao = false;
                                this.aq = false;
                            }
                            if (this.av.booleanValue()) {
                                this.ac += "3";
                            }
                            if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.et_main.append("3");
                                this.ab += "3";
                                this.tv_Display.setText(this.ab);
                                this.f6029n = false;
                            }
                            if (this.f6027Y.booleanValue()) {
                                this.f6025W = this.ab;
                                Log.e("str2", this.f6025W);
                                return;
                            }
                            this.f6024V = this.ab;
                            Log.e("str1", this.f6024V);
                            return;
                        case R.id.iv_two :
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("");
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.tv_Display.setText("0");
                                this.ah = false;
                            }
                            if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                                if (!this.f6030o.booleanValue()) {
                                    this.ab = "";
                                    this.f6030o = false;
                                }
                                this.aj = false;
                                this.ak = false;
                                this.am = false;
                                this.al = false;
                                this.an = false;
                                this.ao = false;
                            }
                            if (this.av.booleanValue()) {
                                this.ac += "2";
                            }
                            if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.et_main.append("2");
                                this.ab += "2";
                                this.tv_Display.setText(this.ab);
                                this.f6029n = false;
                            }
                            if (this.f6027Y.booleanValue()) {
                                this.f6025W = this.ab;
                                Log.e("str2", this.f6025W);
                                return;
                            }
                            this.f6024V = this.ab;
                            Log.e("str1", this.f6024V);
                            return;
                        case R.id.iv_x_exclamation :
                            if (!this.f6033r.booleanValue() && this.et_main.length() != 0) {
                                this.az = true;
                                Boolean.valueOf(false);
                                Log.e("string", this.ac);
                                if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == ')') {
                                    str2 = this.ac;
                                    bool = true;
                                } else {
                                    str2 = this.et_main.getText().toString();
                                    bool = false;
                                }
                                try {
                                    CalculateFactorial calculateFactorial = new CalculateFactorial();
                                    int[] factorial = calculateFactorial.factorial((int) Double.parseDouble(String.valueOf(new ExtendedDoubleEvaluator().evaluate(str2))));
                                    int res = calculateFactorial.getRes();
                                    if (res > 20) {
                                        int i = res - 1;
                                        String str4 = "";
                                        for (int i2 = i; i2 >= res - 20; i2--) {
                                            if (i2 == res - 2) {
                                                str4 = str4 + ".";
                                            }
                                            str4 = str4 + factorial[i2];
                                        }
                                        str3 = str4 + "E" + i;
                                    } else {
                                        while (res - 1 >= 0) {
                                        }
                                        str3 = "";
                                    }
                                    if (bool.booleanValue()) {
                                        String d = new ExtendedDoubleEvaluator().evaluate(this.et_main.getText().toString().replace(this.ac, str3)).toString();
                                        this.et_main.setText(d);
                                        this.tv_Display.setText(d);
                                        this.ac = "";
                                        this.aw = false;
                                        return;
                                    }
                                    this.et_main.setText(str3);
                                    this.tv_Display.setText(str3);
                                    return;
                                } catch (Exception e2) {
                                    if (e2.toString().contains("ArrayIndexOutOfBoundsException")) {
                                        this.et_main.setText("Result too big!");
                                    } else {
                                        this.et_main.setText("Invalid!!");
                                    }
                                    e2.printStackTrace();
                                    return;
                                }
                            } else {
                                return;
                            }
                        case R.id.iv_zero :
                            if (!this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                if (this.f6031p.booleanValue()) {
                                    this.et_main.setText("");
                                    this.ab = "";
                                    this.f6025W = "";
                                    this.f6024V = "";
                                    this.f6026X = "";
                                    this.tv_Display.setText("0");
                                    this.f6031p = false;
                                }
                                if (this.f6032q.booleanValue()) {
                                    this.et_main.setText("");
                                    this.ab = "";
                                    this.f6025W = "";
                                    this.f6024V = "";
                                    this.f6026X = "";
                                    this.tv_Display.setText("0");
                                    this.f6032q = false;
                                }
                                this.f6033r = false;
                                if (this.ah.booleanValue()) {
                                    this.et_main.setText("");
                                    this.f6025W = "";
                                    this.f6024V = "";
                                    this.f6026X = "";
                                    this.ab = "";
                                    this.tv_Display.setText("0");
                                    this.ah = false;
                                }
                                if (this.aj.booleanValue() || this.ak.booleanValue() || this.am.booleanValue() || this.al.booleanValue() || this.an.booleanValue() || this.ao.booleanValue()) {
                                    if (!this.f6030o.booleanValue()) {
                                        this.ab = "";
                                        this.f6030o = false;
                                    }
                                    this.aj = false;
                                    this.ak = false;
                                    this.am = false;
                                    this.al = false;
                                    this.an = false;
                                    this.ao = false;
                                }
                                if (this.av.booleanValue()) {
                                    this.ac += "0";
                                }
                                if (this.tv_Display.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                    if (this.tv_Display.getText().length() != 1 || !this.tv_Display.getText().toString().equalsIgnoreCase("0") || !this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                        this.et_main.append("0");
                                        this.ab += "0";
                                        this.tv_Display.setText(this.ab);
                                        this.f6029n = false;
                                    } else {
                                        return;
                                    }
                                }
                                if (this.f6027Y.booleanValue()) {
                                    this.f6025W = this.ab;
                                    Log.e("str2", this.f6025W);
                                    return;
                                }
                                this.f6024V = this.ab;
                                Log.e("str2", this.f6024V);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
            }
        } else {
            this.f6033r = false;
            int length = this.et_main.getText().length();
            if (this.et_main.getText().toString().equals("Can't divide by 0")) {
                this.f6029n = false;
                this.et_main.setText("");
                this.tv_Display.setText("0");
                this.ab = "";
                this.f6025W = "";
                this.f6024V = "";
                this.f6026X = "";
                this.f6033r = false;
                this.f6031p = false;
                this.f6032q = false;
                this.ac = "";
                this.prev = "";
                this.firststr = "";
                this.f6026X = "";
                this.aC = false;
            } else if (this.et_main.getText().toString().equals("Invalid Input")) {
                this.f6029n = false;
                this.et_main.setText("");
                this.tv_Display.setText("0");
                this.ab = "";
                this.f6025W = "";
                this.f6024V = "";
                this.f6026X = "";
                this.f6033r = false;
                this.f6031p = false;
                this.f6032q = false;
                this.ac = "";
                this.prev = "";
                this.firststr = "";
                this.f6026X = "";
                this.aC = false;
            } else {
                if (length > 0) {
                    int i3 = length - 1;
                    if (Character.isDigit(this.et_main.getText().charAt(i3)) || this.et_main.getText().toString().substring(i3).equalsIgnoreCase(".") || !Character.isDigit(this.et_main.getText().charAt(length - 2))) {
                        if (this.et_main.getText().charAt(i3) == '+' || this.et_main.getText().charAt(i3) == '-' || this.et_main.getText().charAt(i3) == '*' || this.et_main.getText().charAt(i3) == '/') {
                            Log.e("TAG", "here for back operator called : " + this.et_main.getText().charAt(i3));
                            this.firststr = "";
                        } else {
                            this.firststr = "";
                        }
                        this.et_main.getText().delete(i3, length);
                        if (!this.et_main.getText().toString().equalsIgnoreCase("") && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) == '-' && !Character.isDigit(this.et_main.getText().length() - 1)) {
                            EditText editText5 = this.et_main;
                            editText5.setText(editText5.getText().toString().substring(0, this.et_main.getText().toString().length() - 1));
                        }
                        if (this.ah.booleanValue()) {
                            this.ab = this.et_main.getText().toString();
                        } else {
                            String str5 = this.f6025W;
                            if (str5 != null) {
                                if (str5.equalsIgnoreCase("")) {
                                    this.ab = this.et_main.getText().toString();
                                } else {
                                    String str6 = this.f6025W;
                                    this.f6025W = str6.substring(0, str6.length() - 1);
                                    if (this.f6025W.equalsIgnoreCase("-")) {
                                        this.f6025W = "";
                                    }
                                    this.ab = this.f6025W;
                                }
                            }
                        }
                        this.tv_Display.setText(this.ab);
                        if (this.et_main.getText().toString().length() == 1 && !Character.isDigit(this.et_main.getText().toString().charAt(0))) {
                            this.tv_Display.setText("0");
                            this.et_main.setText("");
                            this.ab = "";
                        }
                    } else {
                        this.f6030o = true;
                        this.f6027Y = false;
                        this.et_main.getText().delete(i3, length);
                        this.ab = this.f6024V;
                        this.tv_Display.setText(this.ab);
                        Log.e("idis2", this.ab);
                        this.f6025W = "";
                    }
                }
                if (this.et_main.getText().length() == 0) {
                    this.et_main.setText("");
                    this.tv_Display.setText("0");
                    this.ab = "";
                }
                if (this.et_main.getText().length() == 0) {
                    this.f6029n = false;
                    this.et_main.setText("");
                    this.tv_Display.setText("0");
                    this.ab = "";
                    this.f6025W = "";
                    this.f6024V = "";
                    this.f6026X = "";
                    this.f6033r = false;
                    this.f6031p = false;
                    this.f6032q = false;
                    this.ac = "";
                    this.prev = "";
                    this.firststr = "";
                    this.f6026X = "";
                    this.aC = false;
                }
                this.aC = false;
            }
        }
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
        this.aB = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.aB = true;
    }


    @Override

    public void onStop() {
        super.onStop();
        this.aB = false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            view.setAlpha(0.2f);
            view.callOnClick();
        } else if (action == 1) {
            view.setAlpha(1.0f);
        }
        return true;
    }

    public void sqrlEquals() {
        this.f6031p = true;
        String obj = this.et_main.getText().toString();
        if (!Character.isDigit(obj.charAt(obj.length() - 1))) {
            obj = obj.substring(0, obj.length() - 1);
        }
        Log.e("first", "" + obj.toString().charAt(0));
        if (Character.isDigit(obj.toString().charAt(0))) {
            Double valueOf = Double.valueOf(Double.parseDouble(new DecimalFormat(".########################################################").format(new ExtendedDoubleEvaluator().evaluate(String.valueOf(obj)))));
            Log.e(SecurityQuestionActivity.ADD, "" + valueOf);
            if (this.tv_Display.length() != 0) {
                this.expressions = "sqrt(" + valueOf + ")";
            }
            this.expressions.length();
            new DoubleEvaluator();
            try {
                this.result = new ExtendedDoubleEvaluator().evaluate(this.expressions);
                NumberFormat instance = NumberFormat.getInstance();
                instance.setMaximumIntegerDigits(50);
                instance.setMaximumFractionDigits(50);
                instance.setGroupingUsed(false);
                String format = instance.format(this.result);
                if (format.equalsIgnoreCase("nan")) {
                    this.tv_Display.setText("0");
                    this.et_main.setText("Invalid Input");
                    this.f6033r = true;
                } else {
                    if (format.length() > 16) {
                        format = format.substring(0, 16);
                    } else {
                        this.tv_Display.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                    }
                    EditText editText = this.tv_Display;
                    editText.setText(format + "");
                    EditText editText2 = this.et_main;
                    editText2.setText("" + format);
                    this.ab = format + "";
                }
                this.f6024V = this.tv_Display.getText().toString();
                this.f6025W = "";
            } catch (Exception e) {
                Log.e("TAG", "Toast invalid expression");
                Toast.makeText(this, "Invalid Expression", Toast.LENGTH_SHORT).show();
                this.et_main.setText("");
                this.tv_Display.setText("0");
                this.ab = "";
                this.ac = "";
                this.prev = "";
                this.firststr = "";
                this.aC = false;
                this.expressions = "";
                e.printStackTrace();
            }
        } else {
            this.tv_Display.setText("0");
            this.et_main.setText("Invalid Input");
            this.f6033r = true;
        }
    }

    private void showSetPasswordDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_set_password_hint);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout(-1, -2);
        }
        ((ImageView) dialog.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void adsstatus(Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final APIInterface apiInterface = retrofit.create(APIInterface.class);
        Call<JsonArray> callallappstatus = apiInterface.GetAppstatus(APPNAME);
        callallappstatus.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {

                Log.e("new",response.body().toString());
                try {
                    JSONArray jsonArray = new JSONArray(response.body().toString());

                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject object = jsonArray.getJSONObject(i);
                        if(object.getString("app_name").equals(APPNAME)){

                            if(object.getString("ads_status").equals("true")){
                                SharedPreferences sharedPreferences = context.getSharedPreferences(DATA, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(AdsString, true);
                                editor.commit();
                                Log.e("if","if");

                            }else {
                                SharedPreferences sharedPreferences = context.getSharedPreferences(DATA, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(AdsString, false);
                                editor.commit();
                                Log.e("else","else");

                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Creating editor to store values to shared preferences


            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Log.e("JSON", t.toString());


            }
        });



    }

}

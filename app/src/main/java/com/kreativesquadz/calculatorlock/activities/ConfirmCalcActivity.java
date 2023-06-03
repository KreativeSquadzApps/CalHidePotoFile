package com.kreativesquadz.calculatorlock.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import androidx.appcompat.app.AppCompatActivity;

import com.kreativesquadz.calculatorlock.R;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.share.Share;
import com.kreativesquadz.calculatorlock.share.share_calc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import net.objecthunter.exp4j.ExpressionBuilder;


public class ConfirmCalcActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    public static String Radian_Degree = "DEG";
    boolean aB;
    Double aa;
    Double ay;
    private AlphaAnimation click_anim;
    EditText et_main;
    Button f6003A;
    Button f6004B;
    Button f6005C;
    Button f6006D;
    ImageView f6007E;
    Button f6008F;
    Button f6009G;
    Button f6010H;
    Button f6011I;
    ImageView f6012J;
    ImageView f6013K;
    ImageView f6014L;
    ImageView f6015M;
    Button f6016N;
    ImageView f6017O;
    EditText f6019Q;
    TextView f6020R;
    LinearLayout f6021S;
    LinearLayout f6022T;
    RelativeLayout f6023U;
    Editable f6028Z;
    int f6034s;
    int f6035t;
    Button f6036u;
    Button f6037v;
    Button f6038w;
    Button f6039x;
    Button f6040y;
    Button f6041z;
    private Double result;
    String f6024V = "";
    String f6025W = "";
    String f6026X = "";
    Boolean f6027Y = false;
    String aA = "";
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
    Boolean ax = false;
    Boolean az = false;
    private String expressions = "";
    private String firststr = "";
    Boolean f6029n = false;
    Boolean f6030o = false;
    Boolean f6031p = false;
    private String prev = "";
    Boolean f6032q = false;
    Boolean f6033r = false;
    private String text = "";

    public ConfirmCalcActivity() {
        Double valueOf = Double.valueOf(0.0d);
        this.ay = valueOf;
        this.result = valueOf;
    }

    @Override

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_confirm_calc);
        initviews();
        initlisteners();
        check_tablet();
        if (MainApplication.getInstance().getPassword().isEmpty()) {
            showConfirmDialog();
        } else {
            this.click_anim = new AlphaAnimation(1.0f, 0.5f);
        }
    }



    public class C16052 implements TextWatcher {
        final ConfirmCalcActivity f6001a;

        @Override
        public void afterTextChanged(Editable editable) {
        }

        C16052(ConfirmCalcActivity confirmCalcActivity) {
            this.f6001a = confirmCalcActivity;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (this.f6001a.f6019Q.getText().toString().equalsIgnoreCase("") || this.f6001a.f6019Q.getText().toString().equalsIgnoreCase("0")) {
                this.f6001a.f6019Q.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!this.f6001a.f6019Q.getText().toString().equalsIgnoreCase("")) {
                this.f6001a.f6019Q.setSelection(this.f6001a.f6019Q.getText().toString().length() - 1);
            }
        }
    }



    public class C16063 implements TextWatcher {
        final ConfirmCalcActivity f6002a;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C16063(ConfirmCalcActivity confirmCalcActivity) {
            this.f6002a = confirmCalcActivity;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!this.f6002a.et_main.getText().toString().equalsIgnoreCase("")) {
                this.f6002a.et_main.setSelection(this.f6002a.et_main.getText().toString().length() - 1);
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!this.f6002a.et_main.getText().toString().equalsIgnoreCase("")) {
                this.f6002a.et_main.setSelection(this.f6002a.et_main.getText().toString().length() - 1);
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
                this.f6019Q.setText("0");
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
                this.f6019Q.setText(this.ab);
                Log.e("dot", "" + charAt);
                if (charAt < '0' || charAt > '9') {
                    Log.e("dot", "else" + charAt);
                    this.et_main.append("* 0.");
                    this.ab += "";
                    this.f6019Q.setText(this.ab);
                    return;
                }
                if (this.av.booleanValue()) {
                    this.ac += ".";
                }
                Log.e("dot", "if" + charAt);
                this.et_main.append(".");
                this.ab += ".";
                this.f6019Q.setText(this.ab);
            }
        }
    }

    private void initlisteners() {
        this.f6040y.setOnClickListener(this);
        this.f6039x.setOnClickListener(this);
        this.f6005C.setOnClickListener(this);
        this.f6010H.setOnClickListener(this);
        this.f6038w.setOnClickListener(this);
        this.f6004B.setOnClickListener(this);
        this.f6009G.setOnClickListener(this);
        this.f6037v.setOnClickListener(this);
        this.f6003A.setOnClickListener(this);
        this.f6008F.setOnClickListener(this);
        this.f6014L.setOnClickListener(this);
        this.f6013K.setOnClickListener(this);
        this.f6012J.setOnClickListener(this);
        this.f6007E.setOnClickListener(this);
        this.f6041z.setOnClickListener(this);
        this.f6036u.setOnClickListener(this);
        this.f6015M.setOnClickListener(this);
        this.f6016N.setOnClickListener(this);
        this.f6021S.setOnClickListener(this);
        this.f6011I.setOnClickListener(this);
        this.f6006D.setOnClickListener(this);
        this.f6040y.setOnTouchListener(this);
        this.f6039x.setOnTouchListener(this);
        this.f6005C.setOnTouchListener(this);
        this.f6010H.setOnTouchListener(this);
        this.f6038w.setOnTouchListener(this);
        this.f6004B.setOnTouchListener(this);
        this.f6009G.setOnTouchListener(this);
        this.f6037v.setOnTouchListener(this);
        this.f6003A.setOnTouchListener(this);
        this.f6008F.setOnTouchListener(this);
        this.f6014L.setOnTouchListener(this);
        this.f6013K.setOnTouchListener(this);
        this.f6012J.setOnTouchListener(this);
        this.f6007E.setOnTouchListener(this);
        this.f6041z.setOnTouchListener(this);
        this.f6036u.setOnTouchListener(this);
        this.f6015M.setOnTouchListener(this);
        this.f6016N.setOnTouchListener(this);
        this.f6021S.setOnTouchListener(this);
        this.f6011I.setOnTouchListener(this);
        this.f6006D.setOnTouchListener(this);
        this.f6019Q.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        this.f6019Q.setSingleLine();
        this.f6019Q.addTextChangedListener(new C16052(this));
        this.et_main.setSingleLine();
        this.f6019Q.setSingleLine();
        this.et_main.addTextChangedListener(new C16063(this));
    }

    private void initviews() {
        this.f6036u = (Button) findViewById(R.id.iv_clear);
        this.f6037v = (Button) findViewById(R.id.iv_seven);
        this.f6038w = (Button) findViewById(R.id.iv_four);
        this.f6039x = (Button) findViewById(R.id.iv_one);
        this.f6040y = (Button) findViewById(R.id.iv_zero);
        this.f6041z = (Button) findViewById(R.id.iv_percent);
        this.f6003A = (Button) findViewById(R.id.iv_eight);
        this.f6004B = (Button) findViewById(R.id.iv_five);
        this.f6005C = (Button) findViewById(R.id.iv_two);
        this.f6006D = (Button) findViewById(R.id.iv_dot);
        this.f6007E = (ImageView) findViewById(R.id.iv_divide);
        this.f6008F = (Button) findViewById(R.id.iv_nine);
        this.f6009G = (Button) findViewById(R.id.iv_six);
        this.f6010H = (Button) findViewById(R.id.iv_three);
        this.f6011I = (Button) findViewById(R.id.iv_plus_minus);
        this.f6012J = (ImageView) findViewById(R.id.iv_multiply);
        this.f6013K = (ImageView) findViewById(R.id.iv_minus);
        this.f6014L = (ImageView) findViewById(R.id.iv_plus);
        this.f6015M = (ImageView) findViewById(R.id.iv_equals);
        this.f6016N = (Button) findViewById(R.id.iv_sqrt);
        this.et_main = (EditText) findViewById(R.id.et_main);
        this.f6019Q = (EditText) findViewById(R.id.tv_Display);
        this.f6020R = (TextView) findViewById(R.id.tv_divide);
        this.f6021S = (LinearLayout) findViewById(R.id.ll_delete);
        this.f6022T = (LinearLayout) findViewById(R.id.ll_calc);
        this.f6023U = (RelativeLayout) findViewById(R.id.rl_calc_layout);
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
                this.f6019Q.setText(valueOf);
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
                    this.f6019Q.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                }
                if (this.ab.equalsIgnoreCase("-0")) {
                    this.ab = "0";
                }
                this.f6019Q.setText(this.ab);
                this.et_main.setText(this.ab);
                this.f6024V = this.ab;
            } catch (Exception e2) {
                Log.e("TAG", "Toast");
                Toast.makeText(this, "Syntax Error", Toast.LENGTH_SHORT).show();
                this.et_main.setText("");
                this.f6019Q.setText("0");
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
            this.f6019Q.setText("0");
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
                if (this.f6019Q.length() > 0) {
                    if (this.f6019Q.getText().charAt(0) == '-') {
                        Log.e("TAG", "if for loop :");
                        for (int i = 1; i < this.f6019Q.length(); i++) {
                            if (this.f6019Q.getText().charAt(i) == '+' || this.f6019Q.getText().charAt(i) == '-' || this.f6019Q.getText().charAt(i) == '*' || this.f6019Q.getText().charAt(i) == '/') {
                                this.aC = false;
                                break;
                            } else {
                                this.aC = true;
                            }
                        }
                    } else {
                        Log.e("TAG", "else for loop :");
                        for (int i2 = 0; i2 < this.f6019Q.length(); i2++) {
                            if (this.f6019Q.getText().charAt(i2) == '+' || this.f6019Q.getText().charAt(i2) == '-' || this.f6019Q.getText().charAt(i2) == '*' || this.f6019Q.getText().charAt(i2) == '/') {
                                this.aC = false;
                                break;
                            } else {
                                this.aC = true;
                            }
                        }
                    }
                }
                String obj = this.f6019Q.getText().toString();
                if (obj.length() > 16) {
                    obj.substring(0, 16);
                } else {
                    this.f6019Q.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                }
                if (this.aC) {
                    if (this.f6019Q.getText().charAt(0) != '-' || this.f6019Q.length() <= 1) {
                        Log.e("TAG", "display : " + this.ab);
                        Log.e("TAG", "display length : " + this.ab.length());
                        EditText editText = this.et_main;
                        editText.setText(this.et_main.getText().toString().substring(0, this.et_main.getText().toString().length() - this.ab.length()) + "-" + this.ab);
                        EditText editText2 = this.f6019Q;
                        editText2.setText("-" + this.ab);
                        this.ar = false;
                        this.ab = this.f6019Q.getText().toString();
                    } else {
                        Log.e("TAG", "oth pos - in display");
                        String obj2 = this.f6019Q.getText().toString();
                        Log.e("TAG", "s before : " + obj2);
                        Log.e("TAG", "s before replacing string : " + this.f6019Q.getText().toString().substring(1));
                        String replace = obj2.replace(this.f6019Q.getText().toString(), this.f6019Q.getText().toString().substring(1));
                        EditText editText3 = this.f6019Q;
                        editText3.setText(editText3.getText().toString().substring(1));
                        if (this.f6025W.equalsIgnoreCase("")) {
                            this.et_main.setText(replace);
                        } else {
                            EditText editText4 = this.et_main;
                            editText4.setText(this.firststr + ((Object) replace));
                        }
                        this.ab = this.f6019Q.getText().toString();
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

    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) == 4 || (context.getResources().getConfiguration().screenLayout & 15) == 3;
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
                case R.id.iv_clear:
                    this.f6029n = false;
                    this.et_main.setText("");
                    this.f6019Q.setText("0");
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
                case R.id.iv_divide:
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
                            this.f6019Q.setText(this.ab);
                            this.ah = false;
                        }
                        this.am = true;
                        this.f6030o = false;
                        if (this.av.booleanValue()) {
                            this.ac += "/";
                        }
                        if (this.et_main.length() > 0) {
                            this.f6019Q.setText(this.ab);
                            if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                this.f6028Z = this.et_main.getText();
                                mid_calculation();
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
                                mid_calculation();
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
                case R.id.iv_dot:
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
                case R.id.iv_eight:
                    this.f6033r = false;
                    if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("");
                    }
                    if (this.f6032q.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
                        this.f6032q = false;
                    }
                    if (this.f6031p.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
                        this.f6031p = false;
                    }
                    if (this.ah.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
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
                    if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                        this.et_main.append("8");
                        this.ab += "8";
                        this.f6019Q.setText(this.ab);
                    }
                    if (this.f6027Y.booleanValue()) {
                        this.f6025W = this.ab;
                        Log.e("str2", this.f6025W);
                        return;
                    }
                    this.f6024V = this.ab;
                    Log.e("str1", this.f6024V);
                    return;
                case R.id.iv_equals:
                    if (MainApplication.getInstance().getPassword().equals("")) {
                        int parseInt = Integer.parseInt(this.et_main.getText().toString());
                        if (parseInt == Share.pass) {
                            MainApplication.getInstance().savePassword(String.valueOf(parseInt));
                            ProgressDialogSuccess();
                            return;
                        }
                        PasswordErrorDialog();
                        return;
                    } else if (!this.f6033r.booleanValue()) {
                        if (this.ah.booleanValue() && !this.f6026X.equals("") && (str = this.f6025W) != null && !str.equalsIgnoreCase("")) {
                            Log.e("str2equal", this.f6025W);
                            this.f6028Z = new SpannableStringBuilder(this.f6019Q.getText().toString() + this.f6026X + this.f6025W);
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
                case R.id.iv_five:
                    this.f6033r = false;
                    if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("");
                    }
                    if (this.f6032q.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
                        this.f6032q = false;
                    }
                    if (this.f6031p.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
                        this.f6031p = false;
                    }
                    if (this.ah.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
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
                    if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                        this.et_main.append("5");
                        this.ab += "5";
                        this.f6019Q.setText(this.ab);
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
                case R.id.iv_four:
                    this.f6033r = false;
                    if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("");
                    }
                    if (this.f6032q.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
                        this.f6032q = false;
                    }
                    if (this.f6031p.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
                        this.f6031p = false;
                    }
                    if (this.ah.booleanValue()) {
                        this.et_main.setText("");
                        this.ab = "";
                        this.f6025W = "";
                        this.f6024V = "";
                        this.f6026X = "";
                        this.f6019Q.setText("0");
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
                    if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                        this.et_main.append("4");
                        this.ab += "4";
                        this.f6019Q.setText(this.ab);
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
                        case R.id.iv_minus:
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
                                    this.f6019Q.setText(this.ab);
                                    this.ah = false;
                                }
                                this.ak = true;
                                this.f6030o = false;
                                if (this.av.booleanValue()) {
                                    this.ac += "-";
                                }
                                if (this.et_main.length() > 0) {
                                    this.f6019Q.setText(this.ab);
                                    if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                        this.f6028Z = this.et_main.getText();
                                        mid_calculation();
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
                                        mid_calculation();
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
                        case R.id.iv_multiply:
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
                                    this.f6019Q.setText(this.ab);
                                    this.ah = false;
                                }
                                this.al = true;
                                this.f6030o = false;
                                if (this.av.booleanValue()) {
                                    this.ac += "*";
                                }
                                if (this.et_main.length() > 0) {
                                    this.f6019Q.setText(this.ab);
                                    if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                        this.f6028Z = this.et_main.getText();
                                        mid_calculation();
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
                                        mid_calculation();
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
                        case R.id.iv_nine:
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("");
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
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
                            if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.f6029n = false;
                                this.et_main.append("9");
                                this.ab += "9";
                                this.f6019Q.setText(this.ab);
                            }
                            if (this.f6027Y.booleanValue()) {
                                this.f6025W = this.ab;
                                Log.e("str2", this.f6025W);
                                return;
                            }
                            this.f6024V = this.ab;
                            Log.e("str1", this.f6024V);
                            return;
                        case R.id.iv_one:
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("");
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6031p = false;
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6032q = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
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
                            if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.et_main.append("1");
                                this.ab += "1";
                                this.f6019Q.setText(this.ab);
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
                        case R.id.iv_percent:
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
                                            this.f6019Q.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                                        }
                                        this.et_main.setText(format + "");
                                        this.ab = format + "";
                                        this.f6019Q.setText(format + "");
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
                        case R.id.iv_plus:
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
                                    this.f6019Q.setText(this.ab);
                                    this.ah = false;
                                }
                                this.aj = true;
                                this.f6030o = false;
                                if (this.av.booleanValue()) {
                                    this.ac += "+";
                                }
                                if (this.et_main.length() > 0) {
                                    this.f6019Q.setText(this.ab);
                                    if (this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '+' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '-' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '/' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '*' && this.et_main.getText().toString().charAt(this.et_main.getText().toString().length() - 1) != '%') {
                                        this.f6028Z = this.et_main.getText();
                                        Log.e("if", "if");
                                        mid_calculation();
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
                                        mid_calculation();
                                        if (!this.et_main.getText().toString().equals("Can't divide by 0")) {
                                            this.et_main.append("+");
                                            this.f6026X = "+";
                                            this.f6027Y = true;
                                            if (this.f6019Q.getText().toString().length() == 16) {
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
                        case R.id.iv_plus_minus:
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
                        case R.id.iv_seven:
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("");
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
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
                            if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.f6029n = false;
                                this.et_main.append("7");
                                this.ab += "7";
                                this.f6019Q.setText(this.ab);
                            }
                            if (this.f6027Y.booleanValue()) {
                                this.f6025W = this.ab;
                                Log.e("str2", this.f6025W);
                                return;
                            }
                            this.f6024V = this.ab;
                            Log.e("str1", this.f6024V);
                            return;
                        case R.id.iv_six:
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("");
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
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
                            if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.et_main.append("6");
                                this.ab += "6";
                                this.f6019Q.setText(this.ab);
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
                        case R.id.iv_sqrt:
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
                        case R.id.iv_square_root:
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
                        case R.id.iv_three:
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.f6019Q.setText("");
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
                                this.f6019Q.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
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
                            if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.et_main.append("3");
                                this.ab += "3";
                                this.f6019Q.setText(this.ab);
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
                        case R.id.iv_two:
                            this.f6033r = false;
                            if (this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("");
                            }
                            if (this.f6032q.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6032q = false;
                            }
                            if (this.f6031p.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
                                this.f6031p = false;
                            }
                            if (this.ah.booleanValue()) {
                                this.et_main.setText("");
                                this.ab = "";
                                this.f6025W = "";
                                this.f6024V = "";
                                this.f6026X = "";
                                this.f6019Q.setText("0");
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
                            if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                this.et_main.append("2");
                                this.ab += "2";
                                this.f6019Q.setText(this.ab);
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
                        case R.id.iv_x_exclamation:
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
                                        this.f6019Q.setText(d);
                                        this.ac = "";
                                        this.aw = false;
                                        return;
                                    }
                                    this.et_main.setText(str3);
                                    this.f6019Q.setText(str3);
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
                        case R.id.iv_zero:
                            if (!this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                if (this.f6031p.booleanValue()) {
                                    this.et_main.setText("");
                                    this.ab = "";
                                    this.f6025W = "";
                                    this.f6024V = "";
                                    this.f6026X = "";
                                    this.f6019Q.setText("0");
                                    this.f6031p = false;
                                }
                                if (this.f6032q.booleanValue()) {
                                    this.et_main.setText("");
                                    this.ab = "";
                                    this.f6025W = "";
                                    this.f6024V = "";
                                    this.f6026X = "";
                                    this.f6019Q.setText("0");
                                    this.f6032q = false;
                                }
                                this.f6033r = false;
                                if (this.ah.booleanValue()) {
                                    this.et_main.setText("");
                                    this.f6025W = "";
                                    this.f6024V = "";
                                    this.f6026X = "";
                                    this.ab = "";
                                    this.f6019Q.setText("0");
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
                                if (this.f6019Q.getText().toString().length() != 16 || this.f6029n.booleanValue()) {
                                    if (this.f6019Q.getText().length() != 1 || !this.f6019Q.getText().toString().equalsIgnoreCase("0") || !this.et_main.getText().toString().equalsIgnoreCase("0")) {
                                        this.et_main.append("0");
                                        this.ab += "0";
                                        this.f6019Q.setText(this.ab);
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
                this.f6019Q.setText("0");
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
                this.f6019Q.setText("0");
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
                        this.f6019Q.setText(this.ab);
                        if (this.et_main.getText().toString().length() == 1 && !Character.isDigit(this.et_main.getText().toString().charAt(0))) {
                            this.f6019Q.setText("0");
                            this.et_main.setText("");
                            this.ab = "";
                        }
                    } else {
                        this.f6030o = true;
                        this.f6027Y = false;
                        this.et_main.getText().delete(i3, length);
                        this.ab = this.f6024V;
                        this.f6019Q.setText(this.ab);
                        Log.e("idis2", this.ab);
                        this.f6025W = "";
                    }
                }
                if (this.et_main.getText().length() == 0) {
                    this.et_main.setText("");
                    this.f6019Q.setText("0");
                    this.ab = "";
                }
                if (this.et_main.getText().length() == 0) {
                    this.f6029n = false;
                    this.et_main.setText("");
                    this.f6019Q.setText("0");
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
            if (this.f6019Q.length() != 0) {
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
                    this.f6019Q.setText("0");
                    this.et_main.setText("Invalid Input");
                    this.f6033r = true;
                } else {
                    if (format.length() > 16) {
                        format = format.substring(0, 16);
                    } else {
                        this.f6019Q.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                    }
                    EditText editText = this.f6019Q;
                    editText.setText(format + "");
                    EditText editText2 = this.et_main;
                    editText2.setText("" + format);
                    this.ab = format + "";
                }
                this.f6024V = this.f6019Q.getText().toString();
                this.f6025W = "";
            } catch (Exception e) {
                Log.e("TAG", "Toast invalid expression");
                Toast.makeText(this, "Invalid Expression", Toast.LENGTH_SHORT).show();
                this.et_main.setText("");
                this.f6019Q.setText("0");
                this.ab = "";
                this.ac = "";
                this.prev = "";
                this.firststr = "";
                this.aC = false;
                this.expressions = "";
                e.printStackTrace();
            }
        } else {
            this.f6019Q.setText("0");
            this.et_main.setText("Invalid Input");
            this.f6033r = true;
        }
    }

    private void showConfirmDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_confirm_set_password);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout(-1, -2);
        }
        TextView textView = (TextView) dialog.findViewById(R.id.txt);
        ((ImageView) dialog.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
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

    public void ProgressDialogSuccess() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_successfully_set_password);
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
                String securityQuestion = MainApplication.getInstance().getSecurityQuestion();
                MainApplication.getInstance().getEmail();
                if (TextUtils.isEmpty(securityQuestion)) {
                    ConfirmCalcActivity confirmCalcActivity = ConfirmCalcActivity.this;
                    confirmCalcActivity.startActivity(new Intent(confirmCalcActivity, SecurityQuestionActivity.class).putExtra(SecurityQuestionActivity.TYPE, SecurityQuestionActivity.ADD));
                    ConfirmCalcActivity.this.finish();
                    return;
                }
                ConfirmCalcActivity confirmCalcActivity2 = ConfirmCalcActivity.this;
                confirmCalcActivity2.startActivity(new Intent(confirmCalcActivity2, HomeActivity.class));
                ConfirmCalcActivity.this.finish();
            }
        });
        dialog.show();
    }

    public void PasswordErrorDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_set_password_failed);
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
}

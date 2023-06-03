package com.kreativesquadz.calculatorlock.activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.internal.view.SupportMenu;

import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;


public class ChangePasswordActivity extends BaseActivity {
    LinearLayout LLMain;
    EditText PassTextString;
    EditText Passtxt1;
    EditText Passtxt2;
    EditText Passtxt3;
    EditText Passtxt4;
    EditText edtFormula;
    ImageView imgEquals;
    LinearLayout lnMore;
    int mSelectedColor;
    EditText txtCalc;
    TextView txtDel;
    ImageView txtDevide;
    TextView txtDot;
    Button txtEight;
    Button txtFive;
    Button txtFour;
    ImageView txtMinus;
    ImageView txtMultiply;
    Button txtNine;
    Button txtOne;
    TextView txtPersent;
    ImageView txtPlus;
    TextView txtPlusMinus;
    Button txtSeven;
    Button txtSix;
    Button txtThree;
    Button txtTwo;
    Button txtZero;
    String Code = "";
    String ConfPassword = "";
    String Password = "";
    private boolean hasChanged = false;
    private double num = 0.0d;
    private int operator = 1;
    private boolean readyToClear = false;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);
        findViews();
        ButtonControls();
        reset();
    }

    private void findViews() {
        this.LLMain = (LinearLayout) findViewById(R.id.LLMain);
        this.PassTextString = (EditText) findViewById(R.id.PassTextString);
        this.Passtxt1 = (EditText) findViewById(R.id.Passtxt1);
        this.Passtxt2 = (EditText) findViewById(R.id.Passtxt2);
        this.Passtxt3 = (EditText) findViewById(R.id.Passtxt3);
        this.Passtxt4 = (EditText) findViewById(R.id.Passtxt4);
        this.edtFormula = (EditText) findViewById(R.id.formula);
        this.imgEquals = (ImageView) findViewById(R.id.img_equals);
        this.lnMore = (LinearLayout) findViewById(R.id.more);
        this.txtCalc = (EditText) findViewById(R.id.result);
        this.txtDel = (TextView) findViewById(R.id.txt_del);
        this.txtDevide = (ImageView) findViewById(R.id.txtDevide);
        this.txtDot = (TextView) findViewById(R.id.txt_dot);
        this.txtEight = (Button) findViewById(R.id.txt_eight);
        this.txtFive = (Button) findViewById(R.id.txt_five);
        this.txtFour = (Button) findViewById(R.id.txt_four);
        this.txtMinus = (ImageView) findViewById(R.id.txtMinus);
        this.txtMultiply = (ImageView) findViewById(R.id.txtMultiply);
        this.txtNine = (Button) findViewById(R.id.txt_nine);
        this.txtOne = (Button) findViewById(R.id.txt_one);
        this.txtPersent = (TextView) findViewById(R.id.txt_persent);
        this.txtPlus = (ImageView) findViewById(R.id.txtPlus);
        this.txtPlusMinus = (TextView) findViewById(R.id.txt_plus_minus);
        this.txtSeven = (Button) findViewById(R.id.txt_seven);
        this.txtSix = (Button) findViewById(R.id.txt_six);
        this.txtThree = (Button) findViewById(R.id.txt_three);
        this.txtTwo = (Button) findViewById(R.id.txt_two);
        this.txtZero = (Button) findViewById(R.id.txt_zero);
    }

    public void ButtonControls() {
        handleEventsForControl();
        if (MainApplication.getInstance().getPassword().equals("") || MainApplication.getInstance().getPassword().equals(" ")) {
            this.LLMain.setVisibility(View.VISIBLE);
            this.PassTextString.setVisibility(View.VISIBLE);
            this.txtCalc.setVisibility(View.INVISIBLE);
            this.PassTextString.setText("Please Enter your current password.");
            return;
        }
        this.LLMain.setVisibility(View.VISIBLE);
        this.PassTextString.setVisibility(View.VISIBLE);
        this.txtCalc.setVisibility(View.INVISIBLE);
        this.PassTextString.setText("Please Enter your current password.");
    }

    public void handleEventsForControl() {
        this.txtZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(0);
            }
        });
        this.txtOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(1);
            }
        });
        this.txtTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(2);
            }
        });
        this.txtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(3);
            }
        });
        this.txtFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(4);
            }
        });
        this.txtFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(5);
            }
        });
        this.txtSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(6);
            }
        });
        this.txtSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(7);
            }
        });
        this.txtNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(9);
            }
        });
        this.txtEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleNumber(8);
            }
        });
        this.imgEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.this.handleEquals(0);
            }
        });
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
                ChangePasswordActivity changePasswordActivity = ChangePasswordActivity.this;
                changePasswordActivity.Password = "";
                changePasswordActivity.ConfPassword = "";
            }
        });
        ((TextView) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ChangePasswordActivity.this.PassTextString.setText("Please Enter your New Password.");
                ChangePasswordActivity changePasswordActivity = ChangePasswordActivity.this;
                changePasswordActivity.Code = "";
                changePasswordActivity.Password = "";
                changePasswordActivity.ConfPassword = "";
                changePasswordActivity.Passtxt1.setText("");
                ChangePasswordActivity.this.Passtxt2.setText("");
                ChangePasswordActivity.this.Passtxt3.setText("");
                ChangePasswordActivity.this.Passtxt4.setText("");
            }
        });
        dialog.show();
    }

    public void CurrentPasswordErrorDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_current_pass_wrong);
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
                ChangePasswordActivity changePasswordActivity = ChangePasswordActivity.this;
                changePasswordActivity.Password = "";
                changePasswordActivity.ConfPassword = "";
            }
        });
        ((TextView) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ChangePasswordActivity changePasswordActivity = ChangePasswordActivity.this;
                changePasswordActivity.Code = "";
                changePasswordActivity.Password = "";
                changePasswordActivity.ConfPassword = "";
                changePasswordActivity.Passtxt1.setText("");
                ChangePasswordActivity.this.Passtxt2.setText("");
                ChangePasswordActivity.this.Passtxt3.setText("");
                ChangePasswordActivity.this.Passtxt4.setText("");
            }
        });
        dialog.show();
    }

    public void showConfirmDialog(final String str) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_confirm_set_password);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout(-1, -2);
        }
        SpannableString spannableString = new SpannableString(this.Code);
        spannableString.setSpan(new ForegroundColorSpan((int) SupportMenu.CATEGORY_MASK), 0, spannableString.length(), 0);
        ((TextView) dialog.findViewById(R.id.txt)).setText("You have enter the following numbers as your Passcode.\n" + ((Object) spannableString));
        ((TextView) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ChangePasswordActivity.this.PassTextString.setText("Please Enter your Confirm Password.");
                ChangePasswordActivity changePasswordActivity = ChangePasswordActivity.this;
                changePasswordActivity.Password = str;
                changePasswordActivity.Code = "";
                changePasswordActivity.Passtxt1.setText("");
                ChangePasswordActivity.this.Passtxt2.setText("");
                ChangePasswordActivity.this.Passtxt3.setText("");
                ChangePasswordActivity.this.Passtxt4.setText("");
                ChangePasswordActivity.this.reset();
            }
        });
        dialog.show();
    }

    public void samePassNotAllowedDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_choose_other_pass);
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
        ((TextView) dialog.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ChangePasswordActivity.this.reset();
                ChangePasswordActivity changePasswordActivity = ChangePasswordActivity.this;
                changePasswordActivity.Code = "";
                changePasswordActivity.Passtxt1.setText("");
                ChangePasswordActivity.this.Passtxt2.setText("");
                ChangePasswordActivity.this.Passtxt3.setText("");
                ChangePasswordActivity.this.Passtxt4.setText("");
            }
        });
        ((TextView) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ChangePasswordActivity changePasswordActivity = ChangePasswordActivity.this;
                changePasswordActivity.Code = "";
                changePasswordActivity.Passtxt1.setText("");
                ChangePasswordActivity.this.Passtxt2.setText("");
                ChangePasswordActivity.this.Passtxt3.setText("");
                ChangePasswordActivity.this.Passtxt4.setText("");
            }
        });
        dialog.show();
    }


    public void handleEquals(int i) {
        String str = this.Code;
        this.txtCalc.setText(str);
        if (str.length() >= 4 && str.length() <= 12) {
            if (this.Password.equals("")) {
                showConfirmDialog(str);
            } else if (this.Password.equals(str)) {
                this.Password = str;
                MainApplication.getInstance().savePassword(this.Password);
                ProgressChangeSuccess();
            } else {
                PasswordErrorDialog();
            }
        }
        this.operator = i;
    }

    public void ProgressChangeSuccess() {
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
                ChangePasswordActivity.this.reset();
                ChangePasswordActivity.this.finish();
            }
        });
        ((TextView) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ChangePasswordActivity.this.reset();
                ChangePasswordActivity.this.finish();
            }
        });
        dialog.show();
    }


    public void handleNumber(int i) {
        EnterText("" + i);
        this.hasChanged = true;
    }

    public void EnterText(String str) {
        if (this.Code.length() != 4) {
            if (this.Passtxt1.getText().toString().equals(" ") || this.Passtxt1.getText().toString().equals("")) {
                this.Passtxt1.setText(str);
                this.Code += str;
            } else if (this.Passtxt2.getText().toString().equals(" ") || this.Passtxt2.getText().toString().equals("")) {
                this.Passtxt2.setText(str);
                this.Code += str;
            } else if (this.Passtxt3.getText().toString().equals(" ") || this.Passtxt3.getText().toString().equals("")) {
                this.Passtxt3.setText(str);
                this.Code += str;
            } else if (this.Passtxt4.getText().toString().equals(" ") || this.Passtxt4.getText().toString().equals("")) {
                this.Passtxt4.setText(str);
                this.Code += str;
                if (MainApplication.getInstance().getPassword().equals(this.Code)) {
                    if (this.PassTextString.getText().toString().equals("Please Enter your current password.")) {
                        this.Code = "";
                        this.Password = "";
                        this.ConfPassword = "";
                        this.Passtxt1.setText("");
                        this.Passtxt2.setText("");
                        this.Passtxt3.setText("");
                        this.Passtxt4.setText("");
                        this.PassTextString.setText("Please Enter your New Password.");
                    } else if (this.PassTextString.getText().toString().equals("Please Enter your New Password.")) {
                        if (MainApplication.getInstance().getPassword().equals(this.Code)) {
                            samePassNotAllowedDialog();
                        } else {
                            showConfirmDialog(this.Code);
                        }
                    } else if (this.PassTextString.getText().toString().equals("Please Enter your Confirm Password.")) {
                        if (this.Password.equals(this.Code)) {
                            this.Password = this.Code;
                            MainApplication.getInstance().savePassword(this.Password);
                            ProgressChangeSuccess();
                            return;
                        }
                        PasswordErrorDialog();
                    }
                } else if (this.PassTextString.getText().toString().equals("Please Enter your current password.")) {
                    CurrentPasswordErrorDialog();
                }
            }
        }
    }


    public void reset() {
        this.num = 0.0d;
        this.txtCalc.setText("0");
        this.edtFormula.setText("");
        this.txtCalc.setSelection(1);
        this.operator = 1;
    }
}

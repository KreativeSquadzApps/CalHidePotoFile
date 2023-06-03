package com.kreativesquadz.calculatorlock.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.album.HomeFragment;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.privacy.PrivacyPolicyActivity;

import java.util.Random;


public class HomeActivity extends BaseActivity {
    ImageView imgHint;
    RecyclerView.Adapter iv_recycler_Adapter;
    ActivityManager manager;
    RecyclerView recyclerview;
    private Drawable[] screenIcons;
    private String[] screenTitles;
    Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    String TAG = "TAG";
    int row_index = 0;
    int posofItem = 0;

    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_layout);
        setHeaderInfo();
        findViews();
        initViews();
    }

    private void findViews() {
        this.imgHint = (ImageView) findViewById(R.id.hint);
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initViews() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.screenIcons = loadScreenIcons();
        this.screenTitles = loadScreenTitles();
        this.recyclerview.setNestedScrollingEnabled(false);
        this.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        this.iv_recycler_Adapter = new RecyclerViewAdapter(this, this.screenTitles, this.screenIcons, this.posofItem);
        this.recyclerview.setAdapter(this.iv_recycler_Adapter);
        addFirstFragment();
        this.imgHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.showForgotPassHintDialog();
            }
        });
    }

    private void addFirstFragment() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.add(R.id.frame, new HomeFragment());
        beginTransaction.commit();
    }

    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Result", "" + new Random().nextInt(5));
    }


    public void showForgotPassHintDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_forgot_pass_hint);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout(-1, -2);
        }
        final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.chk);
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
                if (checkBox.isChecked()) {
                    MainApplication.getInstance().setShowFPHint(true);
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            startActivity(new Intent(this, CalcActivity.class));
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void showRateUsDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_rate_us);
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
                HomeActivity.this.finishAffinity();
                System.exit(0);
                HomeActivity.this.finish();
            }
        });
        ((TextView) dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String packageName = HomeActivity.this.getPackageName();
                try {
                    HomeActivity homeActivity = HomeActivity.this;
                    homeActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
                } catch (Exception unused) {
                    HomeActivity homeActivity2 = HomeActivity.this;
                    homeActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
        });
        dialog.show();
    }



    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        Context context1;
        private Drawable[] screenIcons;
        private String[] screenTitles;

        public RecyclerViewAdapter(Context context, String[] strArr, Drawable[] drawableArr, int i) {
            this.screenTitles = strArr;
            this.screenIcons = drawableArr;
            this.context1 = context;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView icon;
            public LinearLayout layoutItem;
            public TextView title;
            public TextView txtCoin;

            public ViewHolder(View view) {
                super(view);
                this.icon = (ImageView) view.findViewById(R.id.icon);
                this.title = (TextView) view.findViewById(R.id.title);
                this.layoutItem = (LinearLayout) view.findViewById(R.id.layoutItem);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(this.context1).inflate(R.layout.item_option, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            viewHolder.icon.setImageDrawable(this.screenIcons[i]);
            viewHolder.title.setText(this.screenTitles[i]);
            viewHolder.layoutItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    HomeActivity.this.row_index = i;
                    RecyclerViewAdapter.this.notifyDataSetChanged();
                    int i2 = i;
                    if (i2 == 0) {
                        if (new Random().nextInt(5) + 0 != 0) {
                            HomeActivity.this.startActivity(new Intent(HomeActivity.this, ChangePasswordActivity.class));
                        } else {
                            HomeActivity.this.startActivity(new Intent(HomeActivity.this, ChangePasswordActivity.class));
                        }
                    } else if (i2 == 1) {
                        if (new Random().nextInt(5) + 0 != 0) {
                            HomeActivity.this.startActivity(new Intent(HomeActivity.this, SecurityQuestionActivity.class).putExtra(SecurityQuestionActivity.TYPE, SecurityQuestionActivity.CHANGE));
                        } else {
                            HomeActivity.this.startActivity(new Intent(HomeActivity.this, SecurityQuestionActivity.class).putExtra(SecurityQuestionActivity.TYPE, SecurityQuestionActivity.CHANGE));
                        }
                    } else if (i2 == 2) {
                        HomeActivity.this.share();
                    } else if (i2 == 3) {
                        HomeActivity.this.rate();
                    } else if (i2 == 4) {
                        if (new Random().nextInt(5) + 0 != 0) {
                            HomeActivity.this.startActivity(new Intent(HomeActivity.this, PrivacyPolicyActivity.class));
                        } else {
                            HomeActivity.this.startActivity(new Intent(HomeActivity.this, PrivacyPolicyActivity.class));
                        }
                    }
                    ((DrawerLayout) HomeActivity.this.findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.screenTitles.length;
        }
    }


    public void rate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to rate this App?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                try {
                    HomeActivity homeActivity = HomeActivity.this;
                    homeActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + HomeActivity.this.getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    HomeActivity homeActivity2 = HomeActivity.this;
                    homeActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + HomeActivity.this.getPackageName())));
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }


    public void share() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", "Calculator Vault\n\nLet me recommend you this application\n\nhttps://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n");
            startActivity(Intent.createChooser(intent, "choose one"));
        } catch (Exception unused) {
        }
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] drawableArr = new Drawable[obtainTypedArray.length()];
        for (int i = 0; i < obtainTypedArray.length(); i++) {
            int resourceId = obtainTypedArray.getResourceId(i, 0);
            if (resourceId != 0) {
                drawableArr[i] = ContextCompat.getDrawable(this, resourceId);
            }
        }
        obtainTypedArray.recycle();
        return drawableArr;
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
        Log.e(this.TAG, "onDestroy: ");
    }


    @Override

    public void onStop() {
        super.onStop();
        Log.e(this.TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(this.TAG, "onPause: ");
    }
}

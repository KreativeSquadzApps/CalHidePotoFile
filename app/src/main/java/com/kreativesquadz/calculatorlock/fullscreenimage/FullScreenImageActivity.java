package com.kreativesquadz.calculatorlock.fullscreenimage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.fullscreenimage.adapter.FullScreenImageAdapter;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.utils.CustomViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;


public class FullScreenImageActivity extends BaseActivity {
    private static final String FILE_RENAMED = "file_renamed";
    public static final String OBJECT = "object";
    public static final String POSITION = "position";
    int High;
    int Low = 1;
    FullScreenImageAdapter adapter;
    ArrayList<AllImagesModel> imageList;
    private boolean isFileDeleted;
    private boolean isFileRenamed;
    private boolean isImageSavedAfterEditing;
    private int position;
    Toolbar toolbar;
    CustomViewPager viewPager;


    class C05851 implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int i) {
        }

        @Override
        public void onPageScrolled(int i, float f, int i2) {
        }

        C05851() {
        }

        @Override
        public void onPageSelected(int i) {
            if (FullScreenImageActivity.this.imageList != null && FullScreenImageActivity.this.imageList.size() > 0) {
                FullScreenImageActivity.this.getSupportActionBar().setTitle(new File(FullScreenImageActivity.this.imageList.get(i).getImagePath()).getName());
                if (FullScreenImageActivity.this.High != 0) {
                    int nextInt = new Random().nextInt(FullScreenImageActivity.this.High - FullScreenImageActivity.this.Low) + FullScreenImageActivity.this.Low;
                    Log.e("random number", "" + nextInt);
                    if (nextInt % 9 != 0) {
                        int i2 = nextInt % 11;
                    }
                }
            }
        }
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_full_screen_image);


        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);


        if (getIntent().getExtras() != null) {
            this.imageList = getIntent().getParcelableArrayListExtra(OBJECT);
            this.position = getIntent().getIntExtra(POSITION, 0);
            this.adapter = new FullScreenImageAdapter(this);
            this.viewPager.setAdapter(this.adapter);
            ArrayList<AllImagesModel> arrayList = this.imageList;
            if (arrayList != null) {
                this.High = arrayList.size();
                this.adapter.addItems(this.imageList);
                this.viewPager.setCurrentItem(this.position);
                setHeaderInfo();
            }
        }
        this.viewPager.addOnPageChangeListener(new C05851());
    }

    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        if (this.imageList != null) {
            getSupportActionBar().setTitle(new File(this.imageList.get(this.viewPager.getCurrentItem()).getImagePath()).getName());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
    }

    public void showHideUI() {
        if (getSupportActionBar().isShowing()) {
            this.toolbar.animate().translationY((float) (-this.toolbar.getHeight())).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            getSupportActionBar().hide();
            return;
        }
        this.toolbar.animate().translationY(0.0f).setInterpolator(new DecelerateInterpolator(2.0f)).start();
        getSupportActionBar().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.full_screen_image_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    @SuppressLint({"WrongConstant"})
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        ArrayList<AllImagesModel> arrayList;
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        } else if (menuItem.getItemId() == R.id.itm_share) {
            ArrayList<AllImagesModel> arrayList2 = this.imageList;
            if (arrayList2 != null && arrayList2.size() > 0) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                Uri data = FileProvider.getUriForFile(FullScreenImageActivity.this, getPackageName() + ".provider", new File(this.imageList.get(this.viewPager.getCurrentItem()).getImagePath()));
                intent.putExtra("android.intent.extra.STREAM", data);
                intent.setType("image/*");
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "No Application found to perform this action.", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (menuItem.getItemId() == R.id.itm_delete && (arrayList = this.imageList) != null && arrayList.size() > 0) {
            final AlertDialog create = new AlertDialog.Builder(this).create();
            create.setTitle("Alert");
            create.setMessage("Are you sure to delete this files?");
            create.setCancelable(false);
            create.setButton(-1, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    create.dismiss();
                    FullScreenImageActivity.this.adapter.removeItem(FullScreenImageActivity.this.imageList.get(FullScreenImageActivity.this.viewPager.getCurrentItem()));
                    FullScreenImageActivity.this.isFileDeleted = true;
                }
            });
            create.setButton(-2, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    create.dismiss();
                }
            });
            create.show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

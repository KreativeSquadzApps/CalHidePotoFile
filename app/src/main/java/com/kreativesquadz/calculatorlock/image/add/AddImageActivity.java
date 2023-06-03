package com.kreativesquadz.calculatorlock.image.add;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.AdAdmob;
import com.kreativesquadz.calculatorlock.activities.ImageFolderActivity;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.callbacks.OnAllImagesLoadedListener;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.image.adapter.ImagesAdapter;
import com.kreativesquadz.calculatorlock.image.add.adapter.AllImageAdapter;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class AddImageActivity extends BaseActivity implements OnAllImagesLoadedListener {
    private LinearLayout adView;
    AllImageAdapter adapter;

    private int count;
    DBHelper dbHelper;
    private String destPath;
    private Dialog dialog;
    private Timer f17t;
    private String folderName;
    ImagesAdapter imagesAdapter;
    int int_position;
    private boolean isAllSelected;
    private boolean isImageAddedToNewAlbum;
    private MenuItem itemSelectAll;
    private LinearLayout nativeAdContainer;
    private int progress;
    private ProgressDialog progressDialog;
    private ProgressBar progressbar;
    Button btnHide;
    RecyclerView recyclerview;
    CenterTitleToolbar toolbar;
    private TextView txtCount;
    TextView txtError;
    ViewAnimator viewanimator;
    private boolean isFileCopied = true;
    public ArrayList<AllImagesModel> al_ImageForPass = new ArrayList<>();

    private void runOnUiThread() {
    }



    public class C06092 implements Runnable {
        C06092() {
        }

        @Override
        public void run() {
            AddImageActivity.this.progressbar.setProgress(AddImageActivity.this.progress);
        }
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_adds_image);
        ButterKnife.bind(this);
        this.dbHelper = new DBHelper(this);
        btnHide = findViewById(R.id.btn_hide);
        recyclerview = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        txtError = findViewById(R.id.txt_error);
        viewanimator = findViewById(R.id.viewanimator);
        setHeaderInfo();
        Init();

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd( this);
        adAdmob.FullscreenAd(this);




        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllImageAdapter allImageAdapter = adapter;
                if (allImageAdapter != null) {
                    final List<String> selectedImages = allImageAdapter.getSelectedImages();
                    if (selectedImages == null || selectedImages.size() <= 0) {
                        Toast.makeText(AddImageActivity.this, "Please select at least one image!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!isFinishing()) {
                        showProgressDialog(selectedImages);
                    }
                    f17t = new Timer();
                    f17t.scheduleAtFixedRate(new TimerTask() {



                        class C06061 implements Runnable {
                            C06061() {
                            }

                            @Override
                            public void run() {
                                AddImageActivity.this.publishProgress(selectedImages.size());
                            }
                        }



                        class C06072 implements Runnable {
                            C06072() {
                            }

                            @Override
                            public void run() {
                                AddImageActivity.this.hideProgressDialog();
                                AddImageActivity.this.setBackData();
                            }
                        }

                        @Override
                        public void run() {
                            if (AddImageActivity.this.count == selectedImages.size()) {
                                AddImageActivity.this.f17t.cancel();
                                AddImageActivity.this.isImageAddedToNewAlbum = true;
                                AddImageActivity.this.runOnUiThread(new C06072());

                            } else if (AddImageActivity.this.isFileCopied) {
                                AddImageActivity.this.runOnUiThread(new C06061());
                                AddImageActivity.this.isFileCopied = false;
                                File file = new File((String) selectedImages.get(AddImageActivity.this.count));
                                AddImageActivity addImageActivity = AddImageActivity.this;
                                addImageActivity.moveFile(file, new File(addImageActivity.destPath, file.getName()));
                                AddImageActivity.this.count++;
                                AddImageActivity.this.isImageAddedToNewAlbum = true;
                            }
                        }
                    }, 0, 200);
                }
            }
        });
    }


    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(getString(R.string.add_image));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_close_2));
        }

    }

    private void Init() {
        File file = new File(AppConstants.IMAGE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.int_position = getIntent().getIntExtra("value", 0);
        this.folderName = getIntent().getStringExtra("folderName");
        Log.e("folderName-->>", this.folderName);
        this.destPath = file.getAbsolutePath();
        this.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        new ArrayList();
        ArrayList<String> al_Imagepath = ImageFolderActivity.al_Image.get(this.int_position).getAl_Imagepath();
        for (int i = 0; i < al_Imagepath.size(); i++) {
            AllImagesModel allImagesModel = new AllImagesModel();
            allImagesModel.setImagePath(al_Imagepath.get(i).toString());
            this.al_ImageForPass.add(allImagesModel);
        }
        this.adapter = new AllImageAdapter(this, this.al_ImageForPass, this.int_position);
        this.recyclerview.setAdapter(this.adapter);
        GetAllImagesAsyncTask getAllImagesAsyncTask = new GetAllImagesAsyncTask();
        getAllImagesAsyncTask.onAllImagesLoadedListener = this;
        getAllImagesAsyncTask.execute(new Void[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_image_menu, menu);
        this.itemSelectAll = menu.findItem(R.id.action_select_all);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
        } else if (itemId == R.id.action_select_all) {
            if (!this.isAllSelected) {
                AllImageAdapter allImageAdapter = this.adapter;
                if (allImageAdapter != null) {
                    allImageAdapter.selectAllItem();
                }
                this.isAllSelected = true;
                menuItem.setIcon(R.drawable.ic_check_filled);
                showHideButton(true);
            } else {
                AllImageAdapter allImageAdapter2 = this.adapter;
                if (allImageAdapter2 != null) {
                    allImageAdapter2.deSelectAllItem();
                }
                this.isAllSelected = false;
                menuItem.setIcon(R.drawable.ic_check_box_outline);
                showHideButton(false);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void showHideButton(boolean z) {
        this.btnHide.setVisibility(z ? View.VISIBLE : View.GONE);
    }

    public void setSelectAll(boolean z) {
        MenuItem menuItem = this.itemSelectAll;
        if (menuItem != null) {
            if (z) {
                menuItem.setIcon(R.drawable.ic_check_filled);
            } else {
                menuItem.setIcon(R.drawable.ic_check_box_outline);
            }
        }
    }

    @Override
    public void onAllImagesLoaded(ArrayList<AllImagesModel> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            this.viewanimator.setDisplayedChild(2);
        } else {
            this.viewanimator.setDisplayedChild(1);
        }
    }



    public void setBackData() {
        if (!this.isImageAddedToNewAlbum) {
            File file = new File(this.destPath);
            if (file.exists()) {
                file.delete();
            }
        }
        Intent intent = new Intent();
        intent.putExtra(AppConstants.HIDDEN_RESULT, this.isImageAddedToNewAlbum);
        setResult(-1, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showProgressDialog(List<String> list) {
        this.dialog = new Dialog(this);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.dialog_progress);
        int i = 0;
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        if (this.dialog.getWindow() != null) {
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.dialog.getWindow().setLayout(-1, -2);
        }
        this.progressbar = (ProgressBar) this.dialog.findViewById(R.id.progress_bar);
        this.txtCount = (TextView) this.dialog.findViewById(R.id.txt_count);
        this.nativeAdContainer = (LinearLayout) this.dialog.findViewById(R.id.native_ad_container);
        ((TextView) this.dialog.findViewById(R.id.txt_title)).setText("Moving Image(s)");
        TextView textView = this.txtCount;
        textView.setText("Moving 1 of " + list.size());
        for (String str : list) {
            i += (int) new File(str).length();
        }
        this.progressbar.setMax(i);
        this.dialog.show();
    }


    public void hideProgressDialog() {
        Dialog dialog = this.dialog;
        if (dialog != null && dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }


    public void publishProgress(int i) {
        Dialog dialog = this.dialog;
        if (dialog != null && dialog.isShowing()) {
            TextView textView = this.txtCount;
            textView.setText("Moving " + (this.count + 1) + " of " + i);
        }
    }


    public void moveFile(final File file, final File file2) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                    this.progress += read;
                    runOnUiThread(new C06092());
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddImageActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddImageActivity.this.deleteFilePath(file);
                        }
                    });
                    this.isFileCopied = true;
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.isFileCopied = true;
        }
    }


    @SuppressLint({"WrongConstant"})
    public void deleteFilePath(File file) {
        try {
            String[] strArr = {file.getAbsolutePath()};
            ContentResolver contentResolver = getContentResolver();
            Uri contentUri = MediaStore.Files.getContentUri("external");
            contentResolver.delete(contentUri, "_data=?", strArr);
            if (file.exists()) {
                contentResolver.delete(contentUri, "_data=?", strArr);
                file.delete();
            }
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override

    public void onStop() {
        super.onStop();
        Timer timer = this.f17t;
        if (timer != null) {
            timer.cancel();
        }
        hideProgressDialog();
    }
}

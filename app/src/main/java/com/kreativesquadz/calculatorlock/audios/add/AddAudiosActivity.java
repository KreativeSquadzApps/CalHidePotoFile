package com.kreativesquadz.calculatorlock.audios.add;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.AdAdmob;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.audios.add.adapter.AllAudiosAdapter;
import com.kreativesquadz.calculatorlock.callbacks.OnAllAudiosLoadedListener;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.model.AllAudioModel;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class AddAudiosActivity extends BaseActivity implements OnAllAudiosLoadedListener {
    private LinearLayout adView;
    AllAudiosAdapter adapter;
    LinearLayout bannerContainer;
    Button btnHide;
    private int count;
    DBHelper dbHelper;
    private String destPath;
    private Dialog dialog;
    private Timer f12t;
    private boolean isAllSelected;
    private boolean isFileCopied = true;
    private boolean isImageAddedToNewAlbum;
    private MenuItem itemSelectAll;
    private LinearLayout nativeAdContainer;
    private int progress;
    private ProgressDialog progressDialog;
    private ProgressBar progressbar;
    RecyclerView recyclerview;
    CenterTitleToolbar toolbar;
    private TextView txtCount;
    TextView txtError;
    ViewAnimator viewanimator;

    private void runOnUiThread() {
    }



    public class C05092 implements Runnable {
        C05092() {
        }

        @Override
        public void run() {
            AddAudiosActivity.this.progressbar.setProgress(AddAudiosActivity.this.progress);
        }
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_adds_image);
        ButterKnife.bind(this);
        this.dbHelper = new DBHelper(this);
        findViews();
        setHeaderInfo();
        Init();

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd( this);
        adAdmob.FullscreenAd(this);


    }

    private void findViews() {
        this.bannerContainer = (LinearLayout) findViewById(R.id.banner_container);
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.toolbar = (CenterTitleToolbar) findViewById(R.id.toolbar);
        this.txtError = (TextView) findViewById(R.id.txt_error);
        this.viewanimator = (ViewAnimator) findViewById(R.id.viewanimator);
        this.btnHide = (Button) findViewById(R.id.btn_hide);

        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllAudiosAdapter allAudiosAdapter = adapter;
                if (allAudiosAdapter != null) {
                    final List<String> selectedImages = allAudiosAdapter.getSelectedImages();
                    if (selectedImages == null || selectedImages.size() <= 0) {
                        Toast.makeText(AddAudiosActivity.this, "Please select at least one image!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showProgressDialog(selectedImages);
                    f12t = new Timer();
                    f12t.scheduleAtFixedRate(new TimerTask() {



                        class C05061 implements Runnable {
                            C05061() {
                            }

                            @Override
                            public void run() {
                                AddAudiosActivity.this.publishProgress(selectedImages.size());
                            }
                        }



                        class C05072 implements Runnable {
                            C05072() {
                            }

                            @Override
                            public void run() {
                                AddAudiosActivity.this.hideProgressDialog();
                                AddAudiosActivity.this.setBackData();
                            }
                        }

                        @Override
                        public void run() {
                            if (AddAudiosActivity.this.count == selectedImages.size()) {
                                AddAudiosActivity.this.f12t.cancel();
                                AddAudiosActivity.this.isImageAddedToNewAlbum = true;
                                AddAudiosActivity.this.runOnUiThread(new C05072());
                            } else if (AddAudiosActivity.this.isFileCopied) {
                                AddAudiosActivity.this.runOnUiThread(new C05061());
                                AddAudiosActivity.this.isFileCopied = false;
                                File file = new File((String) selectedImages.get(AddAudiosActivity.this.count));
                                AddAudiosActivity addAudiosActivity = AddAudiosActivity.this;
                                addAudiosActivity.moveFile(file, new File(addAudiosActivity.destPath, file.getName()));
                                AddAudiosActivity.this.count++;
                                AddAudiosActivity.this.isImageAddedToNewAlbum = true;
                            }
                        }
                    }, 0, 200);
                }

            }
        });
    }


    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(getString(R.string.add_audio));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_close_2));
        }
    }

    private void Init() {
        MainApplication.getInstance().LogFirebaseEvent("7", "AddAudio");
        File file = new File(AppConstants.AUDIO_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.destPath = file.getAbsolutePath();
        this.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new AllAudiosAdapter(this);
        this.recyclerview.setAdapter(this.adapter);
        GetAllAudiosAsyncTask getAllAudiosAsyncTask = new GetAllAudiosAsyncTask();
        getAllAudiosAsyncTask.onAllAudiosLoadedListener = this;
        getAllAudiosAsyncTask.execute(new Void[0]);
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
                AllAudiosAdapter allAudiosAdapter = this.adapter;
                if (allAudiosAdapter != null) {
                    allAudiosAdapter.selectAllItem();
                }
                this.isAllSelected = true;
                menuItem.setIcon(R.drawable.ic_check_filled);
                showHideButton(true);
            } else {
                AllAudiosAdapter allAudiosAdapter2 = this.adapter;
                if (allAudiosAdapter2 != null) {
                    allAudiosAdapter2.deSelectAllItem();
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
        setBackData();
    }

    private void showProgressDialog(List<String> list) {
        this.dialog = new Dialog(this);
        int i = 0;
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.dialog_progress);
        if (this.dialog.getWindow() != null) {
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.dialog.getWindow().setLayout(-1, -2);
        }
        this.progressbar = (ProgressBar) this.dialog.findViewById(R.id.progress_bar);
        this.txtCount = (TextView) this.dialog.findViewById(R.id.txt_count);
        this.nativeAdContainer = (LinearLayout) this.dialog.findViewById(R.id.native_ad_container);
        ((TextView) this.dialog.findViewById(R.id.txt_title)).setText("Moving Audio(s)");
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
                    runOnUiThread(new C05092());
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddAudiosActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddAudiosActivity.this.deleteFilePath(file);
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
    public void onAllAudiosLoaded(ArrayList<AllAudioModel> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            this.viewanimator.setDisplayedChild(2);
            return;
        }
        this.adapter.addItems(arrayList);
        this.viewanimator.setDisplayedChild(1);
    }


    @Override

    public void onStop() {
        super.onStop();
        Timer timer = this.f12t;
        if (timer != null) {
            timer.cancel();
        }
        hideProgressDialog();
    }
}

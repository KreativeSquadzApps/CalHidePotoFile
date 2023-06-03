package com.kreativesquadz.calculatorlock.video.add;

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
import android.view.ViewGroup;
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
import com.kreativesquadz.calculatorlock.activities.VideoFolderActivity;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.callbacks.OnAllVideosLoadedListener;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.model.AllVideosModel;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;
import com.kreativesquadz.calculatorlock.video.add.adapter.AllVideoAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class AddVideoActivity extends BaseActivity implements OnAllVideosLoadedListener {
    AllVideoAdapter adapter;
    Button btnHide;
    private int count;
    DBHelper dbHelper;
    private String destPath;
    private Dialog dialog;
    int int_position;
    private boolean isAllSelected;
    private boolean isImageAddedToNewAlbum;
    private MenuItem itemSelectAll;
    private ViewGroup nativeAdContainer;
    private ProgressDialog progressDialog;
    private ProgressBar progressbar;
    RecyclerView recyclerview;
    private Timer timer;
    CenterTitleToolbar toolbar;
    private TextView txtCount;
    TextView txtError;
    ViewAnimator viewanimator;
    private boolean isFileCopied = true;
    public ArrayList<AllVideosModel> al_VideoForPass = new ArrayList<>();
    private boolean isTransfering = true;
    private int progress = 0;

    private void runOnUiThread() {
    }



    public class C06512 implements Runnable {
        C06512() {
        }

        @Override
        public void run() {
            AddVideoActivity.this.progressbar.setProgress(AddVideoActivity.this.progress);
        }
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_adds_image);
        ButterKnife.bind(this);
        this.dbHelper = new DBHelper(this);
        findViews();


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd(  this);
        adAdmob.FullscreenAd(this);




        setHeaderInfo();
        Init();
    }

    private void findViews() {
        this.btnHide = (Button) findViewById(R.id.btn_hide);
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.toolbar = (CenterTitleToolbar) findViewById(R.id.toolbar);
        this.txtError = (TextView) findViewById(R.id.txt_error);
        this.viewanimator = (ViewAnimator) findViewById(R.id.viewanimator);


        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllVideoAdapter allVideoAdapter = AddVideoActivity.this.adapter;
                if (allVideoAdapter != null) {
                    final List<String> selectedImages = allVideoAdapter.getSelectedImages();
                    if (selectedImages == null || selectedImages.size() <= 0) {
                        Toast.makeText(AddVideoActivity.this, "Please select at least one image!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AddVideoActivity.this.count = 0;
                    showProgressDialog(selectedImages);
                    AddVideoActivity.this.timer = new Timer();
                    AddVideoActivity.this.timer.scheduleAtFixedRate(new TimerTask() {



                        class C06481 implements Runnable {
                            C06481() {
                            }

                            @Override
                            public void run() {
                                AddVideoActivity.this.publishProgress(selectedImages.size());
                            }
                        }



                        class C06492 implements Runnable {
                            C06492() {
                            }

                            @Override
                            public void run() {
                                AddVideoActivity.this.hideProgressDialog();
                                AddVideoActivity.this.setBackData();
                            }
                        }

                        @Override
                        public void run() {
                            if (AddVideoActivity.this.count == selectedImages.size()) {
                                AddVideoActivity.this.timer.cancel();
                                AddVideoActivity.this.isImageAddedToNewAlbum = true;
                                AddVideoActivity.this.runOnUiThread(new C06492());
                            } else if (AddVideoActivity.this.isFileCopied) {
                                AddVideoActivity.this.runOnUiThread(new C06481());
                                AddVideoActivity.this.isFileCopied = false;
                                File file = new File((String) selectedImages.get(AddVideoActivity.this.count));
                                AddVideoActivity addVideoActivity = AddVideoActivity.this;
                                addVideoActivity.moveFile(file, new File(addVideoActivity.destPath, file.getName()));
                                AddVideoActivity.this.count++;
                                AddVideoActivity.this.isImageAddedToNewAlbum = true;
                            }
                        }
                    }, 0, 200);
                }
            }
        });
    }


    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(getString(R.string.add_video));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_close_2));
        }
    }

    private void Init() {
        MainApplication.getInstance().LogFirebaseEvent("5", "AddVideo");
        File file = new File(AppConstants.VIDEO_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.int_position = getIntent().getIntExtra("value", 0);
        new ArrayList();
        ArrayList<String> al_videopath = VideoFolderActivity.al_videos.get(this.int_position).getAl_videopath();
        for (int i = 0; i < al_videopath.size(); i++) {
            AllVideosModel allVideosModel = new AllVideosModel();
            allVideosModel.setOldPath(al_videopath.get(i).toString());
            this.al_VideoForPass.add(allVideosModel);
        }
        this.destPath = file.getAbsolutePath();
        this.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new AllVideoAdapter(this, this.al_VideoForPass, this.int_position);
        this.recyclerview.setAdapter(this.adapter);
        GetAllVideosAsyncTask getAllVideosAsyncTask = new GetAllVideosAsyncTask();
        getAllVideosAsyncTask.onAllVideosLoadedListener = this;
        getAllVideosAsyncTask.execute(new Void[0]);
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
                AllVideoAdapter allVideoAdapter = this.adapter;
                if (allVideoAdapter != null) {
                    allVideoAdapter.selectAllItem();
                }
                this.isAllSelected = true;
                menuItem.setIcon(R.drawable.ic_check_filled);
                showHideButton(true);
            } else {
                AllVideoAdapter allVideoAdapter2 = this.adapter;
                if (allVideoAdapter2 != null) {
                    allVideoAdapter2.deSelectAllItem();
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
    public void onAllVideosLoaded(ArrayList<AllVideosModel> arrayList) {
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
        int i = 0;
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setContentView(R.layout.dialog_progress);
        if (this.dialog.getWindow() != null) {
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.dialog.getWindow().setLayout(-1, -2);
        }
        this.progressbar = (ProgressBar) this.dialog.findViewById(R.id.progress_bar);
        this.txtCount = (TextView) this.dialog.findViewById(R.id.txt_count);
        this.nativeAdContainer = (LinearLayout) this.dialog.findViewById(R.id.native_ad_container);
        ((TextView) this.dialog.findViewById(R.id.txt_title)).setText("Moving Video(s)");
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
                    runOnUiThread(new C06512());
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddVideoActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddVideoActivity.this.deleteFilePath(file);
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

    public void onStop() {
        super.onStop();
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
        }
        hideProgressDialog();
    }
}

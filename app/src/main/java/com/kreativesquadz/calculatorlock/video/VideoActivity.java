package com.kreativesquadz.calculatorlock.video;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.PointerIconCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.AdAdmob;
import com.kreativesquadz.calculatorlock.activities.VideoFolderActivity;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.callbacks.OnVideosLoadedListener;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.fullscreenimage.FullScreenImageActivity;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.model.AllVideosModel;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;
import com.kreativesquadz.calculatorlock.video.adapter.VideoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class VideoActivity extends BaseActivity implements OnVideosLoadedListener {
    private LinearLayout adView;
    private VideoAdapter adapter;
    Button btnUnhide;
    private int count;
    DBHelper dbHelper;
    private Dialog dialog;
    FloatingActionButton fabAdd;
    private boolean isEditable;
    private boolean isSelectAll;
    private MenuItem menuItemDelete;
    private MenuItem menuItemEdit;
    private MenuItem menuItemSelect;
    private LinearLayout nativeAdContainer;
    private int progress;
    private ProgressDialog progressDialog;
    private ProgressBar progressbar;
    RecyclerView recyclerview;
    private Timer timer;
    CenterTitleToolbar toolbar;
    private TextView txtCount;
    TextView txtError;
    ViewAnimator viewanimator;
    String TAG = "TAG";
    private boolean isFileCopied = true;

    private void runOnUiThread() {
    }



    public class C06392 implements Runnable {
        C06392() {
        }

        @Override
        public void run() {
            VideoActivity.this.progressbar.setProgress(VideoActivity.this.progress);
        }
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        this.dbHelper = new DBHelper(this);
        findViews();
        setHeaderInfo();
        Init();
        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd( this);
        adAdmob.FullscreenAd(this);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(VideoActivity.this, VideoFolderActivity.class), PointerIconCompat.TYPE_NO_DROP);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        });
        btnUnhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverFiles();
            }
        });

    }

    private void findViews() {
        this.btnUnhide = (Button) findViewById(R.id.btn_unhide);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.toolbar = (CenterTitleToolbar) findViewById(R.id.toolbar);
        this.txtError = (TextView) findViewById(R.id.txt_error);
        this.viewanimator = (ViewAnimator) findViewById(R.id.viewanimator);
    }


    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(getString(R.string.video));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow));
        }
    }

    private void Init() {
        MainApplication.getInstance().LogFirebaseEvent("4", AppConstants.VIDEO);
        this.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }


    public void setAdapter() {
        this.adapter = new VideoAdapter(this);
        this.recyclerview.setAdapter(this.adapter);
        GetHiddenVideos getHiddenVideos = new GetHiddenVideos();
        getHiddenVideos.onVideosLoadedListener = this;
        getHiddenVideos.execute(new Void[0]);
    }


    private void recoverFiles() {
        VideoAdapter videoAdapter = this.adapter;
        if (videoAdapter != null) {
            final List<String> selectedImages = videoAdapter.getSelectedImages();
            if (selectedImages == null || selectedImages.size() <= 0) {
                Toast.makeText(this, "Please select at least one image!", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog(selectedImages);
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(new TimerTask() {



                class C06361 implements Runnable {
                    C06361() {
                    }

                    @Override
                    public void run() {
                        VideoActivity.this.publishProgress(selectedImages.size());
                    }
                }



                class C06372 implements Runnable {
                    C06372() {
                    }

                    @Override
                    public void run() {
                        VideoActivity.this.hideProgressDialog();
                        VideoActivity.this.btnUnhide.setVisibility(View.GONE);
                        if (VideoActivity.this.menuItemEdit != null) {
                            VideoActivity.this.menuItemEdit.setVisible(true);
                        }
                        if (VideoActivity.this.menuItemSelect != null) {
                            VideoActivity.this.menuItemSelect.setVisible(false);
                            VideoActivity.this.menuItemSelect.setIcon(R.drawable.ic_check_box_outline);
                        }
                        if (VideoActivity.this.menuItemDelete != null) {
                            VideoActivity.this.menuItemDelete.setVisible(false);
                        }
                        VideoActivity.this.isEditable = false;
                        if (VideoActivity.this.getSupportActionBar() != null) {
                            VideoActivity.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            VideoActivity.this.getSupportActionBar().setHomeAsUpIndicator(VideoActivity.this.getResources().getDrawable(R.drawable.ic_back_arrow));
                        }
                        VideoActivity.this.setAdapter();
                    }
                }

                @Override
                public void run() {
                    if (VideoActivity.this.count == selectedImages.size()) {
                        VideoActivity.this.timer.cancel();
                        VideoActivity.this.count = 0;
                        VideoActivity.this.runOnUiThread(new C06372());
                    } else if (VideoActivity.this.isFileCopied) {
                        VideoActivity.this.runOnUiThread(new C06361());
                        VideoActivity.this.isFileCopied = false;
                        File file = new File((String) selectedImages.get(VideoActivity.this.count));
                        File file2 = new File(AppConstants.VIDEO_EXPORT_PATH);
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }
                        VideoActivity.this.moveFile(file, new File(AppConstants.VIDEO_EXPORT_PATH, file.getName()));
                        VideoActivity.this.count++;
                    }
                }
            }, 0, 200);
        }
    }

    private void showProgressDialog(List<String> list) {
        this.dialog = new Dialog(this);
        this.dialog.requestWindowFeature(1);
        int i = 0;
        this.dialog.setCancelable(false);
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
                    runOnUiThread(new C06392());
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            VideoActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            VideoActivity.this.deleteFilePath(file);
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

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1012 && i2 == -1) {
            this.viewanimator.setDisplayedChild(0);
            setAdapter();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        this.menuItemSelect = menu.findItem(R.id.itm_select);
        this.menuItemDelete = menu.findItem(R.id.itm_delete);
        this.menuItemEdit = menu.findItem(R.id.itm_edit);
        setAdapter();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 16908332) {
            switch (itemId) {
                case R.id.itm_delete:
                    final AlertDialog create = new AlertDialog.Builder(this).create();
                    create.setTitle("Alert");
                    create.setMessage("Are you sure to delete selected files?");
                    create.setCancelable(false);
                    create.setButton(-1, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            VideoActivity.this.deleteSelectedFiles();
                            create.dismiss();
                        }
                    });
                    create.setButton(-2, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            create.dismiss();
                        }
                    });
                    create.show();
                    break;
                case R.id.itm_edit:
                    this.isEditable = true;
                    menuItem.setVisible(false);
                    MenuItem menuItem2 = this.menuItemSelect;
                    if (menuItem2 != null) {
                        menuItem2.setVisible(true);
                    }
                    VideoAdapter videoAdapter = this.adapter;
                    if (videoAdapter != null) {
                        videoAdapter.isItemEditable(true);
                    }
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_close_2));
                        break;
                    }
                    break;
                case R.id.itm_select:
                    MenuItem menuItem3 = this.menuItemSelect;
                    if (menuItem3 != null) {
                        if (this.isSelectAll) {
                            menuItem3.setIcon(R.drawable.ic_check_box_outline);
                            VideoAdapter videoAdapter2 = this.adapter;
                            if (videoAdapter2 != null) {
                                videoAdapter2.deSelectAllItem();
                            }
                            showDeleteButton(false);
                            this.isSelectAll = false;
                            break;
                        } else {
                            menuItem3.setIcon(R.drawable.ic_check_filled);
                            VideoAdapter videoAdapter3 = this.adapter;
                            if (videoAdapter3 != null) {
                                videoAdapter3.selectAllItem();
                            }
                            showDeleteButton(true);
                            this.isSelectAll = true;
                            break;
                        }
                    }
                    break;
            }
        } else {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void deleteSelectedFiles() {
        VideoAdapter videoAdapter = this.adapter;
        if (videoAdapter != null) {
            List<String> selectedImagePaths = videoAdapter.getSelectedImagePaths();
            if (selectedImagePaths != null && selectedImagePaths.size() > 0) {
                for (String str : selectedImagePaths) {
                    new File(str).delete();
                }
            }
            showDeleteButton(false);
            setAdapter();
        }
    }

    public void showDeleteButton(boolean z) {
        MenuItem menuItem = this.menuItemDelete;
        if (menuItem != null) {
            menuItem.setVisible(z);
        }
        this.btnUnhide.setVisibility(z ? View.VISIBLE : View.GONE);
    }

    public void showSelectAllButton(boolean z) {
        MenuItem menuItem = this.menuItemSelect;
        if (menuItem != null) {
            menuItem.setIcon(z ? R.drawable.ic_check_filled : R.drawable.ic_check_box_outline);
            this.isSelectAll = z;
        }
    }

    public void startFullScreenImageActivity(ArrayList<AllImagesModel> arrayList, int i) {
        startActivity(new Intent(this, FullScreenImageActivity.class).putExtra(FullScreenImageActivity.OBJECT, arrayList).putExtra(FullScreenImageActivity.POSITION, i));
    }

    @Override
    public void onBackPressed() {
        if (this.isEditable) {
            MenuItem menuItem = this.menuItemEdit;
            if (menuItem != null) {
                menuItem.setVisible(true);
            }
            MenuItem menuItem2 = this.menuItemSelect;
            if (menuItem2 != null) {
                menuItem2.setVisible(false);
                this.menuItemSelect.setIcon(R.drawable.ic_check_box_outline);
            }
            MenuItem menuItem3 = this.menuItemDelete;
            if (menuItem3 != null) {
                menuItem3.setVisible(false);
            }
            this.isEditable = false;
            VideoAdapter videoAdapter = this.adapter;
            if (videoAdapter != null) {
                videoAdapter.isItemEditable(false);
            }
            VideoAdapter videoAdapter2 = this.adapter;
            if (videoAdapter2 != null) {
                videoAdapter2.deSelectAllItem();
            }
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow));
            }
            this.btnUnhide.setVisibility(View.GONE);
            return;
        }
        setBackData();
    }

    private void setBackData() {
        setResult(-1, new Intent());
        finish();
    }

    @Override
    public void onVideosLoaded(ArrayList<AllVideosModel> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            MainApplication.getInstance().saveVideoCount(0);
            enableMenuItems(false);
            this.viewanimator.setDisplayedChild(2);
            return;
        }
        this.adapter.addItems(arrayList);
        MainApplication.getInstance().saveVideoCount(arrayList.size());
        enableMenuItems(true);
        this.viewanimator.setDisplayedChild(1);
    }

    private void enableMenuItems(boolean z) {
        MenuItem menuItem = this.menuItemEdit;
        if (menuItem != null) {
            menuItem.setVisible(z);
        }
    }

    public void openVideo(final String str) {
        if (new Random().nextInt(5) + 0 != 0) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                intent.setDataAndType(Uri.parse(str), "video/*");
                startActivity(intent);
            } catch (Exception unused) {
                Toast.makeText(this, getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str));
                intent2.setDataAndType(Uri.parse(str), "video/*");
                startActivity(intent2);
            } catch (Exception unused2) {
                Toast.makeText(this, getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
            }
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

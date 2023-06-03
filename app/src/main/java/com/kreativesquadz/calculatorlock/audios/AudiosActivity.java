package com.kreativesquadz.calculatorlock.audios;

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
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.audios.adapter.AudiosAdapter;
import com.kreativesquadz.calculatorlock.audios.add.AddAudiosActivity;
import com.kreativesquadz.calculatorlock.callbacks.OnAudioLoadedListener;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.fullscreenimage.FullScreenImageActivity;
import com.kreativesquadz.calculatorlock.model.AllAudioModel;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AudiosActivity extends BaseActivity implements OnAudioLoadedListener {
    private AudiosAdapter adapter;
    LinearLayout bannerContainer;
    Button btnUnhide;
    private int count;
    DBHelper dbHelper;
    private Dialog dialog;
    private Timer f11t;
    FloatingActionButton fabAdd;
    private boolean isEditable;
    private boolean isFileCopied = true;
    private boolean isSelectAll;
    private MenuItem menuItemDelete;
    private MenuItem menuItemEdit;
    private MenuItem menuItemSelect;
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



    public class C04972 implements Runnable {
        C04972() {
        }

        @Override
        public void run() {
            AudiosActivity.this.progressbar.setProgress(AudiosActivity.this.progress);
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
        adAdmob.BannerAd(  this);
        adAdmob.FullscreenAd(this);

        btnUnhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverFiles();
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AudiosActivity.this, AddAudiosActivity.class), PointerIconCompat.TYPE_NO_DROP);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        });


    }

    private void findViews() {
        this.bannerContainer = (LinearLayout) findViewById(R.id.banner_container);
        this.btnUnhide = (Button) findViewById(R.id.btn_unhide);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.toolbar = (CenterTitleToolbar) findViewById(R.id.toolbar);
        this.txtError = (TextView) findViewById(R.id.txt_error);
        this.viewanimator = (ViewAnimator) findViewById(R.id.viewanimator);
    }


    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(getString(R.string.audio));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow));
        }
    }

    private void Init() {
        MainApplication.getInstance().LogFirebaseEvent("6", AppConstants.AUDIO);
        this.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }


    public void setAdapter() {
        this.adapter = new AudiosAdapter(this);
        this.recyclerview.setAdapter(this.adapter);
        GetHiddenAudio getHiddenAudio = new GetHiddenAudio();
        getHiddenAudio.onAudioLoadedListener = this;
        getHiddenAudio.execute(new Void[0]);
    }


    private void recoverFiles() {
        AudiosAdapter audiosAdapter = this.adapter;
        if (audiosAdapter != null) {
            final List<String> selectedImages = audiosAdapter.getSelectedImages();
            if (selectedImages == null || selectedImages.size() <= 0) {
                Toast.makeText(this, "Please select at least one image!", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog(selectedImages);
            this.f11t = new Timer();
            this.f11t.scheduleAtFixedRate(new TimerTask() {



                class C04941 implements Runnable {
                    C04941() {
                    }

                    @Override
                    public void run() {
                        AudiosActivity.this.publishProgress(selectedImages.size());
                    }
                }



                class C04952 implements Runnable {
                    C04952() {
                    }

                    @Override
                    public void run() {
                        AudiosActivity.this.hideProgressDialog();
                        AudiosActivity.this.btnUnhide.setVisibility(View.GONE);
                        if (AudiosActivity.this.menuItemEdit != null) {
                            AudiosActivity.this.menuItemEdit.setVisible(true);
                        }
                        if (AudiosActivity.this.menuItemSelect != null) {
                            AudiosActivity.this.menuItemSelect.setVisible(false);
                            AudiosActivity.this.menuItemSelect.setIcon(R.drawable.ic_check_box_outline);
                        }
                        if (AudiosActivity.this.menuItemDelete != null) {
                            AudiosActivity.this.menuItemDelete.setVisible(false);
                        }
                        AudiosActivity.this.isEditable = false;
                        if (AudiosActivity.this.getSupportActionBar() != null) {
                            AudiosActivity.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            AudiosActivity.this.getSupportActionBar().setHomeAsUpIndicator(AudiosActivity.this.getResources().getDrawable(R.drawable.ic_back_arrow));
                        }
                        AudiosActivity.this.setAdapter();
                    }
                }

                @Override
                public void run() {
                    if (AudiosActivity.this.count == selectedImages.size()) {
                        AudiosActivity.this.f11t.cancel();
                        AudiosActivity.this.count = 0;
                        AudiosActivity.this.runOnUiThread(new C04952());
                    } else if (AudiosActivity.this.isFileCopied) {
                        AudiosActivity.this.runOnUiThread(new C04941());
                        AudiosActivity.this.isFileCopied = false;
                        File file = new File((String) selectedImages.get(AudiosActivity.this.count));
                        File file2 = new File(AppConstants.AUDIO_EXPORT_PATH);
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }
                        AudiosActivity.this.moveFile(file, new File(AppConstants.AUDIO_EXPORT_PATH, file.getName()));
                        AudiosActivity.this.count++;
                    }
                }
            }, 0, 200);
        }
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
                    runOnUiThread(new C04972());
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AudiosActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AudiosActivity.this.deleteFilePath(file);
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
                            AudiosActivity.this.deleteSelectedFiles();
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
                    AudiosAdapter audiosAdapter = this.adapter;
                    if (audiosAdapter != null) {
                        audiosAdapter.isItemEditable(true);
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
                            AudiosAdapter audiosAdapter2 = this.adapter;
                            if (audiosAdapter2 != null) {
                                audiosAdapter2.deSelectAllItem();
                            }
                            showDeleteButton(false);
                            this.isSelectAll = false;
                            break;
                        } else {
                            menuItem3.setIcon(R.drawable.ic_check_filled);
                            AudiosAdapter audiosAdapter3 = this.adapter;
                            if (audiosAdapter3 != null) {
                                audiosAdapter3.selectAllItem();
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
        AudiosAdapter audiosAdapter = this.adapter;
        if (audiosAdapter != null) {
            List<String> selectedImagePaths = audiosAdapter.getSelectedImagePaths();
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
            AudiosAdapter audiosAdapter = this.adapter;
            if (audiosAdapter != null) {
                audiosAdapter.isItemEditable(false);
            }
            AudiosAdapter audiosAdapter2 = this.adapter;
            if (audiosAdapter2 != null) {
                audiosAdapter2.deSelectAllItem();
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
    public void onAudiosLoaded(ArrayList<AllAudioModel> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            MainApplication.getInstance().saveAudioCount(0);
            enableMenuItems(false);
            this.viewanimator.setDisplayedChild(2);
            return;
        }
        this.adapter.addItems(arrayList);
        MainApplication.getInstance().saveAudioCount(arrayList.size());
        enableMenuItems(true);
        this.viewanimator.setDisplayedChild(1);
    }

    private void enableMenuItems(boolean z) {
        MenuItem menuItem = this.menuItemEdit;
        if (menuItem != null) {
            menuItem.setVisible(z);
        }
    }

    public void openAudio(final String str) {
        if (new Random().nextInt(5) + 0 != 0) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                intent.setDataAndType(Uri.parse(str), "audio/*");
                startActivity(intent);
            } catch (Exception unused) {
                Toast.makeText(this, getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str));
                intent2.setDataAndType(Uri.parse(str), "audio/*");
                startActivity(intent2);
            } catch (Exception unused2) {
                Toast.makeText(this, getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override

    public void onStop() {
        super.onStop();
        Timer timer = this.f11t;
        if (timer != null) {
            timer.cancel();
        }
        hideProgressDialog();
    }
}

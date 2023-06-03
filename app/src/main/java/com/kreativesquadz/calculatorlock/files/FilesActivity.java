package com.kreativesquadz.calculatorlock.files;

import android.app.Dialog;
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
import com.kreativesquadz.calculatorlock.callbacks.OnFilesLoadedListener;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.files.adapter.FilesAdapter;
import com.kreativesquadz.calculatorlock.fullscreenimage.FullScreenImageActivity;
import com.kreativesquadz.calculatorlock.model.AllFilesModel;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FilesActivity extends BaseActivity implements OnFilesLoadedListener {
    private FilesAdapter adapter;
    Button btnUnhide;
    private int count;
    DBHelper dbHelper;
    private Dialog dialog;
    FloatingActionButton fabAdd;
    private boolean isEditable;
    private boolean isFileCopied = true;
    private boolean isSelectAll;
    private MenuItem menuItemDelete;
    private MenuItem menuItemEdit;
    private MenuItem menuItemSelect;
    private LinearLayout nativeAdContainer;
    private int progress;
    private ProgressBar progressbar;
    RecyclerView recyclerview;
    private Timer timer;
    CenterTitleToolbar toolbar;
    private TextView txtCount;
    TextView txtError;
    ViewAnimator viewanimator;

    private void runOnUiThread() {
    }



    public class runnable implements Runnable {
        runnable() {
        }

        @Override
        public void run() {
            FilesActivity.this.progressbar.setProgress(FilesActivity.this.progress);
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
        initViews();


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd( this);
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
                startActivityForResult(new Intent(FilesActivity.this, FileSelectionActivity.class), PointerIconCompat.TYPE_NO_DROP);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
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
        getSupportActionBar().setTitle(getString(R.string.file));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow));
        }
    }

    private void initViews() {
        MainApplication.getInstance().LogFirebaseEvent("8", "File");
        this.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }


    public void setAdapter() {
        this.adapter = new FilesAdapter(this);
        this.recyclerview.setAdapter(this.adapter);
        GetHiddenFiles getHiddenFiles = new GetHiddenFiles();
        getHiddenFiles.onFilesLoadedListener = this;
        getHiddenFiles.execute(new Void[0]);
    }


    private void recoverFiles() {
        FilesAdapter filesAdapter = this.adapter;
        if (filesAdapter != null) {
            final List<String> selectedImages = filesAdapter.getSelectedImages();
            if (selectedImages == null || selectedImages.size() <= 0) {
                Toast.makeText(this, "Please select at least one image!", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog(selectedImages);
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(new TimerTask() {



                class C05621 implements Runnable {
                    C05621() {
                    }

                    @Override
                    public void run() {
                        FilesActivity.this.publishProgress(selectedImages.size());
                    }
                }



                class C05632 implements Runnable {
                    C05632() {
                    }

                    @Override
                    public void run() {
                        FilesActivity.this.hideProgressDialog();
                        FilesActivity.this.btnUnhide.setVisibility(View.GONE);
                        if (FilesActivity.this.menuItemEdit != null) {
                            FilesActivity.this.menuItemEdit.setVisible(true);
                        }
                        if (FilesActivity.this.menuItemSelect != null) {
                            FilesActivity.this.menuItemSelect.setVisible(false);
                            FilesActivity.this.menuItemSelect.setIcon(R.drawable.ic_check_box_outline);
                        }
                        if (FilesActivity.this.menuItemDelete != null) {
                            FilesActivity.this.menuItemDelete.setVisible(false);
                        }
                        FilesActivity.this.isEditable = false;
                        if (FilesActivity.this.getSupportActionBar() != null) {
                            FilesActivity.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            FilesActivity.this.getSupportActionBar().setHomeAsUpIndicator(FilesActivity.this.getResources().getDrawable(R.drawable.ic_back_arrow));
                        }
                        FilesActivity.this.setAdapter();
                    }
                }

                @Override
                public void run() {
                    if (FilesActivity.this.count == selectedImages.size()) {
                        FilesActivity.this.timer.cancel();
                        FilesActivity.this.count = 0;
                        FilesActivity.this.runOnUiThread(new C05632());
                    } else if (FilesActivity.this.isFileCopied) {
                        FilesActivity.this.runOnUiThread(new C05621());
                        FilesActivity.this.isFileCopied = false;
                        File file = new File((String) selectedImages.get(FilesActivity.this.count));
                        File file2 = new File(AppConstants.FILE_EXPORT_PATH);
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }
                        FilesActivity.this.moveFile(file, new File(AppConstants.FILE_EXPORT_PATH, file.getName()));
                        FilesActivity.this.count++;
                    }
                }
            }, 0, 200);
        }
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
        ((TextView) this.dialog.findViewById(R.id.txt_title)).setText("Moving File(s)");
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
                    runOnUiThread(new runnable());
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FilesActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FilesActivity.this.deleteFilePath(file);
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
                            FilesActivity.this.deleteSelectedFiles();
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
                    FilesAdapter filesAdapter = this.adapter;
                    if (filesAdapter != null) {
                        filesAdapter.isItemEditable(true);
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
                            FilesAdapter filesAdapter2 = this.adapter;
                            if (filesAdapter2 != null) {
                                filesAdapter2.deSelectAllItem();
                            }
                            showDeleteButton(false);
                            this.isSelectAll = false;
                            break;
                        } else {
                            menuItem3.setIcon(R.drawable.ic_check_filled);
                            FilesAdapter filesAdapter3 = this.adapter;
                            if (filesAdapter3 != null) {
                                filesAdapter3.selectAllItem();
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
        FilesAdapter filesAdapter = this.adapter;
        if (filesAdapter != null) {
            List<String> selectedImagePaths = filesAdapter.getSelectedImagePaths();
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
            }
            MenuItem menuItem3 = this.menuItemDelete;
            if (menuItem3 != null) {
                menuItem3.setVisible(false);
            }
            this.isEditable = false;
            FilesAdapter filesAdapter = this.adapter;
            if (filesAdapter != null) {
                filesAdapter.isItemEditable(false);
            }
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow));
                return;
            }
            return;
        }
        setBackData();
    }

    private void setBackData() {
        setResult(-1, new Intent());
        finish();
    }

    private void enableMenuItems(boolean z) {
        MenuItem menuItem = this.menuItemEdit;
        if (menuItem != null) {
            menuItem.setVisible(z);
        }
    }

    @Override
    public void onFilesLoaded(ArrayList<AllFilesModel> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            MainApplication.getInstance().saveFilesCount(0);
            enableMenuItems(false);
            this.viewanimator.setDisplayedChild(2);
            return;
        }
        this.adapter.addItems(arrayList);
        MainApplication.getInstance().saveFilesCount(arrayList.size());
        enableMenuItems(true);
        this.viewanimator.setDisplayedChild(1);
    }

    public void openFile(String str) {
        try {
            Intent intent = new Intent();
            intent.addFlags(268435456);
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(new File(str)), "application/*");
            startActivity(intent);
        } catch (Exception unused) {
            Toast.makeText(this, getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
        }
    }
}

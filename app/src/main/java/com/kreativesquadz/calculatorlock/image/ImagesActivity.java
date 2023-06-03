package com.kreativesquadz.calculatorlock.image;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.activities.ImageFolderActivity;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.callbacks.OnImagesLoadedListener;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.fullscreenimage.FullScreenImageActivity;
import com.kreativesquadz.calculatorlock.image.adapter.ImagesAdapter;
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


public class ImagesActivity extends BaseActivity implements OnImagesLoadedListener {
    private LinearLayout adView;
    private ImagesAdapter adapter;
    Button btnUnhide;
    private int count;
    DBHelper dbHelper;
    private Dialog dialog;
    private Timer f16t;
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
    CenterTitleToolbar toolbar;
    private TextView txtCount;
    ViewAnimator viewanimator;
    String TAG = "TAG";
    private boolean isFileCopied = true;

    private void runOnUiThread() {
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        this.dbHelper = new DBHelper(this);


        fabAdd = findViewById(R.id.fab_add);
        recyclerview = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        viewanimator = findViewById(R.id.viewanimator);
        btnUnhide = findViewById(R.id.btn_unhide);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextInt = new Random().nextInt(5) + 0;
                Log.e("TAG", "onClick: " + nextInt);
                if (nextInt != 0) {
                    startActivity(new Intent(ImagesActivity.this, ImageFolderActivity.class));
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                } else {
                    startActivity(new Intent(ImagesActivity.this, ImageFolderActivity.class));
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                }
            }
        });

        btnUnhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverFiles();
            }
        });

        setHeaderInfo();
        Init();
    }




    public class C05982 implements Runnable {
        C05982() {
        }

        @Override
        public void run() {
            ImagesActivity.this.progressbar.setProgress(ImagesActivity.this.progress);
        }
    }

    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle("Image");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow));
        }
    }

    private void Init() {


        this.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
    }


    public void setAdapter() {
        MainApplication.getInstance().LogFirebaseEvent("3", "AddImage");
        this.adapter = new ImagesAdapter(this);
        this.recyclerview.setAdapter(this.adapter);
        GetHiddenImages getHiddenImages = new GetHiddenImages();
        getHiddenImages.onImagesLoadedListener = this;
        getHiddenImages.execute(new Void[0]);
    }


    private void recoverFiles() {
        ImagesAdapter imagesAdapter = this.adapter;
        if (imagesAdapter != null) {
            final List<String> selectedImages = imagesAdapter.getSelectedImages();
            if (selectedImages == null || selectedImages.size() <= 0) {
                Toast.makeText(this, "Please select at least one image!", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog(selectedImages);
            this.f16t = new Timer();
            this.f16t.scheduleAtFixedRate(new TimerTask() {



                class C05951 implements Runnable {
                    C05951() {
                    }

                    @Override
                    public void run() {
                        ImagesActivity.this.publishProgress(selectedImages.size());
                    }
                }



                class C05962 implements Runnable {
                    C05962() {
                    }

                    @Override
                    public void run() {
                        ImagesActivity.this.hideProgressDialog();
                        ImagesActivity.this.btnUnhide.setVisibility(View.GONE);
                        if (ImagesActivity.this.menuItemEdit != null) {
                            ImagesActivity.this.menuItemEdit.setVisible(true);
                        }
                        if (ImagesActivity.this.menuItemSelect != null) {
                            ImagesActivity.this.menuItemSelect.setVisible(false);
                            ImagesActivity.this.menuItemSelect.setIcon(R.drawable.ic_check_box_outline);
                        }
                        if (ImagesActivity.this.menuItemDelete != null) {
                            ImagesActivity.this.menuItemDelete.setVisible(false);
                        }
                        ImagesActivity.this.isEditable = false;
                        if (ImagesActivity.this.getSupportActionBar() != null) {
                            ImagesActivity.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            ImagesActivity.this.getSupportActionBar().setHomeAsUpIndicator(ImagesActivity.this.getResources().getDrawable(R.drawable.ic_back_arrow));
                        }
                        ImagesActivity.this.setAdapter();
                    }
                }

                @Override
                public void run() {
                    if (ImagesActivity.this.count == selectedImages.size()) {
                        ImagesActivity.this.f16t.cancel();
                        ImagesActivity.this.count = 0;
                        ImagesActivity.this.runOnUiThread(new C05962());
                    } else if (ImagesActivity.this.isFileCopied) {
                        ImagesActivity.this.runOnUiThread(new C05951());
                        ImagesActivity.this.isFileCopied = false;
                        File file = new File((String) selectedImages.get(ImagesActivity.this.count));
                        File file2 = new File(AppConstants.IMAGE_EXPORT_PATH);
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }
                        ImagesActivity.this.moveFile(file, new File(AppConstants.IMAGE_EXPORT_PATH, file.getName()));
                        ImagesActivity.this.count++;
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
        this.dialog.setCanceledOnTouchOutside(false);
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
                    runOnUiThread(new C05982());
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImagesActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImagesActivity.this.deleteFilePath(file);
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
    protected void onResume() {
        super.onResume();
        setAdapter();
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
                            ImagesActivity.this.deleteSelectedFiles();
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
                    ImagesAdapter imagesAdapter = this.adapter;
                    if (imagesAdapter != null) {
                        imagesAdapter.isItemEditable(true);
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
                            ImagesAdapter imagesAdapter2 = this.adapter;
                            if (imagesAdapter2 != null) {
                                imagesAdapter2.deSelectAllItem();
                            }
                            showDeleteButton(false);
                            this.isSelectAll = false;
                            break;
                        } else {
                            menuItem3.setIcon(R.drawable.ic_check_filled);
                            ImagesAdapter imagesAdapter3 = this.adapter;
                            if (imagesAdapter3 != null) {
                                imagesAdapter3.selectAllItem();
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
        ImagesAdapter imagesAdapter = this.adapter;
        if (imagesAdapter != null) {
            List<String> selectedImagePaths = imagesAdapter.getSelectedImagePaths();
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

    public void startFullScreenImageActivity(final ArrayList<AllImagesModel> arrayList, final int i) {
        int nextInt = new Random().nextInt(5) + 0;
        String str = this.TAG;
        Log.e(str, "rand:--> " + nextInt);
        if (nextInt != 0) {
            startActivity(new Intent(this, FullScreenImageActivity.class).putExtra(FullScreenImageActivity.OBJECT, arrayList).putExtra(FullScreenImageActivity.POSITION, i));
        } else {
            startActivity(new Intent(this, FullScreenImageActivity.class).putExtra(FullScreenImageActivity.OBJECT, arrayList).putExtra(FullScreenImageActivity.POSITION, i));
        }
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
            ImagesAdapter imagesAdapter = this.adapter;
            if (imagesAdapter != null) {
                imagesAdapter.isItemEditable(false);
            }
            ImagesAdapter imagesAdapter2 = this.adapter;
            if (imagesAdapter2 != null) {
                imagesAdapter2.deSelectAllItem();
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

    private void enableMenuItems(boolean z) {
        MenuItem menuItem = this.menuItemEdit;
        if (menuItem != null) {
            menuItem.setVisible(z);
        }
    }

    @Override
    public void onImagesLoaded(ArrayList<AllImagesModel> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            MainApplication.getInstance().saveImageCount(0);
            enableMenuItems(false);
            this.viewanimator.setDisplayedChild(2);
            return;
        }
        this.adapter.addItems(arrayList);
        MainApplication.getInstance().saveImageCount(arrayList.size());
        enableMenuItems(true);
        this.viewanimator.setDisplayedChild(1);
    }


    @Override

    public void onStop() {
        super.onStop();
        Timer timer = this.f16t;
        if (timer != null) {
            timer.cancel();
        }
        hideProgressDialog();
        Log.e("TAG", "onStop: ");
    }
}

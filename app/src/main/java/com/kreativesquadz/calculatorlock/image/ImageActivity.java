package com.kreativesquadz.calculatorlock.image;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.PointerIconCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.fullscreenimage.FullScreenImageActivity;
import com.kreativesquadz.calculatorlock.image.adapter.ImagesAdapter;
import com.kreativesquadz.calculatorlock.image.add.AddImageActivity;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class ImageActivity extends BaseActivity {
    private ImagesAdapter adapter;
    Button btnUnhide;
    private int count;
    DBHelper dbHelper;
    private Timer f15t;
    FloatingActionButton fabAdd;
    private boolean isFileCopied = true;
    private boolean isSelectAll;
    private MenuItem menuItemDelete;
    private MenuItem menuItemSelect;
    private ProgressDialog progressDialog;
    RecyclerView recyclerview;
    CenterTitleToolbar toolbar;
    TextView txtError;
    ViewAnimator viewanimator;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        this.dbHelper = new DBHelper(this);
        findViews();
        setHeaderInfo();
        Init();

        btnUnhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverFiles();
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ImageActivity.this, AddImageActivity.class), PointerIconCompat.TYPE_NO_DROP);
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
        getSupportActionBar().setTitle(getString(R.string.image));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void Init() {
        MainApplication.getInstance().LogFirebaseEvent("2", AppConstants.IMAGE);
        this.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        setAdapter();
    }

    private void setAdapter() {
        this.adapter = new ImagesAdapter(this);
        this.recyclerview.setAdapter(this.adapter);
        ArrayList<AllImagesModel> images = this.dbHelper.getImages();
        if (images == null || images.size() <= 0) {
            this.viewanimator.setDisplayedChild(2);
        } else {
            this.viewanimator.setDisplayedChild(1);
        }
    }


    private void recoverFiles() {
        if (this.adapter != null) {
            ArrayList arrayList = new ArrayList();
            if (arrayList.size() <= 0) {
                Toast.makeText(this, "Please select at least one image!", Toast.LENGTH_SHORT).show();
            } else {
                showProgressDialog(arrayList.size());
            }
        }
    }

    private void showProgressDialog(int i) {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setTitle("Moving Images");
        ProgressDialog progressDialog = this.progressDialog;
        progressDialog.setMessage("1 of " + i);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.show();
    }

    private void moveFile(File file, final File file2, final int i) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageActivity.this.dbHelper.deleteImage((long) i);
                            ImageActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    file.delete();
                    this.isFileCopied = true;
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.isFileCopied = true;
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MenuItem menuItem2;
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
        } else if (itemId == R.id.itm_delete) {
            final AlertDialog create = new AlertDialog.Builder(this).create();
            create.setTitle("Alert");
            create.setMessage("Are you sure to delete selected files?");
            create.setCancelable(false);
            create.setButton(-1, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ImageActivity.this.deleteSelectedFiles();
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
        } else if (itemId == R.id.itm_select && (menuItem2 = this.menuItemSelect) != null) {
            if (!this.isSelectAll) {
                menuItem2.setIcon(R.drawable.ic_check_filled);
                ImagesAdapter imagesAdapter = this.adapter;
                if (imagesAdapter != null) {
                    imagesAdapter.selectAllItem();
                }
                showDeleteButton(true);
                this.isSelectAll = true;
            } else {
                menuItem2.setIcon(R.drawable.ic_check_box_outline);
                ImagesAdapter imagesAdapter2 = this.adapter;
                if (imagesAdapter2 != null) {
                    imagesAdapter2.deSelectAllItem();
                }
                showDeleteButton(false);
                this.isSelectAll = false;
            }
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

    public void startFullScreenImageActivity(ArrayList<AllImagesModel> arrayList, int i) {
        startActivity(new Intent(this, FullScreenImageActivity.class).putExtra(FullScreenImageActivity.OBJECT, arrayList).putExtra(FullScreenImageActivity.POSITION, i));
    }

    @Override
    public void onBackPressed() {
        setBackData();
    }

    private void setBackData() {
        setResult(-1, new Intent());
        finish();
    }
}

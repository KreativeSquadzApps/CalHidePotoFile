package com.kreativesquadz.calculatorlock.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.image.adapter.AdapterImageFolder;
import com.kreativesquadz.calculatorlock.image.adapter.ModelImage;
import com.kreativesquadz.calculatorlock.image.add.AddImageActivity;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;

import java.util.ArrayList;


public class ImageFolderActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 100;
    public static ArrayList<ModelImage> al_Image = new ArrayList<>();
    public static ArrayList<AllImagesModel> al_ImageForPass = new ArrayList<>();
    boolean boolean_folder;
    GridView gv_folder;
    AdapterImageFolder obj_adapter;
    CenterTitleToolbar toolbar;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_folder);
        this.toolbar = (CenterTitleToolbar) findViewById(R.id.toolbar);
        setHeaderInfo();
        this.gv_folder = (GridView) findViewById(R.id.gv_folder);
        this.gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(ImageFolderActivity.this.getApplicationContext(), AddImageActivity.class);
                intent.putExtra("value", i);
                intent.putExtra("folderName", ImageFolderActivity.al_Image.get(i).getStr_folder());
                ImageFolderActivity.this.startActivityForResult(intent, 101);
            }
        });
        if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            Log.e("Else", "Else");
            fn_Imagepath();
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || !ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 100);
        }
    }

    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(getString(R.string.image));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public ArrayList<ModelImage> fn_Imagepath() {
        al_Image.clear();
        Cursor query = getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "bucket_display_name"}, null, null, "datetaken DESC");
        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
        int columnIndexOrThrow2 = query.getColumnIndexOrThrow("bucket_display_name");
        int i = 0;
        while (query.moveToNext()) {
            String string = query.getString(columnIndexOrThrow);
            Log.e("Column", string);
            int i2 = 0;
            while (true) {
                if (i2 >= al_Image.size()) {
                    break;
                }
                try {
                } catch (Exception e) {
                    Log.e("Errors:-", e.toString());
                }
                try {
                    if (al_Image.get(i2).getStr_folder().equals(query.getString(columnIndexOrThrow2))) {
                        this.boolean_folder = true;
                        i = i2;
                        break;
                    }
                } catch (Exception ee) {

                }

                this.boolean_folder = false;
                i2++;
            }
            if (this.boolean_folder) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.addAll(al_Image.get(i).getAl_Imagepath());
                arrayList.add(string);
                al_Image.get(i).setAl_Imagepath(arrayList);
            } else {
                ArrayList<String> arrayList2 = new ArrayList<>();
                arrayList2.add(string);
                ModelImage modelImage = new ModelImage();
                modelImage.setStr_folder(query.getString(columnIndexOrThrow2));
                modelImage.setAl_Imagepath(arrayList2);
                al_Image.add(modelImage);
                AllImagesModel allImagesModel = new AllImagesModel();
                allImagesModel.setImagePath(string);
                al_ImageForPass.add(allImagesModel);
            }
        }
        for (int i3 = 0; i3 < al_Image.size(); i3++) {
            for (int i4 = 0; i4 < al_Image.get(i3).getAl_Imagepath().size(); i4++) {
                try {
                    Log.e("FILE", al_Image.get(i3).getAl_Imagepath().get(i4));
                } catch (Exception e2) {
                    Log.e("Errors:-", e2.toString());
                }
            }
        }
        this.obj_adapter = new AdapterImageFolder(getApplicationContext(), al_Image);
        this.gv_folder.setAdapter((ListAdapter) this.obj_adapter);
        return al_Image;
    }


    @Override

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 101 && i2 == -1) {
            String stringExtra = intent.getStringExtra("path");
            Intent intent2 = new Intent();
            intent2.putExtra("path", stringExtra);
            setResult(-1, intent2);
            finish();
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100) {
            for (int i2 : iArr) {
                if (iArr.length <= 0 || i2 != 0) {
                    Toast.makeText(this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                } else {
                    fn_Imagepath();
                }
            }
        }
    }
}

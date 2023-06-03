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
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;
import com.kreativesquadz.calculatorlock.video.Model_videos;
import com.kreativesquadz.calculatorlock.video.adapter.Adapter_VideosFolder;
import com.kreativesquadz.calculatorlock.video.add.AddVideoActivity;

import java.util.ArrayList;

public class VideoFolderActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 100;
    public static ArrayList<Model_videos> al_videos = new ArrayList<>();
    boolean boolean_folder;
    GridView gv_folder;
    Adapter_VideosFolder obj_adapter;
    CenterTitleToolbar toolbar;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_folder);
        this.gv_folder = (GridView) findViewById(R.id.gv_folder);
        this.toolbar = (CenterTitleToolbar) findViewById(R.id.toolbar);
        setHeaderInfo();
        this.gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(VideoFolderActivity.this.getApplicationContext(), AddVideoActivity.class);
                intent.putExtra("value", i);
                VideoFolderActivity.this.startActivityForResult(intent, 101);
            }
        });
        if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            Log.e("Else", "Else");
            fn_videospath();
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || !ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 100);
        }
    }

    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(getString(R.string.video));
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

    public ArrayList<Model_videos> fn_videospath() {
        al_videos.clear();
        Cursor query = getApplicationContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "bucket_display_name"}, null, null, "datetaken DESC");
        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
        int columnIndexOrThrow2 = query.getColumnIndexOrThrow("bucket_display_name");
        int i = 0;
        while (query.moveToNext()) {
            String string = query.getString(columnIndexOrThrow);
            Log.e("Column", string);
            Log.e("Folder", query.getString(columnIndexOrThrow2));
            int i2 = 0;
            while (true) {
                if (i2 >= al_videos.size()) {
                    break;
                } else if (al_videos.get(i2).getStr_folder().equals(query.getString(columnIndexOrThrow2))) {
                    this.boolean_folder = true;
                    i = i2;
                    break;
                } else {
                    this.boolean_folder = false;
                    i2++;
                }
            }
            if (this.boolean_folder) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.addAll(al_videos.get(i).getAl_videopath());
                arrayList.add(string);
                al_videos.get(i).setAl_videopath(arrayList);
            } else {
                ArrayList<String> arrayList2 = new ArrayList<>();
                arrayList2.add(string);
                Model_videos model_videos = new Model_videos();
                model_videos.setStr_folder(query.getString(columnIndexOrThrow2));
                model_videos.setAl_videopath(arrayList2);
                al_videos.add(model_videos);
            }
        }
        for (int i3 = 0; i3 < al_videos.size(); i3++) {
            Log.e("FOLDER", al_videos.get(i3).getStr_folder());
            for (int i4 = 0; i4 < al_videos.get(i3).getAl_videopath().size(); i4++) {
                Log.e("FILE", al_videos.get(i3).getAl_videopath().get(i4));
            }
        }
        this.obj_adapter = new Adapter_VideosFolder(getApplicationContext(), al_videos);
        this.gv_folder.setAdapter((ListAdapter) this.obj_adapter);
        this.obj_adapter.notifyDataSetChanged();
        return al_videos;
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
                    fn_videospath();
                }
            }
        }
    }
}

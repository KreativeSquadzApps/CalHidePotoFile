package com.kreativesquadz.calculatorlock.files;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.AdAdmob;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.common.MergeAdapter;
import com.kreativesquadz.calculatorlock.utils.CenterTitleToolbar;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;


public class FileSelectionActivity extends BaseActivity {
    private static final Pattern DIR_SEPORATOR = Pattern.compile("/");
    public static final String FILES_TO_UPLOAD = "upload";
    private static final String TAG = "FileSelection";
    Button New;
    Button all;
    private Button btnHide;
    Button cancel;
    String choiceMode;
    private String destPath;
    private Dialog dialog;
    private ListView directoryView;
    private boolean isImageAddedToNewAlbum;
    private LinearLayout nativeAdContainer;
    private LinearLayout layoutViewAdd;
    Button ok;
    TextView path;
    String primary_sd;
    private int progress;
    private ProgressBar progressbar;
    String secondary_sd;
    Button storage;
    private Timer timer;
    CenterTitleToolbar toolbar;
    private TextView txtCount;
    private int count = 0;
    private ArrayList<File> directoryList = new ArrayList<>();
    private ArrayList<String> directoryNames = new ArrayList<>();
    private ArrayList<File> fileList = new ArrayList<>();
    private ArrayList<String> fileNames = new ArrayList<>();
    int index = 0;
    Boolean Switch = false;
    private boolean isFileCopied = true;
    File mainPath = new File(Environment.getExternalStorageDirectory() + "");
    private ArrayList<File> selectedFiles = new ArrayList<>();
    Boolean switcher = false;
    int top = 0;

    private void initViews() {
    }

    private void runOnUiThread() {
    }

    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_file_selection);
        ButterKnife.bind(this);
        findViews();
        initViews();
        setHeaderInfo();

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd(  this);


    }

    private void findViews() {
        MainApplication.getInstance().LogFirebaseEvent("9", "AddFile");
        if (getIntent().getExtras() != null) {
            this.choiceMode = getIntent().getStringExtra("choiceMode");
        }
        this.directoryView = (ListView) findViewById(R.id.directorySelectionList);
        this.btnHide = (Button) findViewById(R.id.btn_hide);
        if (this.choiceMode != null) {
            this.directoryView.setChoiceMode(1);
        }
        this.ok = (Button) findViewById(R.id.ok);
        this.all = (Button) findViewById(R.id.all);
        this.cancel = (Button) findViewById(R.id.cancel);
        this.storage = (Button) findViewById(R.id.storage);
        this.New = (Button) findViewById(R.id.New);
        this.path = (TextView) findViewById(R.id.folderpath);
        this.toolbar = (CenterTitleToolbar) findViewById(R.id.toolbar);
        this.layoutViewAdd = (LinearLayout) findViewById(R.id.layoutViewAdd);
        loadLists();
        this.New.setEnabled(false);
        this.directoryView.setOnItemClickListener(new C05511());
        this.ok.setOnClickListener(new C05522());
        this.btnHide.setOnClickListener(new C05533());
        this.cancel.setOnClickListener(new C05544());
        this.storage.setOnClickListener(new C05555());
        this.all.setOnClickListener(new C05566());
    }



    public class C05511 implements AdapterView.OnItemClickListener {
        C05511() {
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            FileSelectionActivity fileSelectionActivity = FileSelectionActivity.this;
            fileSelectionActivity.index = fileSelectionActivity.directoryView.getFirstVisiblePosition();
            int i2 = 0;
            View childAt = FileSelectionActivity.this.directoryView.getChildAt(0);
            FileSelectionActivity fileSelectionActivity2 = FileSelectionActivity.this;
            if (childAt != null) {
                i2 = childAt.getTop();
            }
            fileSelectionActivity2.top = i2;
            try {
                if (i < FileSelectionActivity.this.directoryList.size()) {
                    FileSelectionActivity.this.mainPath = (File) FileSelectionActivity.this.directoryList.get(i);
                    FileSelectionActivity.this.loadLists();
                }
            } catch (Throwable unused) {
                FileSelectionActivity fileSelectionActivity3 = FileSelectionActivity.this;
                fileSelectionActivity3.mainPath = fileSelectionActivity3.mainPath;
                FileSelectionActivity.this.loadLists();
            }
        }
    }



    public class C05522 implements View.OnClickListener {
        C05522() {
        }

        @Override
        public void onClick(View view) {
            FileSelectionActivity.this.ok();
        }
    }



    public class C05533 implements View.OnClickListener {
        C05533() {
        }

        @Override
        public void onClick(View view) {
            FileSelectionActivity.this.ok();
        }
    }



    public class C05544 implements View.OnClickListener {
        C05544() {
        }

        @Override
        public void onClick(View view) {
            FileSelectionActivity.this.finish();
        }
    }



    public class C05555 implements View.OnClickListener {
        C05555() {
        }

        @Override
        public void onClick(View view) {
            try {
                if (FileSelectionActivity.this.switcher.booleanValue()) {
                    FileSelectionActivity.this.mainPath = new File(FileSelectionActivity.this.primary_sd);
                    FileSelectionActivity.this.loadLists();
                    FileSelectionActivity.this.switcher = false;
                    FileSelectionActivity.this.storage.setText(FileSelectionActivity.this.getString(R.string.ext));
                    return;
                }
                FileSelectionActivity.this.mainPath = new File(FileSelectionActivity.this.secondary_sd);
                FileSelectionActivity.this.loadLists();
                FileSelectionActivity.this.switcher = true;
                FileSelectionActivity.this.storage.setText(FileSelectionActivity.this.getString(R.string.Int));
            } catch (Throwable unused) {
            }
        }
    }



    public class C05566 implements View.OnClickListener {
        C05566() {
        }

        @Override
        public void onClick(View view) {
            if (!FileSelectionActivity.this.Switch.booleanValue()) {
                for (int size = FileSelectionActivity.this.directoryList.size(); size < FileSelectionActivity.this.directoryView.getCount(); size++) {
                    FileSelectionActivity.this.directoryView.setItemChecked(size, true);
                }
                FileSelectionActivity.this.all.setText(FileSelectionActivity.this.getString(R.string.none));
                FileSelectionActivity.this.Switch = true;
            } else if (FileSelectionActivity.this.Switch.booleanValue()) {
                for (int size2 = FileSelectionActivity.this.directoryList.size(); size2 < FileSelectionActivity.this.directoryView.getCount(); size2++) {
                    FileSelectionActivity.this.directoryView.setItemChecked(size2, false);
                }
                FileSelectionActivity.this.all.setText(FileSelectionActivity.this.getString(R.string.all));
                FileSelectionActivity.this.Switch = false;
            }
        }
    }



    public class C05597 extends TimerTask {


        class C05571 implements Runnable {
            C05571() {
            }

            @Override
            public void run() {
                FileSelectionActivity.this.publishProgress(FileSelectionActivity.this.selectedFiles.size());
            }
        }


        class C05582 implements Runnable {
            C05582() {
            }

            @Override
            public void run() {
                FileSelectionActivity.this.hideProgressDialog();
                FileSelectionActivity.this.setBackData();
            }
        }

        C05597() {
        }

        @Override
        public void run() {
            if (FileSelectionActivity.this.count == FileSelectionActivity.this.selectedFiles.size()) {
                FileSelectionActivity.this.timer.cancel();
                FileSelectionActivity.this.isImageAddedToNewAlbum = true;
                FileSelectionActivity.this.runOnUiThread(new C05582());
            } else if (FileSelectionActivity.this.isFileCopied) {
                FileSelectionActivity.this.runOnUiThread(new C05571());
                FileSelectionActivity.this.isFileCopied = false;
                File file = (File) FileSelectionActivity.this.selectedFiles.get(FileSelectionActivity.this.count);
                FileSelectionActivity fileSelectionActivity = FileSelectionActivity.this;
                fileSelectionActivity.moveFile(file, new File(fileSelectionActivity.destPath, file.getName()));
                FileSelectionActivity.this.count++;
                FileSelectionActivity.this.isImageAddedToNewAlbum = true;
            }
        }
    }



    public class C05608 implements Runnable {
        C05608() {
        }

        @Override
        public void run() {
            FileSelectionActivity.this.progressbar.setProgress(FileSelectionActivity.this.progress);
        }
    }


    private void setHeaderInfo() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(getString(R.string.select_file));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        try {
            if (this.mainPath.equals(Environment.getExternalStorageDirectory())) {
                finish();
                return;
            }
            this.mainPath = this.mainPath.getParentFile();
            loadLists();
            this.directoryView.setSelectionFromTop(this.index, this.top);
            this.btnHide.setVisibility(View.GONE);
        } catch (Throwable unused) {
        }
    }

    public void ok() {
        File file = new File(AppConstants.FILES_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.destPath = file.getAbsolutePath();
        ArrayList<File> arrayList = this.selectedFiles;
        if (arrayList == null || arrayList.size() <= 0) {
            Toast.makeText(this, "Please select at least one file!", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressDialog(this.selectedFiles);
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new C05597(), 0, 200);
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

    private void showProgressDialog(ArrayList<File> arrayList) {
        this.dialog = new Dialog(this);
        this.dialog.requestWindowFeature(1);
        int i = 0;
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.dialog_progress);
        if (this.dialog.getWindow() != null) {
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.dialog.getWindow().setLayout(-1, -2);
        }
        this.progressbar = (ProgressBar) this.dialog.findViewById(R.id.progress_bar);
        this.txtCount = (TextView) this.dialog.findViewById(R.id.txt_count);
        this.nativeAdContainer = (LinearLayout) this.dialog.findViewById(R.id.native_ad_container);
        ((TextView) this.dialog.findViewById(R.id.txt_title)).setText("Moving File(s)");
        TextView textView = this.txtCount;
        textView.setText("Moving 1 of " + arrayList.size());
        Iterator<File> it = arrayList.iterator();
        while (it.hasNext()) {
            i += (int) it.next().length();
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
                    runOnUiThread(new C05608());
                } else {
                    fileOutputStream.close();
                    this.isFileCopied = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FileSelectionActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                        }
                    });
                    fileInputStream.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FileSelectionActivity.this.deleteFilePath(file);
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
            Toast.makeText(this, "" + e.getMessage() , Toast.LENGTH_LONG).show();
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


    public void loadLists() {
        FileFilter r0 = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }
        };
        File[] listFiles = this.mainPath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        if (listFiles != null && listFiles.length > 1) {
            Arrays.sort(listFiles, new Comparator<File>() {
                public int compare(File file, File file2) {
                    return file.getName().compareTo(file2.getName());
                }
            });
        }
        this.directoryList = new ArrayList<>();
        this.directoryNames = new ArrayList<>();
        try {
            for (File file : listFiles) {
                this.directoryList.add(file);
                this.directoryNames.add(file.getName());
            }
        } catch (Exception unused) {
        }
        new ArrayAdapter(this, 17367043, this.directoryNames);
        File[] listFiles2 = this.mainPath.listFiles(r0);
        if (listFiles2 != null && listFiles2.length > 1) {
            Arrays.sort(listFiles2, new Comparator<File>() {
                public int compare(File file2, File file3) {
                    return file2.getName().compareTo(file3.getName());
                }
            });
        }
        this.fileList = new ArrayList<>();
        this.fileNames = new ArrayList<>();
        for (File file2 : listFiles2) {
            if (file2.getAbsolutePath().endsWith(".pps") || file2.getAbsolutePath().endsWith(".ppt") || file2.getAbsolutePath().endsWith(".pptx") || file2.getAbsolutePath().endsWith(".xls") || file2.getAbsolutePath().endsWith(".xlsx") || file2.getAbsolutePath().endsWith(".pdf") || file2.getAbsolutePath().endsWith(".doc") || file2.getAbsolutePath().endsWith(".docx") || file2.getAbsolutePath().endsWith(".rtf") || file2.getAbsolutePath().endsWith(".txt")) {
                this.fileList.add(file2);
                this.fileNames.add(file2.getName());
            }
        }
        this.path.setText(this.mainPath.toString());
        iconload();
        setTitle(this.mainPath.getName());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void iconload() {
        ArrayList<String> arrayList = this.fileNames;
        ArrayList<String> arrayList2 = this.directoryNames;
        CustomListSingleOnly customListSingleOnly = new CustomListSingleOnly(this, (String[]) arrayList2.toArray((String[]) arrayList2.toArray(new String[arrayList2.size()])), this.mainPath.getPath());
        CustomList customList = new CustomList(this, (String[]) this.fileNames.toArray((String[]) arrayList.toArray(new String[arrayList.size()])), this.mainPath.getPath());
        MergeAdapter mergeAdapter = new MergeAdapter();
        mergeAdapter.addAdapter(customListSingleOnly);
        mergeAdapter.addAdapter(customList);
        this.directoryView.setAdapter((ListAdapter) mergeAdapter);
    }

    public boolean hasStorage() {
        String externalStorageState = Environment.getExternalStorageState();
        Log.v(TAG, "storage state is " + externalStorageState);
        return "mounted".equals(externalStorageState);
    }

    public void ExtStorageSearch() {
        this.primary_sd = System.getenv("EXTERNAL_STORAGE");
        if (this.primary_sd == null) {
            this.primary_sd = Environment.getExternalStorageDirectory() + "";
        }
        if (Boolean.valueOf(Environment.getExternalStorageState().equals("mounted")).booleanValue()) {
            String[] storageDirectories = getStorageDirectories(this);
            for (String str : storageDirectories) {
                if (new File(str).exists() && new File(str).isDirectory() && !str.equals(this.primary_sd)) {
                    this.secondary_sd = str;
                    return;
                }
            }
            return;
        }
        this.secondary_sd = null;
    }

    public static String[] getStorageDirectories(Context context) {
        HashSet hashSet = new HashSet();
        String str = System.getenv("EXTERNAL_STORAGE");
        String str2 = System.getenv("SECONDARY_STORAGE");
        String str3 = System.getenv("EMULATED_STORAGE_TARGET");
        boolean z = false;
        if (!TextUtils.isEmpty(str3)) {
            String str4 = "";
            if (Build.VERSION.SDK_INT >= 17) {
                String[] split = DIR_SEPORATOR.split(Environment.getExternalStorageDirectory().getAbsolutePath());
                String str5 = split[split.length - 1];
                try {
                    Integer.valueOf(str5);
                    z = true;
                } catch (NumberFormatException unused) {
                }
                if (z) {
                    str4 = str5;
                }
            }
            if (TextUtils.isEmpty(str4)) {
                hashSet.add(str3);
            } else {
                hashSet.add(str3 + File.separator + str4);
            }
        } else if (Build.VERSION.SDK_INT >= 23) {
            for (File file : context.getExternalFilesDirs(null)) {
                String absolutePath = file.getAbsolutePath();
                hashSet.add(absolutePath.substring(0, absolutePath.indexOf("Android/data")));
            }
        } else if (TextUtils.isEmpty(str)) {
            hashSet.addAll(Arrays.asList(getPhysicalPaths()));
        } else {
            hashSet.add(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            Collections.addAll(hashSet, str2.split(File.pathSeparator));
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    private static String[] getPhysicalPaths() {
        return new String[]{"/storage/sdcard0", "/storage/sdcard1", "/storage/extsdcard", "/storage/sdcard0/external_sdcard", "/mnt/extsdcard", "/mnt/sdcard/external_sd", "/mnt/external_sd", "/mnt/media_rw/sdcard1", "/removable/microsd", "/mnt/emmc", "/storage/external_SD", "/storage/ext_sd", "/storage/removable/sdcard1", "/data/sdext", "/data/sdext2", "/data/sdext3", "/data/sdext4", "/sdcard1", "/sdcard2", "/storage/microsd"};
    }

    public void setFiles(boolean z, String str) {
        int i = 0;
        if (z) {
            for (int i2 = 0; i2 < this.fileList.size(); i2++) {
                if (this.fileList.get(i2).getName().equals(str)) {
                    this.selectedFiles.add(this.fileList.get(i2));
                    Log.e("FILE SELECTED ", str);
                }
            }
        } else {
            for (int i3 = 0; i3 < this.fileList.size(); i3++) {
                if (this.fileList.get(i3).getName().equals(str)) {
                    this.selectedFiles.remove(this.fileList.get(i3));
                    Log.e("FILE REMOVED ", str);
                }
            }
        }
        Button button = this.btnHide;
        if (this.selectedFiles.size() <= 0) {
            i = 8;
        }
        button.setVisibility(i);
    }
}

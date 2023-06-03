package com.kreativesquadz.calculatorlock.files;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.AdAdmob;
import com.kreativesquadz.calculatorlock.app.BaseActivity;
import com.kreativesquadz.calculatorlock.common.MergeAdapter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.regex.Pattern;


public class FolderSelectionsActivity extends BaseActivity {
    private static final Pattern DIR_SEPORATOR = Pattern.compile("/");
    Button New;
    Button all;
    Button cancel;
    private ListView directoryView;
    Button ok;
    TextView path;
    String primary_sd;
    String secondary_sd;
    Button storage;
    private ArrayList<File> directoryList = new ArrayList<>();
    private ArrayList<String> directoryNames = new ArrayList<>();
    private ArrayList<File> fileArrayList = new ArrayList<>();
    private ArrayList<String> fileNames = new ArrayList<>();
    int index = 0;
    File mainPath = new File(Environment.getExternalStorageDirectory() + "");
    Boolean switcher = false;
    int top = 0;



    public class C05721 implements AdapterView.OnItemClickListener {
        C05721() {
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            FolderSelectionsActivity folderSelectionsActivity = FolderSelectionsActivity.this;
            folderSelectionsActivity.index = folderSelectionsActivity.directoryView.getFirstVisiblePosition();
            int i2 = 0;
            View childAt = FolderSelectionsActivity.this.directoryView.getChildAt(0);
            FolderSelectionsActivity folderSelectionsActivity2 = FolderSelectionsActivity.this;
            if (childAt != null) {
                i2 = childAt.getTop();
            }
            folderSelectionsActivity2.top = i2;
            try {
                if (i < FolderSelectionsActivity.this.directoryList.size()) {
                    FolderSelectionsActivity.this.mainPath = (File) FolderSelectionsActivity.this.directoryList.get(i);
                    FolderSelectionsActivity.this.loadLists();
                }
            } catch (Throwable unused) {
                FolderSelectionsActivity folderSelectionsActivity3 = FolderSelectionsActivity.this;
                folderSelectionsActivity3.mainPath = folderSelectionsActivity3.mainPath;
                FolderSelectionsActivity.this.loadLists();
            }
        }
    }



    public class C05732 implements View.OnClickListener {
        C05732() {
        }

        @Override
        public void onClick(View view) {
            FolderSelectionsActivity.this.ok();
        }
    }



    public class C05743 implements View.OnClickListener {
        C05743() {
        }

        @Override
        public void onClick(View view) {
            FolderSelectionsActivity.this.finish();
        }
    }



    public class C05754 implements View.OnClickListener {
        C05754() {
        }

        @Override
        public void onClick(View view) {
            try {
                if (FolderSelectionsActivity.this.switcher.booleanValue()) {
                    FolderSelectionsActivity.this.mainPath = new File(FolderSelectionsActivity.this.primary_sd);
                    FolderSelectionsActivity.this.loadLists();
                    FolderSelectionsActivity.this.switcher = false;
                    FolderSelectionsActivity.this.storage.setText(FolderSelectionsActivity.this.getString(R.string.ext));
                    return;
                }
                FolderSelectionsActivity.this.mainPath = new File(FolderSelectionsActivity.this.secondary_sd);
                FolderSelectionsActivity.this.loadLists();
                FolderSelectionsActivity.this.switcher = true;
                FolderSelectionsActivity.this.storage.setText(FolderSelectionsActivity.this.getString(R.string.Int));
            } catch (Throwable unused) {
            }
        }
    }



    public class C05785 implements View.OnClickListener {


        class C05772 implements DialogInterface.OnClickListener {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }

            C05772() {
            }
        }

        C05785() {
        }

        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(FolderSelectionsActivity.this.getString(R.string.New));
            builder.setMessage(FolderSelectionsActivity.this.getString(R.string.CNew));
            final EditText editText = new EditText(view.getContext());
            builder.setView(editText);
            builder.setPositiveButton(FolderSelectionsActivity.this.getString(R.string.create), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String obj = editText.getText().toString();
                    if (obj != null && obj.length() > 0) {
                        new File(FolderSelectionsActivity.this.mainPath.getPath() + "/" + obj + "/").mkdirs();
                        FolderSelectionsActivity.this.loadLists();
                    }
                }
            });
            builder.setNegativeButton(FolderSelectionsActivity.this.getString(R.string.cancel), new C05772());
            builder.show();
        }
    }



    public class C05796 implements FileFilter {
        C05796() {
        }

        @Override
        public boolean accept(File file) {
            return file.isFile();
        }
    }



    public class C05807 implements FileFilter {
        C05807() {
        }

        @Override
        public boolean accept(File file) {
            return file.isDirectory();
        }
    }



    public class C05818 implements Comparator<File> {
        C05818() {
        }

        public int compare(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }
    }



    public class C05829 implements Comparator<File> {
        C05829() {
        }

        public int compare(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }
    }

    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_file_selection);
        findViews();
        initViews();



        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd(this);


    }

    private void initViews() {
        this.all.setEnabled(false);
        loadLists();
        ExtStorageSearch();
        if (this.secondary_sd == null) {
            this.storage.setEnabled(false);
        }
        this.directoryView.setOnItemClickListener(new C05721());
        this.ok.setOnClickListener(new C05732());
        this.cancel.setOnClickListener(new C05743());
        this.storage.setOnClickListener(new C05754());
        this.New.setOnClickListener(new C05785());
    }

    private void findViews() {
        this.directoryView = (ListView) findViewById(R.id.directorySelectionList);
        this.ok = (Button) findViewById(R.id.ok);
        this.all = (Button) findViewById(R.id.all);
        this.cancel = (Button) findViewById(R.id.cancel);
        this.storage = (Button) findViewById(R.id.storage);
        this.New = (Button) findViewById(R.id.New);
        this.path = (TextView) findViewById(R.id.folderpath);
    }

    @Override
    public void onBackPressed() {
        try {
            if (this.mainPath.equals(Environment.getExternalStorageDirectory().getParentFile().getParentFile())) {
                finish();
                return;
            }
            this.mainPath = this.mainPath.getParentFile();
            loadLists();
            this.directoryView.setSelectionFromTop(this.index, this.top);
        } catch (Throwable unused) {
        }
    }

    public void ok() {
        Intent intent = getIntent();
        intent.putExtra(FileSelectionActivity.FILES_TO_UPLOAD, this.mainPath);
        setResult(-1, intent);
        finish();
    }


    public void loadLists() {
        C05796 c05796 = new C05796();
        File[] listFiles = this.mainPath.listFiles(new C05807());
        if (listFiles != null && listFiles.length > 1) {
            Arrays.sort(listFiles, new C05818());
        }
        this.directoryList = new ArrayList<>();
        this.directoryNames = new ArrayList<>();
        for (File file : listFiles) {
            this.directoryList.add(file);
            this.directoryNames.add(file.getName());
        }
        this.directoryView.setAdapter((ListAdapter) new ArrayAdapter(this, 17367043, this.directoryNames));
        File[] listFiles2 = this.mainPath.listFiles(c05796);
        if (listFiles2 != null && listFiles2.length > 1) {
            Arrays.sort(listFiles2, new C05829());
        }
        this.fileArrayList = new ArrayList<>();
        this.fileNames = new ArrayList<>();
        for (File file2 : listFiles2) {
            this.fileArrayList.add(file2);
            this.fileNames.add(file2.getName());
        }
        this.path.setText(this.mainPath.toString());
        iconload();
    }

    public void iconload() {
        ArrayList<String> arrayList = this.fileNames;
        ArrayList<String> arrayList2 = this.directoryNames;
        CustomListSingleOnly customListSingleOnly = new CustomListSingleOnly(this, (String[]) arrayList2.toArray((String[]) arrayList2.toArray(new String[arrayList2.size()])), this.mainPath.getPath());
        CustomListSingleOnly customListSingleOnly2 = new CustomListSingleOnly(this, (String[]) this.fileNames.toArray((String[]) arrayList.toArray(new String[arrayList.size()])), this.mainPath.getPath());
        MergeAdapter mergeAdapter = new MergeAdapter();
        mergeAdapter.addAdapter(customListSingleOnly);
        mergeAdapter.addAdapter(customListSingleOnly2);
        this.directoryView.setAdapter((ListAdapter) mergeAdapter);
    }

    public void ExtStorageSearch() {
        this.primary_sd = System.getenv("EXTERNAL_STORAGE");
        this.secondary_sd = System.getenv("SECONDARY_STORAGE");
        if (this.primary_sd == null) {
            this.primary_sd = Environment.getExternalStorageDirectory() + "";
        }
        if (this.secondary_sd == null) {
            String[] storageDirectories = getStorageDirectories(this);
            for (String str : storageDirectories) {
                if (new File(str).exists() && new File(str).isDirectory() && !str.equals(this.primary_sd)) {
                    this.secondary_sd = str;
                    return;
                }
            }
        }
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
}

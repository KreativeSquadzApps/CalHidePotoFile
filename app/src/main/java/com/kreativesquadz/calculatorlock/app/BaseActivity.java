package com.kreativesquadz.calculatorlock.app;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kreativesquadz.calculatorlock.callbacks.OnAllAudiosLoadedListener;
import com.kreativesquadz.calculatorlock.callbacks.OnAllImagesLoadedListener;
import com.kreativesquadz.calculatorlock.callbacks.OnAllVideosLoadedListener;
import com.kreativesquadz.calculatorlock.callbacks.OnAudioLoadedListener;
import com.kreativesquadz.calculatorlock.callbacks.OnFilesLoadedListener;
import com.kreativesquadz.calculatorlock.callbacks.OnImagesLoadedListener;
import com.kreativesquadz.calculatorlock.callbacks.OnVideosLoadedListener;
import com.kreativesquadz.calculatorlock.model.AllAudioModel;
import com.kreativesquadz.calculatorlock.model.AllFilesModel;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.model.AllVideosModel;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;


public class BaseActivity extends AppCompatActivity {


    public class GetAllAudiosAsyncTask extends AsyncTask<Void, Void, ArrayList<AllAudioModel>> {
        public OnAllAudiosLoadedListener onAllAudiosLoadedListener;

        public GetAllAudiosAsyncTask() {
        }


        public ArrayList<AllAudioModel> doInBackground(Void... voidArr) {
            ArrayList<AllAudioModel> arrayList = new ArrayList<>();
            Cursor query = BaseActivity.this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if (query == null || !query.moveToFirst()) {
                return arrayList;
            }
            int columnIndex = query.getColumnIndex("_data");
            int columnIndex2 = query.getColumnIndex("date_modified");
            do {
                String string = query.getString(columnIndex);
                if (string.contains(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                    arrayList.add(new AllAudioModel(string, (long) columnIndex2));
                }
            } while (query.moveToNext());
            return arrayList;
        }


        public void onPostExecute(ArrayList<AllAudioModel> arrayList) {
            super.onPostExecute(arrayList);
            OnAllAudiosLoadedListener onAllAudiosLoadedListener = this.onAllAudiosLoadedListener;
            if (onAllAudiosLoadedListener != null) {
                onAllAudiosLoadedListener.onAllAudiosLoaded(arrayList);
            }
        }
    }


    public class GetAllImagesAsyncTask extends AsyncTask<Void, Void, ArrayList<AllImagesModel>> {
        public OnAllImagesLoadedListener onAllImagesLoadedListener;

        public GetAllImagesAsyncTask() {
        }


        public ArrayList<AllImagesModel> doInBackground(Void... voidArr) {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] strArr = {"_data", "date_modified"};
            TreeSet treeSet = new TreeSet();
            ArrayList<AllImagesModel> arrayList = new ArrayList<>();
            String[] strArr2 = null;
            Cursor query = uri != null ? BaseActivity.this.getContentResolver().query(uri, strArr, null, null, null) : null;
            if (query != null && query.moveToFirst()) {
                do {
                    String string = query.getString(0);
                    try {
                        treeSet.add(string.substring(0, string.lastIndexOf("/")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (query.moveToNext());
                strArr2 = new String[treeSet.size()];
                treeSet.toArray(strArr2);
            }
            for (int i = 0; i < treeSet.size(); i++) {
                File[] listFiles = new File(strArr2[i]).listFiles();
                if (listFiles != null) {
                    int length = listFiles.length;
                    int i2 = 0;
                    while (i2 < length) {
                        File file = listFiles[i2];
                        try {
                            if (file.isDirectory()) {
                                file.listFiles();
                            }
                            if (file.getName().contains(".jpg") || file.getName().contains(".JPG") || file.getName().contains(".jpeg") || file.getName().contains(".JPEG") || file.getName().contains(".png") || file.getName().contains(".PNG") || file.getName().contains(".gif") || file.getName().contains(".GIF") || file.getName().contains(".bmp") || file.getName().contains(".BMP")) {
                                String absolutePath = file.getAbsolutePath();
                                long lastModified = file.lastModified();
                                if (absolutePath.contains(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                                    arrayList.add(new AllImagesModel(absolutePath, lastModified));
                                }
                            }
                            i2++;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
            return arrayList;
        }


        public void onPostExecute(ArrayList<AllImagesModel> arrayList) {
            super.onPostExecute( arrayList);
            OnAllImagesLoadedListener onAllImagesLoadedListener = this.onAllImagesLoadedListener;
            if (onAllImagesLoadedListener != null) {
                onAllImagesLoadedListener.onAllImagesLoaded(arrayList);
            }
        }
    }


    public class GetAllVideosAsyncTask extends AsyncTask<Void, Void, ArrayList<AllVideosModel>> {
        public OnAllVideosLoadedListener onAllVideosLoadedListener;

        public GetAllVideosAsyncTask() {
        }


        public ArrayList<AllVideosModel> doInBackground(Void... voidArr) {
            ArrayList<AllVideosModel> arrayList = new ArrayList<>();
            Cursor query = BaseActivity.this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "date_modified"}, null, null, null);
            try {
                query.moveToFirst();
                do {
                    String string = query.getString(query.getColumnIndex("_data"));
                    String string2 = query.getString(query.getColumnIndex("_data"));
                    if (string.contains(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                        arrayList.add(new AllVideosModel(string, string2));
                    }
                } while (query.moveToNext());
                query.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList;
        }


        public void onPostExecute(ArrayList<AllVideosModel> arrayList) {
            super.onPostExecute( arrayList);
            OnAllVideosLoadedListener onAllVideosLoadedListener = this.onAllVideosLoadedListener;
            if (onAllVideosLoadedListener != null) {
                onAllVideosLoadedListener.onAllVideosLoaded(arrayList);
            }
        }
    }


    public class GetHiddenAudio extends AsyncTask<Void, Void, ArrayList<AllAudioModel>> {
        public OnAudioLoadedListener onAudioLoadedListener;

        public GetHiddenAudio() {
        }


        public ArrayList<AllAudioModel> doInBackground(Void... voidArr) {
            File[] listFiles;
            ArrayList<AllAudioModel> arrayList = new ArrayList<>();
            File file = new File(AppConstants.AUDIO_PATH);
            if (!file.exists() || (listFiles = file.listFiles()) == null) {
                return null;
            }
            for (File file2 : listFiles) {
                Log.e("PATH", "" + file2.getAbsolutePath());
                try {
                    arrayList.add(new AllAudioModel(file2.getAbsolutePath(), file2.lastModified()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return arrayList;
        }


        public void onPostExecute(ArrayList<AllAudioModel> arrayList) {
            super.onPostExecute( arrayList);
            OnAudioLoadedListener onAudioLoadedListener = this.onAudioLoadedListener;
            if (onAudioLoadedListener != null) {
                onAudioLoadedListener.onAudiosLoaded(arrayList);
            }
        }
    }


    public class GetHiddenFiles extends AsyncTask<Void, Void, ArrayList<AllFilesModel>> {
        public OnFilesLoadedListener onFilesLoadedListener;

        public GetHiddenFiles() {
        }


        public ArrayList<AllFilesModel> doInBackground(Void... voidArr) {
            File[] listFiles;
            ArrayList<AllFilesModel> arrayList = new ArrayList<>();
            File file = new File(AppConstants.FILES_PATH);
            if (!file.exists() || (listFiles = file.listFiles()) == null) {
                return null;
            }
            for (File file2 : listFiles) {
                Log.e("PATH", "" + file2.getAbsolutePath());
                try {
                    arrayList.add(new AllFilesModel(file2.getAbsolutePath(), file2.lastModified()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return arrayList;
        }


        public void onPostExecute(ArrayList<AllFilesModel> arrayList) {
            super.onPostExecute( arrayList);
            OnFilesLoadedListener onFilesLoadedListener = this.onFilesLoadedListener;
            if (onFilesLoadedListener != null) {
                onFilesLoadedListener.onFilesLoaded(arrayList);
            }
        }
    }


    public class GetHiddenImages extends AsyncTask<Void, Void, ArrayList<AllImagesModel>> {
        public OnImagesLoadedListener onImagesLoadedListener;

        public GetHiddenImages() {
        }


        public ArrayList<AllImagesModel> doInBackground(Void... voidArr) {
            File[] listFiles;
            ArrayList<AllImagesModel> arrayList = new ArrayList<>();
            File file = new File(AppConstants.IMAGE_PATH);
            if (!file.exists() || (listFiles = file.listFiles()) == null) {
                return null;
            }
            for (File file2 : listFiles) {
                Log.e("PATH", "" + file2.getAbsolutePath());
                try {
                    arrayList.add(new AllImagesModel(file2.getAbsolutePath(), file2.lastModified()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return arrayList;
        }


        public void onPostExecute(ArrayList<AllImagesModel> arrayList) {
            super.onPostExecute(arrayList);
            OnImagesLoadedListener onImagesLoadedListener = this.onImagesLoadedListener;
            if (onImagesLoadedListener != null) {
                onImagesLoadedListener.onImagesLoaded(arrayList);
            }
        }
    }


    public class GetHiddenVideos extends AsyncTask<Void, Void, ArrayList<AllVideosModel>> {
        public OnVideosLoadedListener onVideosLoadedListener;

        public GetHiddenVideos() {
        }


        public ArrayList<AllVideosModel> doInBackground(Void... voidArr) {
            File[] listFiles;
            ArrayList<AllVideosModel> arrayList = new ArrayList<>();
            File file = new File(AppConstants.VIDEO_PATH);
            if (!file.exists() || (listFiles = file.listFiles()) == null) {
                return null;
            }
            for (File file2 : listFiles) {
                Log.e("PATH", "" + file2.getAbsolutePath());
                try {
                    arrayList.add(new AllVideosModel(file2.getAbsolutePath(), file2.lastModified()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return arrayList;
        }


        public void onPostExecute(ArrayList<AllVideosModel> arrayList) {
            super.onPostExecute( arrayList);
            OnVideosLoadedListener onVideosLoadedListener = this.onVideosLoadedListener;
            if (onVideosLoadedListener != null) {
                onVideosLoadedListener.onVideosLoaded(arrayList);
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }
}

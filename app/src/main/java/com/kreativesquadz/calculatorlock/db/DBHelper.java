package com.kreativesquadz.calculatorlock.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.model.AllVideosModel;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    private static final String KEY_NEW_PATH = "new_path";
    private static final String KEY_OLD_PATH = "old_path";
    private static final String TABLE_IMAGE = "image_table";
    private static final String TABLE_VIDEO = "video_table";
    Context context;

    public DBHelper(Context context) {
        super(context, AppConstants.EXPORT_FOLDER, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE image_table(_id INTEGER PRIMARY KEY AUTOINCREMENT,old_path TEXT,new_path TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE video_table(_id INTEGER PRIMARY KEY AUTOINCREMENT,old_path TEXT,new_path TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS image_table");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS video_table");
        onCreate(sQLiteDatabase);
    }

    public void addImage(AllImagesModel allImagesModel) {
        Log.e("addImage", "Operation Started");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_OLD_PATH, allImagesModel.getImagePath());
        contentValues.put(KEY_NEW_PATH, allImagesModel.getNewPath());
        writableDatabase.insertWithOnConflict(TABLE_IMAGE, null, contentValues, 5);
        writableDatabase.close();
        Log.e("addImage", "Operation Completed");
    }

    public ArrayList<AllImagesModel> getImages() {
        ArrayList<AllImagesModel> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT  * FROM image_table", null);
        if (rawQuery.moveToFirst()) {
            do {
                AllImagesModel allImagesModel = new AllImagesModel();
                allImagesModel.setImageId(Integer.parseInt(rawQuery.getString(0)));
                allImagesModel.setImagePath(rawQuery.getString(1));
                allImagesModel.setNewPath(rawQuery.getString(2));
                arrayList.add(allImagesModel);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            writableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        writableDatabase.close();
        return arrayList;
    }

    public void deleteImage(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM image_table WHERE _id=" + j);
        writableDatabase.close();
    }

    public int getImageCount() {
        return (int) DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_IMAGE);
    }

    public void addVideo(AllVideosModel allVideosModel) {
        Log.e("addVideo", "Operation Started");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_OLD_PATH, allVideosModel.getOldPath());
        contentValues.put(KEY_NEW_PATH, allVideosModel.getNewPath());
        writableDatabase.insertWithOnConflict(TABLE_VIDEO, null, contentValues, 5);
        writableDatabase.close();
        Log.e("addVideo", "Operation Completed");
    }

    public ArrayList<AllVideosModel> getVideos() {
        ArrayList<AllVideosModel> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT  * FROM video_table", null);
        if (rawQuery.moveToFirst()) {
            do {
                AllVideosModel allVideosModel = new AllVideosModel();
                allVideosModel.setVideoId(Integer.parseInt(rawQuery.getString(0)));
                allVideosModel.setOldPath(rawQuery.getString(1));
                allVideosModel.setNewPath(rawQuery.getString(2));
                arrayList.add(allVideosModel);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            writableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        writableDatabase.close();
        return arrayList;
    }

    public void deleteVideo(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM video_table WHERE _id=" + j);
        writableDatabase.close();
    }

    public int getVideoCount() {
        return (int) DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_VIDEO);
    }
}

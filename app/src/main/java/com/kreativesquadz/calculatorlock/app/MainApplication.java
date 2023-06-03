package com.kreativesquadz.calculatorlock.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.kreativesquadz.calculatorlock.R;

import java.text.DecimalFormat;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {
    private static final String CONSTANAT_SECURITY_ANSWER = "constant_answer";
    private static final String CONSTANAT_SECURITY_QUESTION = "constant_question";
    private static final String CONSTANT_AUDIO_COUNT = "constant_audio_count";
    private static final String CONSTANT_EMAIL = "constant_email";
    private static final String CONSTANT_FILES_COUNT = "constant_files_count";
    private static final String CONSTANT_FP_HINT = "consatnt_fp_hint";
    private static final String CONSTANT_IMAGE_COUNT = "constant_image_count";
    private static final String CONSTANT_PASSWORD = "constant_password";
    private static final String CONSTANT_UNINSTALL = "constant_uninstall";
    private static final String CONSTANT_VIDEO_COUNT = "constant_video_count";
    private static MainApplication mInstance;
    private static SharedPreferences sharedPreferences;


    public void LogFirebaseEvent(String str, String str2) {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("Montserrat-Regular.ttf").setFontAttrId(R.attr.fontPath).build());
    }

    public synchronized void savePassword(String str) {
        sharedPreferences.edit().putString(CONSTANT_PASSWORD, str).apply();
    }

    public synchronized String getPassword() {
        return sharedPreferences.getString(CONSTANT_PASSWORD, "");
    }

    public synchronized String getEmail() {
        return sharedPreferences.getString(CONSTANT_EMAIL, "");
    }

    public synchronized void setShowFPHint(boolean z) {
        sharedPreferences.edit().putBoolean(CONSTANT_FP_HINT, z).apply();
    }

    public synchronized void saveImageCount(int i) {
        sharedPreferences.edit().putInt(CONSTANT_IMAGE_COUNT, i).apply();
    }

    public synchronized void saveVideoCount(int i) {
        sharedPreferences.edit().putInt(CONSTANT_VIDEO_COUNT, i).apply();
    }

    public synchronized void saveAudioCount(int i) {
        sharedPreferences.edit().putInt(CONSTANT_AUDIO_COUNT, i).apply();
    }

    public synchronized void saveFilesCount(int i) {
        sharedPreferences.edit().putInt(CONSTANT_FILES_COUNT, i).apply();
    }

    public static synchronized MainApplication getInstance() {
        MainApplication mainApplication;
        synchronized (MainApplication.class) {
            synchronized (MainApplication.class) {
                mainApplication = mInstance;
            }
            return mainApplication;
        }
    }

    public synchronized boolean isUninstallProtected() {
        return sharedPreferences.getBoolean(CONSTANT_UNINSTALL, false);
    }

    public synchronized void setUninstallProtected(boolean z) {
        sharedPreferences.edit().putBoolean(CONSTANT_UNINSTALL, z).commit();
    }

    public synchronized void setSecurityQuestion(String str) {
        sharedPreferences.edit().putString(CONSTANAT_SECURITY_QUESTION, str).commit();
    }

    public synchronized String getSecurityQuestion() {
        return sharedPreferences.getString(CONSTANAT_SECURITY_QUESTION, "");
    }

    public synchronized void setSecurityAnswer(String str) {
        sharedPreferences.edit().putString(CONSTANAT_SECURITY_ANSWER, str).commit();
    }

    public synchronized String getSecurityAnswer() {
        return sharedPreferences.getString(CONSTANAT_SECURITY_ANSWER, "");
    }

    public synchronized String getFileSize(long j) {
        String str;
        double d = (double) j;
        Double.isNaN(d);
        double d2 = d / 1024.0d;
        double d3 = d2 / 1024.0d;
        double d4 = d3 / 1024.0d;
        double d5 = d4 / 1024.0d;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (d5 > 1.0d) {
            str = decimalFormat.format(d5).concat(" TB");
        } else if (d4 > 1.0d) {
            str = decimalFormat.format(d4).concat(" GB");
        } else if (d3 > 1.0d) {
            str = decimalFormat.format(d3).concat(" MB");
        } else if (d2 > 1.0d) {
            str = decimalFormat.format(d2).concat(" KB");
        } else {
            str = decimalFormat.format(d).concat(" Bytes");
        }
        return str;
    }

}

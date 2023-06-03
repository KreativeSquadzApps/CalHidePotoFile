package com.kreativesquadz.calculatorlock.album;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.AppConstants;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.audios.AudiosActivity;
import com.kreativesquadz.calculatorlock.db.DBHelper;
import com.kreativesquadz.calculatorlock.files.FilesActivity;
import com.kreativesquadz.calculatorlock.image.ImagesActivity;
import com.kreativesquadz.calculatorlock.utils.PolicyManager;
import com.kreativesquadz.calculatorlock.video.VideoActivity;

import java.util.Random;


public class HomeFragment extends Fragment {
    LinearLayout audios;
    DBHelper dbHelper;
    LinearLayout files;
    FrameLayout frameLayout;
    LinearLayout image;
    private String type;
    LinearLayout videos;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.home_laoyut, viewGroup, false);
        ButterKnife.bind(this, inflate);
        findViews(inflate);
        return inflate;
    }

    private void findViews(View view) {
        this.image = (LinearLayout) view.findViewById(R.id.image);
        this.audios = (LinearLayout) view.findViewById(R.id.audios);
        this.videos = (LinearLayout) view.findViewById(R.id.videos);
        this.files = (LinearLayout) view.findViewById(R.id.files);
        this.frameLayout = (FrameLayout) view.findViewById(R.id.fl_adplaceholder);
        this.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                if (new Random().nextInt(5) + 0 != 0) {
                    HomeFragment homeFragment = HomeFragment.this;
                    homeFragment.startActivityForResult(new Intent(homeFragment.getActivity(), ImagesActivity.class), AppConstants.REFRESH_LIST);
                } else {
                    HomeFragment homeFragment2 = HomeFragment.this;
                    homeFragment2.startActivityForResult(new Intent(homeFragment2.getActivity(), ImagesActivity.class), AppConstants.REFRESH_LIST);
                }
            }
        });
        this.audios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                if (new Random().nextInt(5) + 0 != 0) {
                    HomeFragment homeFragment = HomeFragment.this;
                    homeFragment.startActivityForResult(new Intent(homeFragment.getActivity(), AudiosActivity.class), AppConstants.REFRESH_LIST);
                } else {
                    HomeFragment homeFragment2 = HomeFragment.this;
                    homeFragment2.startActivityForResult(new Intent(homeFragment2.getActivity(), AudiosActivity.class), AppConstants.REFRESH_LIST);
                }
            }
        });
        this.videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                if (new Random().nextInt(5) + 0 != 0) {
                    HomeFragment homeFragment = HomeFragment.this;
                    homeFragment.startActivityForResult(new Intent(homeFragment.getActivity(), VideoActivity.class), AppConstants.REFRESH_LIST);
                } else {
                    HomeFragment homeFragment2 = HomeFragment.this;
                    homeFragment2.startActivityForResult(new Intent(homeFragment2.getActivity(), VideoActivity.class), AppConstants.REFRESH_LIST);
                }
            }
        });
        this.files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                if (new Random().nextInt(5) + 0 != 0) {
                    HomeFragment homeFragment = HomeFragment.this;
                    homeFragment.startActivityForResult(new Intent(homeFragment.getActivity(), FilesActivity.class), AppConstants.REFRESH_LIST);
                } else  {
                    HomeFragment homeFragment2 = HomeFragment.this;
                    homeFragment2.startActivityForResult(new Intent(homeFragment2.getActivity(), FilesActivity.class), AppConstants.REFRESH_LIST);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getActivity().getSystemService("device_policy");
        ComponentName componentName = new ComponentName(getActivity(), PolicyManager.class);
        if (devicePolicyManager == null || !devicePolicyManager.isAdminActive(componentName)) {
            MainApplication.getInstance().LogFirebaseEvent("1", "Home");
            AddMobe();
            this.dbHelper = new DBHelper(getActivity());
        } else {
            MainApplication.getInstance().LogFirebaseEvent("1", "Home");
            AddMobe();
            this.dbHelper = new DBHelper(getActivity());
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_EXTERNAL_STORAGE") != 0 || ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
            }
        }
    }

    private void enableAdmin() {
        ComponentName componentName = new ComponentName(getActivity(), PolicyManager.class);
        Log.e("DeviceAdminActive==", "" + componentName);
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", componentName);
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "Disable app");
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "After activating admin, you will be able to block application uninstallation.");
        startActivityForResult(intent, 121);
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100) {
            if (iArr.length <= 0 || iArr[0] != 0 || iArr[1] != 0) {
                final AlertDialog create = new AlertDialog.Builder(getActivity()).create();
                create.setTitle("Grant Permission");
                create.setCancelable(false);
                create.setMessage("Please grant all permissions to access additional functionality.");
                create.setButton(-1, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        create.dismiss();
                        HomeFragment.this.getActivity().finish();
                    }
                });
                create.show();
            }
        }
    }

    private void AddMobe() {
        String str = this.type;
        if (str != null) {
            if (str.equals(AppConstants.IMAGE)) {
                startActivityForResult(new Intent(getActivity(), ImagesActivity.class), AppConstants.REFRESH_LIST);
            } else {
                startActivityForResult(new Intent(getActivity(), VideoActivity.class), AppConstants.REFRESH_LIST);
            }
        }
    }

}

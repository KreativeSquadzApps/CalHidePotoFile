package com.kreativesquadz.calculatorlock.fullscreenimage.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.fullscreenimage.FullScreenImageActivity;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import com.kreativesquadz.calculatorlock.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FullScreenImageAdapter extends PagerAdapter {
    private final GestureDetector detector;
    private Context mContext;
    private List<AllImagesModel> models = new ArrayList();

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    class C05891 implements View.OnClickListener {
        C05891() {
        }

        @Override
        public void onClick(View view) {
            if (FullScreenImageAdapter.this.mContext instanceof FullScreenImageActivity) {
                ((FullScreenImageActivity) FullScreenImageAdapter.this.mContext).showHideUI();
            }
        }
    }


    class GestureTap extends GestureDetector.SimpleOnGestureListener {
        @Override

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        GestureTap() {
        }

        @Override

        public boolean onDoubleTap(MotionEvent motionEvent) {
            Log.i("onDoubleTap :", "" + motionEvent.getAction());
            return true;
        }

        @Override

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            ((FullScreenImageActivity) FullScreenImageAdapter.this.mContext).showHideUI();
            return true;
        }
    }

    public FullScreenImageAdapter(Context context) {
        this.detector = new GestureDetector(context, new GestureTap());
        this.mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.raw_fullscreen, viewGroup, false);
        PhotoView photoView = (PhotoView) viewGroup2.findViewById(R.id.image);
        photoView.setOnClickListener(new C05891());
        File file = new File(this.models.get(i).getImagePath());
        if (!this.models.get(i).getImagePath().isEmpty()) {
            Glide.with(this.mContext).load(Uri.fromFile(file)).into(photoView);
        }
        viewGroup.addView(viewGroup2);
        return viewGroup2;
    }

    @Override
    public int getItemPosition(Object obj) {
        View view = (View) obj;
        if (this.models.contains(view)) {
            return this.models.indexOf(view);
        }
        return -2;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override
    public int getCount() {
        List<AllImagesModel> list = this.models;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void addItems(List<AllImagesModel> list) {
        this.models = list;
        notifyDataSetChanged();
    }

    public void removeItem(AllImagesModel allImagesModel) {
        File file = new File(allImagesModel.getImagePath());
        if (file.exists()) {
            file.delete();
        }
        this.models.remove(allImagesModel);
        notifyDataSetChanged();
    }
}

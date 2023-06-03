package com.kreativesquadz.calculatorlock.image.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kreativesquadz.calculatorlock.R;

import java.util.ArrayList;


public class AdapterImageFolder extends ArrayAdapter<ModelImage> {
    ArrayList<ModelImage> al_menu;
    Context context;
    ViewHolder viewHolder;

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    public AdapterImageFolder(Context context, ArrayList<ModelImage> arrayList) {
        super(context, (int) R.layout.adapter_photosfolder, arrayList);
        this.al_menu = new ArrayList<>();
        this.al_menu = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.e("ADAPTER LIST SIZE", this.al_menu.size() + "");
        return this.al_menu.size();
    }

    @Override
    public int getViewTypeCount() {
        if (this.al_menu.size() > 0) {
            return this.al_menu.size();
        }
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            this.viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_photosfolder, viewGroup, false);
            this.viewHolder.tv_foldern = (TextView) view.findViewById(R.id.tv_folder);
            this.viewHolder.tv_foldersize = (TextView) view.findViewById(R.id.tv_folder2);
            this.viewHolder.iv_image = (ImageView) view.findViewById(R.id.iv_image);
            view.setTag(this.viewHolder);
        } else {
            this.viewHolder = (ViewHolder) view.getTag();
        }
        this.viewHolder.tv_foldern.setText(this.al_menu.get(i).getStr_folder());
        TextView textView = this.viewHolder.tv_foldersize;
        textView.setText(this.al_menu.get(i).getAl_Imagepath().size() + "");
        Log.i("path", "" + this.al_menu.get(i).getAl_Imagepath().get(0));
        RequestManager with = Glide.with(this.context);
        with.load("file://" + this.al_menu.get(i).getAl_Imagepath().get(0)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(this.viewHolder.iv_image);
        return view;
    }


    private static class ViewHolder {
        ImageView iv_image;
        TextView tv_foldern;
        TextView tv_foldersize;

        private ViewHolder() {
        }
    }
}

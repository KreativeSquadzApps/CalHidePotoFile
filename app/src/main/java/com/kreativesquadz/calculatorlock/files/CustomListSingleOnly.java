package com.kreativesquadz.calculatorlock.files;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kreativesquadz.calculatorlock.R;
import com.squareup.picasso.Picasso;

import java.io.File;


public class CustomListSingleOnly extends ArrayAdapter<String> {
    String ParentFolder;
    private final Activity context;
    private final String[] web;

    public CustomListSingleOnly(Activity activity, String[] strArr, String str) {
        super(activity, (int) R.layout.list_single_only, strArr);
        this.context = activity;
        this.web = strArr;
        this.ParentFolder = str;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = this.context.getLayoutInflater().inflate(R.layout.list_single_only, (ViewGroup) null, true);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.img);
        ((TextView) inflate.findViewById(R.id.txt)).setText(this.web[i]);
        if (new File(this.ParentFolder + "/" + this.web[i]).isDirectory()) {
            imageView.setImageResource(R.drawable.folder);
        } else {
            if (new File(this.ParentFolder + "/" + this.web[i]).isFile()) {
                Picasso with = Picasso.get();
                with.load(new File(this.ParentFolder + "/" + this.web[i])).placeholder(R.drawable.document_gray).resize(50, 50).into(imageView);
            }
        }
        return inflate;
    }
}

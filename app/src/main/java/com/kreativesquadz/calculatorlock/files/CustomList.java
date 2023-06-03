package com.kreativesquadz.calculatorlock.files;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kreativesquadz.calculatorlock.R;
import com.squareup.picasso.Picasso;
import java.io.File;


public class CustomList extends ArrayAdapter<String> {
    String ParentFolder;
    private final Activity context;
    int selectedPosition = -1;
    private final String[] web;

    public CustomList(Activity activity, String[] strArr, String str) {
        super(activity, (int) R.layout.list_single, strArr);
        this.context = activity;
        this.web = strArr;
        this.ParentFolder = str;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View inflate = this.context.getLayoutInflater().inflate(R.layout.list_single, (ViewGroup) null, true);
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.myCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (checkBox.isChecked()) {
                    ((FileSelectionActivity) CustomList.this.context).setFiles(true, CustomList.this.web[i]);
                } else {
                    ((FileSelectionActivity) CustomList.this.context).setFiles(false, CustomList.this.web[i]);
                }
            }
        });
        ((TextView) inflate.findViewById(R.id.txt)).setText(this.web[i]);
        Picasso with = Picasso.get();
        with.load(new File(this.ParentFolder + "/" + this.web[i])).placeholder(R.drawable.document).resize(50, 50).into((ImageView) inflate.findViewById(R.id.img));
        return inflate;
    }

    private void makeOtherUnChecked(int i, CheckBox checkBox) {
        for (int i2 = 0; i2 < this.web.length; i2++) {
            if (i2 != i) {
                checkBox.setChecked(false);
            }
        }
        notifyDataSetChanged();
    }
}

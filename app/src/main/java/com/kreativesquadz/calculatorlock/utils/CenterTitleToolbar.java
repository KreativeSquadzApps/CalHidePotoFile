package com.kreativesquadz.calculatorlock.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.kreativesquadz.calculatorlock.R;


public class CenterTitleToolbar extends Toolbar {
    private TextView titleView;

    public CenterTitleToolbar(Context context) {
        this(context, null);
    }

    public CenterTitleToolbar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public CenterTitleToolbar(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.titleView = new TextView(getContext());
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{R.attr.titleTextAppearance}, i, 0);
        try {
            int resourceId = obtainStyledAttributes.getResourceId(0, 0);
            if (resourceId > 0) {
                this.titleView.setTextAppearance(context, resourceId);
            }
            addView(this.titleView, new LayoutParams(-2, -2));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }


    @Override
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.titleView.setX((float) ((getWidth() - this.titleView.getWidth()) / 2));
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.titleView.setText(charSequence);
    }
}

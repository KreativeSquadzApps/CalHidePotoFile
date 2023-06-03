package com.kreativesquadz.calculatorlock.files;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;


public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
    private List<Checkable> checkableList;
    private boolean isChecked;

    public CheckableRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialise(attributeSet);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialise(attributeSet);
    }

    public CheckableRelativeLayout(Context context, int i) {
        super(context);
        initialise(null);
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void setChecked(boolean z) {
        for (Checkable checkable : this.checkableList) {
            checkable.setChecked(z);
        }
    }

    @Override
    public void toggle() {
        this.isChecked = !this.isChecked;
        for (Checkable checkable : this.checkableList) {
            checkable.toggle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            findCheckableChildren(getChildAt(i));
        }
    }

    private void initialise(AttributeSet attributeSet) {
        this.isChecked = false;
        this.checkableList = new ArrayList(5);
    }

    private void findCheckableChildren(View view) {
        if (view instanceof Checkable) {
            this.checkableList.add((Checkable) view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                findCheckableChildren(viewGroup.getChildAt(i));
            }
        }
    }
}

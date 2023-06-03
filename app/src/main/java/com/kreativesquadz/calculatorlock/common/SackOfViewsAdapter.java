package com.kreativesquadz.calculatorlock.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;


public class SackOfViewsAdapter extends BaseAdapter {
    private List<View> views;

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    public SackOfViewsAdapter(int i) {
        this.views = null;
        this.views = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            this.views.add(null);
        }
    }

    public SackOfViewsAdapter(List<View> list) {
        this.views = null;
        this.views = list;
    }

    @Override
    public Object getItem(int i) {
        return this.views.get(i);
    }

    @Override
    public int getCount() {
        return this.views.size();
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = this.views.get(i);
        if (view2 != null) {
            return view2;
        }
        View newView = newView(i, viewGroup);
        this.views.set(i, newView);
        return newView;
    }

    protected View newView(int i, ViewGroup viewGroup) {
        throw new RuntimeException("You must override newView()!");
    }
}

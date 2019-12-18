package com.example.imagegalleryapp.adapter;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    protected OnItemClickListener listener;
    private GestureDetector gestureDetector;
    @Nullable
    private View childView;
    private int childViewPosition;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        this.gestureDetector = new GestureDetector(context, new GestureListener());
        this.listener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent event) {
        childView = view.findChildViewUnder(event.getX(), event.getY());
        childViewPosition = view.getChildPosition(childView);

        return childView != null && gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent event) {
    }


    public interface OnItemClickListener {
        public void onItemClick(View childView, int position);

        public void onItemLongPress(View childView, int position);

        public void onOtherViewClick(View childView, int position);
    }

    public static abstract class SimpleOnItemClickListener implements OnItemClickListener {
        public void onItemClick(View childView, int position) {

        }

        public void onItemLongPress(View childView, int position) {

        }
    }

    protected class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            if (childView != null) {
                listener.onItemClick(childView, childViewPosition);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            if (childView != null) {
                listener.onItemLongPress(childView, childViewPosition);
            }
        }

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

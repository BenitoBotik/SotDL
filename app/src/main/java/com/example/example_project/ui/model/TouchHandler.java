package com.example.example_project.ui.model;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.example_project.R;

public class TouchHandler implements View.OnTouchListener {
    private float dX, dY;
    private ConstraintLayout layout;
    private ImageView icon;
    private ImageView bucket;

    public TouchHandler(ConstraintLayout layout, ImageView icon, ImageView bucket) {
        this.layout = layout;
        this.icon = icon;
        this.bucket = bucket;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Get the initial touch position
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                // Update the position of the ImageView based on touch events
                float newX = event.getRawX() + dX;
                float newY = event.getRawY() + dY;

                // Get the boundaries of the parent ConstraintLayout
                int parentWidth = this.layout.getWidth();
                int parentHeight = this.layout.getHeight();

                // Adjust the position of the ImageView to stay within the boundaries
                newX = Math.max(0, Math.min(newX, parentWidth - view.getWidth()));
                newY = Math.max(0, Math.min(newY, parentHeight - view.getHeight()));

                view.animate()
                        .x(newX)
                        .y(newY)
                        .setDuration(0)
                        .start();

                // Check if the ImageView is overlapping the Bucket view
                if (isViewOverlapping(icon, bucket)) {
                    // If the ImageView is overlapping, change the Bucket to an open bucket
                    bucket.setImageResource(R.drawable.bucket_opened);
                } else {
                    // Otherwise, change the Bucket back to a closed bucket
                    bucket.setImageResource(R.drawable.bucket_closed);
                }
                break;
            case MotionEvent.ACTION_UP:
                // When the touch gesture is released, check if the ImageView is over the Bucket view
                if (isViewOverlapping(icon, bucket)) {
                    // If it is, delete the ImageView and change the Bucket back to a closed bucket
                    ((ViewGroup) icon.getParent()).removeView(icon);
                    bucket.setImageResource(R.drawable.bucket_closed);
                }
                break;
            default:
                return false;
        }
        return true;
    }

    // Helper function to check if two views are overlapping
    public static boolean isViewOverlapping(View firstView, View secondView) {
        Rect firstRect = new Rect();
        firstView.getHitRect(firstRect);
        Rect secondRect = new Rect();
        secondView.getHitRect(secondRect);
        return Rect.intersects(firstRect, secondRect);
    }
}

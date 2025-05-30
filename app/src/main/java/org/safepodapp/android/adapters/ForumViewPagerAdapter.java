package org.safepodapp.android.adapters;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.safepodapp.R;
import org.safepodapp.android.beans.ForumPost;

import java.util.ArrayList;

public class ForumViewPagerAdapter extends PagerAdapter {
    private final ArrayList<ForumPost> forumPosts = new ArrayList<>();

    public int getCount() {
        return forumPosts.size();
    }

    @NonNull
    @SuppressLint("RtlHardcoded")
    public Object instantiateItem(View collection, int position) {

        ScrollView scroll = new ScrollView(collection.getContext());
        LinearLayout layout = new LinearLayout(collection.getContext());

        LayoutParams layoutParameters = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);

        layout.setLayoutParams(layoutParameters);

        TextView location = new TextView(collection.getContext());
        location.setTextColor(collection.getContext().getResources().getColor(R.color.light));
        location.setGravity(Gravity.LEFT);
        location.setTextSize(28);
        location.setLineSpacing(0.0f, 1.2f);
        Typeface face = Typeface.createFromAsset(collection.getContext().getAssets(), "JosefinSans-Regular.ttf");
        location.setTypeface(face);
//        if (forumPosts.get(position).getCity().compareTo("") == 0 && forumPosts.get(position).getCity().compareTo("") == 0)
//            location.setText("Location : Not Specified");
//        else
//            location.setText(forumPosts.get(position).getCity() + ", " + forumPosts.get(position).getState());
        layout.addView(location);

        TextView when = new TextView(collection.getContext());
        when.setTextColor(collection.getContext().getResources().getColor(R.color.light));
        when.setGravity(Gravity.LEFT);
        when.setTextSize(28);
        when.setLineSpacing(0.0f, 1.2f);
        when.setTypeface(face);
//        if (forumPosts.get(position).getMonth().compareTo("0") == 0 && forumPosts.get(position).getMonth().compareTo("0") == 0 && forumPosts.get(position).getMonth().compareTo("0") == 0)
//            when.setText("Date and Time : Not Specified \n\n");
//        else
//            when.setText("Date and Time : " + forumPosts.get(position).getMonth() + "-" + forumPosts.get(position).getDay() + "-" + forumPosts.get(position).getDay() + "\n\n");
        layout.addView(when);

        TextView description = new TextView(collection.getContext());
        description.setTextColor(collection.getContext().getResources().getColor(R.color.light));
        description.setGravity(Gravity.LEFT);
        description.setTextSize(28);
        description.setTypeface(face);
        description.setLineSpacing(0.0f, 1.2f);
        description.setText(forumPosts.get(position).getBody());
        layout.addView(description);
        scroll.addView(layout);

        ((ViewPager) collection).addView(scroll, 0);

        return scroll;
    }

    @Override
    public void destroyItem(@NonNull View arg0, int arg1, @NonNull Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
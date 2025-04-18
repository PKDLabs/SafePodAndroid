package org.safepodapp.android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.safepodapp.R;
import org.safepodapp.android.beans.ForumPost;

import java.util.List;

public class ForumPostsListAdapter extends ArrayAdapter<ForumPost> {
    private final List<ForumPost> postList;
    private final Context context;

    public ForumPostsListAdapter(Context context, int resource,
                                 List<ForumPost> objects) {
        super(context, resource, objects);

        this.context = context;
        this.postList = objects;
        context.getPackageManager();
    }

    @Override
    public int getCount() {
        return ((postList != null) ? postList.size() : 0);
    }

    @Override
    public ForumPost getItem(int position) {
        return ((postList != null) ? postList.get(position) : null);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.posts_list_item, null);
        }

        ForumPost data = postList.get(position);

        if (data != null) {
            TextView postTitle = (TextView) view.findViewById(R.id.textViewPostTags);
            TextView postBody = (TextView) view.findViewById(R.id.textViewPostBody);

            postTitle.setText(data.getTags());
            postBody.setText(data.getBody());
        }
        return view;
    }

}
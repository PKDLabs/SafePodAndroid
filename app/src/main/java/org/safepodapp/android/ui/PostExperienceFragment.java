package org.safepodapp.android.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.safepodapp.R;
import org.safepodapp.android.SafePodApplication;
import org.safepodapp.android.beans.ForumPost;
import org.safepodapp.android.util.NetworkServices;

import com.google.gson.*;

public class PostExperienceFragment extends Fragment {

    private EditText postBody;
    private Button postButton;
//    private Button addLocation;
//    private Button addDate;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post_experience, container, false);

        postBody = (EditText) rootView.findViewById(R.id.writePost);
        postButton = (Button) rootView.findViewById(R.id.post_experience_button);
//        addLocation = (Button) rootView.findViewById(R.id.addLocation);
//        addDate = (Button) rootView.findViewById(R.id.addDate);

        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M)
            // only for versions older than Marhsmallow
            postBody.setTextColor(getResources().getColor(R.color.light));
        else
            postBody.setTextColor(getResources().getColor(R.color.light, rootView.getContext().getTheme()));

        postBody.setGravity(Gravity.LEFT);
        postBody.setTextSize(28);
        Typeface face = Typeface.createFromAsset(container.getContext().getAssets(), "JosefinSans-Regular.ttf");
        postBody.setTypeface(face);
        postBody.setLineSpacing(0.0f, 1.2f);
        
        /*addLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int PLACE_PICKER_REQUEST = 1;
				PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

				startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

			}
		});
        
        addDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = TimePickerFragment.newInstance();
				newFragment = DatePickerFragment.newInstance();
		        newFragment.show(getFragmentManager().beginTransaction(), "date_picker");
		        

			}
		});*/

        postButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new PostExperience().execute();
                TopPostsFragment forumFragment = new TopPostsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(container.getId(), forumFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return rootView;
    }

    class PostExperience extends AsyncTask<Void, Integer, String> {
        protected void onPreExecute() {
            Log.d(SafePodApplication.getDebugTag(), "On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            Log.d(SafePodApplication.getDebugTag(), "On doInBackground...");

            ForumPost forumPost = new ForumPost();


            forumPost.setBody(String.valueOf(postBody.getText()));

            Gson gson = new Gson();
            int id = MainActivity.sharedPreferences.getInt("Id", 0);
            forumPost.setId(id);
//             MainActivity.Id
            String json = gson.toJson(forumPost);

            try {
//				String result = 
                NetworkServices.sendPost("http://safepodapp.org/forum/post/new", json);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {
                publishProgress(i);
            }
            return "You are at PostExecute";
        }

        protected void onProgressUpdate(Integer... a) {
        }

        protected void onPostExecute(String result) {

            Toast.makeText(getActivity(),
                    "Your post has been sent!",
                    Toast.LENGTH_LONG).show();
        }
    }

}
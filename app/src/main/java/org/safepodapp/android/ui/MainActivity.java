package org.safepodapp.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.safepodapp.R;
import org.safepodapp.android.FullscreenActivity;
import org.safepodapp.android.SafePodApplication;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    long Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        createShortCut();

        setUIElements();
        Id = getIntent().getLongExtra("RandVal", 0);
//        setDefaultSharedPreferences();
        sharedPreferences = FullscreenActivity.sharedPreferences;
    }

//    public void test() {
//        int hash = 0;
//        try{
//            PackageManager packageManager = getPackageManager();
//            String packageName = getPackageName();
//            PackageInfo info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
//            Signature[] signs = info.signatures;
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(signs[0].toByteArray()));
//            PublicKey key = cert.getPublicKey();
//            hash = ((RSAPublicKey) key).getModulus().hashCode();
//        } catch(Exception e){
//            Log.d(SafePodApplication.getDebugTag(), "Some error in signature extraction");
//        }
//
//        TextView tv = ((TextView)findViewById(R.id.tv));
//        tv.setText("The Stack Exchange app's signature hash is " + hash + ".");
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
//    }

    private void setUIElements() {
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SafePodApplication.getSharedPreference(), Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostExperienceFragment postExperienceFragment = new PostExperienceFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.container, postExperienceFragment);
                transaction.addToBackStack(null);

                transaction.commit();
                /**
                 * Providing lightweight, quick feedback about an operation is a perfect opportunity to use a snackbar. Snackbars are shown on the bottom of the screen and contain text with an optional single action.
                 * They automatically time out after the given time length by animating off the screen. In addition, users can swipe them away before the timeout.
                 */
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        defaultFragmentLoad();
    }

    private void defaultFragmentLoad() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new TopPostsFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        //This is a bad hack find out a better way
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        Log.d(SafePodApplication.getDebugTag(), "came after query");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(SafePodApplication.getDebugTag(), "submit");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("query", searchView.getQuery().toString());
                editor.commit();
                Log.d(SafePodApplication.getDebugTag(), searchView.getQuery().toString());

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, new QueryPostsFragment())
                        .commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.d(SafePodApplication.getDebugTag(),"change");
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the Home action
//            Log.d(SafePodApplication.getDebugTag(), "I came into the listener");
            fragment = new TopPostsFragment();
        } else if (id == R.id.nav_my_posts) {
            fragment = new MyPostsFragment();
        } else if (id == R.id.nav_about) {
            fragment = new AboutUsFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // update the main content by replacing fragments
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit();
        } else {
            Log.e(SafePodApplication.getDebugTag(), "Error");
        }

        return true;
    }

//    private void setDefaultSharedPreferences() {
//        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
////        final Calendar c = Calendar.getInstance();
////        int year = c.get(Calendar.YEAR);
////        int month = c.get(Calendar.MONTH);
////        int day = c.get(Calendar.DAY_OF_MONTH);
////        int hour = 23; //Default expectation is you will reach home at 11
////        int minute = 0;
////
////        if (sharedPreferences.getInt(SafePodApplication.getYear(), 1970) == 1970)
////            editor.putInt(SafePodApplication.getYear(), year);
////        if (sharedPreferences.getInt(SafePodApplication.getMonth(), 1) == 1)
////            editor.putInt(SafePodApplication.getMonth(), month);
////        if (sharedPreferences.getInt(SafePodApplication.getDayOfMonth(), 1) == 1)
////            editor.putInt(SafePodApplication.getDayOfMonth(), day);
////        if (sharedPreferences.getInt(SafePodApplication.getHourOfDay(), 0) == 0)
////            editor.putInt(SafePodApplication.getHourOfDay(), hour);
////        if (sharedPreferences.getInt(SafePodApplication.getMinute(), 0) == 0)
////            editor.putInt(SafePodApplication.getMinute(), minute);
////
////        		if(sharedPreferences.getString(SafepodAppApplication.getUserHomeAddress(), "empty") == "empty")
////        			editor.putString(SafepodAppApplication.getUserHomeAddress(), "Default Location");
//        editor.putInt("Id", (int)Id);
//        editor.commit();
//    }

//
//    public void createShortCut(){
//        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//        shortcutintent.putExtra("duplicate", false);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
//        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), EnterActivity.class));
//        sendBroadcast(shortcutintent);
//    }

}
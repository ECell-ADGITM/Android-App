package in.ecelladgitm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import in.ecelladgitm.MyApplication;
import in.ecelladgitm.R;
import in.ecelladgitm.fragments.AboutUsFragment;
import in.ecelladgitm.fragments.EventsFragment;
import in.ecelladgitm.fragments.GalleryFragment;
import in.ecelladgitm.fragments.NewsAndUpdatesFragment;
import in.ecelladgitm.fragments.PostsFragment;
import in.ecelladgitm.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView toolbarTitle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.setAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getCurrentFragmentStringID().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer stringID) {
                setToolbarAndMenu(stringID);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_frame_layout
                            , NewsAndUpdatesFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getPhotoUrl() != null && currentUser.getPhotoUrl().toString().length() > 2) {
                findViewById(R.id.toolbar_user_photo_card_view).setVisibility(View.VISIBLE);
                Picasso.with(getApplicationContext()).load(currentUser.getPhotoUrl()).into(((ImageView) findViewById(R.id.toolbar_user_photo)));
                findViewById(R.id.toolbar_user_name_card_view).setVisibility(View.GONE);
            } else if (currentUser.getDisplayName() != null && currentUser.getDisplayName().length() > 0) {
                findViewById(R.id.toolbar_user_name_card_view).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.toolbar_user_name)).setText(currentUser.getDisplayName().split(" ")[0]);
                findViewById(R.id.toolbar_user_photo_card_view).setVisibility(View.GONE);
            }
        } else {
            ((TextView) findViewById(R.id.toolbar_user_name)).setText(R.string.login);
            findViewById(R.id.toolbar_user_photo_card_view).setVisibility(View.GONE);
        }
    }

    private void setToolbarAndMenu(Integer stringID) {
        toolbarTitle.setText(getString(stringID));
        switch (stringID) {
            case R.string.news_and_updates:
                navigationView.setCheckedItem(R.id.nav_news_and_updates);
                break;
            case R.string.events:
                navigationView.setCheckedItem(R.id.nav_events);
                break;
            case R.string.posts:
                navigationView.setCheckedItem(R.id.nav_posts);
                break;
            case R.string.gallery:
                break;
            case R.string.about_us:
                navigationView.setCheckedItem(R.id.nav_about);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage("Are you sure you want to exit the app?");
            alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });
            alert.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_news_and_updates) {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null)
                    .replace(R.id.main_activity_frame_layout, NewsAndUpdatesFragment.newInstance())
                    .commit();
        } else if (id == R.id.nav_events) {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null)
                    .replace(R.id.main_activity_frame_layout, EventsFragment.newInstance())
                    .commit();
        } else if (id == R.id.nav_posts) {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null)
                    .replace(R.id.main_activity_frame_layout, PostsFragment.newInstance())
                    .commit();
        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null)
                    .replace(R.id.main_activity_frame_layout, GalleryFragment.newInstance())
                    .commit();
        } else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null)
                    .replace(R.id.main_activity_frame_layout, AboutUsFragment.newInstance())
                    .commit();
        } else if (id == R.id.nav_share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=in.ecelladgitm");

            startActivity(Intent.createChooser(share, "Share link!"));
        } else if (id == R.id.nav_app_theme) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            ArrayList<String> themeArrayList = new ArrayList<>();
            themeArrayList.add("Default");
            themeArrayList.add("Light");
            themeArrayList.add("Dark");
//          themeArrayList.add("DayNight");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, themeArrayList);
            alert.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("theme", i).apply();
                    if (i == 0) {
                        MyApplication.theme = 0;
                        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (i == 1) {
                        MyApplication.theme = 1;
                        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (i == 2) {
                        MyApplication.theme = 2;
                        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (i == 3) {
                        MyApplication.theme = 3;
                        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });
            alert.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_user_name_card_view:
            case R.id.toolbar_user_photo_card_view:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
        }
    }
}

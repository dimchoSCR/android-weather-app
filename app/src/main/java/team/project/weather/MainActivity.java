package team.project.weather;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        // Inflate the weather card
        inflateMainFragment();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getMenu().getItem(0).setChecked(true);
        } else{
            Log.i("NavigationView","Error in finding NavigationView!");
        }
    }

    private void inflateMainFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, new WeatherFragment(), "Home")
                .commit();
    }

    @Override
    // Handle back key presses
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {
           super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_home && !navigationView.getMenu().findItem(R.id.nav_home).isChecked()){
            getFragmentManager().popBackStack("Home",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if (id == R.id.nav_settings && !navigationView.getMenu().findItem(R.id.nav_settings).isChecked()){
            navigationView.getMenu().getItem(0).setChecked(false);
            // Handle the settings action
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, new SettingsFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack("Home")
                    .commit();
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}

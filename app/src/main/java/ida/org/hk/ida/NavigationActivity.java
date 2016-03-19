package ida.org.hk.ida;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

import java.util.Arrays;

/**
 * Created by ricky on 27/1/16.
 */
public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private View contentView;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    //Facebook Object
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        addNavigationHeaderView(R.layout.nav_header);
        addNavigationMenu(R.menu.drawer);
        setNavViewMenu();
        getNavigationView().setNavigationItemSelectedListener(this);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //LoginManager.getInstance().registerCallback(callbackManager, this);

        setupTracker();
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Intent i = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_log_in:
                //login
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
                setNavViewMenu();
                break;
            case R.id.nav_register:
                i = new Intent(this, RegisterActivity.class);
                break;
            case R.id.nav_follow:
                i = new Intent(this, FollowActivity.class);
                break;
            case R.id.nav_log_out:
                LoginManager.getInstance().logOut();
                setNavViewMenu();
                break;
            default:
                return false;
        }
        getDrawerLayout().closeDrawer(GravityCompat.START);
        if (i != null) startActivity(i);
        return false;
    }

    public void setNavViewMenu() {
        Menu menu = getNavigationView().getMenu();
        boolean isLogin = (Profile.getCurrentProfile() != null);
        menu.setGroupVisible(R.id.drawer_gp_logged_in, isLogin);
        menu.setGroupVisible(R.id.drawer_gp_not_login, !isLogin);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    public void setContent(int view) {
        ViewStub viewStub = (ViewStub) findViewById(R.id.nav_content);
        viewStub.setLayoutResource(view);
        contentView = viewStub.inflate();
    }

    public View getContentView() {
        return contentView;
    }

    public DrawerLayout getDrawerLayout() {
        return drawer;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void addNavigationHeaderView(int header) {
        navigationView.inflateHeaderView(header);
    }

    public void addNavigationMenu(int menu) {
        navigationView.inflateMenu(menu);
    }

    private void setupTracker() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                Log.d("Facebook SDK", "Access Token is changed");
                //Log.d("Facebook SDK", "Login Success! Current Token is " + AccessToken.getCurrentAccessToken());
                AccessToken.setCurrentAccessToken(currentAccessToken);
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                Log.d("Facebook SDK", "Profile is changed");
                //Log.d("Facebook SKD", "Welcome back " + Profile.getCurrentProfile().getName());
                Profile.setCurrentProfile(currentProfile);
                setNavViewMenu();
            }
        };
    }
}

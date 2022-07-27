package com.origin.cpf_standard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.appodeal.ads.Appodeal;
import com.origin.cpf_standard.databinding.ActivityMainBinding;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.origin.cpf_standard.ui.adsFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        //Appodeal.initialize(this, getString(R.string.appodeal_token), Appodeal.INTERSTITIAL);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(getNavController().getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this,getNavController(),appBarConfiguration);

        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.ads_fragment_container, adsFragment.class, null)
                .commit();
    }

    private NavController getNavController() {
        return navHostFragment.getNavController();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
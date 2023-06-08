package mi.videoprime;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;
import mi.videoprime.databinding.ActivityHomeBinding;
import mi.videoprime.service.DarkModeService;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {


    private ActivityHomeBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationHome.setSelectedItemId(R.id.homeFragment);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationHome, navController);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.bottomNavigationHome.setOnItemSelectedListener(item -> {
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (!handled) {
                if (item.getItemId() == R.id.homeFragment) {
                    navController.popBackStack(R.id.homeFragment, false);
                    return true;
                }
            }
            return handled;
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigateUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
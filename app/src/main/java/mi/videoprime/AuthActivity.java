package mi.videoprime;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import mi.videoprime.databinding.ActivityAuthBinding;
import mi.videoprime.fragment.LoginFragment;
import mi.videoprime.fragment.RegisterFragment;
import mi.videoprime.repository.LoginRepository;
import mi.videoprime.viewmodel.AuthViewModel;

@AndroidEntryPoint
public class AuthActivity extends AppCompatActivity {

    private AuthViewModel _viewModel;

    private ActivityAuthBinding _binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());

        _viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, _viewModel.isRegistered() ? LoginFragment.class : RegisterFragment.class, null)
                .commit();


        _viewModel.getNavigateToLogin().observe(this, shouldNavigate -> {

            if (shouldNavigate) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment.class, null)
                        .commit();

                _viewModel.doneNavigating();
            }
        });
    }


}
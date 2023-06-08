package mi.videoprime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import mi.videoprime.HomeActivity;
import mi.videoprime.R;
import mi.videoprime.databinding.FragmentLoginBinding;
import mi.videoprime.model.UserLogin;
import mi.videoprime.viewmodel.AuthViewModel;


@AndroidEntryPoint
public class LoginFragment extends Fragment {

    AuthViewModel _viewModel;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        _viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        UserLogin userLogin = new UserLogin();
        _viewModel.setUserLogin(userLogin);

        binding.setViewModel(_viewModel);
        binding.setLifecycleOwner(this);
        _viewModel.getNavigateToHome().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    _viewModel.onNavigateToHomeCOmplete();
                }
            }
        });

        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}
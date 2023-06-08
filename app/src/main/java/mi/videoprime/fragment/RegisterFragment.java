package mi.videoprime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import mi.videoprime.HomeActivity;
import mi.videoprime.R;
import mi.videoprime.databinding.FragmentRegisterBinding;
import mi.videoprime.model.User;
import mi.videoprime.viewmodel.AuthViewModel;


@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    AuthViewModel _viewModel;

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);

        _viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding.setViewModel(_viewModel);
        binding.setLifecycleOwner(this);


        _viewModel.isRegisterFromValid().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean registerFormValid) {
                Button submitButton = binding.getRoot().findViewById(R.id.registerButton);
                submitButton.setEnabled(registerFormValid);
            }
        });

        _viewModel.getNavigateToLogin().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean navigate) {
                if (navigate) {
                    NavHostFragment.findNavController(RegisterFragment.this)
                            .navigate(R.id.loginFragment);
                    _viewModel.doneNavigating();
                }
            }
        });

        //Création d'un objet user à l'initialisation de la page
        User userData = new User(0,null,null, null);
        _viewModel.setUserData(userData);

        //Récupération des inputs du formulaire
        EditText username = binding.getRoot().findViewById(R.id.inputUsername);
        EditText email = binding.getRoot().findViewById(R.id.inputEmail);
        EditText password = binding.getRoot().findViewById(R.id.inputPassword);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                _viewModel.isUsernameValid(username);
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                _viewModel.isEmailValid(email);
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                _viewModel.isPasswordValid(password);
            }
        });

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

        return binding.getRoot();
    }

}
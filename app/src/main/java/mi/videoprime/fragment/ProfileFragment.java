package mi.videoprime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import mi.videoprime.AuthActivity;
import mi.videoprime.R;
import mi.videoprime.adapters.FavoriteAdapter;
import mi.videoprime.databinding.FragmentProfilBinding;
import mi.videoprime.service.DarkModeService;
import mi.videoprime.service.FavoriteService;
import mi.videoprime.service.FileManager;
import mi.videoprime.service.MovieService;
import mi.videoprime.viewmodel.ProfileViewModel;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    ProfileViewModel _viewModel;
    @Inject
    FileManager _fileManager;

    @Inject
    MovieService _movieService;

    @Inject
    FavoriteService _favoriteService;
    public ProfileFragment() {
        super(R.layout.fragment_profil);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfilBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false);

        _viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        binding.setViewModel(_viewModel);
        binding.setLifecycleOwner(this);

        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        _viewModel.getNavigateToAuth().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Intent intent = new Intent(getActivity(), AuthActivity.class);
                    startActivity(intent);
                    _viewModel.onNavigateToAuthComplete();
                }
            }
        });

        if (_viewModel.isLogged()) {
            binding.loggedView.setVisibility(View.VISIBLE);
            binding.unloggedView.setVisibility(View.GONE);
            _viewModel.loadUser();

            RecyclerView recyclerView = binding.recyclerFavoriteView;

            if (_favoriteService.getAll() != null) {
                binding.noListFavorite.setVisibility(View.GONE);
                FavoriteAdapter favoritesAdapter = new FavoriteAdapter(_favoriteService.getAll()
                        ,_viewModel,_movieService); // Assuming favoriteList is the list of Favorite objects
                recyclerView.setAdapter(favoritesAdapter);
                binding.textViewNombreFavoris.setText(String.valueOf(favoritesAdapter.getItemCount()+" favoris"));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            }else{
                binding.noListFavorite.setVisibility(View.VISIBLE);
            }


        } else {
            binding.loggedView.setVisibility(View.GONE);
            binding.unloggedView.setVisibility(View.VISIBLE);
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
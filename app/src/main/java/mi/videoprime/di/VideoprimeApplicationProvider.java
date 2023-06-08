package mi.videoprime.di;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import mi.videoprime.repository.LoginRepository;
import mi.videoprime.repository.interfaces.ILoginRepository;
import mi.videoprime.service.FavoriteService;
import mi.videoprime.service.FileManager;
import mi.videoprime.service.MovieService;
import mi.videoprime.service.OfflineDataService;
import mi.videoprime.service.PasswordUtils;
import mi.videoprime.service.TMDBService;
import mi.videoprime.service.ToastService;
import mi.videoprime.service.UserService;
import mi.videoprime.service.UtilsService;
import mi.videoprime.service.interfaces.IFavoriteService;
import mi.videoprime.service.interfaces.IFileManager;
import mi.videoprime.service.interfaces.IMovieService;
import mi.videoprime.service.interfaces.IOfflineDataService;
import mi.videoprime.service.interfaces.IPasswordUtils;
import mi.videoprime.service.interfaces.ITMDBService;
import mi.videoprime.service.interfaces.IToastService;
import mi.videoprime.service.interfaces.IUserService;
import mi.videoprime.service.interfaces.IUtilsService;

@Module
@InstallIn(SingletonComponent.class)
public abstract class VideoprimeApplicationProvider {

    @Binds
    public abstract IUtilsService utilsService(UtilsService userService);
    @Binds
    public abstract ITMDBService tmdbService(TMDBService tmdbService);
    @Binds
    public abstract IToastService toastService(ToastService toastService);
    @Binds
    public abstract IUserService userService(UserService userService);
    @Binds
    public abstract IFavoriteService favoriteService(FavoriteService favoriteService);
    @Binds
    public abstract IMovieService movieService(MovieService movieService);
    @Binds
    public abstract IOfflineDataService offlineMode(OfflineDataService offlineDataService);
    @Binds
    public abstract IPasswordUtils passwordUtils(PasswordUtils passwordUtils);
    @Binds
    public abstract IFileManager fileManager(FileManager fileManager);
    @Binds
    public abstract ILoginRepository loginRepository(LoginRepository loginRepository);
}

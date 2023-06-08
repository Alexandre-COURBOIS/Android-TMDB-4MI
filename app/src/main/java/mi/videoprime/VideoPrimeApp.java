package mi.videoprime;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class VideoPrimeApp extends Application {

    public VideoPrimeApp() {
        System.out.println("Launching app with DI Succeed");
    }
}

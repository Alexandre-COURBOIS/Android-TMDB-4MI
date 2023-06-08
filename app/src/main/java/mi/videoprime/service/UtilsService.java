package mi.videoprime.service;

import android.content.Context;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import mi.videoprime.service.interfaces.IUtilsService;

public class UtilsService implements IUtilsService {

    Context _context;

    @Inject
    public UtilsService(@ApplicationContext Context context){
        _context = context;
    }

    @Override
    public Context getContextApplication() {
        return _context.getApplicationContext();
    }
}

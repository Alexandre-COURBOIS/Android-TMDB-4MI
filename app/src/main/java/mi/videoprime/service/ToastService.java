package mi.videoprime.service;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import mi.videoprime.service.interfaces.IToastService;

public class ToastService implements IToastService {

    private final String ERROR_COLOR = "#d9534f";
    private final String SUCCESS_COLOR = "#5cb85c";
    Context _context;

    @Inject
    public ToastService(@ApplicationContext Context context) {
        _context = context;
    }

    public void showToastError(CharSequence text, int duration) {
        Toast toast = new Toast(_context);

        TextView view = new TextView(_context);
        view.setText(text);
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        view.setPadding(30, 30, 30, 30);

        GradientDrawable drawable = new GradientDrawable();

        drawable.setCornerRadius(50);
        drawable.setColor(Color.parseColor(ERROR_COLOR));

        view.setBackground(drawable);

        toast.setView(view);
        toast.setDuration(duration);
        toast.setGravity(Gravity.TOP, 0,0);
        toast.show();
    }

    public void showToastSuccess(CharSequence text, int duration) {
        Toast toast = new Toast(_context);

        TextView view = new TextView(_context);
        view.setText(text);
        view.setTextColor(Color.WHITE);
        view.setPadding(30, 30, 30, 30);

        GradientDrawable drawable = new GradientDrawable();

        drawable.setCornerRadius(50);
        drawable.setColor(Color.parseColor(SUCCESS_COLOR));

        view.setBackground(drawable);

        toast.setView(view);
        toast.setDuration(duration);
        toast.setGravity(Gravity.TOP, 0,0);

        toast.show();
    }

    public void showDefaultToast(CharSequence text, int duration) {
        Toast toast = Toast.makeText(_context, text, duration);
        toast.show();
    }
}

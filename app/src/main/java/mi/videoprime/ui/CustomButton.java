package mi.videoprime.ui;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import mi.videoprime.R;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton);
        int buttonColor = typedArray.getColor(R.styleable.CustomButton_buttonColor, ContextCompat.getColor(context, R.color.defaultButtonColor));
        int pressedButtonColor = typedArray.getColor(R.styleable.CustomButton_pressedButtonColor, ContextCompat.getColor(context, R.color.pressedButtonColor));
        float cornerRadius = typedArray.getDimension(R.styleable.CustomButton_cornerRadius, getResources().getDimension(R.dimen.defaultCornerRadius));

        GradientDrawable defaultBackground = new GradientDrawable();
        defaultBackground.setColor(buttonColor);
        defaultBackground.setCornerRadius(cornerRadius);

        GradientDrawable pressedBackground = new GradientDrawable();
        pressedBackground.setColor(pressedButtonColor);
        pressedBackground.setCornerRadius(cornerRadius);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedBackground);
        stateListDrawable.addState(new int[]{}, defaultBackground);

        setBackground(stateListDrawable);
        typedArray.recycle();
    }
}


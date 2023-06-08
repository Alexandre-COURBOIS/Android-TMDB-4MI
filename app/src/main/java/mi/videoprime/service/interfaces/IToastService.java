package mi.videoprime.service.interfaces;

public interface IToastService {

    void showToastError(CharSequence text, int duration);
    void showToastSuccess(CharSequence text, int duration);
    void showDefaultToast(CharSequence text, int duration);

}

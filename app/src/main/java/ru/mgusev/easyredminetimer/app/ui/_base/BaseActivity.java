package ru.mgusev.easyredminetimer.app.ui._base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;

import java.lang.annotation.Annotation;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import moxy.MvpDelegate;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.presentation.base.BaseView;


public class BaseActivity extends DaggerAppCompatActivity implements BaseView {

    private MvpDelegate<? extends BaseActivity> mMvpDelegate;
    private Unbinder unbinder;
    private AlertDialog dialog;
    private View toastView;
    private View errorToastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);

        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)) return;

        Annotation annotation = cls.getAnnotation(Layout.class);
        Layout layout = (Layout) annotation;
        setContentView(layout.id());
        unbinder = ButterKnife.bind(this);
        toastView = LayoutInflater.from(this).inflate(R.layout.view_toast, null);
        errorToastView = LayoutInflater.from(this).inflate(R.layout.view_toast_error, null);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getMvpDelegate().onAttach();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMvpDelegate().onAttach();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    protected void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        getMvpDelegate().onDestroyView();

        if (isFinishing()) {
            getMvpDelegate().onDestroy();
        }
    }

    /**
     * @return The {@link MvpDelegate} being used by this Activity.
     */
    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }
        return mMvpDelegate;
    }

    @Override
    public void showMessage(String message) {
        showDialog(message);
    }

    @Override
    public void showSuccessToast(String message) {
        ((AppCompatTextView) toastView.findViewById(R.id.text)).setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(toastView);
        toast.setGravity(Gravity.BOTTOM, 0, (int) getResources().getDimension(R.dimen.very_large_margin));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showErrorToast(String message) {
        ((AppCompatTextView) errorToastView.findViewById(R.id.text)).setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(errorToastView);
        toast.setGravity(Gravity.BOTTOM, 0, (int) getResources().getDimension(R.dimen.very_large_margin));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(String message) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .setPositiveButton(R.string.ok, null)
                    .create();
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog.setMessage(message);
        dialog.show();
    }

}

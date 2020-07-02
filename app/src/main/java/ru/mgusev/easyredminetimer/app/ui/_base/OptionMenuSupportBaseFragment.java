package ru.mgusev.easyredminetimer.app.ui._base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.lang.annotation.Annotation;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import moxy.MvpDelegate;
import ru.mgusev.easyredminetimer.app.presentation.base.BaseView;
import timber.log.Timber;

/**
 * Класс является аналогом com.arellomobile.mvp.MvpAppCompatActivity и применяется только для активити с меню
 *
 * Решает следующую проблему:
 * Если требуется выполнять какие-либо действия с MenuItem через getViewState(),
 * то после смены конфигурации onCreateOptionsMenu(Menu menu) вызывается после onStart() и OnResume(),
 * в которых происходит getMvpDelegate().onAttach() в стандарном com.arellomobile.mvp.MvpAppCompatActivity
 * и MenuItem на тот момент еще равен null, как следствие - NPE
 *
 * Особенность:
 * В методах onStart() и OnResume() не вызывается getMvpDelegate().onAttach()
 *
 * В дочернем классе необходимо вызвать getMvpDelegate().onAttach()
 * в onCreateOptionsMenu(Menu menu) перед return true;
 */

public abstract class OptionMenuSupportBaseFragment extends DaggerFragment implements BaseView {

    private Unbinder unbinder;

    private boolean showed = false;
    private boolean menuCreated = false;

    private boolean mIsStateSaved;
    private MvpDelegate<? extends OptionMenuSupportBaseFragment> mMvpDelegate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)) {
            throw new IllegalStateException("Layout id not found");
        }

        Annotation annotation = cls.getAnnotation(Layout.class);
        Layout layout = (Layout) annotation;
        View view = inflater.inflate(layout.id(), container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        mIsStateSaved = false;

        if (menuCreated) getMvpDelegate().onAttach();
    }

    public void onResume() {
        super.onResume();

        mIsStateSaved = false;
        if (menuCreated) getMvpDelegate().onAttach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menuCreated = true;
        getMvpDelegate().onAttach();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mIsStateSaved = true;

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        getMvpDelegate().onDetach();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //We leave the screen and respectively all fragments will be destroyed
        if (getActivity().isFinishing()) {
            getMvpDelegate().onDestroy();
            return;
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (mIsStateSaved) {
            mIsStateSaved = false;
            return;
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        boolean anyParentIsRemoving = false;
        Fragment parent = getParentFragment();
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving();
            parent = parent.getParentFragment();
        }

        if (isRemoving() || anyParentIsRemoving) {
            getMvpDelegate().onDestroy();
        }
    }

    /**
     * @return The {@link MvpDelegate} being used by this Fragment.
     */
    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }

        return mMvpDelegate;
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showMessage(message);
        }
    }

    @Override
    public void showSuccessToast(String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showSuccessToast(message);
        }
    }

    @Override
    public void showErrorToast(String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showErrorToast(message);
        }
    }

    protected boolean hideKeyboard() {
        return hideKeyboard(null);
    }

    protected boolean hideKeyboard(View v) {
        FragmentActivity activity = getActivity();
        if (activity == null) return false;

        View view = activity.getCurrentFocus();
        if (view == null) return false;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return false;

        try {
            return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void onShow() {
        Timber.d("%s - onShow", getClass().getSimpleName());
        showed = true;
    }

    public void onHide() {
        Timber.d("%s - onHide", getClass().getSimpleName());
        showed = false;
    }

    protected boolean isShowed() {
        return showed;
    }
}
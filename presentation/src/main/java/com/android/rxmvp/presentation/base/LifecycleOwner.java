package com.android.rxmvp.presentation.base;

import androidx.annotation.NonNull;


public class LifecycleOwner {
    private final BaseView baseView;
    private ViewState viewState = ViewState.onViewDetached;

    private LifecycleOwner(@NonNull BaseView baseView) {
        this.baseView = baseView;
    }

    public static LifecycleOwner register(@NonNull BaseView view) {
        return new LifecycleOwner(view);
    }

    public void onAttachedToWindow() {
        setViewState(ViewState.onViewAttached);
    }

    public void onDetachedFromWindow() {
        setViewState(ViewState.onViewDetached);
    }

    public void onVisibilityChanged(boolean isShown) {
        if (isShown) {
            setViewState(ViewState.onViewWillShow);
        } else {
            setViewState(ViewState.onViewWillHide);
        }
    }

    private void setViewState(ViewState newViewState) {
        // Skip if equals
        if (viewState == newViewState) {
            return;
        }

        // Valid transactions
        // onViewDetached <-> onAttached -> onViewWillShow <-> onViewWillHide -> onViewDetached
        //                                                 ->  onViewWillHide(*) -> onViewDetached
        if (newViewState == ViewState.onViewDetached) {
            if (viewState == ViewState.onViewWillShow) {
                this.viewState = ViewState.onViewWillHide;
                notifyView();
            }
        }
        if (viewState.isValidTransaction(newViewState)) {
            this.viewState = newViewState;
            notifyView();
        }
    }

    private void notifyView() {
        switch (viewState) {
            case onViewAttached:
                baseView.onViewAttached();
                break;
            case onViewWillShow:
                baseView.onViewWillShow();
                break;
            case onViewWillHide:
                baseView.onViewWillHide();
                break;
            case onViewDetached:
                baseView.onViewDetached();
                break;
        }
    }

    private enum ViewState {
        // Valid transactions
        // onViewDetached <-> onAttached -> onViewWillShow <-> onViewWillHide -> onViewDetached
        //                                                 ->  onViewWillHide(*) -> onViewDetached
        onViewDetached {
            @Override
            public boolean isValidTransaction(ViewState viewState) {
                switch (viewState) {
                    case onViewDetached:
                    case onViewWillShow:
                    case onViewWillHide:
                        return false;
                    case onViewAttached:
                        return true;
                }
                return false;
            }
        },
        onViewAttached {
            @Override
            public boolean isValidTransaction(ViewState viewState) {
                switch (viewState) {
                    case onViewDetached:
                    case onViewWillShow:
                        return true;
                    case onViewWillHide:
                    case onViewAttached:
                        return false;
                }
                return false;
            }
        },
        onViewWillShow {
            @Override
            public boolean isValidTransaction(ViewState viewState) {
                switch (viewState) {
                    case onViewDetached:

                    case onViewAttached:
                    case onViewWillShow:
                        return false;

                    case onViewWillHide:
                        return true;
                }
                return false;
            }
        },
        onViewWillHide {
            @Override
            public boolean isValidTransaction(ViewState viewState) {
                switch (viewState) {
                    case onViewAttached:
                    case onViewWillHide:
                        return false;

                    case onViewDetached:
                    case onViewWillShow:
                        return true;
                }
                return false;
            }
        };

        public abstract boolean isValidTransaction(ViewState viewState);
    }
}

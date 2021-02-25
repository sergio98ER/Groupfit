package com.example.groupfitnuevo.ui.misejercicios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MisEjerciciosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MisEjerciciosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
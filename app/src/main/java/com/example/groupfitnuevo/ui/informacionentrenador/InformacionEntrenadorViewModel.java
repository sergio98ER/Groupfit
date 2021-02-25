package com.example.groupfitnuevo.ui.informacionentrenador;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InformacionEntrenadorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InformacionEntrenadorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
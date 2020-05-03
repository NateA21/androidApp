package com.example.idontcare.data;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.preference.PreferenceFragmentCompat;

import com.example.idontcare.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }





}

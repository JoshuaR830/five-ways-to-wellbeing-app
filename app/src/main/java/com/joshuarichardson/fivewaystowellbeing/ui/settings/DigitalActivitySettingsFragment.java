package com.joshuarichardson.fivewaystowellbeing.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.joshuarichardson.fivewaystowellbeing.R;
import com.joshuarichardson.fivewaystowellbeing.automated_activity_tracking.app_usage_tracking.AppUsageActivityTrackingService;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Display the settings
 * Start a service when the app tracking is toggled
 */
public class DigitalActivitySettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.digital_activity_settings, rootKey);

        Preference appEnabled = findPreference("notification_app_enabled");

        appEnabled.setOnPreferenceChangeListener((preference, newValue) -> {
            if(newValue == Boolean.valueOf(String.valueOf(newValue))) {
                requireContext().startService(new Intent(requireContext(), AppUsageActivityTrackingService.class));
            }

            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

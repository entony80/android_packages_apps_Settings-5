/*
 * Copyright (C) 2016 The Xperia Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.xosp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.TwoStatePreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.view.View;

import com.android.internal.logging.MetricsLogger;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cyanogenmod.providers.CMSettings;
import com.android.settings.xosp.SeekBarPreference;

public class BlurPersonalizations extends SettingsPreferenceFragment
        implements OnPreferenceChangeListener {

    private SwitchPreference mExpand;
    private SeitchPreference mNotiTrans;
    private SwitchPreference mHeadSett;
    private SwitchPreference mQuickSett;
    private SwitchPreference mRecentsSett;
    
    //StatusBar
    private SeekBarPreference mScale;
    private SeekBarPreference mRadius;

    //Recents
    private SeekBarPreference mRecentsScale;
    private SeekBarPreference mRecentsRadius;   

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.xosp_blur_cat);
        PreferenceScreen prefSet = getPreferenceScreen();

        ContentResolver resolver = getActivity().getContentResolver();

        mExpand = (SwitchPreference) prefSet.findPreference(STATUS_BAR_EXPANDED_ENABLED_PREFERENCE_KEY);
        mExpand.setChecked((Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.STATUS_BAR_EXPANDED_ENABLED_PREFERENCE_KEY, 0) == 1));

        mScale = (SeekBarPreference) findPreference(BLUR_SCALE_PREFERENCE_KEY);
        
        mScale.setValue(Settings.System.getInt(resolver, Settings.System.BLUR_SCALE_PREFERENCE_KEY, 10));
        mScale.setOnPreferenceChangeListener(this);

        mRadius = (SeekBarPreference) findPreference(BLUR_RADIUS_PREFERENCE_KEY);
        
        mRadius.setValue(Settings.System.getInt(resolver, Settings.System.BLUR_RADIUS_PREFERENCE_KEY, 5));
        mRadius.setOnPreferenceChangeListener(this);

        mNotiTrans = (SwitchPreference) prefSet.findPreference(TRANSLUCENT_NOTIFICATIONS_PREFERENCE_KEY);
        mNotiTrans.setChecked((Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.TRANSLUCENT_NOTIFICATIONS_PREFERENCE_KEY, 0) == 1));

        mHeadSett = (SwitchPreference) prefSet.findPreference(TRANSLUCENT_HEADER_PREFERENCE_KEY);
        mHeadSett.setChecked((Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.TRANSLUCENT_HEADER_PREFERENCE_KEY, 0) == 1));

        mQuickSett = (SwitchPreference) prefSet.findPreference(TRANSLUCENT_QUICK_SETTINGS_PREFERENCE_KEY);
        mQuickSett.setChecked((Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.TRANSLUCENT_QUICK_SETTINGS_PREFERENCE_KEY, 0) == 1));

        mRecentsSett = (TwoStatePreference) findPreference("blurred_recent_app_enabled_pref");

        boolean mRecentsSettint = (Settings.System.getInt(resolver,
                Settings.System.RECENT_APPS_ENABLED_PREFERENCE_KEY, 1) == 0);
        mRecentsSett.setChecked(mRecentsSettint);
        mRecentsSett.setOnPreferenceChangeListener(this);

        mRecentsScale = (SeekBarPreference) findPreference("recents_blur_scale");
        
        mRecentsScale.setValue(Settings.System.getInt(resolver, Settings.System.BLUR_SCALE_RECENTS_PREFERENCE_KEY, 20));
        mRecentsScale.setOnPreferenceChangeListener(this);

        mRecentsRadius = (SeekBarPreference) findPreference("recents_blur_radius");
        
        mRecentsRadius.setValue(Settings.System.getInt(resolver, Settings.System.BLUR_RADIUS_RECENTS_PREFERENCE_KEY, 3));
        mRecentsRadius.setOnPreferenceChangeListener(this);

    }

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.APPLICATION;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mScale) {
            int value = ((Integer)newValue).intValue();
            Settings.System.putInt(
                resolver, Settings.System.BLUR_SCALE_PREFERENCE_KEY, value);
            return true;
        } else if (preference == mRadius) {
            int value = ((Integer)newValue).intValue();
            Settings.System.putInt(
                resolver, Settings.System.BLUR_RADIUS_PREFERENCE_KEY, value);
            return true;
        } else if (preference == mRecentsSett) {
            Settings.System.putInt(
                    resolver, Settings.System.RECENT_APPS_ENABLED_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
            return true;
        } else if (preference == mRecentsScale) {
            int value = ((Integer)newValue).intValue();
            Settings.System.putInt(
                resolver, Settings.System.BLUR_SCALE_RECENTS_PREFERENCE_KEY, value);
            return true;
        } else if (preference == mRecentsRadius) {
            int value = ((Integer)newValue).intValue();
            Settings.System.putInt(
                resolver, Settings.System.BLUR_RADIUS_RECENTS_PREFERENCE_KEY, value);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if  (preference == mExpand) {
            boolean enabled = ((SwitchPreference)preference).isChecked();
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUS_BAR_EXPANDED_ENABLED_PREFERENCE_KEY, enabled ? 1:0);  
        } else if (preference == mNotiTrans) {
            boolean enabled = ((SwitchPreference)preference).isChecked();
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.TRANSLUCENT_NOTIFICATIONS_PREFERENCE_KEY, enabled ? 1:0);  
        } else if (preference == mHeadSett) {
            boolean enabled = ((SwitchPreference)preference).isChecked();
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.TRANSLUCENT_HEADER_PREFERENCE_KEY, enabled ? 1:0);          
        } else if (preference == mQuickSett) {
            boolean enabled = ((SwitchPreference)preference).isChecked();
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.TRANSLUCENT_QUICK_SETTINGS_PREFERENCE_KEY, enabled ? 1:0); 
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}

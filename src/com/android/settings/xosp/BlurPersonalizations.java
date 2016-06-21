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

    private TwoStatePreference mExpand;
    private TwoStatePreference mNotiTrans;
    private TwoStatePreference mHeadSett;
    private TwoStatePreference mQuickSett;
    private TwoStatePreference mRecentsSett;
    
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

        ContentResolver resolver = getActivity().getContentResolver();

        mExpand = (TwoStatePreference) findPreference("blurred_status_bar_expanded_enabled_pref");

        boolean mExpandint = (Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_EXPANDED_ENABLED_PREFERENCE_KEY, 1) == 0);
        mExpand.setChecked(mExpandint);
        mExpand.setOnPreferenceChangeListener(this);

        mScale = (SeekBarPreference) findPreference("statusbar_blur_scale");
        
        mScale.setValue(Settings.System.getInt(resolver, Settings.System.BLUR_SCALE_PREFERENCE_KEY, 10));
        mScale.setOnPreferenceChangeListener(this);

        mRadius = (SeekBarPreference) findPreference("statusbar_blur_radius");
        
        mRadius.setValue(Settings.System.getInt(resolver, Settings.System.BLUR_RADIUS_PREFERENCE_KEY, 5));
        mRadius.setOnPreferenceChangeListener(this);

        mNotiTrans = (TwoStatePreference) findPreference("translucent_notifications_pref");

        boolean mNotiTransint = (Settings.System.getInt(resolver,
                Settings.System.TRANSLUCENT_NOTIFICATIONS_PREFERENCE_KEY, 1) == 0);
        mNotiTrans.setChecked(mNotiTransint);
        mNotiTrans.setOnPreferenceChangeListener(this);

        mHeadSett = (TwoStatePreference) findPreference("translucent_header_pref");

        boolean mHeadSettint = (Settings.System.getInt(resolver,
                Settings.System.TRANSLUCENT_HEADER_PREFERENCE_KEY, 1) == 0);
        mHeadSett.setChecked(mHeadSettint);
        mHeadSett.setOnPreferenceChangeListener(this);

        mQuickSett = (TwoStatePreference) findPreference("translucent_quick_settings_pref");

        boolean mQuickSettint = (Settings.System.getInt(resolver,
                Settings.System.TRANSLUCENT_QUICK_SETTINGS_PREFERENCE_KEY, 1) == 0);
        mQuickSett.setChecked(mQuickSettint);
        mQuickSett.setOnPreferenceChangeListener(this);

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
        if (preference == mExpand) {
            Settings.System.putInt(
                    resolver, Settings.System.STATUS_BAR_EXPANDED_ENABLED_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
            return true;
        } else if (preference == mScale) {
            int value = ((Integer)newValue).intValue();
            Settings.System.putInt(
                resolver, Settings.System.BLUR_SCALE_PREFERENCE_KEY, value);
            return true;
        } else if (preference == mRadius) {
            int value = ((Integer)newValue).intValue();
            Settings.System.putInt(
                resolver, Settings.System.BLUR_RADIUS_PREFERENCE_KEY, value);
            return true;
        } else if (preference == mNotiTrans) {
            Settings.System.putInt(
                    resolver, Settings.System.TRANSLUCENT_NOTIFICATIONS_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
            return true;
        } else if (preference == mHeadSett) {
            Settings.System.putInt(
                    resolver, Settings.System.TRANSLUCENT_HEADER_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
            return true;
        } else if (preference == mQuickSett) {
            Settings.System.putInt(
                    resolver, Settings.System.TRANSLUCENT_QUICK_SETTINGS_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
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
}

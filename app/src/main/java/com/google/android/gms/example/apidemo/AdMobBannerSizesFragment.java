/*
 * Copyright (C) 2015 Google, Inc.
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
package com.google.android.gms.example.apidemo;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * The {@link AdMobBannerSizesFragment} class demonstrates how to set a desired banner size
 * prior to loading an ad.
 */
public class AdMobBannerSizesFragment extends Fragment {

    private AdView adView;
    private Button loadButton;
    private FrameLayout adFrameLayout;
    private Spinner sizesSpinner;

    public AdMobBannerSizesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admob_banner_sizes, container, false);

        sizesSpinner = rootView.findViewById(R.id.bannersizes_spn_size);
        loadButton = rootView.findViewById(R.id.bannersizes_btn_loadad);
        adFrameLayout = rootView.findViewById(R.id.bannersizes_fl_adframe);

        String[] sizesArray;

        // It is a Mobile Ads SDK policy that only the banner, large banner, and smart banner ad
        // sizes are shown on phones, and that the full banner, leaderboard, and medium rectangle
        // sizes are reserved for use on tablets.  The conditional below checks the screen size
        // and retrieves the correct list.

        int screenSize = getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;

        if ((screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                || (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)) {
            sizesArray = getResources().getStringArray(R.array.bannersizes_largesizes);
        } else {
            sizesArray = getResources().getStringArray(R.array.bannersizes_smallsizes);
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(rootView.getContext(),
                android.R.layout.simple_spinner_dropdown_item, sizesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizesSpinner.setAdapter(adapter);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adView != null) {
                    adFrameLayout.removeView(adView);
                    adView.destroy();
                }

                adView = new AdView(getActivity());
                adView.setAdUnitId(getString(R.string.admob_banner_ad_unit_id));
                adFrameLayout.addView(adView);

                switch (sizesSpinner.getSelectedItemPosition()) {
                    case 0:
                        adView.setAdSize(AdSize.BANNER);
                        break;
                    case 1:
                        adView.setAdSize(AdSize.LARGE_BANNER);
                        break;
                    case 2:
                        adView.setAdSize(AdSize.SMART_BANNER);
                        break;
                    case 3:
                        adView.setAdSize(AdSize.FULL_BANNER);
                        break;
                    case 4:
                        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                        break;
                    case 5:
                        adView.setAdSize(AdSize.LEADERBOARD);
                        break;
                    default:
                        // fall through.
                }

                adView.loadAd(new AdRequest.Builder().build());
            }
        });

        return rootView;
    }
}

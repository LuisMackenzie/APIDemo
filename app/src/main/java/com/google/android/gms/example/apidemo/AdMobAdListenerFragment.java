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

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

/**
 * The {@link AdMobAdListenerFragment} demonstrates the use of the
 * {@link com.google.android.gms.ads.AdListener} class.
 */
public class AdMobAdListenerFragment extends Fragment {

    private AdView adView;

    public AdMobAdListenerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admob_ad_listener, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adView = getView().findViewById(R.id.listener_av_main);

    adView.setAdListener(
        new AdListener() {
          private void showToast(String message) {
            View view = getView();
            if (view != null) {
              Toast.makeText(getView().getContext(), message, Toast.LENGTH_SHORT).show();
            }
          }

          @Override
          public void onAdLoaded() {
            showToast("Ad loaded.");
          }

          @Override
          public void onAdFailedToLoad(LoadAdError loadAdError) {
            String error =
                String.format(
                    "domain: %s, code: %d, message: %s",
                    loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
            showToast(String.format("Ad failed to load with error %s", error));
          }

          @Override
          public void onAdOpened() {
            showToast("Ad opened.");
          }

          @Override
          public void onAdClosed() {
            showToast("Ad closed.");
          }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}

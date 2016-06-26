/*
 * Copyright (C) 2016 Xperia Open Source Project
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

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.android.settings.R; 

public class XOSPLogo extends Preference {

	 public XOSPLogo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
         super(context, attrs, defStyleAttr, defStyleRes);
     }
 
     public XOSPLogo(Context context, AttributeSet attrs, int defStyleAttr) {
         super(context, attrs, defStyleAttr);
     }
 
     public XOSPLogo(Context context, AttributeSet attrs) {
         super(context, attrs);
     }

    AnimationDrawable XOSPAnimationPart1;
    AnimationDrawable XOSPAnimationPart2;
    AnimationDrawable XOSPAnimationPart3;

    protected void onBindView(View rootView) {
        super.onBindView(rootView);

        ImageView XOSPImagePart1 = (ImageView) rootView.findViewById(R.id.xosp_logo);
        ImageView XOSPImagePart2 = (ImageView) rootView.findViewById(R.id.xosp_logo);
        ImageView XOSPImagePart3 = (ImageView) rootView.findViewById(R.id.xosp_logo);
        XOSPImagePart1.setBackgroundResource(R.drawable.xosp_logo_part1);
        XOSPImagePart2.setBackgroundResource(R.drawable.xosp_logo_part2);
        XOSPImagePart3.setBackgroundResource(R.drawable.xosp_logo_part3);
        while(true){
            XOSPAnimationPart1.stop();
            XOSPAnimationPart2.stop();
            XOSPAnimationPart3.stop();
            if(XOSPAnimationPart1.isRunning()==false && XOSPAnimationPart2.isRunning()==false && XOSPAnimationPart2.isRunning()==false){
                XOSPAnimationPart1 = (AnimationDrawable) XOSPImagePart1.getBackground();
                XOSPAnimationPart1.start();
            }
            if(XOSPAnimationPart1.isRunning()==false && XOSPAnimationPart2.isRunning()==false && XOSPAnimationPart3.isRunning()==false){
                XOSPAnimationPart2 = (AnimationDrawable) XOSPImagePart2.getBackground();
                XOSPAnimationPart2.start();
            }
            if(XOSPAnimationPart1.isRunning()==false && XOSPAnimationPart2.isRunning()==false && XOSPAnimationPart3.isRunning()==false){
                XOSPAnimationPart3 = (AnimationDrawable) XOSPImagePart3.getBackground();
                XOSPAnimationPart3.start();
            }
        }
    }
}

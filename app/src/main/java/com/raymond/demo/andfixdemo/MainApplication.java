/*
 * 
 * Copyright (c) 2015, alipay.com
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

package com.raymond.demo.andfixdemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;
import com.raymond.demo.andfixdemo.exception.CrashHandler;

import java.io.IOException;

/**
 * sample application
 * 
 * @author sanping.li@alipay.com
 * 
 */
public class MainApplication extends Application {
	private static final String TAG = "euler";

	private static final String APATCH_PATH = "/out.apatch";
	/**
	 * patch manager
	 */
	private PatchManager mPatchManager;

	@Override
	public void onCreate() {
		super.onCreate();

		CrashHandler.getInstance().init(this);

		// initialize
		mPatchManager = new PatchManager(this);
		mPatchManager.init("1.0");
		Log.d(TAG, "inited.");

		// load patch
		mPatchManager.loadPatch();
		Log.d(TAG, "apatch loaded.");

		// add patch at runtime
		try {
			// .apatch file path
			String patchFileString = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + APATCH_PATH;
			mPatchManager.addPatch(patchFileString);
			Log.d(TAG, "apatch:" + patchFileString + " added.");
		} catch (IOException e) {
			Log.e(TAG, "", e);
		}

	}
}

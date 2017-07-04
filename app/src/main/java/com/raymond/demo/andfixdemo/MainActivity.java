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

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.raymond.demo.andfixdemo.andfix.AndFixManager;
import com.raymond.demo.andfixdemo.exception.CrashHandler;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_PERMISSION_SDCARD = 1000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.clickMe).setOnClickListener(this);
		findViewById(R.id.fixBug).setOnClickListener(this);

        checkPermission();
	}

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				init();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
										Manifest.permission.READ_EXTERNAL_STORAGE,
											Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
                        REQUEST_PERMISSION_SDCARD);
            }

        } else {
			init();
		}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_SDCARD: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					init();
                } else {

                }
                return;
            }

        }
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private void init() {
		CrashHandler.getInstance().init(this);
		AndFixManager.getInstance().init(this);
		showToast("-------初始化完成-------");
	}

    @Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.clickMe:
				clickMe(v);
				break;

			case R.id.fixBug:
				fixBug(v);
				break;
		}
	}

	public void clickMe(View v) {
		showToast("计算结果 = " + compute());
	}

	private int compute() {
		return 4/1;
	}

	public void fixBug(View v) {
		String patchFile =  "/sdcard/out.apatch";
		Log.d("lost", "patch file: " + patchFile);
		if(new File(patchFile).exists()) {
			showToast("patch file: " + patchFile);
		}
		AndFixManager.getInstance().addPatch(patchFile);
	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

}

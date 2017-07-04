package com.raymond.demo.andfixdemo.andfix;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.raymond.demo.andfixdemo.utils.Utils;

import java.io.File;

/**
 * Created by lifanlong@le.com on 2017/7/3.
 */
public class AndFixManager {

    private volatile static AndFixManager sInstance;

    private AndFixManager() {}

    public static AndFixManager getInstance() {
        if(null == sInstance) {
            synchronized (AndFixManager.class) {
                if(null == sInstance) {
                    sInstance = new AndFixManager();
                }
            }
        }
        return sInstance;
    }

    private PatchManager mPatchManager;

    public void init(Context context) {
        try {
            if(null == mPatchManager) {
                mPatchManager = new PatchManager(context);
            }
            mPatchManager.init(Utils.getVersionName(context));
            mPatchManager.loadPatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPatch(String path) {
        try {
            if (mPatchManager != null) {
                mPatchManager.addPatch(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

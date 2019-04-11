package com.twomonth.wavegame.util;

import android.support.v4.app.Fragment;
import java.util.WeakHashMap;

/**
 * Created by hejunlin on 17/3/21.
 */

public class FragmentManagerWrapper {

    private volatile static FragmentManagerWrapper mInstance = null;

    public static FragmentManagerWrapper getInstance(){
        if (mInstance == null) {
            synchronized (FragmentManagerWrapper.class) {
                if (mInstance == null) {
                    mInstance = new FragmentManagerWrapper();
                }
            }
        }
        return mInstance;
    }

    private WeakHashMap<String, Fragment> mHashMap = new WeakHashMap<>();

    public Fragment createFragment(Class<?> clazz){
        return createFragment(clazz, true);
    }

    public Fragment createFragment(Class<?> clazz, boolean isobtain){
        Fragment fragment = null;
        String className = clazz.getName();
        if (mHashMap.containsKey(className)) {
            fragment = mHashMap.get(className);
        } else {
            try {
                fragment = (Fragment) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        if (isobtain) {
            mHashMap.put(className,fragment);
        }
        return fragment;
    }

}

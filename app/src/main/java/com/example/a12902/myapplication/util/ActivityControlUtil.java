package com.example.a12902.myapplication.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityControlUtil {
    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void removeAll() {
        for (Activity activity :
                activities) {
            activity.finish();
        }
    }
}

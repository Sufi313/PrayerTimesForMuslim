package net.vorson.muhammadsufwan.prayertimesformuslim.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;

/*
 * Created by Muhammad.sufwan on 12/06/2018.
 */

public class ScreenUtils {

  /**
   * Locks the device window in actual screen mode.
   */
  public static void lockOrientation(Activity activity) {
    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
  }

  /**
   * Unlocks the device window in user defined screen mode.
   */
  public static void unlockOrientation(Activity activity) {
    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
  }

}

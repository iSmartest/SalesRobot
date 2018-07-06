package com.empowerment.salesrobot.uitls;

import android.content.pm.PackageManager;

/**
 * Created by 小火
 * Create time on  2017/8/15
 * My mailbox is 1403241630@qq.com
 */

public class PermissionUtil {
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
      if(grantResults.length < 1){ return false; }
        // Verify that each required permission has been granted, otherwise return false.
         for (int result : grantResults)
         {
             if (result != PackageManager.PERMISSION_GRANTED)
             {
             return false;
             }
         }
        return true;
    }


}

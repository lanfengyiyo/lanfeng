package com.guo.utils;

import java.io.File;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class InstallApk {

	public static void installApk(Activity context, File file) {

		String apkPath = file.getPath();
		PackageManager packageManager = context.getPackageManager();
		PackageInfo info = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		if (info != null && info.applicationInfo != null) {
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

			context.startActivity(intent);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification notice = new Notification();
			notice.tickerText = "下载完成";
			notice.when = System.currentTimeMillis();
			notice.defaults = Notification.DEFAULT_SOUND;
			CharSequence gameName = context.getApplicationInfo().loadLabel(context.getPackageManager());
			notice.setLatestEventInfo(context, "下载完成", gameName + "-" + "下载完成", pendingIntent);

			NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(0, notice);

			if (context instanceof Activity) {
				((Activity) context).finish();
			}
		} else {
			Toast.makeText(context, "下载失败", Toast.LENGTH_LONG).show();
		}
	}

}

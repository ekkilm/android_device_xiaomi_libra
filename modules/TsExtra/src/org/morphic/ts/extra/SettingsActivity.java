package org.morphic.ts.extra;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.util.Log;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Notification;
import android.app.NotificationManager;

import android.os.SystemProperties;
import android.os.PowerManager;

import java.io.File;

import android.provider.Settings;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

		private static final String TAG = "TsSettingsActivity";

		private static final String ROOT_ACCESS_PROPERTY = "persist.sys.root_access";

		private static final String PAYPAL_DONATE_URL =
				"https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=ekkilm%2ets%40gmail%2ecom&lc=FI&item_name=Team%20Superluminal%20ROM%20ekkilm&no_note=0&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHostedGuest";
/*		    "https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=morphic%2ets%40gmail%2ecom&lc=FI&item_name=Team%20Superluminal%20ROM%20%2d%20Morphic&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHosted";*/

		private static final String MARKET_DONATE_URL =
		    "market://details?id=org.teamsuperluminal.support";

		private void reboot(String reason)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
			.setTitle(getString(R.string.reboot_needed))
			.setMessage(reason)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					PowerManager pm = (PowerManager)SettingsActivity.this.getSystemService(Context.POWER_SERVICE);
					pm.reboot(null);
				}
			});
			builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});

			AlertDialog alert = builder.create();
			alert.show();
		}

		private void error(String reason)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
			.setTitle(getString(R.string.error))
			.setMessage(reason)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});

			AlertDialog alert = builder.create();
			alert.show();
		}

		private boolean checkSU()
		{
			return TsUtils.fileExists("/su/");
		}
		
		private void installSu()
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
			.setTitle(getString(R.string.pref_title_install_root))
			.setMessage(getString(R.string.pref_popup_install_root))
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					{
					TsUtils.recoInstall(SettingsActivity.this, "/system/opt/supersu.zip");
					}
				}
			});
			builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});

			AlertDialog alert = builder.create();
			alert.show();
		}

		private void uninstallSu()
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
			.setTitle(getString(R.string.pref_title_uninstall_root))
			.setMessage(getString(R.string.pref_popup_uninstall_root))
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					{
					TsUtils.recoInstall(SettingsActivity.this, "/system/opt/supersu_un.zip");
					}
				}
			});
			builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});

			AlertDialog alert = builder.create();
			alert.show();
		}
		
		private boolean checkVulkan()
		{
			return TsUtils.fileExists("/vendor/lib/hw/vulkan.msm8992.so");
		}

		private void installVulkan() {

			if (checkVulkan())
			{
				 AlertDialog.Builder builder = new AlertDialog.Builder(this);
					  builder
					  .setTitle(getString(R.string.pref_title_install_vulkan))
					  .setMessage(getString(R.string.pref_popup_already_vulkan))
					  .setIcon(android.R.drawable.ic_dialog_alert)
					  .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener()
					  {
						public void onClick(DialogInterface dialog, int which)
						{
						  dialog.dismiss();
						}
					  });
				builder.create().show();
				return;
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
			.setTitle(getString(R.string.pref_title_install_vulkan))
			.setMessage(getString(R.string.pref_popup_install_vulkan))
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					TsUtils.recoInstall(SettingsActivity.this, "/system/opt/vulkan.zip");
				}
			});
			builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});

			AlertDialog alert = builder.create();
			alert.show();
		}

		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
		{
			Log.d(TAG, key);
			
			if (key.endsWith("_b")) // Boolean settings ends with "_b"
			{
				saveSettingToProp(key, sharedPreferences.getBoolean(key, false) ? "1" : "0");
			}
			else
			{
				saveSettingToProp(key, sharedPreferences.getString(key, ""));
			}
		
			if (key.equals("capt_en_b"))
			{
				Settings.Global.putInt(this.getContentResolver(),
				                       Settings.Global.CAPTIVE_PORTAL_DETECTION_ENABLED, sharedPreferences.getBoolean(key, true) ? 1 : 0);
				reboot(getString(R.string.reboot_reason_captive));
			}
			else if (key.equals("powerprofile"))
			{
				try {
					String val = sharedPreferences.getString(key, "1");
					SystemProperties.set("persist.ts.profile", val);
					if (TsUtils.fileExists("/data/ts_power.sh"))
					{
						Runtime.getRuntime().exec(new String[] { "/system/bin/sh", "/data/ts_power.sh", "set_profile", val});
					} else {
						Runtime.getRuntime().exec(new String[] { "/system/bin/sh", "/system/etc/ts_power.sh", "set_profile", val});
					}
				}
				catch (Exception e) {
					Log.d(TAG, "ERROR: " + e.getMessage());
				}
			}
		}

		@Override
		protected void onResume() {
			super.onResume();
			getPreferenceScreen().getSharedPreferences()
			.registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		protected void onPause() {
			super.onPause();
			getPreferenceScreen().getSharedPreferences()
			.unregisterOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Get intent, action and MIME type
			Intent intent = getIntent();
			String action = (intent == null) ? "" : intent.getAction();

			if (action != null && (action.equals("DONATE") || action.equals("OPEN")))
			{
				NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				notificationManager.cancelAll();

				if (action.equals("DONATE"))
				{
					try { 
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PAYPAL_DONATE_URL));
						startActivity(browserIntent);
					} catch (Exception e) { }
					this.finish();
				}
			}
		}

		@Override
		protected void onPostCreate(Bundle savedInstanceState)
		{
			super.onPostCreate(savedInstanceState);

			getPreferenceManager().setSharedPreferencesName("TsExtra_preferences");

			SharedPreferences.Editor editor = getSharedPreferences("TsExtra_preferences", 0).edit();

			loadBoolSettingFromProp(editor, "hroot_en_b");

			//loadSettingFromProp(editor, "powerprofile");			
			editor.putString("powerprofile", SystemProperties.get("persist.ts.profile", "1"));

			editor.putBoolean("capt_en_b", Settings.Global.getInt(this.getContentResolver(),
			                  Settings.Global.CAPTIVE_PORTAL_DETECTION_ENABLED, 1) == 1 ? true : false);

			editor.commit();

			// we use the older PreferenceActivity APIs.
			addPreferencesFromResource(R.xml.pref_allsettings);

			if (!SystemProperties.get("ro.ts.build").equals(""))
				setValueSummary("teamsuperluminal", "ro.ts.build");

			Preference button = (Preference)findPreference("donate");
			button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					try {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PAYPAL_DONATE_URL));
						startActivity(browserIntent);
					} catch (Exception e) { }
					return true;
				}
			});

			button = (Preference)findPreference("donate2");
			button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					try {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_DONATE_URL));
						startActivity(browserIntent);
					} catch (Exception e) { }
					return true;
				}
			});

			button = (Preference)findPreference("install_su");
			button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					try {
						installSu();
					} catch (Exception e) { }
					return true;
				}
			});

			button = (Preference)findPreference("uninstall_su");
			button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					try {
						uninstallSu();
					} catch (Exception e) { }
					return true;
				}
			});


			button = (Preference)findPreference("install_vulkan");
			button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					Log.d(TAG, "install_vulkan clicked");
					try {
						installVulkan();
					} catch (Exception e) { }
					return true;
				}
			});

			if (checkSU()) {
				Log.d(TAG, "SU detected");
				((PreferenceGroup) findPreference("pref_header_root_settings")).removePreference(findPreference("install_su"));
			} else {
				Log.d(TAG, "SU not detected");
				((PreferenceGroup) findPreference("pref_header_root_settings")).removePreference(findPreference("uninstall_su"));
			}
		}

		private void setValueSummary(String preference, String property) {
			try {
				findPreference(preference).setSummary(SystemProperties.get(property));
			} catch (RuntimeException e) {
				// No recovery
			}
		}

		void loadBoolSettingFromProp(SharedPreferences.Editor editor, String setting)
		{
			Log.d(TAG, "Load " + setting);
			String val = SystemProperties.get("persist.sys.ts." + setting);
			Log.d(TAG, setting + " = " + val);
			editor.putBoolean(setting, "1".equals(val) ? true : false);
		}

		void loadSettingFromProp(SharedPreferences.Editor editor, String setting)
		{
			Log.d(TAG, "Load " + setting);
			String val = SystemProperties.get("persist.sys.ts." + setting);
			Log.d(TAG, setting + " = " + val);
			editor.putString(setting, val);
		}

		void saveSettingToProp(String setting, String val)
		{
			Log.d(TAG, setting + " => " + val);
			SystemProperties.set("persist.sys.ts." + setting, val);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean onIsMultiPane() {
			return false;
		}
}

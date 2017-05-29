package org.morphic.ts.extra;

import android.util.Log;
import android.os.SystemProperties;
import android.os.PowerManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

class TsUtils
{
	private static final String TAG = "TsUtils";    

	public static boolean writeLine(String fileName, String value) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(value.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "Could not write to file " + fileName, e);
            return false;
        }

        return true;
    }
		
	static public boolean fileExists(String path)
	{
		File file = new File(path);
		return file.exists();
	}

	static public void copyFile(File src, File dst) throws IOException 
	{
		InputStream in = new FileInputStream(src);
		try {
			OutputStream out = new FileOutputStream(dst);
			try {
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} finally {
				out.close();
			}
		} finally {
			in.close();
		}
	}

	static public boolean recoInstall(Context ctx, String filePath)
	{
		try {
			copyFile(new File(filePath), new File("/cache/recoinstall.zip"));
			copyFile(new File("/system/opt/recoinstall"), new File("/cache/recovery/openrecoveryscript"));
			
			PowerManager pm = (PowerManager)ctx.getSystemService(Context.POWER_SERVICE);
			pm.reboot("recovery");
			return true;
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
}


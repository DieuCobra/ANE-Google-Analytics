package eu.alebianco.air.extensions.utils;

import android.util.Log;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREObject;

public class FREUtils {

    public static final String TAG = "ANE";

    public static void logEvent(FREContext context, LogLevel level, String format, Object... args) {
        String message = String.format(format, args);
        Log.println(level.getPriority(), TAG, message);
        context.dispatchStatusEventAsync(message, level.getName());
    }
    
    public static String stripPackageFromClassName(String className) {
    	return className.substring(className.lastIndexOf(".") + 1);
    }

	public static FREObject createRuntimeException(String className, int id, String message, Object... data) {
		FREObject error = null;
        FREObject[] args = new FREObject[2] ;
        try {
        	args[0] = FREObject.newObject(String.format(message, data));
            args[1] = FREObject.newObject(id);
            error = FREObject.newObject(className, args);
		} catch (Exception e) {
			Log.println(Log.ERROR, TAG, String.format("Inception Error: Unable to create the runtime exception to notify the application about the previous error.\n" +
                    "[Error:(type:%s, message:%s]\n" +
                    "[Error:(type:%s, message:%s]",
                    stripPackageFromClassName(e.toString()), e.getMessage(),
                    stripPackageFromClassName(className), String.format(message, data)));
		}
        return error;
	}
}

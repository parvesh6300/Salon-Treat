package utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class LocationManagerClass {

	MyApplication global;
	String towers;
	private Context context;
	private LocationManager lm;
	private boolean gps_enabled = false,network_enabled = false;
	private LocationListener locListener = new MyLocationListener();
	
	public LocationManagerClass(Context ctx) {
		
		context = ctx;
		lm      = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		global  = (MyApplication) context.getApplicationContext();
		 /*
         * Getting the current location of the user
         */
		Log.v("turning gps ","on");
		turnGPSOn(ctx);
		Log.v("gps ","on");

         try{
        	 gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
         }catch(Exception e){
		 }
         try{
        	 network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
         }catch(Exception e){
		 }
         
         if (!gps_enabled) {
        	 Log.v("gps ","not enabled");
        	// Toast.makeText(context, "Your GPS is not enabled.\r\nPlease enable it manually.", Toast.LENGTH_SHORT).show();
  	  	} 
         if(!network_enabled){
        	 //Toast.makeText(context, "Your GPS is not enabled.\r\nPlease enable it manually.", Toast.LENGTH_SHORT).show();
         }
         
         if (gps_enabled) {
        	 Log.v("requesting loc ","on");

			 if(lm != null) {
				 if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
						 || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
					 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
				 }
			 }
		}
 		if (network_enabled) {

			if(lm != null) {
				if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
						|| ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
					lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
				}
			}
 		}
	}
	
	class MyLocationListener implements LocationListener {
	 	@Override
		public void onLocationChanged(Location location) {
	 		Log.v("loc is",""+location);
			if (location != null) {
				// This needs to stop getting the location data and save the battery power.
				Log.v("setting  ","global");

				if(lm != null) {
					if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
							|| ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
						lm.removeUpdates(locListener);
						Log.v("lat long set in locman"," lat lon"+location.getLatitude()+"  "+location.getLongitude());
					}
				}

				global.setLang(String.valueOf(location.getLongitude())) ;
				global.setLat(String.valueOf(location.getLatitude()));

			}
		}
		public void onProviderDisabled(String provider) {}
		public void onProviderEnabled(String provider) {}
		public void onStatusChanged(String provider, int status, Bundle extras) {}
	}
	
	public void turnGPSOn(Context ctx)
	{
		  String provider = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		  if(!provider.contains("gps")){
			    final Intent poke = new Intent();
			    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			    poke.setData(Uri.parse("3")); 
			    ctx.sendBroadcast(poke);
			    //Toast.makeText(this, "Your GPS is Enabled",Toast.LENGTH_SHORT).show();
		  }
	}
}

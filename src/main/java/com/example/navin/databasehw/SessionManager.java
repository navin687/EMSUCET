package com.example.navin.databasehw;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	SharedPreferences pref;
	
	Editor editor;
	
	Context _context;
	
	int PRIVATE_MODE = 0;
	
	private static final String PREF_NAME = "testpref";
	
	private static final String IS_LOGIN = "IsLoggedIn";
	
	public static final String KEY_NAME = "name";
	
	public static final String KEY_EMAIL = "email";
	
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	

	public void createLoginSession(){
		//	public void createLoginSession(String name, String email){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
		// Storing name in pref
	//	editor.putString(KEY_NAME, name);
		
		// Storing email in pref
	//	editor.putString(KEY_EMAIL, email);
		
		// commit changes
		editor.commit();
	}	
	

	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn())
		{
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LgsnActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			// Staring Login Activity
			_context.startActivity(i);
		}

		
	}
	
	
	

	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		
		// user email id
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		
		// return user
		return user;
	}

	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		
		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, SplashActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring Login Activity
		_context.startActivity(i);
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}

package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize Crash Reporting.
		ParseCrashReporting.enable(this);

		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);

		// Add your initialization code here
		Parse.initialize(this, "3k7ew6BMn2pDCMLCgx2sjT3gqqMJ7A8DVvkkZ9QT",
				"FfQQaitXic2dWq4tGyIHMQRBeaTJEEfNluxaiIAz");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

		ParseInstallation.getCurrentInstallation().saveInBackground();

	}
}

package com.amapv2.cn.apis.poisearch;

import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.net.Uri;

public class MySuggestionProvider_TT extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "com.amapv2.cn.apis.poisearch.MySuggestionProvider_TT";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public MySuggestionProvider_TT() {
		setupSuggestions(AUTHORITY, MODE);
	}

	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		Cursor cursor = super.query(uri, projection, selection, selectionArgs,
				sortOrder);

		return cursor;
	}

}

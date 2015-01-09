package com.jch.lib.view.TurnPage;

import android.graphics.Bitmap;

/**
 * loading page bitmap callback.
 */
public interface PageLoadCallback {

    public void onLoadStart(Bitmap loadingBtp);

    public void onLoadComplited(Bitmap overBtp);

    public void onLoadFailed(Bitmap failedBtp);

}

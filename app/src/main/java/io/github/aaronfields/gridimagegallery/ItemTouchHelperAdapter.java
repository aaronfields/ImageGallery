package io.github.aaronfields.gridimagegallery;

/**
 * Created by aaronfields on 10/4/16.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

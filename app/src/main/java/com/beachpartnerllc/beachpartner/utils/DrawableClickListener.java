package com.beachpartnerllc.beachpartner.utils;

/**
 * Created by Owner on 4/17/2018.
 */

public interface DrawableClickListener {
    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);
}

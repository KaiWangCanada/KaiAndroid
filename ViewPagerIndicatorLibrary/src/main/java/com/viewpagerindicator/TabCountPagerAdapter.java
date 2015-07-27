package com.viewpagerindicator;

public interface TabCountPagerAdapter {
    /**
     * Get icon representing the page at {@code index} in the adapter.
     */
    int getCountIntText(int index);

    // From PagerAdapter
    int getCount();
}

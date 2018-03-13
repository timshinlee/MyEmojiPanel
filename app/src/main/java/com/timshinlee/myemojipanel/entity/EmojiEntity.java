package com.timshinlee.myemojipanel.entity;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;

import com.timshinlee.myemojipanel.util.EmojiConstants;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class EmojiEntity {
    private Drawable avatar;
    private List<Integer> emojis;
    private int columnCount;
    private boolean hasDelete;
    private Pair<Integer, Integer> pageRange = new Pair<>(0, 0);
    /**
     * 单页中的总格数
     */
    private int pageCapability;
    /**
     * 单页中包含的表情数
     */
    private int emojiNumberOfPage;

    public EmojiEntity(Drawable avatar, List<Integer> emojis, int columnCount, boolean hasDelete) {
        this.avatar = avatar;
        this.emojis = emojis;
        this.columnCount = columnCount;
        this.hasDelete = hasDelete;

        pageCapability = EmojiConstants.ROW_COUNT * columnCount;
        emojiNumberOfPage = pageCapability - (hasDelete ? 1 : 0);
    }

    public int getPageCapability() {
        return pageCapability;
    }

    public int getEmojiNumberOfPage() {
        return emojiNumberOfPage;
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }

    public List<Integer> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<Integer> emojis) {
        this.emojis = emojis;
    }

    public boolean hasDelete() {
        return hasDelete;
    }

    public void setHasDelete(boolean hasDelete) {
        this.hasDelete = hasDelete;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    /**
     * @return 当前表情组占用的页数
     */
    public int getPageCount() {
        if (emojis.size() == 0) {
            return 1;
        }
        final boolean pageIsFilled = (emojis.size() % emojiNumberOfPage == 0);
        // 如果刚好一页，就不用加一页了
        return (emojis.size() / emojiNumberOfPage) + (pageIsFilled ? 0 : 1);
    }

    /**
     * 设置在ViewPager中的页码范围
     */
    public void setPageRange(int start, int end) {
        pageRange = new Pair<>(start, end);
    }

    public Pair<Integer, Integer> getPageRange() {
        return pageRange;
    }

    /**
     * 根据当前页面在ViewPager中的页码，获取内含表情的起始结束position
     *
     * @param viewPagerPosition
     * @return
     */
    public Pair<Integer, Integer> getCurrentPageBound(int viewPagerPosition) {
        final int offset = viewPagerPosition - pageRange.first;
        final int start = (emojiNumberOfPage - 1) * offset + (offset == 0 ? 0 : 1);
        final int end = Math.min((emojiNumberOfPage - 1) * (offset + 1), emojis.size() - 1);
        Log.e("TAG", "emojiNumberOfPage: "+emojiNumberOfPage );
        Log.e("TAG", "getCurrentPageBound: start = " + start + ", end = " + end);
        return new Pair<>(start, end);
    }
}

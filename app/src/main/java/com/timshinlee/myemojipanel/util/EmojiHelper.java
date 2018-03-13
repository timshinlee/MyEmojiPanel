package com.timshinlee.myemojipanel.util;

import android.util.Pair;

import com.timshinlee.myemojipanel.entity.EmojiEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class EmojiHelper {
    private static EmojiHelper sInstance;

    private EmojiHelper() {
    }

    public static EmojiHelper getInstance() {
        if (sInstance == null) {
            synchronized (EmojiHelper.class) {
                if (sInstance == null) {
                    sInstance = new EmojiHelper();
                }
            }
        }
        return sInstance;
    }

    private List<EmojiEntity> emojiEntities = new ArrayList<>();
    private int allPageCount = 0;

    public List<EmojiEntity> getEmojiEntities() {
        return emojiEntities;
    }

    public void addEntity(EmojiEntity entity) {
        emojiEntities.add(entity);
        invalidate();
    }

    public void removeEntity(EmojiEntity entity) {
        emojiEntities.remove(entity);
        invalidate();
    }

    public int getAllPageCount() {
        return allPageCount;
    }

    private synchronized void invalidate() {
        allPageCount = 0;
        for (EmojiEntity entity : emojiEntities) {
            final int thisPageCount = entity.getPageCount();
            entity.setPageRange(allPageCount, thisPageCount + allPageCount - 1);
            allPageCount += thisPageCount;
        }
    }

    public EmojiEntity getEntity(int position) {
        for (EmojiEntity entity : emojiEntities) {
            final Pair<Integer, Integer> pageRange = entity.getPageRange();
            if (position >= pageRange.first && position <= pageRange.second) {
                return entity;
            }
        }
        return null;
    }


}

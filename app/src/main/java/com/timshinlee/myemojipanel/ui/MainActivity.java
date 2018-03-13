package com.timshinlee.myemojipanel.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timshinlee.myemojipanel.R;
import com.timshinlee.myemojipanel.entity.EmojiEntity;
import com.timshinlee.myemojipanel.util.EmojiHelper;
import com.timshinlee.myemojipanel.util.EmojiSource;
import com.timshinlee.myemojipanel.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<List<String>> strs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


    }

    private void initView() {
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_launcher_foreground, null);
        List<Integer> emojis = Arrays.asList(EmojiSource.REGULARS);
        EmojiHelper.getInstance().addEntity(
                new EmojiEntity(drawable, emojis, 7, true));

        List<Integer> emojis2 = Arrays.asList(EmojiSource.REGULARS);
        EmojiHelper.getInstance().addEntity(
                new EmojiEntity(drawable, emojis2, 3, false));

        List<Integer> emojis3 = Arrays.asList(EmojiSource.REGULARS);
        EmojiHelper.getInstance().addEntity(
                new EmojiEntity(drawable, emojis3, 1, false));

        final ViewPager pager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(MainActivity.this);
        pager.setAdapter(pagerAdapter);

        final ViewPagerIndicator indicator = findViewById(R.id.indicator);
        indicator.bindViewPager(pager);

        final RecyclerView rvCategory = findViewById(R.id.rv_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(new CategoryAdapter());

    }

    private static class PagerAdapter extends FragmentStatePagerAdapter {
        private AppCompatActivity activity;

        public PagerAdapter(AppCompatActivity activity) {
            super(activity.getSupportFragmentManager());
            this.activity = activity;
        }

        @Override
        public Fragment getItem(int position) {
            final Bundle bundle = new Bundle();
            bundle.putInt(EmojiFragment.POSITION, position);
            return Fragment.instantiate(activity, EmojiFragment.class.getName(), bundle);
        }

        @Override
        public int getCount() {
            return EmojiHelper.getInstance().getAllPageCount();
        }
    }

    private static class Holder extends RecyclerView.ViewHolder {

        private final TextView mTextView;

        public Holder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
        }
    }

    private static class CategoryAdapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View layout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item_category, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}

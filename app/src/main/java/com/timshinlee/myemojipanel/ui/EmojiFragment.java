package com.timshinlee.myemojipanel.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.timshinlee.myemojipanel.R;
import com.timshinlee.myemojipanel.entity.EmojiEntity;
import com.timshinlee.myemojipanel.util.EmojiHelper;


public class EmojiFragment extends Fragment {

    private View mView;
    public static final String POSITION = "position";

    public EmojiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_emoji, container, false);

            final int position = getArguments().getInt(POSITION, 0);
            final EmojiEntity entity = EmojiHelper.getInstance().getEntity(position);
            final RecyclerView recycler = mView.findViewById(R.id.recycler);
            recycler.setLayoutManager(new GridLayoutManager(getContext(), entity.getColumnCount()));
            recycler.setAdapter(new Adapter(getActivity(), entity.getCurrentPageBound(position), entity));
        }
        return mView;
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final TextView mTvEmoji;

        public Holder(View itemView) {
            super(itemView);
            mTvEmoji = itemView.findViewById(R.id.tv_emoji);
        }
    }

    static class Adapter extends RecyclerView.Adapter<Holder> {
        private Context context;
        private Pair<Integer, Integer> positionBound;
        private EmojiEntity entity;

        public Adapter(Context context, Pair<Integer, Integer> positionBound, EmojiEntity entity) {
            this.context = context;
            this.positionBound = positionBound;
            this.entity = entity;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_emoji, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            if (entity.hasDelete()) {
                if ((position != 0 && (position + 1) % (entity.getPageCapability()) == 0)
                        || positionBound.first + position >= entity.getEmojis().size()) {
                    holder.mTvEmoji.setText("del");
                    holder.mTvEmoji.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "del", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }
            final Integer intEmoji = entity.getEmojis().get(positionBound.first + position);
            final String emoji = String.valueOf(Character.toChars(intEmoji));
            holder.mTvEmoji.setText(emoji);
            holder.mTvEmoji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, emoji, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return positionBound.second - positionBound.first + 1 + (entity.hasDelete() ? 1 : 0);
        }
    }

}

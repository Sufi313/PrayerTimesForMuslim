package com.sufi.prayertimes.findMosque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sufi.prayertimes.R;


import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.List;


public class FindMosqueAdapter extends RecyclerView.Adapter<FindMosqueAdapter.RecyclerViewHolder> {

    private Context mCtx;
    private List<MosqueDataModel> list;
    private RecyclerViewItemClickListener itemListener;

    public FindMosqueAdapter(Context mCtx, java.util.List<MosqueDataModel> list, RecyclerViewItemClickListener itemListener) {
        this.mCtx = mCtx;
        this.list = list;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mosque_info_layout, parent, false);
        return new RecyclerViewHolder(view, itemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {

        final MosqueDataModel model = list.get(position);
        holder.mosqueName.setText(model.getName());
        holder.mosqueAddress.setText(model.getAddress());
        DecimalFormat df = new DecimalFormat("0.00");
        holder.mosqueDistance.setText(mCtx.getString(R.string.mosque_distance, df.format(model.getDistance())));
        Glide.with(mCtx)
                .load(model.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_map_marker)
                .into(holder.imageView);

        if (position != 0) {
            holder.back.setEnabled(true);
        }

        if (position+1 != getItemCount()) {
            holder.next.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mosqueName, mosqueAddress, mosqueDistance;
        Button next, back;
        ImageView imageView;
        private WeakReference<RecyclerViewItemClickListener> listenerRef;


        public RecyclerViewHolder(@NonNull View itemView, RecyclerViewItemClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);
            mosqueName = itemView.findViewById(R.id.tv_mosqueName);
            mosqueAddress = itemView.findViewById(R.id.tv_mosqueAddress);
            mosqueDistance = itemView.findViewById(R.id.tv_mosqueDistance);
            next = itemView.findViewById(R.id.nextBtnMosque);
            back = itemView.findViewById(R.id.backBtnMosque);
            imageView = itemView.findViewById(R.id.iv_mosqueImage);

            imageView.setOnClickListener(this);
            next.setOnClickListener(this);
            back.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nextBtnMosque:
                    listenerRef.get().onNextButtonClick(v, this.getAdapterPosition());
                    break;

                case R.id.backBtnMosque:
                    listenerRef.get().onBackButtonClick(v, this.getAdapterPosition());
                    break;

                case R.id.iv_mosqueImage:
                    listenerRef.get().onImageButtonClick(v, this.getAdapterPosition());
                    break;
            }

        }

    }

    public interface RecyclerViewItemClickListener {
        void onNextButtonClick(View v, int position);

        void onBackButtonClick(View v, int position);

        void onImageButtonClick(View v, int position);
    }
}

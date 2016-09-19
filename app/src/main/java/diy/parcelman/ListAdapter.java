package diy.parcelman;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public interface OnItemClickListener {

        void onClick(View view, int position);

        void onLongClick(View view, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView textData;
        public TextView textPreviousData;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            textData = (TextView) view.findViewById(R.id.text_data);
            textPreviousData = (TextView) view.findViewById(R.id.text_previous_data);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onLongClick(view, getLayoutPosition());
            return true;
        }

    }

    private Context context = null;
    private Sample list = null;
    private SparseBooleanArray selected = null;
    private OnItemClickListener listener = null;

    public ListAdapter(Context context, @NonNull Sample list, @NonNull OnItemClickListener listener) {
        super();
        this.context = context;
        this.list = list;
        this.selected = new SparseBooleanArray();
        this.listener = listener;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Sample.Subsample item = list.get(position);
        String data = Integer.toString(item.getData());
        String previousData = Integer.toString(item.getPreviousData());
        holder.textData.setText(data);
        holder.textPreviousData.setText(previousData);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

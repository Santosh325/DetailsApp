package com.example.detailsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.detailsapp.database.AppDatabase;
import com.example.detailsapp.database.DetailsEntry;

import java.util.List;

public class DetailsListAdapter extends RecyclerView.Adapter<DetailsListAdapter.DetailsViewHolder> {
    private Context context;
    final private ItemClickListener mItemClickListener;
    private List<DetailsEntry> detailsEntryList;

    public DetailsListAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item,parent,false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
       DetailsEntry detailsEntry = detailsEntryList.get(position);
       String name = detailsEntry.getName();
       String fatherName = detailsEntry.getFathersName();
       String phoneNumber = detailsEntry.getPhoneNumber();
       String address = detailsEntry.getAddress();

       holder.textViewName.setText(name);
       holder.textViewFatherName.setText(fatherName);
       holder.textViewPhone.setText(phoneNumber);
       holder.textViewAddress.setText(address);
    }

    @Override
    public int getItemCount() {
        if(detailsEntryList == null) {
            return 0;
        }
        return detailsEntryList.size();
    }
    public void setTasks(List<DetailsEntry> detailsEntries) {
        detailsEntryList = detailsEntries;
        notifyDataSetChanged();
    }



    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public List<DetailsEntry> getDetails() {
        return detailsEntryList;
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName,textViewFatherName,textViewPhone,textViewAddress;
        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_name);
            textViewFatherName = itemView.findViewById(R.id.textview_father_name);
            textViewPhone = itemView.findViewById(R.id.textview_phone);
            textViewAddress= itemView.findViewById(R.id.textview_address);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int elementId = detailsEntryList.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);


        }
    }
}

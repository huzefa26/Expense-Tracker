package com.example.a16it030_p17;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public ExpenseAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder{
        public TextView expenseText, amountText, dateText;

        public ExpenseViewHolder(View view){
            super(view);
            expenseText = view.findViewById(R.id.expenseText);
            amountText = view.findViewById(R.id.amountText);
            dateText = view.findViewById(R.id.dateText);
        }
    }

    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycler_element, viewGroup, false);
        return new ExpenseViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ExpenseViewHolder viewHolder, int i) {
        if(!mCursor.moveToPosition(i)){
            return;
        }
        String expense = mCursor.getString(1);
        int amount = mCursor.getInt(2);
        long date = mCursor.getLong(3);
        int id = mCursor.getInt(0);


        viewHolder.expenseText.setText(expense);
        viewHolder.amountText.setText(String.valueOf(amount));
        //viewHolder.dateText.setText((new Date(date)).toString());
        viewHolder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }
    }
}

package sumago.androidipt.b3expensemanagement.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.activities.ExpenseInfoActivity;
import sumago.androidipt.b3expensemanagement.activities.UpdateExpenseActivity;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;
import sumago.androidipt.b3expensemanagement.interfaces.onItemDeleteListener;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.ViewHolder> {

    ArrayList<Expense> filteredExpenseList;
    TextView tvName, tvAmount;
    ImageView btnInfo, btnEdit, btnDelete;
    DbHelper dbHelper;
    onItemDeleteListener onItemDeleteListener;

    public FilterListAdapter(ArrayList<Expense> filteredExpenseList, onItemDeleteListener onItemDeleteListener) {
        this.filteredExpenseList = filteredExpenseList;
        this.onItemDeleteListener = onItemDeleteListener;
    }

    @NonNull
    @Override
    public FilterListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);
        return new FilterListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterListAdapter.ViewHolder holder, int position) {
        tvName.setText(filteredExpenseList.get(position).getName());
        tvAmount.setText(String.valueOf(filteredExpenseList.get(position).getAmount()));
        btnDelete.setOnClickListener(v -> {
            onItemDeleteListener.deleteExpense(filteredExpenseList.get(position).getId());
        });

        btnEdit.setOnClickListener(v->{
            Intent i = new Intent(v.getContext(), UpdateExpenseActivity.class);
            i.putExtra("id", filteredExpenseList.get(position).getId());
            holder.itemView.getContext().startActivity(i);
        });

        btnInfo.setOnClickListener(v->{
            Intent i = new Intent(v.getContext(), ExpenseInfoActivity.class);
            i.putExtra("id", filteredExpenseList.get(position).getId());
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return filteredExpenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btndelete);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            btnEdit = itemView.findViewById(R.id.btnedit);
            tvName = itemView.findViewById(R.id.tvExpenseName);
            tvAmount = itemView.findViewById(R.id.tvExpenseAmount);
            dbHelper = new DbHelper(itemView.getContext());
        }
    }
}
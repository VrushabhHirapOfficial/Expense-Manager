package sumago.androidipt.b3expensemanagement.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class ExpenseInfoActivity extends AppCompatActivity {
    TextView tvName, tvDate, tvCategory, tvAmount, tvNote;
    Expense expense;
    DbHelper dbHelper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_info);


        tvNote = findViewById(R.id.tvExpenseNote);
        tvName = findViewById(R.id.tvExpenseName);
        tvDate = findViewById(R.id.tvExpenseDate);
        tvCategory = findViewById(R.id.tvExpenseCategory);
        tvAmount = findViewById(R.id.tvExpenseAmount);
        dbHelper = new DbHelper(this);
        id = getIntent().getIntExtra("id", -1);
        if(id==-1) expense = new Expense();
        else expense = dbHelper.getExpenseById(id);

        // In ExpenseInfoActivity
        if (expense != null) {
            Log.d("ExpenseInfoActivity", "Expense Category: " + expense.getCategory());
            tvName.setText(expense.getName());
            tvNote.setText(expense.getNote());
            tvDate.setText(expense.getDate());
            tvCategory.setText(expense.getCategory());
            tvAmount.setText(String.valueOf(expense.getAmount()));
        } else {
            Log.d("ExpenseInfoActivity", "Expense is null");
        }



    }

}
package sumago.androidipt.b3expensemanagement.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etName, etAmount, etNote, etcategory;
    TextView tvDate;
    Button btnAdd;
    DbHelper dbHelper;

    Calendar calender;
    int day, month , year;

    String selecteddate,seletedcategory;
    Spinner spCategory;
    ArrayList<String> categorynames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etAmount = findViewById(R.id.etAmount);
        etName = findViewById(R.id.etName);
        tvDate = findViewById(R.id.etDate);
        etNote = findViewById(R.id.etNote);
        etcategory = findViewById(R.id.etCategory);
        spCategory = findViewById(R.id.spinnerCategory);
        btnAdd = findViewById(R.id.btnAdd);
        dbHelper = new DbHelper(this);
        calender = Calendar.getInstance();
        day = calender.get(Calendar.DAY_OF_MONTH);
        month = calender.get(Calendar.MONTH);
        year = calender.get(Calendar.YEAR);
        categorynames = dbHelper.getAllCategories();
        categorynames.clear();
        categorynames.add("Food");
        categorynames.add("Stationary");
        categorynames.add("Ticket");
        categorynames.add("Rikshaw");
        categorynames.add("Vegetables");
        categorynames.add("Other");
        selecteddate = year+"-"+(month+1)+"-"+day;
        tvDate.setText(selecteddate);


        spCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categorynames));
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(categorynames.get(position).equals("Other")){
                    etcategory.setText(null);
                    etcategory.setVisibility(View.VISIBLE);
                } else {
                    etcategory.setVisibility(View.GONE);
                    seletedcategory = categorynames.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tvDate.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            selecteddate = dayOfMonth+"-"+(month+1)+"-"+year;
                            tvDate.setText(selecteddate);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        });

        btnAdd.setOnClickListener(v->{
            //insert data into database
            if(etcategory.getVisibility() == View.VISIBLE){
                seletedcategory = etcategory.getText().toString();
            }
            double amount = Double.parseDouble(etAmount.getText().toString());
            String name = etName.getText().toString(),
                    note = etNote.getText().toString(),
                    date = tvDate.getText().toString();


            long result = dbHelper.insertExpense(new Expense(name, date, note, amount, seletedcategory));

            if(result>0)
                Toast.makeText(this, "Expense Inserted Successfully", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddExpenseActivity.this,MainActivity.class);
            startActivity(intent);
        });

    }
}
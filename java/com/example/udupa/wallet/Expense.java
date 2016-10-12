package com.example.udupa.wallet;

import android.app.ActionBar;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;


public class Expense extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alt_expense);
        LinearLayout expView = (LinearLayout)findViewById(R.id.myLayout);
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllInfo();
        if(c.moveToFirst()) {
            do {
                //display(c);
                LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
                TextView tv = new TextView(this);
                tv.setLayoutParams(lparams);
                tv.setText("Date: " + c.getString(1) + "\nAmount: " + c.getString(2) + "\nReason: " + c.getString(3) + "\n");
                expView.addView(tv);
            } while (c.moveToNext());
        }
        db.close();
    }

    public void display(Cursor c) {
        //Toast.makeText(this, "id: " + c.getString(0) + "\nDate: " + c.getString(1) + "\nAmount: " + c.getString(2) + "\nReason: " + c.getString(3),Toast.LENGTH_LONG).show();
        //LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //TextView tv = new TextView(this);
        //tv.setLayoutParams(lparams);
        //tv.setText("id: " + c.getString(0) + "\nDate: " + c.getString(1) + "\nAmount: " + c.getString(2) + "\nReason: " + c.getString(3));
        //this.expView.addView(tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

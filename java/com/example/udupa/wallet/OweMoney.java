package com.example.udupa.wallet;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class OweMoney extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owe_money);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button button1 = (Button)findViewById(R.id.btn_lend);
        Button button2 = (Button)findViewById(R.id.btn_borrow);
        final DBAdapter db = new DBAdapter(this);
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                db.open();
                long id = db.lendMoney(Integer.parseInt(((EditText)findViewById(R.id.deal_amount)).getText().toString()),((EditText)findViewById(R.id.deal_person)).getText().toString());
                db.close();
                Intent data = new Intent();
                EditText deal_money = (EditText)findViewById(R.id.deal_amount);
                data.setData(Uri.parse(deal_money.getText().toString()+"/lend"));
                setResult(RESULT_OK,data);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                db.open();
                long id = db.borrowMoney(Integer.parseInt(((EditText)findViewById(R.id.deal_amount)).getText().toString()),((EditText)findViewById(R.id.deal_person)).getText().toString());
                db.close();
                Intent data = new Intent();
                EditText deal_money = (EditText)findViewById(R.id.deal_amount);
                data.setData(Uri.parse(deal_money.getText().toString()+"/borrow"));
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_owe_money, menu);
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

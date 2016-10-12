package com.example.udupa.wallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public static int money = 0;
    int r_Code = 2;
    int request_Code = 1;
    int req_Code = 0;
    private SharedPreferences prefs;
    private String prefname = "myPref";
    private static final String MONEY_LEFT_VALUE = "money_left";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences(prefname,MODE_PRIVATE);
        money = prefs.getInt(MONEY_LEFT_VALUE,0);
        String message = "You have "+ money +" Rs in your wallet";
        TextView textView = (TextView)findViewById(R.id.tv_money);
        textView.setText(message);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void spendMoney(View view) {
        startActivityForResult(new Intent(this,SpendMoney.class),req_Code);
    }

    public void addMoney(View view) {
        startActivityForResult(new Intent(this,AddMoney.class),request_Code);
    }

    public void viewExpenses(View view) {
        Intent intent = new Intent(this, Expense.class);
        startActivity(intent);
    }

    public void dealMoney(View view) {
        startActivityForResult(new Intent(this,OweMoney.class),r_Code);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(requestCode==request_Code) {
            if(resultCode==RESULT_OK) {
                TextView textView = (TextView)findViewById(R.id.tv_money);
                money += Integer.parseInt(data.getData().toString());
                textView.setText("You have "+ money +" Rs in your wallet");
            }
        }
        if(requestCode==req_Code) {
            if(resultCode==RESULT_OK) {
                TextView textView = (TextView)findViewById(R.id.tv_money);
                money -= Integer.parseInt(data.getData().toString());
                textView.setText("You have "+ money +" Rs in your wallet");
            }
        }
        if(requestCode==r_Code) {
            if(resultCode==RESULT_OK) {
                TextView textView = (TextView)findViewById(R.id.tv_money);
                String res = data.getData().toString();
                String ress[] = res.split("/");
                if(ress[1].equalsIgnoreCase("lend")) {
                    money -= Integer.parseInt(ress[0]);
                }
                else if(ress[1].equalsIgnoreCase("borrow")) {
                    money += Integer.parseInt(ress[0]);
                }
                textView.setText("You have " + money + " Rs in your wallet");
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        prefs = getSharedPreferences(prefname,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MONEY_LEFT_VALUE,money);
        editor.commit();
    }
}

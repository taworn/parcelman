package diy.parcelman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = EditActivity.class.getSimpleName();

    private EditText editData = null;

    private Sample.Subsample item = null;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        editData = (EditText) findViewById(R.id.edit_name);

        Button buttonSave = (Button) findViewById(R.id.button_save);
        if (buttonSave != null) {
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uiToData();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("data.item", item);
                    resultIntent.putExtra("data.position", position);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            });
        }
        Button buttonDelete = (Button) findViewById(R.id.button_delete);
        if (buttonDelete != null) {
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("data.delete", true);
                    resultIntent.putExtra("data.position", position);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            });
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            item = bundle.getParcelable("data.item");
            position = bundle.getInt("data.position");
        }
        else {
            item = new Sample.Subsample();
            if (buttonDelete != null)
                buttonDelete.setVisibility(View.INVISIBLE);
        }
        uiFromData();
        Log.d(TAG, "onCreate() item: " + item.getData() + "/" + item.getPreviousData());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void uiFromData() {
        String data = Integer.toString(item.getData());
        editData.setText(data);
    }

    private void uiToData() {
        try {
            String data = editData.getText().toString().trim();
            item.setData(Integer.parseInt(data));
        }
        catch (NumberFormatException ex) {
            item.setData(0);
        }
    }

}

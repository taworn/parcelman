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

public class EditMasterActivity extends AppCompatActivity {

    private static final String TAG = EditMasterActivity.class.getSimpleName();

    private EditText editName = null;
    private EditText editData = null;

    private Sample item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        editName = (EditText) findViewById(R.id.edit_name);
        editData = (EditText) findViewById(R.id.edit_data);

        Button buttonSave = (Button) findViewById(R.id.button_save);
        if (buttonSave != null) {
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uiToData();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("data.item", item);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            });
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            item = bundle.getParcelable("data.item");
        }
        else {
            item = new Sample();
        }
        uiFromData();
        Log.d(TAG, "onCreate()");
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
        String name = item.getName();
        String data = Integer.toString(item.getData());
        editName.setText(name);
        editData.setText(data);
    }

    private void uiToData() {
        try {
            String name = editName.getText().toString().trim();
            String data = editData.getText().toString().trim();
            item.setName(name);
            item.setData(Integer.parseInt(data));
        }
        catch (NumberFormatException ex) {
            item.setData(0);
        }
    }

}

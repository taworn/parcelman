package diy.parcelman;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_ADD = 100;
    private static final int REQUEST_EDIT = 101;

    private Sample sample = null;
    private ListAdapter listAdapter = null;
    private RecyclerView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            sample = savedInstanceState.getParcelable("data.sample");
        }
        else {
            sample = new Sample();
        }

        listAdapter = new ListAdapter(this, sample, new ListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Sample.Subsample item = sample.get(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("data.item", item);
                intent.putExtra("data.position", position);
                startActivityForResult(intent, REQUEST_EDIT);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        });

        listView = (RecyclerView) findViewById(R.id.list_view);
        if (listView != null) {
            listView.setHasFixedSize(true);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setItemAnimator(new DefaultItemAnimator());
            listView.setAdapter(listAdapter);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    startActivityForResult(intent, REQUEST_ADD);
                }
            });
        }
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

        if (id == R.id.action_edit) {
            //
            return true;
        }
        else if (id == R.id.action_delete_all) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_clear_black_24dp)
                    .setTitle(R.string.delete_all_dialog_caption)
                    .setMessage(R.string.delete_all_dialog_message)
                    .setNegativeButton(R.string.delete_all_dialog_negative, null)
                    .setPositiveButton(R.string.delete_all_dialog_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sample.clear();
                            listAdapter.notifyDataSetChanged();
                            Log.d(TAG, "delete with ALL items");
                        }
                    })
                    .show();
            return true;
        }
        else if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_android_black_24dp)
                    .setTitle(R.string.about_dialog_caption)
                    .setMessage(R.string.about_dialog_message)
                    .setNeutralButton(R.string.about_dialog_neutral, null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);

        if (requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK) {
            Sample.Subsample item = resultIntent.getParcelableExtra("data.item");
            if (item != null) {
                sample.add(item);
                listAdapter.notifyDataSetChanged();
                Log.d(TAG, "add with item: " + item.getData() + "/" + item.getPreviousData());
            }
        }

        else if (requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            Sample.Subsample item = resultIntent.getParcelableExtra("data.item");
            boolean delete = resultIntent.getBooleanExtra("data.delete", false);
            int position = resultIntent.getIntExtra("data.position", 0);
            if (item != null) {
                sample.edit(position, item.getData());
                listAdapter.notifyDataSetChanged();
                Log.d(TAG, "edit with item: " + item.getData() + "/" + item.getPreviousData());
            }
            else if (delete) {
                sample.delete(position);
                listAdapter.notifyDataSetChanged();
                Log.d(TAG, "delete with item position: " + position);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("data.sample", sample);
    }

}

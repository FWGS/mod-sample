package in.celest.xash3d.bshift;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;

import in.celest.xash3d.bshift.R;

public class LauncherActivity extends Activity {
	// public final static String ARGV = "in.celest.xash3d.MESSAGE";
	static EditText cmdArgs;
	static SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
		mPref = getSharedPreferences("mod", 0);
		cmdArgs = (EditText)findViewById(R.id.cmdArgs);
		cmdArgs.setText(mPref.getString("argv","-dev 3 -log"));
	}

    public void startXash(View view)
    {
		Intent intent = new Intent(Intent.ACTION_VIEW,null);
		intent.setType("xash3d.android/game");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		SharedPreferences.Editor editor = mPref.edit();
		editor.putString("argv", cmdArgs.getText().toString());
		editor.commit();
		editor.apply();
		if(cmdArgs.length() != 0) intent.putExtra("argv", cmdArgs.getText().toString());
		intent.putExtra("gamedir", "bshift");
		intent.putExtra("gamelibdir", getFilesDir().getAbsolutePath().replace("/files","/lib"));
		startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
		 return true;
		 }*/

        return super.onOptionsItemSelected(item);
    }
}

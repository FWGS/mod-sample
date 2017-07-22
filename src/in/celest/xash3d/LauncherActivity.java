package in.celest.xash3d;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import java.io.File;

public class LauncherActivity extends Activity {
	static EditText cmdArgs;
	static SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Build layout
        LinearLayout launcher = new LinearLayout(this);
        launcher.setOrientation(LinearLayout.VERTICAL);
        launcher.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        TextView titleView = new TextView(this);
        titleView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        titleView.setText("Command-line arguments");
        titleView.setTextAppearance(this, android.R.attr.textAppearanceLarge);
        cmdArgs = new EditText(this);
        cmdArgs.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		Button startButton = new Button(this);
		// Set launch button title here
		startButton.setText("Launch " + "mod" + "!");
		LayoutParams buttonParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		buttonParams.gravity = 5;
		startButton.setLayoutParams(buttonParams);
		startButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startXash(v);
            }
        });
		launcher.addView(titleView);
		launcher.addView(cmdArgs);
		// Add other options here
		launcher.addView(startButton);
        setContentView(launcher);
		mPref = getSharedPreferences("mod", 0);
		cmdArgs.setText(mPref.getString("argv","-dev 3 -log"));
		// Uncomment this if you have pak file
		// ExtractAssets.extractPAK(this, false);
	}

	private Intent prepareIntent(Intent i)
	{
		String argv = cmdArgs.getText().toString();
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		SharedPreferences.Editor editor = mPref.edit();

		editor.putString("argv", argv);
		editor.commit();
		editor.apply();

		// Command-line arguments
		// if not passed, uses arguments from xash3d package
		// Uncoment this if you are using client from other package
		/*
		String libserver = getFilesDir().getAbsolutePath().replace("/files","/lib/libserver_hardfp.so");
		if( !(new File(libserver).exists()) )
			libserver = getFilesDir().getAbsolutePath().replace("/files","/lib/libserver.so");
		argv = "-dll "+ libserver + " " + argv;
		*/
		if(argv.length() != 0)
			i.putExtra("argv", argv);

		// default gamedir
		// may be overriden by -game command-line option
		// Uncomment to set gamedir here
		/*intent.putExtra("gamedir", "mod" );*/

		// default library package
		// if you are using client from other package (not from half-life),
		// replace following line by:
		/*i.putExtra("gamelibdir", "/data/data/<clientpkgname>/lib");*/
		i.putExtra("gamelibdir", getFilesDir().getAbsolutePath().replace("/files","/lib"));
		
		// if you are using pak file, uncomment this:
		// i.putExtra("pakfile", getFilesDir().getAbsolutePath() + "/extras.pak");

		// you may pass extra enviroment variables to game
		// it is availiable from game code with getenv() function
		/*
		String envp[] = 
		{
			"VAR1", "value1",
			"VAR2", "value2"
		};
		i.putExtra("env", envp);
		*/

		return i;
	}
	
    public void startXash(View view)
    {
		try
		{
			Intent intent = new Intent();
			intent.setAction("in.celest.xash3d.START");
			intent = prepareIntent(intent);
			startActivity(intent);
			return;
		}
		catch(Exception e){}
		// some samsung devices have 
		// completely broken intent resolver
		// so try specify exact component here
		try
		{
			Intent intent = new Intent();
			intent.setComponent(new ComponentName("in.celest.xash3d.hl.test", "in.celest.xash3d.XashActivity")); 
			intent = prepareIntent(intent);
			startActivity(intent);
			return;
		}
		catch(Exception e){}
		try
		{
			Intent intent = new Intent();
			intent.setComponent(new ComponentName("in.celest.xash3d.hl", "in.celest.xash3d.XashActivity")); 
			intent = prepareIntent(intent);
			startActivity(intent);
			return;
		}
		catch(Exception e){}
	}
}

package LifeCounter.Main;

import android.os.Bundle;
import android.app.Activity;

public class EditProfilesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profiles);
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}

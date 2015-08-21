package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jawnnypoo.physicslayout.PhysicsLinearLayout;

import kai.kaiprivate.R;

public class KaiPhysicsLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_physics_layout);
    }

    public void click(View v) {
        PhysicsLinearLayout physicsLinearLayout = (PhysicsLinearLayout) findViewById(R.id.physics_layout);
        physicsLinearLayout.getPhysics().giveRandomImpulse();
    }
}

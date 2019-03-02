package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Date;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;

public class AddMatchActivity extends AppCompatActivity {

    public final static String EXTRA_REPLY = "de.hwr.basketistis.AddMatchActivity.REPLY";

    private Button addMatchButton;
    private EditText opponentEditText;
    private EditText dateTimeEditText;
    private Switch isHomeSwitch;
    private EditText cityEditText;
    private EditText descriptionEditText;

    private Boolean isHome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

        addMatchButton = findViewById(R.id.addMatchButton);
        opponentEditText = findViewById(R.id.addMatchOpponentEditText);
        dateTimeEditText = findViewById(R.id.addMatchDate);
        isHomeSwitch = findViewById(R.id.addMatchIsHomeSwitch);
        cityEditText = findViewById(R.id.addMatchCity);
        descriptionEditText = findViewById(R.id.addMatchDescription);

        isHomeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHome = isChecked;
            }
        });

        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchEntity match = new MatchEntity(
                        cityEditText.getText().toString(),
                        opponentEditText.getText().toString(),
                        isHome,
                        dateTimeEditText.getText().toString(),
                        descriptionEditText.getText().toString());

                Intent matchesIntent = new Intent(AddMatchActivity.this, MatchesActivity.class);
                matchesIntent.putExtra(EXTRA_REPLY, match);
                setResult(RESULT_OK, matchesIntent);
                finish();
            }
        });
    }
}

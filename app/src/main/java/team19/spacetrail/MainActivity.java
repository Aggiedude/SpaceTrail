package team19.spacetrail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javagame.*;


public class MainActivity extends Activity {

    private final double FOOD_COST = 1;
    private final double FUEL_COST = 5;
    private final int ENGINE_COST = 15;
    private final int ALUMINUM_COST = 10;
    private final int WING_COST = 15;
    private final int LIVING_BAY_COST = 15;

    private Game game;
    private int startingMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        game = new Game();
        startingMoney = game.getMoney();
    }

    /* Helper Methods */
    //Sets Layout to the new game menu
    public void newGameMenu(View view) {
        setContentView(R.layout.new_game_menu);
    }

    //Sets Layout to the buying starting resources menu
    public void buyResourceMenu(View view) {
        EditText captain = (EditText) findViewById(R.id.editCapField);
        EditText crew1 = (EditText) findViewById(R.id.firstOtherNameField);
        EditText crew2 = (EditText) findViewById(R.id.secOtherNameField);
        EditText crew3 = (EditText) findViewById(R.id.thirdOtherNameField);
        EditText crew4 = (EditText) findViewById(R.id.fourOtherNameField);

        String capName = captain.getText().toString();
        String crew1Name = crew1.getText().toString();
        String crew2Name = crew2.getText().toString();
        String crew3Name = crew3.getText().toString();
        String crew4Name = crew4.getText().toString();

        if(capName.length()!=0 && crew1Name.length()!=0 && crew2Name.length()!=0 && crew3Name.length()!=0 && crew4Name.length()!=0) {
            game.addCrew(capName, true);
            game.addCrew(crew1Name, false);
            game.addCrew(crew2Name, false);
            game.addCrew(crew3Name, false);
            game.addCrew(crew4Name, false);
            setContentView(R.layout.resource_menu);
            TextView moneyText = (TextView) findViewById(R.id.moneyVariable);
            moneyText.setText(Integer.toString(startingMoney));
        }
        else {
            //Pop-up alert telling user to fill in all names
            Context context = getApplicationContext();
            CharSequence popup = "Need to input all names!";
            Toast toast = Toast.makeText(context, popup, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        }
    }

    //Sets Layout to the load game menu
    public void loadGame(View view) {
        Intent intent = new Intent(this, AsteroidActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_load_game);
    }

    //Sets Layout to the Instructions menu
    public void loadInstructions(View view) {
        setContentView(R.layout.instruction_screen);
    }

    //Does the function of buying the original resources.
    public void buyStartingResources(View view) {
        //This is where we will change the xml file to reflect the changes in resources.
        Button buy = (Button) findViewById(R.id.buyButton);
        EditText fuel = (EditText) findViewById(R.id.fuelTextField);
        EditText food = (EditText) findViewById(R.id.foodTextField);
        EditText engine = (EditText) findViewById(R.id.engineTextField);
        EditText aluminum = (EditText) findViewById(R.id.aluminumTextField);
        EditText wings = (EditText) findViewById(R.id.wingsTextField);
        EditText livingBays = (EditText) findViewById(R.id.livingBayTextField);

        TextView fuelText = (TextView) findViewById(R.id.fuelQuantity);
        TextView foodText = (TextView) findViewById(R.id.foodQuantity);
        TextView engineText = (TextView) findViewById(R.id.engineQuantity);
        TextView aluminumText = (TextView) findViewById(R.id.aluminumQuantity);
        TextView wingsText = (TextView) findViewById(R.id.wingQuantity);
        TextView livingBayText = (TextView) findViewById(R.id.livingBayQuantity);

        if(Integer.parseInt(fuel.getText().toString()) * FUEL_COST + Integer.parseInt(food.getText().toString())*FOOD_COST + Integer.parseInt(engine.getText().toString())*ENGINE_COST+Integer.parseInt(aluminum.getText().toString())*ALUMINUM_COST+Integer.parseInt(wings.getText().toString())*WING_COST+Integer.parseInt(livingBays.getText().toString()) * LIVING_BAY_COST - startingMoney < 0) {
            fuelText.setText(Integer.toString(Integer.parseInt(fuelText.getText().toString()) + Integer.parseInt(fuel.getText().toString())));
            foodText.setText(Integer.toString(Integer.parseInt(foodText.getText().toString()) + Integer.parseInt(food.getText().toString())));
            engineText.setText(Integer.toString(Integer.parseInt(engineText.getText().toString()) + Integer.parseInt(engine.getText().toString())));
            aluminumText.setText(Integer.toString(Integer.parseInt(aluminumText.getText().toString()) + Integer.parseInt(aluminum.getText().toString())));
            wingsText.setText(Integer.toString(Integer.parseInt(wingsText.getText().toString()) + Integer.parseInt(wings.getText().toString())));
            livingBayText.setText(Integer.toString(Integer.parseInt(livingBayText.getText().toString()) + Integer.parseInt(livingBays.getText().toString())));

            startingMoney -= Integer.parseInt(fuel.getText().toString()) * FUEL_COST + Integer.parseInt(food.getText().toString())*FOOD_COST + Integer.parseInt(engine.getText().toString())*ENGINE_COST+Integer.parseInt(aluminum.getText().toString())*ALUMINUM_COST+Integer.parseInt(wings.getText().toString())*WING_COST + Integer.parseInt(livingBays.getText().toString()) * LIVING_BAY_COST;

            //Creates pop-up letting user know the resources were purchased correctly
            Context context = getApplicationContext();
            CharSequence popup = "Resources Acquired!";
            Toast toast = Toast.makeText(context, popup, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        }
        else {
            //Creates pop-up letting user know the resources were purchased correctly
            Context context = getApplicationContext();
            CharSequence popup = "Not Enough Funds!";
            Toast toast = Toast.makeText(context, popup, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        }


        //Resets the fields to be blank values
        fuel.setText("0");
        food.setText("0");
        engine.setText("0");
        aluminum.setText("0");
        wings.setText("0");
        livingBays.setText("0");
        TextView moneyText = (TextView) findViewById(R.id.moneyVariable);
        moneyText.setText(Integer.toString(startingMoney));
    }
    // Called when the user hits the send to space button, stores the starting resources
    public void headToSpace(View view) {
        Intent intent = new Intent(this, GameScreenActivity.class);
        TextView fuel = (TextView) findViewById(R.id.fuelQuantity);
        int fuelint = Integer.parseInt(fuel.getText().toString());
        game.sellFuel(fuelint);

        TextView food = (TextView) findViewById(R.id.foodQuantity);
        int foodint = Integer.parseInt(food.getText().toString());
        game.sellFood(foodint);

        TextView engine = (TextView) findViewById(R.id.engineQuantity);
        int engineint = Integer.parseInt(engine.getText().toString());
        game.sellParts(engineint, "engine");

        TextView alum = (TextView) findViewById(R.id.aluminumQuantity);
        int alumint = Integer.parseInt(alum.getText().toString());
        game.sellAluminum(alumint);

        TextView wings = (TextView) findViewById(R.id.wingQuantity);
        int wingsint = Integer.parseInt(wings.getText().toString());
        game.sellParts(wingsint, "wing");

        //Ends MainActivity and starts the GameScreenActivity
        //intent.putExtra("Game", game);
        startActivity(intent);
        finish();
    }

    //Used for a button to start the GameScreenActivity
    public void gameScreen(View view) {
        Intent intent = new Intent(this, GameScreenActivity.class);
        intent.putExtra("Game", game);
        startActivity(intent);
        finish();
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
}

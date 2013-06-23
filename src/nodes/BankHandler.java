package nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class BankHandler extends Node {
    private int[] BANKS = {42378,42377,42217};
    private int[] SHIELDS = {11283,11284,1540};
    private int foodNum = 16;
    private int food = 385;
    Tile bankTile = new Tile(3093,3494,0);

    @Override
    public boolean activate(){

        return Players.getLocal().isValid() && Calculations.distanceTo(bankTile)<=8
                || Inventory.isFull();
    }

    @Override
    public void execute(){

        SceneObject Booth = SceneEntities.getNearest(BANKS);

        if(!Bank.isOpen()){
            if(!Equipment.containsOneOf(SHIELDS)){
                Booth.interact("Bank");
                if(Bank.getItemCount(SHIELDS)>=1){
                    for(int x = 0; x <= SHIELDS.length; x++){
                        Bank.withdraw(SHIELDS[x], 1);
                        Bank.close();
                        if(Inventory.contains(SHIELDS)){
                            Equipment.equip(SHIELDS);
                            break;
                        }
                    }
                } else {
                    sleep(1);
                }
            }
        }

        if(!Bank.isOpen()){
            if(Booth.getLocation().distance(Booth) >= 6){
                Walking.walk(Booth);
            }
            Camera.turnTo(Booth);
            Booth.interact("Bank");
            Task.sleep(500,1000);
        }
        Bank.depositInventory();
        Task.sleep(100,250);
        Bank.withdraw(food, foodNum);
        Bank.close();

    }
}

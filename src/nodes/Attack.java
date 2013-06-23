package nodes;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
//KBD 17776

public class Attack extends Node {

    LootHandler looting = new LootHandler();
    FoodHandler eating = new FoodHandler();
    private int KBDDEATHID = 17780;
    private int RESONANCE = 7;
    private int PREPARATION = 6;
    private int REJUVENATE = 9;
    private int REFLECT = 0;
    private int BARRICADE = 8;
    private int ASSAULT = 5;



    public void buildAdrenaline(){
        do{
            eating.emergencyEat();
            for(int i=1; i<5; i++){
                if(ActionBarHandler.abilityReady(i)){
                    ActionBarHandler.executeAbility(i);
                    eating.emergencyEat();
                }
            }
            Task.sleep(150,300);
        }while(ActionBarHandler.getAdrenaline()<100);
    }

    @Override
    public boolean activate(){
        Area dragonArea = new Area(new Tile(2256,4681,0), new Tile(2287,4709,0));
        if (NPCs.getNearest("King Black Dragon") != null && dragonArea.contains(Players.getLocal().getLocation())){
                return true;
        }
        return false;
    }

    @Override
    public void execute(){
            NPC theKBD = NPCs.getNearest("King Black Dragon");
        if(theKBD != null && !Players.getLocal().isInCombat()){
                if(!theKBD.isOnScreen()){
                    Camera.turnTo(theKBD);
                }
                if(Calculations.distanceTo(theKBD)>=6){
                    Walking.walk(theKBD);
                }
                theKBD.interact("Attack");
            } //else {
              //  theKBD.interact("Attack");
            //}


            if(eating.activate()){
                eating.execute();
            }
            if(Players.getLocal().isInCombat()){
            if(Players.getLocal().getHealthPercent()<=85){
                if(ActionBarHandler.getAdrenaline()>=75){
                    buildAdrenaline();
                    if(ActionBarHandler.abilityReady(REJUVENATE)){
                        ActionBarHandler.executeAbility(REJUVENATE);
                    }
                }else{
                    if(eating.activate()){
                        eating.execute();
                    }
                }
            }

           // if(!ActionBarHandler.damageReduction()){
                if(ActionBarHandler.abilityReady(RESONANCE)){
                    ActionBarHandler.executeAbility(RESONANCE);
          //      }
            }

        //   if(!ActionBarHandler.damageReduction()){
        if(ActionBarHandler.abilityReady(BARRICADE)){
            ActionBarHandler.executeAbility(BARRICADE);
        }
        //    }
            if(ActionBarHandler.abilityCoolDown(RESONANCE)<=15){
                if(ActionBarHandler.abilityReady(PREPARATION)){
                    ActionBarHandler.executeAbility(PREPARATION);
                }
            }

            if(!ActionBarHandler.damageReduction()){
                if(ActionBarHandler.abilityReady(REFLECT)){
                    ActionBarHandler.executeAbility(REFLECT);
                }
            }



            if(ActionBarHandler.abilityReady(ASSAULT)){
                ActionBarHandler.executeAbility(ASSAULT);
            }

             int i = Random.nextInt(1,4);
                if(ActionBarHandler.abilityReady(i)){
                    ActionBarHandler.executeAbility(i);
                }

            }
        if(looting.activate() && !Players.getLocal().isInCombat()){
            looting.execute();
        }
     }
}


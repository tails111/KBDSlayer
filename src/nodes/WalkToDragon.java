package nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

//Artifact = 77834

public class WalkToDragon extends Node {
    Tile[] toArtifactPath = new Tile[] {
            new Tile(3086,3500,0), new Tile(3073, 3504, 0), new Tile(3060, 3514, 0), new Tile(3051, 3520, 0)};
    Area dragonArea = new Area(new Tile(2256,4681,0), new Tile(2287,4709,0));


    @Override
    public boolean activate(){
        return !dragonArea.contains(Players.getLocal().getLocation())
                && Inventory.getCount(385)>6;
    }

    @Override
    public void execute(){
        int ARTIFACT = 77834;
        SceneObject Artifact = null;
        Artifact = SceneEntities.getNearest(ARTIFACT);
        Tile t1 = new Tile(3051,3519,0);
        do{
            if(Calculations.distanceTo(t1)>=4){
                Walking.walk(t1);
                if(!Artifact.isOnScreen() && Artifact.validate()){
                    Camera.turnTo(Artifact);
                }
            }
            if(!Artifact.interact("Activate")){
                Task.sleep(500,1000);
                Artifact.interact("Activate");
            }
        }while(!dragonArea.contains(Players.getLocal().getLocation()));
       // Task.sleep(500,2000);
        }
    }



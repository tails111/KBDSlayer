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

//1817 at 2273, 4681, 0

public class WalkToBank extends Node {
    Tile[] toBankPath = new Tile[] {
            new Tile(3059,3511,0), new Tile(3069, 3505, 0), new Tile(3080, 3500, 0), new Tile(3093, 3494, 0)};
    Tile artifactInCave = new Tile(2273,4682,0);
    Tile bankTile = new Tile(3093,3494,0);

    Area dragonArea = new Area(new Tile(2256,4681,0), new Tile(2287,4709,0));

    @Override
    public boolean activate(){
        return Inventory.getCount(385)<=3
                && Calculations.distanceTo(bankTile)>=4
                || Inventory.isFull();
    }

    @Override
    public void execute(){
        int ARTIFACT = 1817;
        SceneObject Artifact = SceneEntities.getNearest(ARTIFACT);
        Tile ArtifactOutSide = new Tile(3051, 3520, 0);
        do{
            if(dragonArea.contains(Players.getLocal().getLocation())){
                Walking.walk(artifactInCave);
                Camera.turnTo(Artifact);
                Artifact.interact("Activate");
            }else {
                Walking.walk(bankTile);
            }
        } while(Calculations.distanceTo(bankTile)>=4);
    }
}

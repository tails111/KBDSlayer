import nodes.*;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.wrappers.interactive.NPC;

import java.awt.*;

@Manifest(name = "King Black Dragon Slayer", authors = "tails111", description = "Kills KBD, heals at bank.")
public class KBDSlayer extends ActiveScript implements PaintListener {

    private Tree script = new Tree(new Node[]{
            new WalkToDragon(),
            new Attack(),
            new LootHandler(),
            new WalkToBank(),
            new BankHandler()
    });

    @Override
    public void onRepaint(Graphics g){

        final String profit = String.valueOf(LootHandler.profit);

        g.setColor(Color.WHITE);
        g.drawString(profit, 250, 250);

        final NPC interacting = NPCs.getNearest("King Black Dragon");

        if(interacting != null){
            if(interacting.getHealthPercent() >= 75){
                g.setColor(Color.GREEN);
            } else if(interacting.getHealthPercent() >= 50){
                g.setColor(Color.YELLOW);
            } else if(interacting.getHealthPercent() >= 25){
                g.setColor(Color.ORANGE);
            } else {
                g.setColor(Color.RED);
            }

            for(final Polygon p : interacting.getBounds()){
                g.fillPolygon(p);
            }
        }
    }

    @Override
    public void onStart(){
    }

    @Override
    public int loop(){
        final Node stateNode = script.state();
        if(stateNode != null && Game.isLoggedIn()){
            script.set(stateNode);
            final Node setNode = script.get();
            if(setNode != null){
                getContainer().submit(setNode);
                setNode.join();
            }
        }
        return Random.nextInt(250, 450);
    }
}





package nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LootHandler extends Node {

    public int[] LootTable = {1516,6571,1631,1617,1619,1621,1623,985,443,2366,385,452,9431,2364,1462,1514,987,441,445,
                              1443,1452,15152,533,2361,2362,886,9144,892,888,561,563,560,565,1185,1079,1127,1201,25318,
                              25319,25316,25317,1149,1247,1303,830,1373,1319,1249,1215,536,1747,19346,19348,19350,19352,
                              19354,19356,19358,19360,19362,19364,19366,19368,19370,12160,18019,12158,12527,18017,12159,
                              18018,12163,18020,7980};
    public static int profit = 0;

    private int getPrices(final int id) {
        int price = 0;
        String add = "http://scriptwith.us/api/?return=text&item=";
        add += id;
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(
                    new URL(add).openConnection().getInputStream()));
            final String line = in.readLine();
            in.close();
            final String[] subset = line.split("[:]");
            price = Integer.parseInt(subset[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    @Override
    public boolean activate(){
        if(!Tabs.getCurrent().equals(Tabs.INVENTORY)){
            Tabs.INVENTORY.open();
        }
        GroundItem Loot = GroundItems.getNearest(LootTable);
        if(Loot != null && !Inventory.isFull() && !Players.getLocal().isInCombat()){
            return true;
        }
        return false;
    }

    @Override
    public void execute(){
        GroundItem Loot = GroundItems.getNearest(LootTable);

        if(Loot != null){      //If Loot isn't on screen, will walk and turn camera to it.
            if(!Loot.isOnScreen()){
                    Walking.walk(Loot);
                    Camera.turnTo(Loot);
                    if(Loot.getId()==995){
                        if(Loot.getGroundItem().getStackSize()>=10){
                            profit += getPrices(Loot.getId());
                            System.out.println(profit);
                            sleep(500);
                        }
                    }
                    if(Loot.interact("Take",Loot.getGroundItem().getName())){
                        profit += getPrices(Loot.getId());
                        System.out.println(profit);
                        sleep(500);
                    }
                    while(Players.getLocal().isMoving()){
                        sleep(50,100);
                    }
            } else {         //If Loot is on screen and close, picks it up.
                if(Loot.isOnScreen()){
                    Camera.turnTo(Loot);
                    if(Loot.getId()==995){
                        if(Loot.getGroundItem().getStackSize()>=10){
                            profit += getPrices(Loot.getId());
                            System.out.println(profit);
                            sleep(500);
                        }
                    }
                    if(Loot.interact("Take",Loot.getGroundItem().getName())){
                        profit += getPrices(Loot.getId());
                        System.out.println(profit);
                        sleep(500);
                    }
                    while(Players.getLocal().isMoving()){
                        sleep(50,100);
                    }
                }
            }
        }
    }

}
//445  | Gold Ore(Noted)
//441  | Iron ore(Noted)
//987  | Loop Half
//1514 | Magic Logs
//1462 | Nature Talisman
//2364 | Runebar(noted)
//9431 | Runite Limbs
//452  | Runite Ore(Noted)
//385  | Shark
//2366 | Shield Left Half
//443  | Silver Ore(Noted)
//985  | Tooth half
//1623 | Uncut Sapp
//1621 | Uncut Emer
//1619 | Uncut Ruby
//1617 | Uncut Diam
//1631 | Uncut Drag
//6571 | Uncut Onyx
//1516 | Uncut Yew
//1247 | Rune Spear
//1149 | Dragon Helm
//25316,25317 | Dragon Rider Boots
//25318,25319 | Dragon ride gloves
//1201 | Rune Kiteshield
//1127 | Rune Platebody
//1079 | Rune Platlegs
//1185 | Rune sq shield
//565  | Blood rune
//560  | Death rune
//563  | Law Rune
//561  | Nature rune
//888  | Mithril Arrow
//892  | Rune Arrow
//9144 | Runite bolts
//886  | Steel Arrow
//2361,2362 | Adamant Bar
//533  | Big Bones(Noted)
//15152| TailBone
//1452 | Chaos Tally
//1443 | Fire Tally(Noted)
//1747 | B D-Hide
//536  | D-Bones
//1215 | D-Dagger
//1249 | D-Spear
//1319 | Rune 2h
//1373 | Rune B-axe
//830  | Rune javelin
//1303 | Rune Longsword


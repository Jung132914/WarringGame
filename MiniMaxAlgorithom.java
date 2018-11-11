package comp1110.ass2;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The author of this code is Zhongzheng Li, This function using the MiniMax algorithm and alphe-beta pruning to get the
 * next step of computer.
 */

/**
 * This class is the data structure of DFS Tree.
 */
class treeNode {

    String id;
    double value=22222;
    double alphe=22222;
    double beta=22222;
    String CurrentString;

    treeNode(String id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return CurrentString+" "+"\n"+"value: "+value+" a: "+alphe+" b: "+beta+"\n";//+children+"/n";
    }

}

/**
 * This class is the algorithm. The whole DFS Tree is stored in the treeNodeHashMap.
 */

public class Algorithm {


    HashMap<String, treeNode> treeNodeHashMap = new HashMap<>();
    treeNode originalNode;
    String setup;
    char nextStep;

    String getFather(treeNode child) {
        int endex = child.id.length() - 1;
        String parent = child.id.substring(0, endex);
        return parent;
    }

    /**
     * This function use to get the child of the parent node.
     *
     * @param parent Parent tree node.
     * @return A list of the children node.
     */


    List<treeNode> getChilds(treeNode parent) {

        List<treeNode> Childs = new ArrayList<>();
        List<Character> node = ALboard.valideMovement(parent.CurrentString);
        node.forEach(nod -> {
            treeNode treenode = new treeNode("" + parent.id + nod);
            treenode.CurrentString = ALboard.getCurrentPlacementOneStep(parent.CurrentString, nod);
            Childs.add(treenode);
        });

        treeNodeHashMap.put(parent.id, parent);
        return Childs;
    }

    /**
     * This function used MiniMax algorithm and alphe-beta pruning to build the whole DFS tree which is stored
     * in the treeNodeHashMap.
     * @param treenode The Current tree node
     * @param level The depth of research.
     */
    public void recursive(treeNode treenode, int level) {

        if (treenode.alphe == 22222 && treenode.beta == 22222) {
            treenode.alphe = treeNodeHashMap.get(getFather(treenode)).alphe;
            treenode.beta = treeNodeHashMap.get(getFather(treenode)).beta;
        }

        int currentlevel = treenode.id.length() - originalNode.id.length();

        if (currentlevel == level) {

            treenode.value = Evaluation(setup, treenode.id);
            if (currentlevel % 2 == 1) {
                if (treenode.value > treeNodeHashMap.get(getFather(treenode)).alphe) {
                    treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                    treeNodeHashMap.get(getFather(treenode)).alphe = treenode.value;
                }
                if (treeNodeHashMap.get(getFather(treenode)).value == 22222) {
                    treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                }

            } else {
                if (treenode.value < treeNodeHashMap.get(getFather(treenode)).beta) {
                    treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                    treeNodeHashMap.get(getFather(treenode)).beta = treenode.value;
                }
                if (treeNodeHashMap.get(getFather(treenode)).value == 22222) {
                    treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                }
            }
            treeNodeHashMap.put(treenode.id,treenode);
            return;
        } else if (ALboard.valideMovement(treenode.CurrentString).isEmpty()) {

            treenode.value = Evaluation(setup, treenode.id) * 10;
            if (currentlevel % 2 == 1) {
                if (treenode.value > treeNodeHashMap.get(getFather(treenode)).alphe) {
                    treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                    treeNodeHashMap.get(getFather(treenode)).alphe = treenode.value;
                }
                if (treeNodeHashMap.get(getFather(treenode)).value == 22222) {
                    treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                }

            } else {
                if (treenode.value < treeNodeHashMap.get(getFather(treenode)).beta) {
                    treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                    treeNodeHashMap.get(getFather(treenode)).beta = treenode.value;
                }
                if (treeNodeHashMap.get(getFather(treenode)).value == 22222) {
                    treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                }
            }
            treeNodeHashMap.put(treenode.id,treenode);
            return;

        } else {
            List<treeNode> childs = getChilds(treenode);
            if (childs != null) {
                for (treeNode child : childs) {
                    if (treenode.beta >= treenode.alphe) {
                        recursive(child, level);
                    } else {
                        continue;
                    }
                }
            }

            if (currentlevel == 0) {

            } else {
                if (currentlevel % 2 == 1) {
                    if (treenode.value > treeNodeHashMap.get(getFather(treenode)).alphe) {
                        treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                        treeNodeHashMap.get(getFather(treenode)).alphe = treenode.value;
                    }
                    if (treeNodeHashMap.get(getFather(treenode)).value == 22222) {
                        treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                    }
                } else {
                    if (treenode.value < treeNodeHashMap.get(getFather(treenode)).beta) {
                        treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                        treeNodeHashMap.get(getFather(treenode)).beta = treenode.value;
                    }
                    if (treeNodeHashMap.get(getFather(treenode)).value == 22222) {
                        treeNodeHashMap.get(getFather(treenode)).value = treenode.value;
                    }
                }
                return;
            }
        }
    }

    /**
     * This function is evaluative function to get the leaves value.
     * The algorithm of the function is computer's flags * n + computer's cards - player's flags * n - player's cards
     *
     * @param setup The string of the original placement.
     * @param movement The string of current movement.
     * @return The double value of the leaf node.
     */
    public static double Evaluation(String setup, String movement){

        double play_hold_cards=WarringStatesGame.getSupporters(setup,movement,2,0).length()/2;
        double computer_hold_cards=WarringStatesGame.getSupporters(setup,movement,2,1).length()/2;
        int[] flag=WarringStatesGame.getFlags(setup,movement,2);

        List<Integer> flag_list=Arrays.stream(flag).boxed().collect(Collectors.toList());

        int player_flag=Collections.frequency(flag_list,0);
        int computer_flag=Collections.frequency(flag_list,1);


        double value=computer_flag*movement.length()+computer_hold_cards-player_flag*movement.length()-play_hold_cards;


        return value;
    }

    /**
     * To search the treeNodeHashMap and return the next step.
     * @return A char of the next step.
     */
    public char Get_Min_Max_result(){
        List<Character> Next_Steps=ALboard.valideMovement(originalNode.CurrentString);
        if (!Next_Steps.isEmpty()) {
            Next_Steps.forEach(next -> {
                if (originalNode.value == treeNodeHashMap.get(originalNode.id + next).value) {
                    nextStep = next;
                }
            });
            return nextStep;
        }else {
            return ' ';
        }
    }

    /**
     * This function is the constructor of the class.
     * @param setup The string of the original placement.
     * @param CurrentString The string of the current placement during the game.
     * @param movement The string of the current movement.
     * @param level The level of depth to search.
     */
    public Algorithm(String setup,String CurrentString,String movement,int level){
        treeNode treenode=new treeNode(movement);
        treenode.CurrentString=CurrentString;
        treenode.alphe=-1000;
        treenode.beta=1000;
        this.setup=setup;
        this.originalNode=treenode;
        treeNodeHashMap.put(originalNode.id,originalNode);
        recursive(treenode,level);
    }
}


import java.util.*;

public class Teacher
{
    public static ArrayList<String> drawPool = new ArrayList<String>();
    public static ArrayList<String> chosenOnes = new ArrayList<String>();
    public static ArrayList<String> repeats = new ArrayList<String>();
    public static int[] counters = new int[9];
    public static int oneSC = 0;
    public static int twoSC = 0;
    public static int fourSC = 0;
    public static int eightSC = 0;
    public static int sixteenSC = 0;
    public static int thirtytwoSC = 0;
    public static int sixtyfourSC = 0;
    public static int hundredtwentyeightSC = 0;
    public static int twohundredfiftysixSC = 0;

    public static void main(String[] args) {
        long timestart = System.currentTimeMillis();
        
        setUpTickets();

        int teachercounter = 0;
        for(int i = 0; i < 5000; i++) {
            chosenOnes = simulation();
            teachercounter += results();
            resetLottery();
        }
        System.out.println("Teacher: "+teachercounter);
        for(int i = 0; i < counters.length; i++) {
            System.out.println(String.format("%d Ticket(s): %d", (int)(Math.pow(2, i)), (int)(counters[i])));
        }

        System.out.println(System.currentTimeMillis()-timestart + " milliseconds");
    }

    //Adds the correct number of tickets using this setup: 
    //  1Ticket1 is set up so:
    //  The first number is the number of tickets that person has and the second number is that person's "ID". So if there's 3560 people with 1 ticket, the last person would be 1Ticket3560
    //  Then, depending on how many tickets that person has, it'll be added to the drawPool that many times. 4Ticket5 will have 5 tickets named "4Ticket5" all representing 1 person.
    public static void setUpTickets() {
        for(int i = 0; i < 3560; i++) {
            drawPool.add("1Ticket"+(i+1));
        }
        for(int i = 0; i < 1577; i++) {
            for(int j = 0; j < 2; j++) {
                drawPool.add("2Ticket"+(i+1));
            }
        }
        for(int i = 0; i < 2; i++) {
            drawPool.add("Teacher");
        }
        for(int i = 0; i < 731; i++) {
            for(int j = 0; j < 4; j++) {
                drawPool.add("4Ticket"+(i+1));
            }
        }
        for(int i = 0; i < 525; i++) {
            for(int j = 0; j < 8; j++) {
                drawPool.add("8Ticket"+(i+1));
            }
        }
        for(int i = 0; i < 374; i++) {
            for(int j = 0; j < 16; j++) {
                drawPool.add("16Ticket"+(i+1));
            }
        }
        for(int i = 0; i < 232; i++) {
            for(int j = 0; j < 32; j++) {
                drawPool.add("32Ticket"+(i+1));
            }
        }
        for(int i = 0; i < 127; i++) {
            for(int j = 0; j < 64; j++) {
                drawPool.add("64Ticket"+(i+1));
            }
        }
        for(int i = 0; i < 37; i++) {
            for(int j = 0; j < 128; j++) {
                drawPool.add("128Ticket"+(i+1));
            }
        }
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 256; j++) {
                drawPool.add("256Ticket"+(i+1));
            }
        }
    }
    
    //Reset the drawPool by adding in all the chosen/repeated tickets, then cleaer the chosen/repeated lists
    public static void resetLottery() {
        for(String i : chosenOnes) {
            drawPool.add(i);
        }
        for(String i : repeats) {
            drawPool.add(i);
        }
        chosenOnes.clear();
        repeats.clear();
        if(drawPool.size() != 41392)
            System.err.println("Error: "+drawPool.size());
    }
    
    //Randomly selects tickets from the drawPool, checking if that person has already been chosen
    //  If that person has already been chosen, remove that ticket from drawPool, add it to repeats list, and reroll (decrement and continue)
    //  If that person hasn't already been chosen, remove that ticket from the drawPool and add it to the chosen list
    //Return the list of chosen tickets once a certain number of people have been selected
    public static ArrayList<String> simulation() {
        ArrayList<String> chosen = new ArrayList<String>();
        for(int i = 0; i < 349; i++) {
            int rand = (int)(Math.random()*drawPool.size());
            String remove = drawPool.remove(rand);
            if(chosen.contains(remove)) {
                repeats.add(remove);
                i--;
                continue;
            }
            chosen.add(remove);
        }
        return chosen;
    }
    
    //U
    public static int results() {

        /* To track "Group Counts" rather than the count of one individual person with that many tickets
        int oneGC = 0;
        int twoGC = 0;
        int fourGC = 0;
        int eightGC = 0;
        int sixteenGC = 0;
        int thirtytwoGC = 0;
        int sixtyfourGC = 0;
        int hundredtwentyeightGC = 0;
        int twohundredfiftysixGC = 0;
        */
        boolean teacher = false;
        for(int i = 0; i < chosenOnes.size(); i++) {
            String text = chosenOnes.get(i);
            int index = text.indexOf("Ticket");
            if(index == - 1) {
                teacher = true;
                continue;
            }

            //I only track Ticket1 because I only want to count how many of one type of person is chosen within each ticket group.
            //As a result, I get the probability of one person's chance of being chosen if they have a certain number of tickets.
            if(text.substring(index).equals("Ticket1")) {
                int begin = Integer.parseInt(text.substring(0, index));
                begin = (int)(Math.log(begin)/Math.log(2));
                counters[begin]++;
            }
            /*
            Below code is for tracking what percentage of the total chosen group is represented by a certain number of tickets. A "Group Count"

            int ticket = Integer.parseInt(chosenOnes.get(i).substring(0, index));
            switch(ticket) {
                case 1: oneGC++; break;
                case 2: twoGC++; break;
                case 4: fourGC++; break;
                case 8: eightGC++; break;
                case 16: sixteenGC++; break;
                case 32: thirtytwoGC++; break;
                case 64: sixtyfourGC++; break;
                case 128: hundredtwentyeightGC++; break;
                case 256: twohundredfiftysixGC++; break;
                default: break;
            }
            */
        }
        if(teacher)
            return 1;
        return 0;
        
    }
}

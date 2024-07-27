
import java.util.ArrayList;
import java.lang.Math;


public class WSERLottery
{
    //drawPool is the list of total tickets
    //chosenOnes is the list of chosen participants
    //repeats is the list of tickets that have been drawn but are already in chosenOnes
    //soloCount is the count of tickets of 1 person with that many tickets that got drawn
    //numPeople is the number of people with that many tickets in the year
    public static ArrayList<String> drawPool = new ArrayList<String>();
    public static ArrayList<String> chosenOnes = new ArrayList<String>();
    public static ArrayList<String> repeats = new ArrayList<String>();
    public static int[] soloCount = new int[9];
    //2022:
    //public static int[] numPeople = {3318, 1063, 722, 514, 328, 186, 59, 18};
    //2023:
    public static int[] numPeople = {3560, 1578, 731, 525, 374, 232, 127, 37, 5};
    //2024:
    //public static int[] numPeople = {4434, 2216, 1231, 606, 420, 256, 147, 70, 8};


    public static void main(String[] args) {

        System.out.println(runLottery());
    }

    public static int[] runLottery() {
        long timestart = System.currentTimeMillis();
        
        setUpTickets();

        for(int i = 0; i < 5000; i++) {
            chosenOnes = simulation();
            results();
            resetLottery();
        }

        System.out.println(System.currentTimeMillis()-timestart + " milliseconds");

        return soloCount;
    }

    //Adds the correct number of tickets using this setup: 
    //  1Ticket1 is set up so:
    //  The first number is the number of tickets that person has and the second number is that person's "ID". So if there's 3560 people with 1 ticket, the last person would be 1Ticket3560
    //  Then, depending on how many tickets that person has, it'll be added to the drawPool that many times. 4Ticket5 will have 5 tickets named "4Ticket5" all representing 1 person.
    public static void setUpTickets() {
        for(int i = 0; i < numPeople.length; i++) {
            int numTickets = (int)Math.pow(2, i);
            for(int j = 0; j < numPeople[i]; j++) {
                for(int k = 0; k < numTickets; k++) {
                    drawPool.add(String.format("%dTicket%d", numTickets, j));
                }
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
    public static void results() {

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
        for(int i = 0; i < chosenOnes.size(); i++) {
            String text = chosenOnes.get(i);
            int index = text.indexOf("Ticket");

            //I only track Ticket1 because I only want to count how many of one type of person is chosen within each ticket group.
            //As a result, I get the probability of one person's chance of being chosen if they have a certain number of tickets.
            if(text.substring(index).equals("Ticket1")) {
                int begin = Integer.parseInt(text.substring(0, index));
                begin = (int)(Math.log(begin)/Math.log(2));
                soloCount[begin]++;
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
        
    }
}

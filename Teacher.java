
import java.util.*;

public class Teacher
{
    public static ArrayList<String> drawPool = new ArrayList<String>();
    public static ArrayList<String> chosenOnes = new ArrayList<String>();
    public static ArrayList<String> repeats = new ArrayList<String>();
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
        System.out.println("1 Ticket: "+oneSC);
        System.out.println("2 Tickets: "+twoSC);
        System.out.println("4 Tickets: "+fourSC);
        System.out.println("8 Tickets: "+eightSC);
        System.out.println("16 Tickets: "+sixteenSC);
        System.out.println("32 Tickets: "+thirtytwoSC);
        System.out.println("64 Tickets: "+sixtyfourSC);
        System.out.println("128 Tickets: "+hundredtwentyeightSC);
        System.out.println("256 Tickets: "+twohundredfiftysixSC);

        System.out.println(System.currentTimeMillis()-timestart);
        
        
    }

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
    
    public static int results() {
        /*int oneGC = 0;
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
            if(text.substring(index).equals("Ticket1")) {
                String begin = text.substring(0, index);
                if(begin.equals("1"))
                oneSC++;
            else if(begin.equals("2"))
                twoSC++;
            else if(begin.equals("4"))
                fourSC++;
            else if(begin.equals("8"))
                eightSC++;
            else if(begin.equals("16"))
                sixteenSC++;
            else if(begin.equals("32"))
                thirtytwoSC++;
            else if(begin.equals("64"))
                sixtyfourSC++;
            else if(begin.equals("128"))
                hundredtwentyeightSC++;
            else if(begin.equals("256"))
                twohundredfiftysixSC++;
            }
            /*
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

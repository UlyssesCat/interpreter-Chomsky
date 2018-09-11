import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;

public class Main {

    static int Max=15;


    public static void input(ArrayList<Pri> p)
    {
        String[] left=new String[Max];
        String[] right=new String[Max];
        Scanner scanner = new Scanner(System.in);
        for(int i=0;;i++)
        {
            String tmp=scanner.nextLine();
            if(tmp=="end")   break;
            String[] tmp2=tmp.split("->");
            left[i]=tmp2[0];
            right[i]=tmp2[1];
            Pri temp=new Pri(tmp2[0],tmp2[1]);
            p.add(temp);
        }
    }

    public static boolean hasVn(String s){
        String news=s.toLowerCase();
        if(news.equals(s))
            return false;
        else
            return true;//存在非终结符

    }

    public static void main(String[] args)
    {
        System.out.println("Hello World!");

        Grammer G=new Grammer();
        input(G.p);
    }


}


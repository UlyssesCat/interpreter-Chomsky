import java.util.HashSet;
import java.util.Scanner;
import java.lang.*;
import java.util.Set;
import java.util.ArrayList;
public class Main {

    /**
     * @param args
     */
    static int count=0;

    static String G;
    static Set<String> setVt=new HashSet<String>();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString;
        CSS[] p = new CSS[50];

        System.out.println("输入文法");
        G=scanner.next();
        System.out.println("输入非终结符Vn(第一个为开始符号)");
        String vnss = scanner.next();
        String[] vns = vnss.trim().split(",");
        char vn[] = new char[vns.length];
        for(int i=0;i<vns.length;i++)
        {
            vn[i]=vns[i].charAt(0);
        }





        ArrayList<String> inputStr=new ArrayList<String>();

        System.out.println("输入产生式");
        for (int k = 0; k < p.length; k++) {
            inputString = scanner.next();

            String[] LAR;
            if(inputString.trim().equals("end"))
            {
                scanner.close();
                break;
            }
            else
            {
                inputStr.add(inputString);
                if(inputString.contains("->"))
                {
                    LAR = inputString.trim().split("->");
                    if(!LAR[1].contains("|"))
                    {
                        p[count++] = new CSS(LAR[0],LAR[1]);
                        char[] ctmp=LAR[0].toCharArray();
                        for(int i=0;i<ctmp.length;i++)
                        {
                            setVt.add(ctmp[i]+"");
                        }
                        char[] ctmp2=LAR[1].toCharArray();
                        for(int i=0;i<ctmp2.length;i++)
                        {
                            setVt.add(ctmp2[i]+"");
                        }

                    }
                    else {
                        //判断有几个“|”
                        String str = LAR[1].replace("|","");
                        int times = LAR[1].length()-str.length();//出现了times次“|”
                        String[] RS = LAR[1].split("|");
                        for(int i = 0 ; i < times + 1 ; i++)
                        {
                            p[count++] = new CSS(LAR[0],RS[i]);
                        }
                        char[] ctmp=LAR[0].toCharArray();
                        for(int i=0;i<ctmp.length;i++)
                        {
                            setVt.add(ctmp[i]+"");
                        }

                        char[] ctmp2=str.toCharArray();
                        for(int i=0;i<ctmp2.length;i++)
                        {
                            setVt.add(ctmp2[i]+"");
                        }


                    }
                }
            }
        }

        for(String str : vns)
        {
            setVt.remove(str);
        }


        scanner.close();

        System.out.print("文法" + G + "=" + "({" + vnss + "},{");
        for(String str : setVt)
        {
            if(!str.equals("ε"))
                System.out.print(str+" ");
        }
        System.out.println("},P,"+ vns[0] +")");

        System.out.println("P:");
        for(String str : inputStr)
        {
            System.out.println(str);
        }
        Third(p);

    }

    public static boolean Zero(CSS[] p) {
        int i, j;
        for (i = 0; i < count; i++) // 循环n次，即遍历所有产生式
        {
            for (j = 0; j < p[i].left.length(); j++) // 遍历产生式左部每一个字符
            {
                if ( 'A' <= p[i].left.charAt(j) && p[i].left.charAt(j) <= 'Z') // 判断字符是否是非终结符
                    break;
            }
            if (j == p[i].left.length()) {
                System.out.println("该文法不是0型文法");
                return false;
            }
        }
        if (i == count)
            return true;// 如果每个产生时都能找到非终结符
        return false;
    }

    public static boolean First(CSS[] p) {
        int i;
        if (Zero(p)) {
            for (i = 0; i < count; i++) {
                if ((p[i].left.length() > p[i].right.length())
                        && p[i].right.length() != 0) // 判断产生式左部长度是否大于右部
                    break;
            }
            if (i == count)
                return true;
            else {
                System.out.println("0型文法");
                return false;
            }
        } else
            return false;

    }

    public static boolean Second(CSS[] p) {
        int i;
        if (First(p)) // 同上，先判断低级文法是否成立
        {
            for (i = 0; i < count; i++) // 同上，遍历所有文法产生式
            {
                if ((p[i].left.length() != 1)
                        || !(p[i].left.charAt(0) >= 'A' && p[i].left.charAt(0) <= 'Z'))
                    break;
            }
            if (i == count)
                return true;
            else {
                System.out.println("1型文法");
                return false;
            }
        } else
            return false;

    }

    public static void Third(CSS[] p) // 判断3型文法
    {
        int i;
        if (Second(p)) // 同上，先判断是否是2型文法
        {
            for (i = 0; i < count; i++) // 同上，遍历文法所有的产生式
            {
                if ((p[i].right.length() == 0)
                        || (p[i].right.length() >= 3)
                        || (p[i].right.charAt(0) >= 'A' && p[i].right.charAt(0) <= 'Z')) // 判断产生式右部字符个数是否在12之间，判断右部第一个字符是否是非终结符
                    break;
            }
            if (i == count) {
                for (i = 0; i < count; i++) {
                    if (p[i].right.length() == 2) {
                        if (!(p[i].right.charAt(1) >= 'A' && p[i].right
                                .charAt(1) <= 'Z'))
                            break;
                    }
                }
                if (i == count) {
                    if(!setVt.contains("ε"))
                    System.out.println("该文法属于3型文法");
                    else
                        System.out.println("该文法属于扩充3型文法");
                } else
                if(!setVt.contains("ε"))
                    System.out.println("该文法属于2型文法");
                else
                    System.out.println("该文法属于扩充2型文法");

            } else
            if(!setVt.contains("ε"))
                System.out.println("该文法属于2型文法");
            else
                System.out.println("该文法属于扩充2型文法");
        } else
            System.out.println("END");
    }

}


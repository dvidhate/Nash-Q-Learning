/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nash;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Details 
{
    static int Agent=3;
    static ArrayList input[]=new ArrayList[Agent];
    static int Xmax=10; // Max. no of customer in queue
    static int Imax=20; // Max. no of Stock in shop
    static int StateGoal=0;
    static int State=10;
    static double flow[]={0.5,0.3,0.2};
    
    static double alpha=0.2; // learning rate
    static double gamma=0.99; //discount factor
    
    static int startPrice[]={8,5,10};
    static int endPrice[]={14,9,13};
    
    static ArrayList priceList[]=new ArrayList[Agent];
    
    static double Q[][];
    static double UAP[][];
    static double R[][];
    
    static String technique[]={"Simple","group", "dynamic", "goal-oriented", "expert_agent "};
    
    static ArrayList states[]=new ArrayList[Agent];
    static ArrayList action[]=new ArrayList[Agent];
    static ArrayList reward[]=new ArrayList[Agent];
    
    static double ei[]=new double[Agent];
    static double ri[]=new double[Agent];
    static double re[]=new double[Agent];
    
    static double ea=0;
    
    static String result[][]=new String[technique.length][Agent];
    static String text="";
}

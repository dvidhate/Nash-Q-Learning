/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nash;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Process extends Thread
{
    MainFrame mf;
    Details dt=new Details();
    int count=0;
    Process(MainFrame me)
    {
        mf=me;
    }
    
    public void run()
    {
        try
        {
            for(int i=0;i<dt.Agent;i++)
            {
                File fe1=new File("Agent"+(i+1)+".txt");
                FileInputStream fis1=new FileInputStream(fe1);
                byte bt1[]=new byte[fis1.available()];
                fis1.read(bt1);
                fis1.close();
                
                ArrayList ay=new ArrayList();
                String g[]=new String(bt1).split("\n");
                for(int j=1;j<g.length;j++)
                {
                    ay.add(g[j]);
                }
                
                dt.input[i]=ay;
            }
            
            while(true)
            {
                count++;
                ArrayList in1[]=new ArrayList[dt.Agent];
                for(int ii=0;ii<dt.Agent;ii++)
                {
                    ArrayList lt1=dt.input[ii];
                    ArrayList lt2=new ArrayList();
                    for(int in=0;in<dt.State;in++)
                    {
                        lt2.add(lt1.get(in));
                    }
                    in1[ii]=lt2;
                    
                    System.out.println("lt1 = "+lt1.size());
                }
                
                for(int i=0;i<dt.Agent;i++)
                {
                    // find state
                    ArrayList at1=new ArrayList(); //sta
                    ArrayList at2=new ArrayList(); //pr
                    
                    ArrayList aa=in1[i];
                    
                    ArrayList price1=dt.priceList[i];
                    for(int j=0;j<dt.State;j++)
                    {
                        String g1[]=aa.get(j).toString().split("\t");
                        String dt=g1[0].trim();
                        int c=Integer.parseInt(g1[1].trim()); //cutomer
                        int it=Integer.parseInt(g1[2].trim()); //item
                        System.out.println("State for "+(i+1)+" - "+dt+" = "+c+","+it+" : "+price1.get(it-1));
                        c=c-1;
                        it=it-1;
                        
                        at1.add(c+"#"+it);
                        at2.add(price1.get(it));
                    }
                    
                    System.out.println("at1= "+at1);
                    System.out.println("at2= "+at2);
                    // Reward computation
                    ArrayList at3=new ArrayList();
                    for(int j=0;j<at1.size()-1;j++)
                    {
                        String g1[]=at1.get(j).toString().split("#");
                        String g2[]=at1.get(j+1).toString().split("#");
                        
                        int x=Integer.parseInt(g1[0]);
                        int i1=Integer.parseInt(g1[1]);
                        
                        int xd=Integer.parseInt(g2[0]);
                        int id1=Integer.parseInt(g2[1]);
                        double r=0;
                        double p1=Double.parseDouble(at2.get(x).toString());
                        if(id1==(i1-3))
                            r=2*p1;
                        else if(xd>x)
                            r=p1;
                        else if(id1<i1)
                            r=p1;
                        else
                            p1=0;
                        at3.add(p1);
                    }
                    String g2[]=at1.get(at1.size()-1).toString().split("#");
                    int x=Integer.parseInt(g2[0]);
                    at3.add(at2.get(x));
                    
                    dt.states[i]=at1;
                    dt.action[i]=at2;
                    dt.reward[i]=at3;
                    
                    System.out.println("re == "+at3);        
            
                    dt.StateGoal=dt.State-1;          
                    
                    
                }
                
                dt.R=new double[dt.Agent][dt.State];
                for(int i=0;i<dt.Agent;i++)
                {
                    ArrayList at=dt.reward[i];
                    for(int j=0;j<at.size();j++)
                    {
                        dt.R[i][j]=Double.parseDouble(at.get(j).toString());
                    }
                }
                
                for(int i=0;i<dt.R.length;i++)
                {
                    for(int j=0;j<dt.R[0].length;j++)
                        System.out.print(dt.R[i][j]+" ");
                    System.out.println();
                    
                }
                System.out.println("Nash Learning === ");
                int m = dt.R.length;
                int n = dt.R[0].length;
                Nash1 nh=new Nash1();
                nh.find();
                System.out.println(nh.value());
                
                
              
                mf.jTextArea1.setText(dt.text);
                
                System.out.println("updated....... "+count);
                Thread.sleep(15000);
                
                
                
                // remove previous records
                for(int ii=0;ii<dt.Agent;ii++)
                {
                    ArrayList ay1=dt.input[ii];
                    ArrayList lt2=in1[ii];
                    for(int in=0;in<lt2.size();in++)
                    {
                        ay1.remove(lt2.get(in));
                    }
                    dt.input[ii]=ay1;
                    System.out.println("ay1 = "+ay1.size());
                 }
                
                // Check the record 
                ArrayList lt1=dt.input[0];
                if(lt1.size()<10)
                {
                    System.out.println("Stop");
                    break;
                }
                
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

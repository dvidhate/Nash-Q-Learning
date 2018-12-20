/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nash;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author admin
 */
public class PriceRange 
{
    Details dt=new Details();
    
    PriceRange()
    {
        
    }
    public void find()
    {
        try
        {
            DecimalFormat df=new DecimalFormat("#.##");
            for(int i=0;i<dt.Agent;i++)
            {
                int st1=dt.startPrice[i];
                int end=dt.endPrice[i];
                
                int mid=dt.Imax;//(st1+end)/2;
                ArrayList p1=new ArrayList();
                for(int j=0;j<mid;j++)
                {
                    double v1=st1+j;
                    if(v1<end)
                        p1.add(v1);
                    
                }             
		
                while(p1.size()!=mid)
                {
                    double k=Double.parseDouble(df.format(ThreadLocalRandom.current().nextDouble(st1, end )));
                    
                    if(!p1.contains(k))
                        p1.add(k);
                }
                Collections.sort(p1);
                dt.priceList[i]=p1;
                System.out.println(st1+"-"+end+" : "+p1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String ar[])
    {
        new PriceRange().find();
    }
}

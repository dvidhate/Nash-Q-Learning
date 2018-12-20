/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nash;

import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 *
 * @author admin
 */
public class Nash1 
{
    Details dt=new Details();
    Program pm;
    double EPSILON = 1E-8;
    double constant;
    double payoff[][];
            int m;
            int n;
    Nash1()
    {
         
    }
     
    public void find()
    {
        try
        {
            DecimalFormat df=new DecimalFormat("#.###");
            dt.Q=new double[dt.Agent][dt.State];
            dt.UAP=new double[dt.Agent][dt.State];
            
            // Initialize Q and GAP
            for(int i=0;i<dt.Agent;i++)
            {
                for(int j=0;j<dt.State;j++)
                {                
                    dt.Q[i][j]=0;
                    dt.UAP[i][j]=0;                   
                }
            }
            
            payoff=dt.R;
            m = payoff.length;
            n = payoff[0].length;
            
            double[] c = new double[n];
            double[] b = new double[m];
            double[][] A = new double[m][n];
            for (int i = 0; i < m; i++)
                b[i] = 1.0;
            for (int j = 0; j < n; j++)
                c[j] = 1.0;

        
            constant = Double.POSITIVE_INFINITY;
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    if (payoff[i][j] < constant)
                        constant = payoff[i][j];

        
            if (constant <= 0) 
                constant = -constant + 1;
            else
                constant = 0;
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    A[i][j] = payoff[i][j] + constant;
            
            pm=new Program(A,b,c);
            certifySolution(payoff);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public double value() 
    {
        return 1.0 / scale() - constant;
    }


    
    private double scale() 
    {
        double[] x = pm.primal();
        double sum = 0.0;
        for (int j = 0; j < n; j++)
            sum += x[j];
        return sum;
    }

   
    public double[] row() {
        double scale = scale();
        double[] x = pm.primal();
        for (int j = 0; j < n; j++)
            x[j] /= scale;
        return x;
    }

    
    public double[] column() 
    {
        double scale = scale();
        double[] y =pm.dual();
        for (int i = 0; i < m; i++)
            y[i] /= scale;
        return y;
    }


   
    private boolean isPrimalFeasible() 
    {
        double[] x = row();
        double sum = 0.0;
        for (int j = 0; j < n; j++) {
            if (x[j] < 0) {
               
                return false;
            }
            sum += x[j];
        }
        if (Math.abs(sum - 1.0) > EPSILON) {            
            return false;
        }
        return true;
    }

    
    private boolean isDualFeasible() {
        double[] y = column();
        double sum = 0.0;
        for (int i = 0; i < m; i++) {
            if (y[i] < 0) {                
                return false;
            }
            sum += y[i];
        }
        if (Math.abs(sum - 1.0) > EPSILON) {            
            return false;
        }
        return true;
    }

    
    private boolean isNashEquilibrium(double[][] payoff) {
        double[] x = row();
        double[] y = column();
        double value = value();

        
        double opt1 = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < m; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += payoff[i][j] * x[j];
            }
            if (sum > opt1) opt1 = sum;
        }
        if (Math.abs(opt1 - value) > EPSILON) {
           
            return false;
        }

        
        double opt2 = Double.POSITIVE_INFINITY;
        for (int j = 0; j < n; j++) {
            double sum = 0.0;
            for (int i = 0; i < m; i++) {
                sum += payoff[i][j] * y[i];
            }
            if (sum < opt2) opt2 = sum;
        }
        if (Math.abs(opt2 - value) > EPSILON) {
            
            return false;
        }


        return true;
    }

    private boolean certifySolution(double[][] payoff) {
        return isPrimalFeasible() && isDualFeasible() && isNashEquilibrium(payoff);
    }
}


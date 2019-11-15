/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmc;

import java.util.ArrayList;

/**
 *
 * @author Pierre
 */
public class RegularLinear {
    
     ArrayList<Double> jourList;
     ArrayList<Double> valList;

    public RegularLinear(ArrayList<Double> j,ArrayList<Double> v) 
    {
     
        jourList=j;
        valList=v;
        
    }
    
    public double traitement()
    {
        double  sumx=0.0;
        double sumy=0.0;
        double sumx2=0.0;
        int i=0;
        
        for ( i= 0; i < jourList.size(); i++) 
        {
            sumx += jourList.get(i);
            sumx2 += jourList.get(i)*jourList.get(i);
            sumy +=valList.get(i);

        }
        
        double xbar = sumx / i;
        double ybar = sumy / i;
        
         double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (i = 0; i < jourList.size(); i++) 
        {
            xxbar =xxbar+ (jourList.get(i) - xbar) * (jourList.get(i) - xbar);
            yybar =yybar+ (valList.get(i) - ybar) * (valList.get(i) - ybar);
            xybar =xybar+ (jourList.get(i) - xbar) * (jourList.get(i) - ybar);
        }
        System.out.println(xxbar);
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;
        
        System.out.println("y   = " + beta1 + " * x + " + beta0);
        
        int df = jourList.size() - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (i = 0; i < jourList.size(); i++) 
        {
            double fit = beta1*jourList.get(i) + beta0;
            rss += (fit - valList.get(i)) * (fit - valList.get(i));
            ssr += (fit - ybar) * (fit - ybar);
        }
        double R2    = ssr / yybar;

        
        
        double R=Math.abs(Math.sqrt(R2));
        
        return R;
    }
    
    
    
    
}

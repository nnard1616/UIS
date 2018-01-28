/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox;

/**
 *
 * @author nathan
 */
public class Sandbox {
    public static long[] knownFib = new long[500];
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long ans = dynFib(100);
        long end = System.currentTimeMillis();
        System.out.println(ans + " : " + (end-start)/1000.0);
        start = System.currentTimeMillis();
        ans = knownFib[100];
        end = System.currentTimeMillis();
        System.out.println(ans + " : " + (end-start)/1000.0);
    }
    
    public static long rf(int n){
        return rf(n--);
        
        if(n < 0)
            return 0;
    }
    
    
    
}

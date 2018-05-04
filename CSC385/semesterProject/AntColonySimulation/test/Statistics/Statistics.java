/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import java.util.Arrays;

/**
 *
 * @author nathan
 */


public class Statistics {
    Integer[] data;
    Integer size;   

    public Statistics(Integer[] data) {
        this.data = data;
        size = data.length;
    }   

    public Double getMean() {
        Double sum = 0.0;
        for(Integer a : data)
            sum += a;
        return sum/size;
    }
    
    public Double getVariance() {
        Double mean = getMean();
        Double temp = 0.0;
        for(Integer a :data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }

    public Double getStdDev() {
        return Math.sqrt(getVariance());
    }

//    public Double median() {
//       Arrays.sort(data);
//
//       if (data.length % 2 == 0) {
//          return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
//       } 
//       return data[data.length / 2];
//    }
}



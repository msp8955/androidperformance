package com.ping.utils;

import java.util.Scanner;

import com.ping.models.Measure;
import com.ping.models.Throughput;

public class ParseUtil {
	
	public static Measure PingParser(String s){
		try{
			
		
		String lastLine=null;
		Scanner scanLines=new Scanner(s);
		Scanner scanLastLine;
		Scanner scanValues;
		double max;
		double min;
		double avr;
		double stddev;
		
		while (scanLines.hasNextLine())
			lastLine=scanLines.nextLine();
		
		scanLastLine=new Scanner(lastLine);
		scanLastLine.findInLine("=");
		
		if (scanLastLine.hasNext()) {
			scanValues=new Scanner(scanLastLine.next());
			scanValues.useDelimiter("/");
			try {
				min=scanValues.nextDouble();
				avr=scanValues.nextDouble();
				max=scanValues.nextDouble();
				stddev=scanValues.nextDouble();
				return (new Measure(max,min,avr,stddev));
			}catch(Exception e){return null;}
		}
		else
			return (new Measure(-1,-1,-1,-1));
		}
		catch (Exception e){
			return (new Measure(-1,-1,-1,-1));
		}
	}

	public static com.ping.models.Throughput ThroughputParser(String output) {
		// TODO Auto-generated method stub
		return null;
	}

}

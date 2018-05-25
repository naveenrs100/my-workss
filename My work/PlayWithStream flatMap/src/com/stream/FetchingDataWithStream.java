package com.stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FetchingDataWithStream {

	public static void main(String[] args) throws Exception {
		
	 Set<String> shakespeareWords = Files.lines(Paths.get("file6.txt"))
			 .map(string -> string.toLowerCase())
			 .collect(Collectors.toSet());
	 
		
	 Set<String> scrubbles = Files.lines(Paths.get("file5.txt"))
			 .map(string -> string.toLowerCase())
			 .collect(Collectors.toSet());
	 
	// System.out.println("no of words are : "+shakespeareWords.size());
	 
	 int[] scrabbleScore = {
		//	 a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z
			 1,2,1,3,4,5,4,3,6,2,1,3,4,2,1,2,5,8,7,9,2,1,5,4,3,10
	 };
	
	  Function<String,Integer> method = 
			 word -> word.chars().map(letter -> scrabbleScore[letter - 'a']).sum();
			 
			 ToIntFunction<String> number =
					 word -> word.chars().map(letter -> scrabbleScore[letter - 'a']).sum();
					 
					 System.out.println("Score of hello is : "+number.applyAsInt("hello"));
					 
					 
					 String bestWord = shakespeareWords.stream().max(Comparator.comparing(method)).get();
					 
					 IntSummaryStatistics summaryStatistics = shakespeareWords.stream().filter(word -> scrubbles.contains(word))
							 .mapToInt(number).summaryStatistics();
					 
					 IntStream letsee = shakespeareWords.stream().filter(word -> scrubbles.contains(word))
							 .mapToInt(number);
					 
					 System.out.println("Best word is : "+bestWord);
					 
					 System.out.println("And its length is : "+number.applyAsInt(bestWord));
					 
					 System.out.println(summaryStatistics);
					 
					 System.out.println(summaryStatistics.getSum());
					 
					 System.out.println(letsee.max());
					 
				//	 System.out.println(letsee.sum());
					 
					
					 
					 
	}

}

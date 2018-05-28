package com.stream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws IOException {
	
	
		try(Stream<String> stream1 = Files.lines(Paths.get("file1.txt"));
		Stream<String> stream2 = Files.lines(Paths.get("file2.txt"));
		Stream<String> stream3 = Files.lines(Paths.get("file3.txt"));
		Stream<String> stream4 = Files.lines(Paths.get("file4.txt"));
		//Stream<String> stream5 = Files.lines(Paths.get("file5.txt"));
				)
		{
		
		Stream<Stream<String>> streamOfStreams = Stream.of(stream1,stream2,stream3,stream4);
		
		Stream<String> lines = streamOfStreams.flatMap(stream -> stream);
	
	
	//	Same way as (stream -> stream)
	//  Stream<String> lines = streamOfStreams.flatMap(Function.identity());
		
		Function<String, Stream<String>> lineSplitter = 
				line -> Pattern.compile(" ").splitAsStream(line);
				
				Stream<String> linesOfFile = lines.flatMap(lineSplitter);
				
				
				linesOfFile.forEach(g ->
				{System.out.println(g);
				System.out.println();
				});
				
		}catch(IOException ioe )
		{
			System.out.println(ioe.getClass().getSimpleName());
			
			ioe.printStackTrace();
		}
				
				
			//	linesOfFile.forEach(g -> System.out.println(g));
				
			//	System.out.println(linesOfFile.count());
				
				
		
		
//		String[] records = {"Ashok","22","Bangalore","Gouri Sankar","22","Barrackpore","Ramu","22","Tamil Nadu"};
//
// 
//		try(BufferedWriter br = Files.newBufferedWriter(Paths.get("file6.txt"), StandardOpenOption.CREATE,StandardOpenOption.APPEND);)
//		{
//			for(String record:records)
//			{
//				br.write(record);
//				br.newLine();
//			}
//			
//			System.out.println("Execution successful");
//		}catch(IOException e)
//		{
//			System.out.println(e.getClass().getSimpleName());
//			e.printStackTrace();
//		}
//
	}

}

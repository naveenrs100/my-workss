import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreams {

	public static void main(String[] args) {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
		
		Stream.iterate("+",s -> s +"+")
		.parallel()
		.limit(10)
		.peek(s ->System.out.println(s+"Thread is processing in "+Thread.currentThread().getName()))
		.forEach(System.out::println);
		
		
		// Extreme time consuming  pattern to Collect a List
		
		List<String> unsafe = new CopyOnWriteArrayList<>();
				
				Stream.iterate("+",s -> s +"+")
				.parallel()
				.limit(10000)
				//.peek(s ->System.out.println(s+"Thread is processing in "+Thread.currentThread().getName()))
				.forEach(m -> unsafe.add(m));
		System.out.println("Size of list is "+ unsafe.size());
		
		
		// Safe way or best way to collect List
		
		List<String> safe = Stream.iterate("+",s -> s +"+")
				.parallel()
				.limit(10000)
				//.peek(s ->System.out.println(s+"Thread is processing in "+Thread.currentThread().getName()))
				//.forEach(m -> unsafe.add(m));
				.collect(Collectors.toList());
				
				
		System.out.println("Size of list is "+ safe.size());
		
		
		
	}

}

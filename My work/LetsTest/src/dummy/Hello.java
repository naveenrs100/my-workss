package dummy;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.sun.javafx.binding.StringFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.Person;
import com.ibm.PersonSpliterator;
//import com.ibm.SiteConn;



public class Hello {

	public static void main(String[] args) throws Exception {






//
//					String[] lala = s.split(":");
//					  List<String> hey = Arrays.asList(lala);
//
//					List<String> result =  hey.stream().map(i ->
//					  {
//						  String[] k = i.split(",");
//						  return k[0];
//					  }).collect(Collectors.toList());
//
//					  result.forEach(f -> System.out.println(f.toString()));
//					  System.out.println(sb.toString());
//					} catch (Exception mfe) {
//					  System.err.println(mfe.getMessage());
//					}

			//    RestTemplate rest = new RestTemplate();

//				String[] records = {"Ashok","22","Bangalore","Gouri Sankar","22","Barrackpore","Ramu","22","Tamil Nadu"};
//
//				List<String> hey = Arrays.asList(records);
//
//				hey.stream().map(i -> {
//					System.out.println(i);
//					return i;
//				}).collect(Collectors.toList());
//
//				//System.out.println("Hello GS How Are you");
//
//				try(BufferedWriter br = Files.newBufferedWriter(Paths.get("file.txt"), StandardOpenOption.CREATE);)
//				{
//					for(String record:records)
//					{
//						br.write(record);
//						br.newLine();
//					}
//
//					System.out.println("Execution successful");
//				}catch(IOException e)
//				{
//					System.out.println(e.getClass().getSimpleName());
//					e.printStackTrace();
//				}


		Path path = Paths.get("file.txt");

		Stream<String> lines = Files.lines(path);

		Spliterator<String> lineSpliterator = lines.spliterator();


		Spliterator<Person> personSpliterator = new PersonSpliterator(lineSpliterator);


		Stream<Person> persons = StreamSupport.stream(personSpliterator, false);

		persons.forEach(System.out::println);


		// *********Converting a stream to LIST**************
		List<Person> personss = StreamSupport.stream(personSpliterator, false).collect(Collectors.toList());



		String ashok = "Hello %-20.2f  This space is required if u have a name of uptil 20";
		String gouri = "Hello %-20s  This space is required if u have a name of uptil 20";


		String url = "http://localhost:8098/?username=%s&password=%s ";
		System.out.format(url, "Ashok","123445678788jdjshakj");

		System.out.println();


		StringJoiner s = new StringJoiner("} , {","[ {","} ]");

		s.add("Ashok");

		s.add("gouri");

		s.add("Ramu");

		System.out.println(s);


		ashok = ashok.format(ashok,1000.827386276382);


		gouri = gouri.format(gouri,"Gouri Sankar");

		System.out.println(ashok);

		System.out.println(gouri);

		System.out.println("---------------------------------------------------------------");

		personss.forEach(i ->
		{
			System.out.println(i);
			System.out.println("---------------------------------------------------------------");
		});	



	}

}

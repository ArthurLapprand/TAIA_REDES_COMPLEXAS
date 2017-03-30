import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		List<String> nodeLines = new ArrayList<String>();
		nodeLines.add("ID");
		List<String> edgesLines = new ArrayList<String>();
		edgesLines.add("Source;Target;Type");
		
		Random r = new Random();
		
		ArrayList<Integer> nodes = new ArrayList<Integer>();
		
		double p = 0.04;
		
		for (int id = 0; id < 200; id++) {
			nodes.add(id);
			nodeLines.add("" + (id + 1));
			if (id > 0) {
				for (int i = 0; i < nodes.size() - 1; i++) {
					if (r.nextDouble() <= p) { edgesLines.add(id + ";" + i + ";Undirected"); }					
				}
			}
		}
		
		Path file = Paths.get("nodes.csv");
		Files.write(file, nodeLines, Charset.forName("UTF-8"));
		file = Paths.get("edges.csv");
		Files.write(file, edgesLines, Charset.forName("UTF-8"));
		
	}

}

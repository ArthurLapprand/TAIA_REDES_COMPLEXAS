package modelos;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScaleFreeNetworkType2 {
	
	public static class Node {
		private int grau;
		
		public Node (int id, int grau) {
			this.grau = grau;
		}
		
		public void incGrau() { this.grau++; }
		public int getGrau() { return this.grau; }
	}

	public static void main(String[] args) throws IOException {
		List<String> nodeLines = new ArrayList<String>();
		nodeLines.add("ID");
		List<String> edgesLines = new ArrayList<String>();
		edgesLines.add("Source;Target;Type");
		
		Random r = new Random();
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		double totalGrau = 0;
		for (int id = 0; id < 200; id++) {
			nodes.add(new Node(id, 0));
			nodeLines.add("" + (id + 1));
			if (id == 1) {
				edgesLines.add(id + ";" + 0 + ";Undirected");
				nodes.get(0).incGrau();
				nodes.get(id).incGrau();
				totalGrau += 2;
			} else if (id > 1) {
				for (int i = 0; i < nodes.size()-1; i++) {
					if (nodes.get(i).getGrau() == 0) {
						if (r.nextDouble() <= 0.1) {
							edgesLines.add(id + ";" + i + ";Undirected");
							nodes.get(i).incGrau();
							nodes.get(id).incGrau();
							totalGrau += 2;
						}
					} else if (r.nextDouble() <= (nodes.get(i).getGrau() / totalGrau)) {
						edgesLines.add(id + ";" + i + ";Undirected");
						nodes.get(i).incGrau();
						nodes.get(id).incGrau();
						totalGrau += 2;
					}			
				}
			}
		}
		
		Path file = Paths.get("scale_free_nodes_t2.csv");
		Files.write(file, nodeLines, Charset.forName("UTF-8"));
		file = Paths.get("scale_free_edges_t2.csv");
		Files.write(file, edgesLines, Charset.forName("UTF-8"));
	}

}

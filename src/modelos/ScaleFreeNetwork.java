package modelos;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScaleFreeNetwork {
	
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
		
		double p = 0.5;
		int n1, n2;
		for (int id = 0; id < 200; id++) {
			nodes.add(new Node(id, 0));
			nodeLines.add("" + (id + 1));
			if (id == 1) {
				if (r.nextDouble() <= p) { 
					edgesLines.add(id + ";" + 0 + ";Undirected");
					nodes.get(0).incGrau();
					nodes.get(id).incGrau();
				}
			} else if (id > 1) {
				n1 = r.nextInt(nodes.size()-1);
				if (nodes.get(n1).getGrau() == 0) {
					if (r.nextDouble() <= p) { 
						edgesLines.add(id + ";" + n1 + ";Undirected");
						nodes.get(n1).incGrau();
						nodes.get(id).incGrau();
					}
				} else {
					if (Math.pow(r.nextDouble(), (nodes.get(n1).getGrau())) <= p) {
						edgesLines.add(id + ";" + n1 + ";Undirected");
						nodes.get(n1).incGrau();
						nodes.get(id).incGrau();
					}
				}
				
				n2 = n1;
				while (n2 == n1) { n2 = r.nextInt(nodes.size()-1); }
				
				if (nodes.get(n2).getGrau() == 0) {
					if (r.nextDouble() <= p) { 
						edgesLines.add(id + ";" + n2 + ";Undirected");
						nodes.get(n2).incGrau();
						nodes.get(id).incGrau();
					}
				} else {
					if (Math.pow(r.nextDouble(), (nodes.get(n2).getGrau())) <= p) {
						edgesLines.add(id + ";" + n2 + ";Undirected");
						nodes.get(n2).incGrau();
						nodes.get(id).incGrau();
					}
				}
				
			}
		}
		
		Path file = Paths.get("scale_free_nodes.csv");
		Files.write(file, nodeLines, Charset.forName("UTF-8"));
		file = Paths.get("scale_free_edges.csv");
		Files.write(file, edgesLines, Charset.forName("UTF-8"));

	}

}

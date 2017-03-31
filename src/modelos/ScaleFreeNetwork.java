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
		
		double totalGrau = 0;
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		// insere nós iniciais, neste caso, 2 nós
		nodes.add(new Node(0, 0));
		nodes.add(new Node(1, 0));
		nodeLines.add("0");
		nodeLines.add("1");
		edgesLines.add(1 + ";" + 0 + ";Undirected");
		nodes.get(0).incGrau();
		nodes.get(1).incGrau();
		totalGrau += 2;
		
		int n = 2; // quantidade de nós inicial
		
		for (int id = n; id < 200; id++) {
			nodes.add(new Node(id, 0));
			System.out.println(id);
			nodeLines.add("" + id);
			int c = 0;
			// aqui a ordem de tentativa de conexão dos nós é do mais recente ao mais antigo
			int id2 = nodes.size()-2;
			while (c < n) {
				if (id2 < 0) id2 = nodes.size()-2;
				if ((r.nextDouble() <= (nodes.get(id2).getGrau() / totalGrau))
						&& !edgesLines.contains(id + ";" + id2 + ";Undirected")) {
					c++;
					edgesLines.add(id + ";" + id2 + ";Undirected");
					nodes.get(id).incGrau();
					nodes.get(id2).incGrau();
					totalGrau += 2;
				}
				id2--;
			}
		}
		
		Path file = Paths.get("scale_free_nodes.csv");
		Files.write(file, nodeLines, Charset.forName("UTF-8"));
		file = Paths.get("scale_free_edges.csv");
		Files.write(file, edgesLines, Charset.forName("UTF-8"));

	}

}

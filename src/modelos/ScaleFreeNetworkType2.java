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
		
		public Node (int grau) {
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
		
		int totalGrau = 0;
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Integer> escolhidos = new ArrayList<Integer>();
		
		// insere nós iniciais, neste caso, 2 nós
		nodes.add(new Node(0));
		nodes.add(new Node(0));
		nodeLines.add("0");
		nodeLines.add("1");
		edgesLines.add(1 + ";" + 0 + ";Undirected");
		nodes.get(0).incGrau();
		nodes.get(1).incGrau();
		totalGrau += 2;
		
		int n = 2; // quantidade de nós inicial
		
		// método da roleta visto em aula
		for (int id = n; id < 200; id++) {
			nodes.add(new Node(0));
			nodeLines.add("" + id);
			escolhidos.clear();
			int c = 0;
			while (c < n) {
				int nrand = r.nextInt(totalGrau + 1); 
				int sum = 0;
				for (int i = 0; i < (nodes.size()-1); i++) {
					sum += nodes.get(i).getGrau();
					//System.out.println(i-1 + " / " + sum + " / " + nrand + " / " + (nodes.size()-1));
					
					// caso caia no primeiro
					if (nrand >= 0 && nrand < nodes.get(i).getGrau()) {
						if (escolhidos.contains(0)) break;
						edgesLines.add(id + ";" + 0 + ";Undirected");
						escolhidos.add(0);
						nodes.get(id).incGrau();
						nodes.get(0).incGrau();
						totalGrau += 2;
						c++;
						break;
					} else if (nrand == sum) {
						if (escolhidos.contains(i)) break;
						edgesLines.add(id + ";" + i + ";Undirected");
						escolhidos.add(i);
						nodes.get(id).incGrau();
						nodes.get(i).incGrau();
						totalGrau += 2;
						c++;
						break;
					} else if (sum > nrand) {
						if (escolhidos.contains(i-1)) break;
						edgesLines.add(id + ";" + (i-1) + ";Undirected");
						escolhidos.add(i-1);
						nodes.get(id).incGrau();
						nodes.get(i-1).incGrau();
						totalGrau += 2;
						c++;
						break;
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

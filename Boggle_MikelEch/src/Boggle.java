import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Boggle {

	private int n, m; // Errenkada (n) eta zutabe (m) kopurua
	private char[][] taula;
	
	// Eraikitzaile normala, maiztasunak.txt erabili gabe
//	public Boggle(int n, int m) {
//		this.n = n;
//		this.m = m;
//		this.taula = new char[n][m];
//		char[] letrak = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < m; j++) {
//				int indize = new Random().nextInt(letrak.length);
//				this.taula[i][j] = letrak[indize];
//			}
//		}
//	}
	
	public int getErrenkada() {
		return this.n;
	}
	
	public int getZutabe() {
		return this.m;
	}
	
	public char getKarakterea(int i, int j) {
		return this.taula[i][j];
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				s = s + "+---";
			}
			s = s + "+\n";
			for (int j = 0; j < this.m; j++) {
				s = s + "| " + this.taula[i][j] + " ";
			}
			s = s + "|\n";
		}
		for (int j = 0; j < this.m; j++) {
			s = s + "+---";
		}
		s = s + "+\n";
		return s;
	}
	
	// Hautazkoa, maiztasunak.txt erabiliz
	public Boggle(int n, int m) {
		this.n = n;
		this.m = m;
		this.taula = new char[n][m];
		try {
			// Bektoreak prestatu:
			File fitx = new File("files/maiztasunak.txt");
			Scanner sc = new Scanner(fitx);
			char[] letrak = new char[27];
			double[] prob = new double[28];
			prob[0] = 0;
			for (int i = 1; i < 28; i++) {
				char letra = sc.next().charAt(0);
				double zenb = sc.nextDouble();
				letrak[i-1] = letra;
				prob[i] = zenb + prob[i-1];
				if (sc.hasNextLine()) {
					sc.nextLine();
				}
			}
			sc.close();
			// Taula osatu:
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					this.taula[i][j] = Zorizkoa(letrak, prob);
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Ez da maiztasunak.txt fitxategia aurkitu");
		}
	}
	
	public char Zorizkoa(char[] letrak, double[] prob) {
		Random r = new Random();
		double p = r.nextDouble();
		for (int i = 0; i < letrak.length; i++) {
			if (p < prob[i+1]) {
				return letrak[i];
			}
		}
		return 'a'; // Zorizko zenbakia 0.999996 eta 1 artean baldin badago, hau itzuliko da
	}
	
	// Probatzeko
	public static void main(String[] args) {
		Boggle t = new Boggle(4, 4);
		System.out.println(t);
	}
	
}

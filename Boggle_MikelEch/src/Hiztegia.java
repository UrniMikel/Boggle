import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Hiztegia {

	public static class Nodo {
		
		private int balioa;
		private Nodo[] umeak;
		
		public Nodo() {
			this.balioa = 0;
			this.umeak = new Nodo[27];
		}
		
		public int getBalioa() {
			return this.balioa;
		}
		
		public void setBalioa(int balioa) {
			this.balioa = balioa;
		}
		
		public Nodo getUmea(int i) {
			return this.umeak[i];
		}
		
		public void setUmea(int i) {
			this.umeak[i] = new Nodo();
		}
		
	}
	
	private Nodo erroa;
	
	public Hiztegia() {
		this.erroa = new Nodo();
	}
	
	public Hiztegia(Scanner sc) {
		this.erroa = new Nodo();
		while (sc.hasNext()) {
			String hitza = sc.next();
			int balioa = sc.nextInt();
			if (balioa >= 9) { // 9 eta 10 balioak dituzten hitzak ez kargatzeko
				break;
			}
			this.addWord(hitza, balioa);
		}
	}
	
	public Nodo getErroa() {
		return this.erroa;
	}
	
	// Hitz bat emanda, lehenengo karakterea hartu eta nodo baten zein posiziori dagokion itzultzen du
	public int lortuIndizea(String hitza) {
		if (hitza.length() == 0) {
			return -1; // Hitz hutsa
		}
		int i = hitza.charAt(0) - 97;
		if (i >= 0 && i <= 13) {
			return i; // a-n
		}
		else if (i == 144) {
			return 14; // ñ
		}
		else if (i >= 14 && i <= 25) {
			return i++; // o-z
		}
		else {
			return -1; // Beste edozein karaktere
		}
	}
	
	public void addWord(String hitza, int balioa) {
		int pos = lortuIndizea(hitza);
		if (this.erroa.getUmea(pos) == null) {
			this.erroa.setUmea(pos); // Karaktere kate berri bat gorde behar denean hiztegian
		}
		addWord(hitza.substring(1), balioa, this.erroa.getUmea(pos));
	}
	
	private void addWord(String hitza, int balioa, Nodo nodoa) {
		if (hitza.length() == 0) {
			nodoa.setBalioa(balioa);
		}
		else {
			int pos = lortuIndizea(hitza);
			if (nodoa.getUmea(pos) == null) {
				nodoa.setUmea(pos); // Karaktere kate berri bat gorde behar denean hiztegian
			}
			if (hitza.length() >= 1) {
				addWord(hitza.substring(1), balioa, nodoa.getUmea(pos));
			}
		}
	}
	
	public int checkWord(String hitza) {
		int pos = lortuIndizea(hitza);
		if (pos == -1 || this.erroa.getUmea(pos) == null) {
			return 0;
		}
		else {
			return checkWord(hitza.substring(1), this.erroa.getUmea(pos));
		}
	}
	
	private int checkWord(String hitza, Nodo nodoa) {
		int pos = lortuIndizea(hitza);
		if (pos == -1 || nodoa.getUmea(pos) == null) {
			return 0;
		}
		if (hitza.length() == 1) {
			int balioa = nodoa.getUmea(pos).getBalioa();
			return balioa;
		}
		return checkWord(hitza.substring(1), nodoa.getUmea(pos));
	}
	
	// Probatzeko
	public static void main(String args[]) {
		System.out.println("Hiztegia irekitzen...\n");
		try {
			File fitx = new File("files/hiztegia.txt");
			Scanner sc = new Scanner(fitx);
			long hasiera = System.nanoTime();
			Hiztegia hiztegia = new Hiztegia(sc);
			long bukaera = System.nanoTime() - hasiera;
			System.out.println("Hiztegia irekitzeko denbora: " + bukaera/1000000000.0 + " s\n");
			sc.close();
			Scanner sarrera = new Scanner(System.in);
			System.out.println("Idatzi hitz bat");
			String hitza = sarrera.next();
			sarrera.close();
			hasiera = System.nanoTime();
			int puntuak = hiztegia.checkWord(hitza);
			bukaera = System.nanoTime() - hasiera;
			System.out.println("Hitza hiztegian bilatzeko denbora: " + bukaera/1000.0 + " µs\n");
			System.out.println("Puntuazioa: " + puntuak + "\n");
		}
		catch (FileNotFoundException e) {
			System.out.println("Ez da hiztegia.txt fitxategia aurkitu");
		}
	}
	
}

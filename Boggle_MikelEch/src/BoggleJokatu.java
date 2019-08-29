import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoggleJokatu {

	Boggle taula;
	Hiztegia hiztegia;
	
	public BoggleJokatu(Boggle boggle, Hiztegia hitzak) {
		this.taula = boggle;
		this.hiztegia = hitzak;
	}
	
	public boolean taulanDago(String hitza) {
		char kar = hitza.charAt(0);
		for(int i = 0; i < this.taula.getErrenkada(); i++) {
			for (int j = 0; j < this.taula.getZutabe(); j++) {
				if (kar == this.taula.getKarakterea(i, j) && this.hemendikJarraitzenDu(hitza, i, j)) {
					System.out.println("Hitz hori taulan dago");
					return true;
				}
			}
		}
		System.out.println("Hitz hori ez dago taulan");
		return false;
	}
	
	private boolean hemendikJarraitzenDu(String hitza, int i, int j) {
		if (hitza.charAt(0) != this.taula.getKarakterea(i, j)) { // 1. kasu nabaria
			return false;
		}
		else if (hitza.length() == 1 && hitza.charAt(0) == this.taula.getKarakterea(i, j)) { // 2. kasu nabaria
			return true;
		}
		else {
			if (i == 0) {
				if (j == 0) {
					return this.hemendikJarraitzenDu(hitza.substring(1), i, j+1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j+1);
				}
				else if (j == this.taula.getZutabe()-1) {
					return this.hemendikJarraitzenDu(hitza.substring(1), i, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j);
				}
				else {
					return this.hemendikJarraitzenDu(hitza.substring(1), i, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i, j+1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j+1);
				}
			}
			else if (i == this.taula.getErrenkada()-1) {
				if (j == 0) {
					return this.hemendikJarraitzenDu(hitza.substring(1), i-1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i-1, j+1) || this.hemendikJarraitzenDu(hitza.substring(1), i, j+1);
				}
				else if (j == this.taula.getZutabe()-1) {
					return this.hemendikJarraitzenDu(hitza.substring(1), i-1, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i-1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i, j-1);
				}
				else {
					return this.hemendikJarraitzenDu(hitza.substring(1), i-1, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i-1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i-1, j+1) || this.hemendikJarraitzenDu(hitza.substring(1), i, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i, j+1);
				}
			}
			else {
				if (j == 0) {
					return this.hemendikJarraitzenDu(hitza.substring(1), i-1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i-1, j+1) || this.hemendikJarraitzenDu(hitza.substring(1), i, j+1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j+1);
				}
				else if (j == this.taula.getZutabe()-1) {
					return this.hemendikJarraitzenDu(hitza.substring(1), i-1, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i-1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j);
				}
				else {
					return this.hemendikJarraitzenDu(hitza.substring(1), i-1, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i-1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i-1, j+1) || this.hemendikJarraitzenDu(hitza.substring(1), i, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i, j+1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j-1) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j) || this.hemendikJarraitzenDu(hitza.substring(1), i+1, j+1);
				}
			}
		}
	}
	
	public String lortuHobea(int x) {
		String hitzHobea = "";
		int balioHobea = 0;
		Hiztegia.Nodo nodoa = this.hiztegia.getErroa();
		for (int i = 0; i < this.taula.getErrenkada(); i++) {
			for (int j = 0; j < this.taula.getZutabe(); j++) {
				hitzHobea = ingurutikHobea(nodoa, "", i, j, 0, x);
				balioHobea = this.hiztegia.checkWord(hitzHobea);
				if (balioHobea > x) {
					return hitzHobea;
				}
			}
		}
		return "";
	}
	
	public String ingurutikHobea(Hiztegia.Nodo nodoa, String hitza, int i, int j, int pos, int x) {
		hitza = hitza + this.taula.getKarakterea(i, j);
		int indize = this.hiztegia.lortuIndizea(hitza.substring(pos));
		if (nodoa.getBalioa() == 0 && nodoa.getUmea(indize) == null) { // 1. kasu nabaria
			return "";
		}
		else if ((nodoa.getBalioa() > 0 && nodoa.getUmea(indize) == null) || nodoa.getBalioa() > x) { // 2. kasu nabaria
			return hitza;
		}
		else {
			nodoa = nodoa.getUmea(indize);
			pos++;
			String hitz11 = "", hitz12 = "", hitz13 = "", hitz21 = "", hitz23 = "", hitz31 = "", hitz32 = "", hitz33 = "";
			int b11, b12, b13, b21, b23, b31, b32, b33;
			if (i == 0) {
				if (j == 0) {
					hitz23 = ingurutikHobea(nodoa, hitza, i, j+1, pos, x);
					hitz32 = ingurutikHobea(nodoa, hitza, i+1, j, pos, x);
					hitz33 = ingurutikHobea(nodoa, hitza, i+1, j+1, pos, x);
				}
				else if (j == this.taula.getZutabe()-1) {
					hitz21 = ingurutikHobea(nodoa, hitza, i, j-1, pos, x);
					hitz31 = ingurutikHobea(nodoa, hitza, i+1, j-1, pos, x);
					hitz32 = ingurutikHobea(nodoa, hitza, i+1, j, pos, x);
				}
				else {
					hitz21 = ingurutikHobea(nodoa, hitza, i, j-1, pos, x);
					hitz23 = ingurutikHobea(nodoa, hitza, i, j+1, pos, x);
					hitz31 = ingurutikHobea(nodoa, hitza, i+1, j-1, pos, x);
					hitz32 = ingurutikHobea(nodoa, hitza, i+1, j, pos, x);
					hitz33 = ingurutikHobea(nodoa, hitza, i+1, j+1, pos, x);
				}
			}
			else if (i == this.taula.getErrenkada()-1) {
				if (j == 0) {
					hitz12 = ingurutikHobea(nodoa, hitza, i-1, j, pos, x);
					hitz13 = ingurutikHobea(nodoa, hitza, i-1, j+1, pos, x);
					hitz23 = ingurutikHobea(nodoa, hitza, i, j+1, pos, x);
				}
				else if (j == this.taula.getZutabe()-1) {
					hitz11 = ingurutikHobea(nodoa, hitza, i-1, j-1, pos, x);
					hitz12 = ingurutikHobea(nodoa, hitza, i-1, j, pos, x);
					hitz21 = ingurutikHobea(nodoa, hitza, i, j-1, pos, x);
				}
				else {
					hitz11 = ingurutikHobea(nodoa, hitza, i-1, j-1, pos, x);
					hitz12 = ingurutikHobea(nodoa, hitza, i-1, j, pos, x);
					hitz13 = ingurutikHobea(nodoa, hitza, i-1, j+1, pos, x);
					hitz21 = ingurutikHobea(nodoa, hitza, i, j-1, pos, x);
					hitz23 = ingurutikHobea(nodoa, hitza, i, j+1, pos, x);
				}
			}
			else {
				if (j == 0) {
					hitz12 = ingurutikHobea(nodoa, hitza, i-1, j, pos, x);
					hitz13 = ingurutikHobea(nodoa, hitza, i-1, j+1, pos, x);
					hitz23 = ingurutikHobea(nodoa, hitza, i, j+1, pos, x);
					hitz32 = ingurutikHobea(nodoa, hitza, i+1, j, pos, x);
					hitz33 = ingurutikHobea(nodoa, hitza, i+1, j+1, pos, x);
				}
				else if (j == this.taula.getZutabe()-1) {
					hitz11 = ingurutikHobea(nodoa, hitza, i-1, j-1, pos, x);
					hitz12 = ingurutikHobea(nodoa, hitza, i-1, j, pos, x);
					hitz21 = ingurutikHobea(nodoa, hitza, i, j-1, pos, x);
					hitz31 = ingurutikHobea(nodoa, hitza, i+1, j-1, pos, x);
					hitz32 = ingurutikHobea(nodoa, hitza, i+1, j, pos, x);
				}
				else {
					hitz11 = ingurutikHobea(nodoa, hitza, i-1, j-1, pos, x);
					hitz12 = ingurutikHobea(nodoa, hitza, i-1, j, pos, x);
					hitz13 = ingurutikHobea(nodoa, hitza, i-1, j+1, pos, x);
					hitz21 = ingurutikHobea(nodoa, hitza, i, j-1, pos, x);
					hitz23 = ingurutikHobea(nodoa, hitza, i, j+1, pos, x);
					hitz31 = ingurutikHobea(nodoa, hitza, i+1, j-1, pos, x);
					hitz32 = ingurutikHobea(nodoa, hitza, i+1, j, pos, x);
					hitz33 = ingurutikHobea(nodoa, hitza, i+1, j+1, pos, x);
				}
			}
			b11 = this.hiztegia.checkWord(hitz11);
			b12 = this.hiztegia.checkWord(hitz12);
			b13 = this.hiztegia.checkWord(hitz13);
			b21 = this.hiztegia.checkWord(hitz21);
			b23 = this.hiztegia.checkWord(hitz23);
			b31 = this.hiztegia.checkWord(hitz31);
			b32 = this.hiztegia.checkWord(hitz32);
			b33 = this.hiztegia.checkWord(hitz33);
			if (b11 >= b12 && b11 >= b13 && b11 >= b21 && b11 >= b23 && b11 >= b31 && b11 >= b32 && b11 >= b33) {
				return hitz11;
			}
			else if (b12 >= b11 && b12 >= b13 && b12 >= b21 && b12 >= b23 && b12 >= b31 && b12 >= b32 && b12 >= b33) {
				return hitz12;
			}
			else if (b13 >= b11 && b13 >= b12 && b13 >= b21 && b13 >= b23 && b13 >= b31 && b13 >= b32 && b13 >= b33) {
				return hitz13;
			}
			else if (b21 >= b11 && b21 >= b12 && b21 >= b13 && b21 >= b23 && b21 >= b31 && b21 >= b32 && b21 >= b33) {
				return hitz21;
			}
			else if (b23 >= b11 && b23 >= b12 && b23 >= b13 && b23 >= b21 && b23 >= b31 && b23 >= b32 && b23 >= b33) {
				return hitz23;
			}
			else if (b31 >= b11 && b31 >= b12 && b31 >= b13 && b31 >= b21 && b31 >= b23 && b31 >= b32 && b31 >= b33) {
				return hitz31;
			}
			else if (b32 >= b11 && b32 >= b12 && b32 >= b13 && b32 >= b21 && b32 >= b23 && b32 >= b31 && b32 >= b33) {
				return hitz32;
			}
			else {
				return hitz33;
			}
		}
	}
	
	public static void main(String args[]) {
		try {
			// long hasiera, bukaera; // System.nanoTime() erabiliz aplikazioaren zati bakoitzarekin probak egiteko erabili ditudan aldagaiak
			File fitx = new File("files/hiztegia.txt");
			Scanner sc = new Scanner(fitx);
			// hasiera = System.nanoTime();
			Hiztegia hiztegia = new Hiztegia(sc);
			// bukaera = System.nanoTime() - hasiera;
			// System.out.println("Hiztegia irekitzeko denbora: " + bukaera/1000000000.0 + " s\n");
			sc.close();
			// hasiera = System.nanoTime();
			Boggle t = new Boggle(8, 13); // (5, 5), (20, 20)...
			System.out.println("Bilatu hitz bat taula honetan:\n");
			System.out.println(t);
			// bukaera = System.nanoTime() - hasiera;
			// System.out.println("Taula sortzeko eta idazteko denbora: " + bukaera/1000000.0 + " ms\n");
			BoggleJokatu jokoa = new BoggleJokatu(t, hiztegia);
			Scanner sarrera = new Scanner(System.in);
			System.out.println("Idatzi zure hitza");
			String hitza = sarrera.next();
			System.out.println();
			// hasiera = System.nanoTime();
			boolean badago = jokoa.taulanDago(hitza);
			// bukaera = System.nanoTime() - hasiera;
			// System.out.println("Hitza taulan bilatzeko denbora: " + bukaera/1000.0 + " µs\n");
			int puntuak;
			if (badago) {
				// hasiera = System.nanoTime();
				puntuak = jokoa.hiztegia.checkWord(hitza);
				// bukaera = System.nanoTime() - hasiera;
				if (puntuak == 0) {
					System.out.println("Hitz hori ez dago hiztegian");
				}
				else {
					System.out.println("Hitz hori hiztegian dago");
				}
				// System.out.println("Hitza hiztegian bilatzeko denbora: " + bukaera/1000.0 + " µs\n");
				System.out.println("Puntuazioa: " + puntuak + "\n");
			}
			else {
				puntuak = 0;
				System.out.println("Puntuazioa: 0\n");
			}
			sarrera.close();
			System.out.println("Orain ordenagailuaren txanda da");
			System.out.println("Zuk aukeratutakoa baino puntuazio hobea duen hitz bat bilatzen saiatuko da\n");
			// hasiera = System.nanoTime();
			String hitzHobea = jokoa.lortuHobea(puntuak);
			// bukaera = System.nanoTime() - hasiera;
			// System.out.println("Hitz hobea bilatzeko behar den denbora: " + bukaera/1000000.0 + " ms\n");
			if (hitzHobea.length() == 0) {
				System.out.println("Ez dago hitz hoberik\n");
				System.out.println("Zorionak, irabazi duzu!");
			}
			else {
				System.out.println("Hitz hobe bat: " + hitzHobea);
				System.out.println("Bere puntuazioa: " + jokoa.hiztegia.checkWord(hitzHobea) + "\n");
				System.out.println("Ordenagailuak irabazi du");
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Ez da hiztegia.txt fitxategia aurkitu");
		}
	}
	
}

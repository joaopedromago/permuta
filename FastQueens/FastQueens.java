package FastQueens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

class NQueens {

	private int[] queen;
	private int[] dp;
	private int[] dn;

	public boolean noCollisions() {
		HashSet<Integer> dps = new HashSet<Integer>();
		HashSet<Integer> dns = new HashSet<Integer>();

		for (int i = 0; i < queen.length; ++i) {
			int p = i + queen[i];
			int n = queen[i] - i + queen.length - 1;

			if (dps.contains(p) || dns.contains(n)) {
				return false;
			}
		}

		return true;
	}

	public void print(OutputStream os) {
		PrintStream printStream = new PrintStream(os);

		if (queen.length < 20) {
			printStream.print("queen \t [ ");
			for (int i = 0; i < queen.length; ++i) {
				printStream.print(queen[i] + " ");
			}
			printStream.println("]");

			printStream.print("dp \t [ ");
			for (int i = 0; i < dp.length; ++i) {
				printStream.print(dp[i] + " ");
			}
			printStream.println("]");

			printStream.print("dn \t [ ");
			for (int i = 0; i < dn.length; ++i) {
				printStream.print(dn[i] + " ");
			}
			printStream.println("]");

			printStream.print("Atacadas: [ ");
			for (Integer q : computeAttacks()) {
				printStream.print(q + " ");
			}
			printStream.println("]");
		}

		printStream.println("Colisoes: " + computeCollisions());

	}

	public NQueens(int n) {
		queen = new int[n];
		dp = new int[2 * n - 1];
		dn = new int[2 * n - 1];

		for (int i = 0; i < n; ++i)
			queen[i] = i;

		for (int i = 0; i < 2 * n - 1; ++i) {
			if ((i & 1) == 0)
				dp[i] = 1;
			else
				dp[i] = 0;

			if (i == n - 1)
				dn[i] = n;
			else
				dn[i] = 0;
		}

	}

	public int computeCollisions() {
		int aux = 0;

		for (int i = 0; i < dp.length; ++i) {
			if (dp[i] > 1)
				aux = aux + dp[i] - 1;
			if (dn[i] > 1)
				aux = aux + dn[i] - 1;
		}

		return aux;
	}

	public ArrayList<Integer> computeAttacks() {
		ArrayList<Integer> attacks = new ArrayList<Integer>();

		for (int i = 0; i < queen.length; ++i) {
			int idp = queen[i] + i;
			int idn = queen[i] - i + queen.length - 1;

			if (dp[idp] > 1 || dn[idn] > 1)
				attacks.add(i);
		}

		return attacks;
	}

	public int computeCollisions(int q1, int q2) {
		TreeSet<Integer> setP = new TreeSet<Integer>();
		TreeSet<Integer> setN = new TreeSet<Integer>();

		setP.add(queen[q1] + q1);
		setN.add(queen[q1] - q1 + queen.length - 1);
		setP.add(queen[q2] + q2);
		setN.add(queen[q2] - q2 + queen.length - 1);
		setP.add(queen[q2] + q1);
		setN.add(queen[q2] - q1 + queen.length - 1);
		setP.add(queen[q1] + q2);
		setN.add(queen[q1] - q2 + queen.length - 1);

		int aux = 0;

		for (Integer q : setP) {
			aux += dp[q] > 1 ? dp[q] - 1 : 0;
		}

		for (Integer q : setN) {
			aux += dn[q] > 1 ? dn[q] - 1 : 0;
		}

		return aux;
	}

	public int swap(int q1, int q2) {
		int dpq1 = queen[q1] + q1;
		int dnq1 = queen[q1] - q1 + queen.length - 1;
		int dpq2 = queen[q2] + q2;
		int dnq2 = queen[q2] - q2 + queen.length - 1;

		int colBefore = computeCollisions(q1, q2);

		dp[dpq1]--;
		dp[dpq2]--;
		dn[dnq1]--;
		dn[dnq2]--;

		int tempq = queen[q1];
		queen[q1] = queen[q2];
		queen[q2] = tempq;

		dpq1 = queen[q1] + q1;
		dnq1 = queen[q1] - q1 + queen.length - 1;
		dpq2 = queen[q2] + q2;
		dnq2 = queen[q2] - q2 + queen.length - 1;

		dp[dpq1]++;
		dp[dpq2]++;
		dn[dnq1]++;
		dn[dnq2]++;

		int colAfter = computeCollisions(q1, q2);

		return colAfter - colBefore;
	}

	public void shuffle() {
		Random rnd = new Random();

		for (int i = 0; i < queen.length; ++i) {
			swap(i, rnd.nextInt(queen.length));
		}
	}

	public void qs2() {

		int collisions;
		int loopcount;
		Random rnd = new Random();
		do {
			shuffle();
			collisions = computeCollisions();
			if (collisions == 0)
				return;
			int limit = (int) (0.45 * collisions);
			ArrayList<Integer> attacks = computeAttacks();
			loopcount = 0;

			do {
				for (Integer q1 : attacks) {
					Integer q2 = rnd.nextInt(queen.length);
					int delta = swap(q1, q2);
					if (delta < 0) {
						collisions += delta;
						if (collisions == 0)
							return;
						if (collisions < limit) {
							limit = (int) (0.45 * collisions);
							attacks = computeAttacks();
						}
					} else {
						swap(q1, q2);
					}
				}

				loopcount += attacks.size();
			} while (loopcount <= 32 * queen.length);

		} while (collisions > 0);
	}

}

/**
 *
 * @author LCM
 */
public class FastQueens {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Digite o número de rainhas: ");
		int numRainhas = Integer.parseInt(reader.readLine());
		System.out.println();

		NQueens queens = new NQueens(numRainhas);

		long start = System.nanoTime();
		queens.qs2();
		long end = System.nanoTime();

		System.out.println(((double) (end - start)) / 1E9 + " segundos");

		queens.print(System.out);
		
		if (!queens.noCollisions()) {
			System.out.println("Erro no Algoritmo");
		}

	}
}

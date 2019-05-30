package jp.co.szk.aoj.grl.grl_1_c;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws IOException {
		new Main().solve();
	}

	private void solve() throws IOException {
		try {
			solveA();
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}

	}

	private void solveA() {
		int v = nextInt();
		int e = nextInt();

		int[][] wk = Stream.generate(() -> new int[] { nextInt(), nextInt() }).limit(e - 1).toArray(int[][]::new);

		int[][] graph = new int[v][v];

		for (int i = 0; i < graph.length; i++) {
			Arrays.fill(graph[i], Integer.MAX_VALUE / 10);
			graph[i][i] = 0;
		}
		for (int i = 0; i < wk.length; i++) {
			int from = wk[i][0];
			int to = wk[i][1];
			graph[from][to] = 1;
			graph[to][from] = 1;
		}

		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				for (int k = 0; k < v; k++) {
					graph[j][k] = Integer.min(graph[j][k], graph[j][i] + graph[i][k]);
				}
			}
		}
		int[][] dag = new int[v][v];
		for (int i = 0; i < wk.length; i++) {
			int from = wk[i][0];
			int to = wk[i][1];
			if (graph[0][from] + 1 == graph[0][to]) {
				dag[from][to] = 1;
			}
			if (graph[0][from] - 1 == graph[0][to]) {
				dag[to][from] = 1;
			}
		}
		out.println(0);
		for (int i = 0; i < dag.length; i++) {
			for (int j = 0; j < dag[i].length; j++) {
				if (dag[i][j] != 0) {
					out.println(j);
				}
			}
		}
	}

	private final PrintWriter out = new PrintWriter(System.out);
	private final InputStream in = System.in;
	private final byte[] buffer = new byte[1024];
	private int ptr = 0;
	private int buflen = 0;

	private boolean hasNextByte() {
		if (ptr < buflen) {
			return true;
		} else {
			ptr = 0;
			try {
				buflen = in.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (buflen <= 0) {
				return false;
			}
		}
		return true;
	}

	private int readByte() {
		if (hasNextByte())
			return buffer[ptr++];
		else
			return -1;
	}

	private static boolean isPrintableChar(int c) {
		return 33 <= c && c <= 126;
	}

	private void skipUnprintable() {
		while (hasNextByte() && !isPrintableChar(buffer[ptr]))
			ptr++;
	}

	public boolean hasNext() {
		skipUnprintable();
		return hasNextByte();
	}

	public int nextInt() {
		return Integer.parseInt(next());
	}

	public String next() {
		if (!hasNext())
			throw new NoSuchElementException();
		StringBuilder sb = new StringBuilder();
		int b = readByte();
		while (isPrintableChar(b)) {
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}

	public long nextLong() {
		if (!hasNext())
			throw new NoSuchElementException();
		long n = 0;
		boolean minus = false;
		int b = readByte();
		if (b == '-') {
			minus = true;
			b = readByte();
		}
		if (b < '0' || '9' < b) {
			throw new NumberFormatException();
		}
		while (true) {
			if ('0' <= b && b <= '9') {
				n *= 10;
				n += b - '0';
			} else if (b == -1 || !isPrintableChar(b)) {
				return minus ? -n : n;
			} else {
				throw new NumberFormatException();
			}
			b = readByte();
		}
	}
}
package jp.co.szk.aoj.volume0.sum_of_integers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class Main {

	public static void main(String[] args) throws IOException {
		new Main().solve();
	}

	private void solve() throws IOException {
		try {
			//			solveProblem();
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

	private void solveProblem() {
		int n = nextInt();
		int[] wk = IntStream.range(0, n).map(i -> nextInt()).toArray();
		int a = nextInt();

		boolean[][] dp = new boolean[n + 1][a + 1];

		dp[0][0] = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= a; j++) {
				if (j >= wk[i]) {
					dp[i + 1][j] = dp[i][j - wk[i]] || dp[i][j];
				} else {
					dp[i + 1][j] = dp[i][j];
				}

			}
		}

		out.println(dp[n][a]);
	}

	private void solveA() {
		//		while (true) {
		//		int n = nextInt();
		//		int a = nextInt();
		int n = 3;
		int a = 6;

		int[] iA = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		if (n == 0 && a == 0) {
			return;
		}

		int[][] dp = new int[n + 1][a + 1];

		dp[0][0] = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= a; j++) {
				if (j >= i) {
					//					if (j >= iA[i]) {
					dp[i + 1][j] = dp[i][j] + dp[i][j - i];
					//					dp[i + 1][j] = dp[i][j] + dp[i][j - iA[i]];
				} else {
					dp[i + 1][j] = dp[i][j];
				}
			}
		}

		out.println(dp[n][a]);
		//		}
		//		out.println("finish");
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
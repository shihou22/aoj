package jp.co.szk.aoj.volume11.n1130;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
		int w = nextInt();
		int h = nextInt();

		while (w != 0 && h != 0) {
			char[][] map = Stream.generate(() -> next().toCharArray()).limit(h).toArray(char[][]::new);

			int wkH = 0;
			int wkW = 0;
			boolean isDiscover = false;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (map[i][j] == '@') {
						wkH = i;
						wkW = j;
						isDiscover = true;
						break;
					}
				}
				if (isDiscover) {
					break;
				}
			}

			long res = recursiveA(map, new boolean[h][w], h, w, wkH, wkW);
			out.println(res);

			w = nextInt();
			h = nextInt();
		}

	}

	private long recursiveA(char[][] map, boolean[][] visit, int h, int w, int cH, int cW) {
		if (cH >= h || cW >= w || cH < 0 || cW < 0) {
			return 0;
		}
		if (map[cH][cW] == '#') {
			return 0;
		}
		if (visit[cH][cW]) {
			return 0;
		}

		visit[cH][cW] = true;
		long res = 1;

		final int[] d = new int[] { 0, 1, 0, -1 };

		for (int i = 0; i < d.length; i++) {
			int wkY = cH + d[i ^ 1];
			int wkX = cW + d[i];
			res += recursiveA(map, visit, h, w, wkY, wkX);
		}

		return res;
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
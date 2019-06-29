package jp.co.szk.aoj.volume01.n0118;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws IOException {
		new Main().solve();
	}

	private void solve() throws IOException {
		try {
			//			solveA();
			solveA2();
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

	private void solveA2() {
		try {
			int h = nextInt();
			int w = nextInt();
			while (h != 0 && w != 0) {
				char[][] map = Stream.generate(() -> next().toCharArray()).limit(h).toArray(char[][]::new);
				int res = 0;
				boolean[][] finish = new boolean[h][w];
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						if (!finish[i][j]) {
							recursiveA2(map, finish, h, w, i, j, map[i][j]);
							res++;
						}
					}
				}
				out.println(res);
				h = nextInt();
				w = nextInt();
			}
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	private static class Position {
		final int h;
		final int w;

		public Position(int h, int w) {
			this.h = h;
			this.w = w;
		}
	}

	private void recursiveA2(char[][] map, boolean[][] finish, int h, int w, int cH, int cW, char mark) {

		ArrayDeque<Position> queue = new ArrayDeque<Position>();
		Position pos = new Position(cH, cW);
		queue.addLast(pos);
		while (!queue.isEmpty()) {
			Position cPosition = queue.pollLast();
			//探索済みとする
			finish[cPosition.h][cPosition.w] = true;

			final int[] d = new int[] { 0, 1, 0, -1 };
			for (int i = 0; i < d.length; i++) {
				int wkH = cPosition.h + d[i];
				int wkW = cPosition.w + d[i ^ 1];
				//範囲外
				if (wkH >= h || wkH < 0 || wkW >= w || wkW < 0) {
					continue;
				}
				//探索済み
				if (finish[wkH][wkW]) {
					continue;
				}
				//同じ果物ではない
				if (map[wkH][wkW] != mark) {
					continue;
				}
				queue.addLast(new Position(wkH, wkW));
			}
		}

	}

	private void solveA() {
		try {
			int h = nextInt();
			int w = nextInt();
			while (h != 0 && w != 0) {
				char[][] map = Stream.generate(() -> next().toCharArray()).limit(h).toArray(char[][]::new);
				int res = 0;
				boolean[][] finish = new boolean[h][w];
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						if (!finish[i][j]) {
							recursiveA(map, finish, h, w, i, j, map[i][j]);
							res++;
						}
					}
				}
				out.println(res);
				h = nextInt();
				w = nextInt();
			}
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	private void recursiveA(char[][] map, boolean[][] finish, int h, int w, int cH, int cW, char mark) {
		//範囲外
		if (cH >= h || cH < 0 || cW >= w || cW < 0) {
			return;
		}
		//探索済み
		if (finish[cH][cW]) {
			return;
		}
		//同じ果物ではない
		if (map[cH][cW] != mark) {
			return;
		}
		//探索済みとする
		finish[cH][cW] = true;

		final int[] d = new int[] { 0, 1, 0, -1 };
		for (int i = 0; i < d.length; i++) {
			recursiveA(map, finish, h, w, cH + d[i], cW + d[i ^ 1], mark);
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
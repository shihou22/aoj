package jp.co.szk.aoj.volume5.n0558;

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

	private void solveA() {
		int h = nextInt();
		int w = nextInt();
		int n = nextInt();
		Position startPos = null;
		char[][] map = Stream.generate(() -> next().toCharArray()).limit(h).toArray(char[][]::new);

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (map[i][j] == 'S') {
					startPos = new Position(i, j, 0);
				}
			}
		}

		int res = 0;
		for (int i = 1; i <= n; i++) {
			startPos = bfsA(map, i, h, w, startPos.clone());
			res += startPos.step;
		}
		out.println(res);
	}

	private Position bfsA(char[][] map, int currentHp, int h, int w, Position pos) {

		ArrayDeque<Position> queue = new ArrayDeque<Position>();
		queue.addLast(pos);

		final int[] d = new int[] { 0, 1, 0, -1 };
		boolean[][] visited = new boolean[h][w];
		visited[pos.h][pos.w] = true;

		while (!queue.isEmpty()) {
			Position curPos = queue.pollLast();

			for (int i = 0; i < d.length; i++) {
				int wkH = d[i] + curPos.h;
				int wkW = d[i ^ 1] + curPos.w;
				if (wkW >= w || wkW < 0 || wkH >= h || wkH < 0
						|| map[wkH][wkW] == 'X' || visited[wkH][wkW]) {
					continue;
				}
				Position resPos = new Position(wkH, wkW, curPos.step + 1);
				//				if (currentHp == 7) {
				//					resPos.print();
				//				}
				if (map[wkH][wkW] == '0' + currentHp) {
					return resPos;
				}
				queue.addFirst(resPos);
				visited[resPos.h][resPos.w] = true;
			}
		}
		return null;

	}

	private static class Position {
		int h;
		int w;
		int step;

		public Position(int h, int w, int step) {
			this.h = h;
			this.w = w;
			this.step = step;
		}

		public Position clone() {
			return new Position(this.h, this.w, 0);
		}

		public void print() {
			System.out.println(this.h + " " + this.w + " " + this.step);
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
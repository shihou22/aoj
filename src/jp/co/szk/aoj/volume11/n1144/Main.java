package jp.co.szk.aoj.volume11.n1144;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

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

			int[][] map = new int[h][w];
			int wkH = 0;
			int wkW = 0;
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					map[i][j] = nextInt();
					if (map[i][j] == 2) {
						wkH = i;
						wkW = j;
					}
				}
			}
			long res = recursiveA(map, w, h, wkW, wkH, 1);

			out.println(res > 10 ? -1 : res);
			w = nextInt();
			h = nextInt();
		}
	}

	private long recursiveA(int[][] map, int w, int h, int currentW, int currentH, int num) {
		//範囲外
		if (currentW >= w || currentW < 0 || currentH >= h || currentH < 0) {
			return 11;
		}
		//手数オーバー
		if (num > 10) {
			return 11;
		}

		final int[] d = new int[] { 0, 1, 0, -1 };

		long res = 11;

		for (int i = 0; i < d.length; i++) {
			int tH = d[i];
			int tW = d[i ^ 1];
			int dH = currentH;
			int dW = currentW;
			//一定方向への移動を行う
			for (int j = 1;; j++) {
				//現在の移動量
				int bH = j * tH + currentH;
				int bW = j * tW + currentW;
				//範囲外なのでこの方向への移動はNG
				if (bW >= w || bW < 0 || bH >= h || bH < 0) {
					break;
				}
				//隣が障害物のために動けない
				if (j == 1 && map[bH][bW] == 1) {
					break;
				}
				if (map[bH][bW] == 1) {
					//障害物なので障害物を壊す
					int mapC = map[bH][bW];
					map[bH][bW] = 0;
					//移動もとは障害物手前
					res = Math.min(res, recursiveA(map, w, h, dW, dH, num + 1));
					//壊した障害物をもとに戻す
					map[bH][bW] = mapC;
					break;
				} else if (map[bH][bW] == 3) {
					//この手数で移動できた
					res = Math.min(res, num);
					break;
				}
				//今いる場所
				dH = bH;
				dW = bW;
			}

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
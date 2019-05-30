package jp.co.szk.aoj.grl.grl_4_b;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
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

	/**
	 * https://atcoder.jp/contests/nikkei2019-qual/tasks/nikkei2019_qual_d
	 * D - Restore the Tree
	 */
	private void solveA() {
		int v = nextInt();
		int e = nextInt();

		List<List<Integer>> rinsetuList = new ArrayList<List<Integer>>();
		//		Map<Integer, Set<Integer>> fromList = new HashMap<Integer, Set<Integer>>();

		for (int i = 0; i < v; i++) {
			rinsetuList.add(new ArrayList<Integer>());
			//			fromList.put(new Integer(i), new HashSet<Integer>());
		}

		int[] indegrees = new int[v];
		for (int i = 0; i < v - 1 + e; i++) {
			int from = nextInt() - 1;
			int to = nextInt() - 1;
			rinsetuList.get(from).add(to);
			//			fromList.get(to).add(from);
			indegrees[to]++;
		}

		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		for (int i = 0; i < indegrees.length; i++) {
			if (indegrees[i] == 0) {
				queue.addLast(i);
			}
		}

		//		List<Integer> res = new ArrayList<Integer>();
		int[] par = new int[v];
		int cnt = 0;
		while (queue.size() != 0) {
			int index = queue.removeLast();
			//			res.add(index);

			for (Integer nextDegree : rinsetuList.get(index)) {
				int nextCnt = --indegrees[nextDegree];
				if (nextCnt == 0) {
					queue.addLast(nextDegree);
					par[nextDegree] = index + 1;
				}
			}
			if (cnt > v) {
				out.println("This graph includes the Cycle");
				return;
			}
		}

		for (int i : par) {
			out.println(i);
		}
		//		int count = 0;
		//		for (Integer integer : res) {
		//			count++;
		//			for (Set<Integer> set : fromList.values()) {
		//				if (set.size() > 1 && set.contains(integer)) {
		//					set.remove(integer);
		//				}
		//			}
		//		}
		//
		//		for (Set<Integer> set : fromList.values()) {
		//			if (set.size() == 0) {
		//				out.println(0);
		//			}
		//			for (Integer body : set) {
		//				out.println(body + 1);
		//			}
		//		}
	}

	/**
	 * http://www.thothchildren.com/chapter/5bcc8bc051d9305189030f9f
	 * https://tubo28.me/algorithm/tsort/
	 * http://www.dais.is.tohoku.ac.jp/~shioura/teaching/ad09/ad09-13.pdf
	 * https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
	 * トポロジカルソート
	 */
	private void solveA2() {
		int v = nextInt();
		int e = nextInt();

		/*
		 *隣接リストの作成
		 */
		List<List<Integer>> adj = new ArrayList<List<Integer>>();
		for (int i = 0; i < v; i++) {
			adj.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < e; i++) {
			int from = nextInt();
			int to = nextInt();
			adj.get(from).add(to);
		}
		// 入次数が0のものを判定するための配列
		int indegree[] = new int[v];
		// 入次数0を判定
		for (int i = 0; i < v; i++) {
			List<Integer> temp = adj.get(i);
			//iをfromとするnode達
			for (int node : temp) {
				//入次数の個数
				indegree[node]++;
			}
		}

		/*
		 * queueの作成
		 * 入次数0のものをqueueに詰める
		 * 入次数0から調査していく
		 */
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		for (int i = 0; i < v; i++) {
			if (indegree[i] == 0) {
				q.addLast(i);
			}
		}

		//訪問済み頂点数
		int cnt = 0;

		// トポロジカルソートの結果
		List<Integer> res = new ArrayList<Integer>();

		/*
		 * BFS
		 */
		while (!q.isEmpty()) {
			// 接続先の頂点を探索開始
			int u = q.removeFirst();
			//入次数0なのでリザルトにadd
			res.add(u);

			/*
			 * この頂点の次の接続先の入次数を-する
			 * その結果、入次数=0となる場合はソートリザルトに追加し、次の探索に利用する
			 */
			for (int node : adj.get(u)) {

				indegree[node]--;
				if (indegree[node] == 0) {
					q.addFirst(node);
				}
			}
			cnt++;
			if (cnt > v) {
				System.out.println("graph内に循環有");
				return;
			}
		}

		for (int i : res) {
			out.println(i);
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
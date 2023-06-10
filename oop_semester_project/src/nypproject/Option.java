package nypproject;

public class Option implements java.io.Serializable {
	private static final long serialVersionUID = -2699847018902387393L;
	private int aStart;
	private int aEnd;
	private int bStart;
	private int bEnd;
	private int N;
	
	public Option(int aStart, int aEnd, int bStart, int bEnd, int n) {
		this.aStart = aStart;
		this.aEnd = aEnd;
		this.bStart = bStart;
		this.bEnd = bEnd;
		this.N = n;
	}

	public int getaStart() {
		return aStart;
	}

	public void setaStart(int aStart) {
		this.aStart = aStart;
	}

	public int getaEnd() {
		return aEnd;
	}

	public void setaEnd(int aEnd) {
		this.aEnd = aEnd;
	}

	public int getbStart() {
		return bStart;
	}

	public void setbStart(int bStart) {
		this.bStart = bStart;
	}

	public int getbEnd() {
		return bEnd;
	}

	public void setbEnd(int bEnd) {
		this.bEnd = bEnd;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}
	
	
}

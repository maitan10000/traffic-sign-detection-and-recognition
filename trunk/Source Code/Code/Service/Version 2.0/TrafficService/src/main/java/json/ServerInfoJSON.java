package json;

public class ServerInfoJSON {
	private int totalUser;
	private int totalSearch;
	private long space;
	private long freeSpace;
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	public int getTotalSearch() {
		return totalSearch;
	}
	public void setTotalSearch(int totalSearch) {
		this.totalSearch = totalSearch;
	}
	public long getSpace() {
		return space;
	}
	public void setSpace(long space) {
		this.space = space;
	}
	public long getFreeSpace() {
		return freeSpace;
	}
	public void setFreeSpace(long freeSpace) {
		this.freeSpace = freeSpace;
	}
	
	
}

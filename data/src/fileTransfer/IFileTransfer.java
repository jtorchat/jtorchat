package fileTransfer;


public interface IFileTransfer {
	public void close();
	public void delete();
	public void startstop();
	public void open();
	public void opendir();
}

package a4.Observer;


public interface IObservable {
	public void addObserver(IObserver obs);
	public void notifyObservers();
}

package a4.Collection;

public interface IIterator<Type> {
	public boolean hasNext();
	public Type getNext();
	public void remove();
}

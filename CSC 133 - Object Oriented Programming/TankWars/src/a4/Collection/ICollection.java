package a4.Collection;

public interface ICollection<Type> {
	public void addObj(Type o);
	public IIterator<?> getIterator();
	public int getSize();
	public void removeObj(Type o);
}

package common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class PeekIterator<T> implements Iterator<T> {

    private static final int CACHE_SIZE = 10;
    private Iterator<T> it;
    private T _endToken = null;

    /**
     * 缓存
     */
    private LinkedList<T> queueCache = new LinkedList<>();
    private LinkedList<T> stackPutBack = new LinkedList<>();

    public PeekIterator(Stream<T> stream) {
        it = stream.iterator();
    }

    public PeekIterator(Stream<T> stream, T endToken) {
        it = stream.iterator();
        _endToken = endToken;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return _endToken != null || this.stackPutBack.size() > 0 || it.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() {
        T val = null;
        if (this.stackPutBack.size() > 0) {
            val = this.stackPutBack.pop();
        } else {
            if (!it.hasNext()) {
                T tmp = _endToken;
                _endToken=null;
                return tmp;
            }
            val = it.next();
        }
        while (queueCache.size() > CACHE_SIZE - 1) {
            queueCache.poll();
        }
        queueCache.add(val);
        return val;
    }

    public T peek() {
        if (this.stackPutBack.size() > 0) {
            return this.stackPutBack.getFirst();
        }
        if (!it.hasNext()) {
            return _endToken;
        }
        T val = next();
        this.putBack();
        return val;
    }


    /**
     * 缓存：A->B->C->D
     * 放回：D->C->B->A
     *
     * @return
     */
    public void putBack() {

        if (this.queueCache.size() > 0) {
            this.stackPutBack.push(this.queueCache.pollLast());
        }

    }
}

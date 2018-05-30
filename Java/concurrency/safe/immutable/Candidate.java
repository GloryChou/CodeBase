package safe.immutable;

import com.sun.xml.internal.stream.util.ReadOnlyIterator;
import synchrone.Endpoint;

import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author Kyle
 * @create 2018/5/29 23:34
 */
public class Candidate implements Iterable<Endpoint> {

    // 下游部件节点列表
    private final Set<Endpoint> endpoints;

    // 下游部件节点的总权重
    public final int totalWeight;

    public Candidate(Set<Endpoint> endpoints) {
        int sum = 0;
        for(Endpoint endpoint : endpoints) {
            sum += endpoint.weight;
        }
        this.totalWeight = sum;
        this.endpoints = endpoints;
    }

    @Override
    public final Iterator<Endpoint> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Endpoint> action) {

    }

    @Override
    public Spliterator<Endpoint> spliterator() {
        return null;
    }
}

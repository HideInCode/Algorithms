package Graphs.MinimumSpanningTrees;

/**
 * 1.有两个顶点,权重
 * 2.两个顶点互通
 * 3.给出获取相连顶点的接口,给出获取边的权重的接口.
 */
public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    //边的权重
    double weight() {
        return weight;
    }

    //边两端的顶点之一
    int either() {
        return v;
    }

    //另一个顶点
    int other(int vertex) {
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        } else {
            throw new RuntimeException("Inconsistent edge");
        }
    }


    @Override
    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }

    @Override
    public int compareTo(Edge that) {
        return (this.weight() < that.weight()) ? -1 : (this.weight() > this.weight() ? 1 : 0);
    }
}

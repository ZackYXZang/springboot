package com.example.leetcode.utils;

/**
 * 建立一张图
 */
public class GraphGenerator {

	/**
	 * matrix的格式：
	 * 	[1, 2, 3]
	 * 	第一个位置是边的权重，第二个位置是from节点，第三个位置是to节点
	 * @param matrix
	 * @return
	 */
	public static Graph createGraph(int[][] matrix) {
		Graph graph = new Graph();
		for (int i = 0; i < matrix.length; i++) {
			//边的权重
			Integer weight = matrix[i][0];
			//from节点的value
			Integer from = matrix[i][1];
			//to节点的value
			Integer to = matrix[i][2];
			//如果图中没有from节点，就加上
			if (!graph.nodes.containsKey(from)) {
				graph.nodes.put(from, new GraphNode(from));
			}
			//如果图中没有to节点，就加上
			if (!graph.nodes.containsKey(to)) {
				graph.nodes.put(to, new GraphNode(to));
			}

			GraphNode fromNode = graph.nodes.get(from);
			GraphNode toNode = graph.nodes.get(to);
			//建立从from到to的边
			GraphEdge newEdge = new GraphEdge(weight, fromNode, toNode);
			//from节点的nexts加上to节点
			fromNode.nexts.add(toNode);
			//from节点的出度加1
			fromNode.out++;
			//to节点的入度加1
			toNode.in++;
			//from节点的edges加上新的边
			fromNode.edges.add(newEdge);
			//整张图的边的集合中加入新的边，因为可能重复，所以是set
			graph.edges.add(newEdge);
		}
		return graph;
	}

}

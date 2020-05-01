package symbol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class FlowGraph {
    public String graphName;
    public int flowNodeId;
    public HashMap<Integer, FlowNode> nodeId2flowNode = new HashMap<>();
    public HashMap<String, Integer> label2nodeId = new HashMap<>();
    public HashMap<Integer, String> pendingEdges = new HashMap<>();

    public FlowGraph(String _name) {
        graphName = _name;
        flowNodeId = 0;

        FlowNode entryNode = new FlowNode(0, this);
        nodeId2flowNode.put(0, entryNode);
    }

    public void addExitNode() {
        flowNodeId++;
        FlowNode exitNode = new FlowNode(flowNodeId, this);
        nodeId2flowNode.put(flowNodeId, exitNode);
    }

    public void finishGraph() {
        for (Integer _from: pendingEdges.keySet()) {
            String _toLabel = pendingEdges.get(_from);
            Integer _to = label2nodeId.get(_toLabel);
            addEdge(_from, _to);
        }

        int cnt = 0;
        while (true) {
            boolean change = updateInOutSet();
            if (! change) { break; }
            System.out.println(cnt++);
        }
    }

    public HashSet<Integer> AND(HashSet<Integer> x, HashSet<Integer> y) {
        HashSet<Integer> ret = new HashSet<>(x);
        ret.retainAll(y);
        return ret;
    }

    public HashSet<Integer> OR(HashSet<Integer> x, HashSet<Integer> y) {
        HashSet<Integer> ret = new HashSet<>(x);
        ret.addAll(y);
        return ret;
    }

    public HashSet<Integer> MINUS(HashSet<Integer> x, HashSet<Integer> y) {
        HashSet<Integer> ret = new HashSet<>(x);
        ret.removeAll(y);
        return ret;
    }

    public boolean updateInOutSet() {
        boolean change = false;
        for (FlowNode node: nodeId2flowNode.values()) {
            HashSet<Integer> _in = OR(node.useTempHashSet, MINUS(node.outTempHashSet, node.defTempHashSet));
            HashSet<Integer> _out = new HashSet<>();
            for (FlowNode nextNode: node.nextNodeHashSet) {
                _out = OR(_out, nextNode.inTempHashSet);
            }

            if ((!node.inTempHashSet.equals(_in)) || (!node.outTempHashSet.equals(_out))) {
                change = true;
            }

            node.inTempHashSet = _in;
            node.outTempHashSet = _out;
        }
        return change;
    }

    public void addPendingEdge(Integer _nodeId, String _lineLabel) {
        pendingEdges.put(_nodeId, _lineLabel);
    }


    public int newFlowNode(String _lineLabel, boolean _seqEdgeFlag) {
        flowNodeId++;
        FlowNode _node = new FlowNode(flowNodeId, this);
        nodeId2flowNode.put(flowNodeId, _node);

        if (_lineLabel != null) {
            label2nodeId.put(_lineLabel, flowNodeId);
        }
        if (_seqEdgeFlag) {
            addSeqEdge(flowNodeId);
        }

        return flowNodeId;
    }

    public FlowNode getFlowNode(Integer _nodeId) {
        return nodeId2flowNode.get(_nodeId);
    }

    public void addEdge(Integer _from, Integer _to) {
        FlowNode fromNode = getFlowNode(_from);
        FlowNode toNode = getFlowNode(_to);

        fromNode.addNextNode(toNode);
        toNode.addPreNode(fromNode);
    }

    public void addSeqEdge(Integer _cur) {
        Integer pre = _cur - 1;
        addEdge(pre, _cur);
    }

}

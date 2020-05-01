//
// Generated by JTB 1.3.2
//

package visitor;

import symbol.FlowGraph;
import symbol.FlowNode;
import syntaxtree.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class BuildGraphVisitor extends GJNoArguDepthFirst<Object> {

    public HashMap<String, FlowGraph> label2flowGraph = new HashMap<>();
    public FlowGraph curFlowGraph;
    public String curLineLabel;
    public boolean seqEdgeFlag = true;

    public Object visit(NodeListOptional n) {
        if (n.present()) {
            Vector<Object> _ret = new Vector<>();
            int _count = 0;
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                Object e_ret = e.nextElement().accept(this);
                _ret.add(e_ret);
                _count++;
            }
            return _ret;
        } else
            return null;
    }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> "MAIN"
     * f1 -> StmtList()
     * f2 -> "END"
     * f3 -> ( Procedure() )*
     * f4 -> <EOF>
     */
    public Object visit(Goal n) {
        Object _ret = null;

        curFlowGraph = new FlowGraph("MAIN");
        label2flowGraph.put("MAIN", curFlowGraph);

        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);

        curFlowGraph.addExitNode();
        curFlowGraph.finishGraph();

        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    public Object visit(StmtList n) {
        Object _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> StmtExp()
     */
    public Object visit(Procedure n) {
        Object _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
     * f0 -> NoOpStmt()
     * | ErrorStmt()
     * | CJumpStmt()
     * | JumpStmt()
     * | HStoreStmt()
     * | HLoadStmt()
     * | MoveStmt()
     * | PrintStmt()
     */
    public Object visit(Stmt n) {
        Object _ret = null;
        // whether a sequential edge exists
        n.f0.accept(this);
        curLineLabel = null;
        return _ret;
    }

    /**
     * f0 -> "NOOP"
     */
    public Object visit(NoOpStmt n) {
        Object _ret = null;
        n.f0.accept(this);
        curFlowGraph.newFlowNode(curLineLabel, seqEdgeFlag);
        seqEdgeFlag = true;
        return _ret;
    }

    /**
     * f0 -> "ERROR"
     */
    public Object visit(ErrorStmt n) {
        Object _ret = null;
        n.f0.accept(this);
        curFlowGraph.newFlowNode(curLineLabel, seqEdgeFlag);
        seqEdgeFlag = true;
        return _ret;
    }

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    public Object visit(CJumpStmt n) {
        Object _ret = null;
        n.f0.accept(this);
        Integer tempId = (Integer) n.f1.accept(this);

        Integer curNodeId = curFlowGraph.newFlowNode(curLineLabel, seqEdgeFlag);
        FlowNode curFlowNode = curFlowGraph.getFlowNode(curNodeId);
        curFlowNode.addUseTemp(tempId);

        String jumpLabel = (String) n.f2.accept(this);
        curFlowGraph.addPendingEdge(curNodeId, jumpLabel);

        seqEdgeFlag = true;
        return _ret;
    }

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    public Object visit(JumpStmt n) {
        Object _ret = null;
        n.f0.accept(this);
        Integer curNodeId = curFlowGraph.newFlowNode(curLineLabel, seqEdgeFlag);

        String jumpLabel = (String) n.f1.accept(this);
        curFlowGraph.addPendingEdge(curNodeId, jumpLabel);

        seqEdgeFlag = false;
        return _ret;
    }

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    public Object visit(HStoreStmt n) {
        Object _ret = null;
        n.f0.accept(this);
        Integer tempId_1_use = (Integer) n.f1.accept(this);
        n.f2.accept(this);
        Integer tempId_2_use = (Integer) n.f3.accept(this);

        Integer curNodeId = curFlowGraph.newFlowNode(curLineLabel, seqEdgeFlag);
        FlowNode curFlowNode = curFlowGraph.getFlowNode(curNodeId);
        curFlowNode.addUseTemp(tempId_1_use);
        curFlowNode.addUseTemp(tempId_2_use);
        seqEdgeFlag = true;

        return _ret;
    }

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    public Object visit(HLoadStmt n) {
        Object _ret = null;
        n.f0.accept(this);
        Integer tempId_1_def = (Integer) n.f1.accept(this);
        Integer tempId_2_use = (Integer) n.f2.accept(this);
        n.f3.accept(this);

        Integer curNodeId = curFlowGraph.newFlowNode(curLineLabel, seqEdgeFlag);
        FlowNode curFlowNode = curFlowGraph.getFlowNode(curNodeId);
        curFlowNode.addDefTemp(tempId_1_def);
        curFlowNode.addUseTemp(tempId_2_use);
        seqEdgeFlag = true;

        return _ret;
    }

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    public Object visit(MoveStmt n) {
        Object _ret = null;
        n.f0.accept(this);
        Integer tempId = (Integer) n.f1.accept(this);
        Vector<Integer> expTempIds = (Vector<Integer>) n.f2.accept(this);

        Integer curNodeId = curFlowGraph.newFlowNode(curLineLabel, seqEdgeFlag);
        FlowNode curFlowNode = curFlowGraph.getFlowNode(curNodeId);
        curFlowNode.addDefTemp(tempId);
        for (Integer i: expTempIds) {
            curFlowNode.addUseTemp(i);
        }
        seqEdgeFlag = true;

        return _ret;
    }

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    public Object visit(PrintStmt n) {
        Object _ret = null;
        n.f0.accept(this);
        Vector<Integer> expTempIds = (Vector<Integer>) n.f1.accept(this);
        Integer curNodeId = curFlowGraph.newFlowNode(curLineLabel, seqEdgeFlag);
        FlowNode curFlowNode = curFlowGraph.getFlowNode(curNodeId);
        for (Integer i: expTempIds) {
            curFlowNode.addUseTemp(i);
        }
        seqEdgeFlag = true;

        return _ret;
    }

    /**
     * f0 -> Call()
     * | HAllocate()
     * | BinOp()
     * | SimpleExp()
     */
    public Object visit(Exp n) {
        return n.f0.accept(this);
    }

    /**
     * f0 -> "BEGIN"
     * f1 -> StmtList()
     * f2 -> "RETURN"
     * f3 -> SimpleExp()
     * f4 -> "END"
     */
    public Object visit(StmtExp n) {
        Object _ret = null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     * f2 -> "("
     * f3 -> ( Temp() )*
     * f4 -> ")"
     */
    public Object visit(Call n) {
        Vector<Integer> curUseTempIds;
        n.f0.accept(this);
        curUseTempIds = (Vector<Integer>) n.f1.accept(this);
        n.f2.accept(this);
        Vector<Object> tempList = (Vector<Object>) n.f3.accept(this);
        n.f4.accept(this);

        for (Object i: tempList) {
            curUseTempIds.add((Integer) i);
        }

        return curUseTempIds;
    }

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    public Object visit(HAllocate n) {
        Object _ret;
        n.f0.accept(this);
        Vector<Integer> expTempIds = (Vector<Integer>) n.f1.accept(this);

        _ret = expTempIds;

        return _ret;
    }

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    public Object visit(BinOp n) {
        Object _ret;
        n.f0.accept(this);
        Integer tempId = (Integer) n.f1.accept(this);
        Vector<Integer> expTempIds = (Vector<Integer>) n.f2.accept(this);

        expTempIds.add(tempId);
        _ret = expTempIds;

        return _ret;
    }

    /**
     * f0 -> "LT"
     * | "PLUS"
     * | "MINUS"
     * | "TIMES"
     */
    public Object visit(Operator n) {
        Object _ret = null;
        n.f0.accept(this);
        return _ret;
    }

    /**
     * f0 -> Temp()
     * | IntegerLiteral()
     * | Label()
     */
    public Object visit(SimpleExp n) {
        Vector<Integer> _ret = new Vector<>();
        if (n.f0.which == 0) {
            _ret.add((Integer)n.f0.accept(this));
        }
        return _ret;
    }

    /**
     * f0 -> "TEMP"
     * f1 -> IntegerLiteral()
     */
    public Object visit(Temp n) {
        Object _ret;
        n.f0.accept(this);
        _ret = n.f1.accept(this);
        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public Object visit(IntegerLiteral n) {
        Object _ret;
        n.f0.accept(this);
        _ret = Integer.parseInt(n.f0.tokenImage);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public Object visit(Label n) {
        n.f0.accept(this);
        curLineLabel = n.f0.tokenImage;
        return n.f0.tokenImage;
    }

}

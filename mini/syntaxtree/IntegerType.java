//
// Generated by JTB 1.3.2
//

package mini.syntaxtree;

/**
 * Grammar production:
 * f0 -> "int"
 */
public class IntegerType implements Node {
   public NodeToken f0;

   public IntegerType(NodeToken n0) {
      f0 = n0;
   }

   public IntegerType() {
      f0 = new NodeToken("int");
   }

   public void accept(mini.visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(mini.visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(mini.visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(mini.visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
}


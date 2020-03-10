package symbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MClassList extends MType {
    protected  HashMap<String, MClass> classHashMap = new HashMap<String, MClass>();

    public MClassList() {
        super("ClassList");
    }

    public boolean insertClass(MClass _value) {
        String _key = _value.getName();
        if (classHashMap.containsKey(_key)) {
            return false;
        }
        classHashMap.put(_key, _value);
        return true;
    }

    public MClass getClass(String _key) {
        if (! classHashMap.containsKey(_key)) {
            return null;
        }
        return classHashMap.get(_key);
    }

    public boolean setAllExtendClass() {
        boolean _ret = true;
        Collection<MClass> classCollection = classHashMap.values();
        for (MClass x: classCollection ) {
            _ret &= x.setExtendClass(this);
        }
        return _ret;
    }

    public boolean findExtendClassLoop() {
        Queue<String> queue = new LinkedList<String>();
        int cnt = 0;

        for (MClass x: classHashMap.values()) {
            if (x.extendClassName == null) {
                queue.add(x.name);
                cnt += 1;
            }
        }
        while (! queue.isEmpty()) {
            String independentClassName = queue.poll();
            for (MClass x: classHashMap.values()) {
                if (x.extendClassName != null && x.extendClassName.equals(independentClassName)) {
                    queue.add(x.name);
                    cnt += 1;
                }
            }
        }

        return cnt != classHashMap.size();
    }

    public boolean findAllOverride() {
        for (MClass x: classHashMap.values()) {
            if (x.findOverride()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExtendAssign(String left, String right) {
//        left = right
        if ((!classHashMap.containsKey(left)) | (!classHashMap.containsKey(right))) {
            return left.equals(right);
        }

        String target = left;
        String step = right;

        while (true) {
            if (target.equals(step)) {
                return true;
            }
            else if (classHashMap.get(step).extendClassName != null) {
                step = classHashMap.get(step).extendClassName;
            }
            else {
                break;
            }
        }
        return false;

    }

}

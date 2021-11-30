import java.util.*;

class ActivationRecord{
    public String funName;
    public HashMap<String, Integer> vars = new HashMap<String, Integer>();
    public int returnAddr;
    public ActivationRecord prevRecord;
    public ActivationRecord nestingLink;
    public int returnVal;
    public int layer;

    public ActivationRecord(String name, int type){
        funName = name;
        layer = type;
    }
}

class h{

    static Stack<ActivationRecord> actRecords = new Stack<ActivationRecord>();

    public static int fFunc(int x){
        //HANDLE ACTIVATION RECORDS
        ActivationRecord rec = new ActivationRecord("fFunc", 2);
        rec.prevRecord = actRecords.peek();
        //check for nesting link rule
        if(rec.layer>rec.prevRecord.layer)
            rec.nestingLink = rec.prevRecord;
        else if (rec.layer == rec.prevRecord.layer)
            rec.nestingLink = rec.prevRecord.nestingLink;
        else{
            ActivationRecord temp = rec.prevRecord.prevRecord;
            while(rec.layer < temp.layer){
                temp = temp.prevRecord;
            }
            rec.nestingLink = temp.nestingLink;
        }
        //store variables and add to record stack
        rec.vars.put("x", x);
        rec.returnAddr = rec.prevRecord.returnVal;
        actRecords.push(rec);


        //LOGIC
        if(x == 0)
            rec.returnVal = 0;
        else
            rec.returnVal = rec.nestingLink.vars.get("z")+x+gFunc(rec.nestingLink.vars.get("w")-1);
        
        actRecords.pop();
        return rec.returnVal;

    }

    public static int gFunc(int w){
        //HANDLE ACTIVATION RECORDS
        ActivationRecord rec = new ActivationRecord("gFunc", 1);
        rec.prevRecord = actRecords.peek();
        //check for ensting record rule
        if(rec.layer>rec.prevRecord.layer)
            rec.nestingLink = rec.prevRecord;
        else if (rec.layer == rec.prevRecord.layer)
            rec.nestingLink = rec.prevRecord.nestingLink;
        else{
            ActivationRecord temp = rec.prevRecord.prevRecord;
            while(rec.layer < temp.layer){
                temp = temp.prevRecord;
            }
            rec.nestingLink = temp.nestingLink;
        }
        //store variables and add to record stack
        rec.vars.put("w", w);
        int z = rec.nestingLink.vars.get("y")+1;
        rec.vars.put("z", z);
        rec.returnAddr = rec.prevRecord.returnVal;
        actRecords.push(rec);


        //LOGIC
        if(w == 0)
            rec.returnVal = rec.nestingLink.vars.get("x");
        else
            rec.returnVal = z+fFunc(w-1);

        actRecords.pop();
        return rec.returnVal;

    }

    public static int hFunc(int x, int y){
        //HANDLE ACTIVATION RECORDS
        ActivationRecord rec = new ActivationRecord("hFunc", 0);
        rec.prevRecord = actRecords.peek();
        //store records and add to record stack
        rec.vars.put("x", x);
        rec.vars.put("y", y);
        int z = x+1;
        rec.vars.put("z", z);
        actRecords.push(rec);


        //LOGIC
        if(x == 0){
            rec.returnVal = gFunc(y);
        }
        else
            rec.returnVal = z+gFunc(hFunc(x-1, y));

        actRecords.pop();
        return rec.returnVal;
    }


    public static void main(String[] args){
        ActivationRecord initial = new ActivationRecord("main", 0);
        actRecords.push(initial);

        int ans = hFunc(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        //int ans = hFunc(3, 2);

        System.out.println("h("+args[0]+","+args[1]+")="+ans);
    }
}
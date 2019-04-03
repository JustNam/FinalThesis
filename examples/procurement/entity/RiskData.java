package procurement.entity;

import java.util.ArrayList;

public class RiskData {
    private ArrayList<Conflict> conflicts;
    private ArrayList<Risk> risks;

    public ArrayList<Conflict> getConflicts() {
        return conflicts;
    }

    public void setConflicts(ArrayList<Conflict> conflicts) {
        this.conflicts = conflicts;
    }

    public ArrayList<Risk> getRisks() {
        return risks;
    }

    public void setRisks(ArrayList<Risk> risks) {
        this.risks = risks;
    }
}

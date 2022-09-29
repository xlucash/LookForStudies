package studies.lucas.lookforstudies;

public class SubjectHelper {
    // "Matematyka","Jezyk polski","Jezyk angielski","Jezyk niemiecki","Informatyka","Fizyka","Biologia","Chemia","Geografia","Historia"
    public static String getSubject(String subject) {
        String wantedSubject;
        switch(subject) {
            case "Matematyka":
                wantedSubject="ADVANCEDMATH";
                break;
            case "Jezyk polski":
                wantedSubject="ADVANCEDPOLISH";
                break;
            case "Jezyk angielski":
                wantedSubject="ADVANCEDENGLISH";
                break;
            case "Jezyk niemiecki":
                wantedSubject="ADVANCEDGERMAN";
                break;
            case "Informatyka":
                wantedSubject="ADVANCEDINFORMATICS";
                break;
            case "Fizyka":
                wantedSubject="ADVANCEDPHYSICS";
                break;
            case "Biologia":
                wantedSubject="ADVANCEDBIOLOGY";
                break;
            case "Chemia":
                wantedSubject="ADVANCEDCHEMISTRY";
                break;
            case "Geografia":
                wantedSubject="ADVANCEDGEOGRAPHY";
                break;
            case "Historia":
                wantedSubject="ADVANCEDHISTORY";
                break;
            default:
                throw new IllegalArgumentException("Invalid subject: " + subject);
        }
        return wantedSubject;
    }

    //"ADVANCEDMATH", "ADVANCEDPOLISH", "ADVANCEDENGLISH", "ADVANCEDGERMAN", "ADVANCEDINFORMATICS", "ADVANCEDPHYSICS", "ADVANCEDBIOLOGY", "ADVANCEDCHEMISTRY", "ADVANCEDBIOLOGY", "ADVANCEDGEOGRAPHY", "ADVANCEDHISTORY"
    public static String getSubjectReverse(String subject) {
        String wantedSubject;
        switch (subject) {
            case "ADVANCEDMATH":
                wantedSubject="Matematyka";
                break;
            case "ADVANCEDPOLISH":
                wantedSubject="Jezyk polski";
                break;
            case "ADVANCEDENGLISH":
                wantedSubject="Jezyk Angielski";
                break;
            case "ADVANCEDGERMAN":
                wantedSubject="Jezyk Niemiecki";
                break;
            case "ADVANCEDINFORMATICS":
                wantedSubject="Informatyka";
                break;
            case "ADVANCEDPHYSICS":
                wantedSubject="Fizyka";
                break;
            case "ADVANCEDBIOLOGY":
                wantedSubject="Biologia";
                break;
            case "ADVANCEDCHEMISTRY":
                wantedSubject="Chemia";
                break;
            case "ADVANCEDGEOGRAPHY":
                wantedSubject="Geografia";
                break;
            case "ADVANCEDHISTORY":
                wantedSubject="Historia";
                break;
            default:
                throw new IllegalArgumentException("Invalid subject: " + subject);
        }
        return wantedSubject;
    }
}

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
}

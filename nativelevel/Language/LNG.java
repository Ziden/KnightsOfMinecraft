package nativelevel.Language;

/**
 *
 * @author Nosliw
 */
public enum LNG {
    EN("English"), 
    PT("Portuguese");
    
    private final String fullName;

    private LNG(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

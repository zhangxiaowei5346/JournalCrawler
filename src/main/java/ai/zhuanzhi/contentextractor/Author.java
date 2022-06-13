package ai.zhuanzhi.contentextractor;

public class Author {
    private String zhName, enName, znInstitution, enInstitution, email, isFirstAuthor, isContactAuthor,
            sequence, firstName, middleName, lastName, resume, instituteSuperscript;

    public Author(String zhName, String enName, String znInstitution, String enInstitution,
                  String email, String isFirstAuthor, String isContactAuthor, String sequence,
                  String firstName, String middleName, String lastName, String resume, String instituteSuperscript) {
        this.zhName = zhName;
        this.enName = enName;
        this.znInstitution = znInstitution;
        this.enInstitution = enInstitution;
        this.email = email;
        this.isFirstAuthor = isFirstAuthor;
        this.isContactAuthor = isContactAuthor;
        this.sequence = sequence;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.resume = resume;
        this.instituteSuperscript = instituteSuperscript;
    }

    @Override
    public String toString() {
        return "Author{" +
                "zhName='" + zhName + '\'' +
                ", enName='" + enName + '\'' +
                ", znInstitution='" + znInstitution + '\'' +
                ", enInstitution='" + enInstitution + '\'' +
                ", email='" + email + '\'' +
                ", isFirstAuthor='" + isFirstAuthor + '\'' +
                ", isContactAuthor='" + isContactAuthor + '\'' +
                ", sequence='" + sequence + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", resume='" + resume + '\'' +
                ", instituteSuperscript='" + instituteSuperscript + '\'' +
                '}';
    }

    public Author() {
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getZnInstitution() {
        return znInstitution;
    }

    public void setZnInstitution(String znInstitution) {
        this.znInstitution = znInstitution;
    }

    public String getEnInstitution() {
        return enInstitution;
    }

    public void setEnInstitution(String enInstitution) {
        this.enInstitution = enInstitution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsFirstAuthor() {
        return isFirstAuthor;
    }

    public void setIsFirstAuthor(String isFirstAuthor) {
        this.isFirstAuthor = isFirstAuthor;
    }

    public String getIsContactAuthor() {
        return isContactAuthor;
    }

    public void setIsContactAuthor(String isContactAuthor) {
        this.isContactAuthor = isContactAuthor;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getInstituteSuperscript() {
        return instituteSuperscript;
    }

    public void setInstituteSuperscript(String instituteSuperscript) {
        this.instituteSuperscript = instituteSuperscript;
    }
}

package capstone.hackathon.capstone.web.dto;

public class MailMessages {
    public String registrationSuccessfull="Successfully registered";
    public String teamRegistration= "Your team is successfully registered";
    public String ideaSelection="Your team has been selected for next round";
    public String ideaRejection="Your team has been eliminated from further rounds";

    public String getRegistrationSuccessfull() {
        return registrationSuccessfull;
    }

    public String getTeamRegistration() {
        return teamRegistration;
    }

    public String getIdeaSelection() {
        return ideaSelection;
    }

    public String getIdeaRejection() {
        return ideaRejection;
    }

    public MailMessages() {
        super();
    }
}

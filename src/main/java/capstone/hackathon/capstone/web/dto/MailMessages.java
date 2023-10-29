package capstone.hackathon.capstone.web.dto;

public class MailMessages {
    public String registrationSuccessfull="Successfully registered";

    public String ideaSelection="Your team has been selected for next round";
    public String ideaRejection="Your team has been eliminated from further rounds";

    public String teamRegistration = "Congratulations on successfully registering your team for the iHackathon competition! \n\nWe're thrilled to have you on board. We wish you the best of luck in the first round of the competition. Your participation is a vital part of making this event a success. If you have any questions or need further assistance, feel free to reach out to us.\n\nBest regads \nThe iHackathon Team";


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

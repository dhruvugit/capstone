package capstone.hackathon.capstone.web.dto;

public class RegisterTeamDto {
	private String teamName;
	private String teamSummary;

	@Override
	public String toString() {
		return "RegisterTeamDto{" +
				"teamName='" + teamName + '\'' +
				", teamSummary='" + teamSummary + '\'' +
				'}';
	}

	public RegisterTeamDto(String teamName, String teamSummary) {
		this.teamName = teamName;
		this.teamSummary = teamSummary;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamSummary() {
		return teamSummary;
	}

	public void setTeamSummary(String teamSummary) {
		this.teamSummary = teamSummary;
	}
	  
	  
}

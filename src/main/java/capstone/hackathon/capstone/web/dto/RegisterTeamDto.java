package capstone.hackathon.capstone.web.dto;

public class RegisterTeamDto {
	private String teamName;
    private String leaderName;
    private String leaderEmail;
	
	
	  private String member1Name; 
	  private String member1Email; 
	  private String member2Name; 
	  private String member2Email; 
	  private String member3Name; 
	  private String member3Email;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getLeaderEmail() {
		return leaderEmail;
	}
	public void setLeaderEmail(String leaderEmail) {
		this.leaderEmail = leaderEmail;
	}
	public String getMember1Name() {
		return member1Name;
	}
	public void setMember1Name(String member1Name) {
		this.member1Name = member1Name;
	}
	public String getMember1Email() {
		return member1Email;
	}
	public void setMember1Email(String member1Email) {
		this.member1Email = member1Email;
	}
	public String getMember2Name() {
		return member2Name;
	}
	public void setMember2Name(String member2Name) {
		this.member2Name = member2Name;
	}
	public String getMember2Email() {
		return member2Email;
	}
	public void setMember2Email(String member2Email) {
		this.member2Email = member2Email;
	}
	public String getMember3Name() {
		return member3Name;
	}
	public void setMember3Name(String member3Name) {
		this.member3Name = member3Name;
	}
	public String getMember3Email() {
		return member3Email;
	}
	public void setMember3Email(String member3Email) {
		this.member3Email = member3Email;
	}
	@Override
	public String toString() {
		return "RegisterTeamDto [teamName=" + teamName + ", leaderName=" + leaderName + ", leaderEmail=" + leaderEmail
				+ ", member1Name=" + member1Name + ", member1Email=" + member1Email + ", member2Name=" + member2Name
				+ ", member2Email=" + member2Email + ", member3Name=" + member3Name + ", member3Email=" + member3Email
				+ "]";
	}
	public RegisterTeamDto(String teamName, String leaderName, String leaderEmail, String member1Name,
			String member1Email, String member2Name, String member2Email, String member3Name, String member3Email) {
		super();
		this.teamName = teamName;
		this.leaderName = leaderName;
		this.leaderEmail = leaderEmail;
		this.member1Name = member1Name;
		this.member1Email = member1Email;
		this.member2Name = member2Name;
		this.member2Email = member2Email;
		this.member3Name = member3Name;
		this.member3Email = member3Email;
	}
	public RegisterTeamDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
	  
}

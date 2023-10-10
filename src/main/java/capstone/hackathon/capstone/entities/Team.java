package capstone.hackathon.capstone.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TEAM_DETAILS")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String teamName;
	private long leaderId;
	
	public Team() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Team(String teamName, long leaderId) {
		super();
		this.teamName = teamName;
		this.leaderId = leaderId;
	}
	@Override
	public String toString() {
		return "Team [id=" + id + ", teamName=" + teamName + ", leaderId=" + leaderId + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public long getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(long leaderId) {
		this.leaderId = leaderId;
	}
	
	
	
}

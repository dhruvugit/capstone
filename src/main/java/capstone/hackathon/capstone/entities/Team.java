package capstone.hackathon.capstone.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TEAM_DETAILS")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long teamId;
	private String teamName;
	private long leaderId;
	
	@OneToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name = "id", referencedColumnName = "implementationId")
	 private Implementation implementation;
	
	public Implementation getImplementation() {
		return implementation;
	}
	public void setImplementation(Implementation implementation) {
		this.implementation = implementation;
	}
	public Team() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Team [id=" + teamId + ", teamName=" + teamName + ", leaderId=" + leaderId + ", implementation="
				+ implementation + "]";
	}
	public Team(long id, String teamName, long leaderId, Implementation implementation) {
		super();
		this.teamId = id;
		this.teamName = teamName;
		this.leaderId = leaderId;
		this.implementation = implementation;
	}
	public long getId() {
		return teamId;
	}
	public void setId(long id) {
		this.teamId = id;
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

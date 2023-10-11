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
	 @JoinColumn(name = "implId", referencedColumnName = "implementation_Id")
	 private Implementation implementation;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ideaId", referencedColumnName = "id")
	private Idea idea;

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public Implementation getImplementation() {
		return implementation;
	}
	public void setImplementation(Implementation implementation) {
		this.implementation = implementation;
	}

	public Team(long teamId, String teamName, long leaderId, Implementation implementation, Idea idea) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.leaderId = leaderId;
		this.implementation = implementation;
		this.idea = idea;
	}

	public Team() {
		super();
	}

	@Override
	public String toString() {
		return "Team{" +
				"teamId=" + teamId +
				", teamName='" + teamName + '\'' +
				", leaderId=" + leaderId +
				", implementation=" + implementation +
				", idea=" + idea +
				'}';
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

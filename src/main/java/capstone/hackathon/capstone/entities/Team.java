package capstone.hackathon.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="TEAM_DETAILS",uniqueConstraints = @UniqueConstraint(columnNames = "TeamCode"))
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long teamId;
	private String teamName;
	private String teamSummary;

	public String getTeamSummary() {
		return teamSummary;
	}
	@Column(name = "teamCode")
	public String teamCode;


	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public void setTeamSummary(String teamSummary) {
		this.teamSummary = teamSummary;
	}

	private long leaderId;

	@JsonIgnore
	@OneToOne(mappedBy = "team")
	private Implementation implementation;

	@JsonIgnore
	@OneToOne(mappedBy = "team")
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

	public Team(String teamName, String teamSummary, String teamCode, long leaderId, Implementation implementation, Idea idea) {
		this.teamName = teamName;
		this.teamSummary = teamSummary;
		this.teamCode = teamCode;
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
				", teamSummary='" + teamSummary + '\'' +
				", TeamCode='" + teamCode + '\'' +
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
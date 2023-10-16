package capstone.hackathon.capstone.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TEAM_MEMBERS_TABLE")
public class TeamMembers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long teamId;
	private long memberId;
	public TeamMembers(long teamId, long memberId) {
		super();
		this.teamId = teamId;
		this.memberId = memberId;
	}

	public TeamMembers(){

	}
	@Override
	public String toString() {
		return "TeamMembers [id=" + id + ", teamId=" + teamId + ", memberId=" + memberId + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTeamId() {
		return teamId;
	}
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	
	
}

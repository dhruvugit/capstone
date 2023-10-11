package capstone.hackathon.capstone.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Table(name="implementations")
@Entity

public class Implementation {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="implementation_Id")
		private int implementationId;
		
		@Column(name="GitHubURL")
		private String gitHubURL;
		
		@Column(name="RecordingURL")
		private String recordingURL;
		
		@Column(name="PPTURL")
		private String pptURL;
		
		@Column(name="Descprition")
		private String description;
		
		@ElementCollection
		private List<Integer> score;
		
		@OneToOne(mappedBy = "implementation")
		private Team team;

		public Team getTeam() {
			return team;
		}

		public void setTeam(Team team) {
			this.team = team;
		}

		public List<Integer> getScore() {
			return score;
		}

		public void setScore(List<Integer> score) {
			this.score = score;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getImplementationId() {
			return implementationId;
		}

		public void setImplementationId(int implementationId) {
			this.implementationId = implementationId;
		}

		public String getGitHubURL() {
			return gitHubURL;
		}

		public void setGitHubURL(String gitHubURL) {
			this.gitHubURL = gitHubURL;
		}

		public String getRecordingURL() {
			return recordingURL;
		}

		public void setRecordingURL(String recordingURL) {
			this.recordingURL = recordingURL;
		}

		public String getPptURL() {
			return pptURL;
		}

		public void setPptURL(String pptURL) {
			this.pptURL = pptURL;
		}

		

		public Implementation(int implementationId, String gitHubURL, String recordingURL, String pptURL,
				String description, List<Integer> score, Team team) {
			super();
			this.implementationId = implementationId;
			this.gitHubURL = gitHubURL;
			this.recordingURL = recordingURL;
			this.pptURL = pptURL;
			this.description = description;
			this.score = score;
			this.team = team;
		}

		@Override
		public String toString() {
			return "Implementation [implementationId=" + implementationId + ", gitHubURL=" + gitHubURL
					+ ", recordingURL=" + recordingURL + ", pptURL=" + pptURL + ", description=" + description
					+ ", score=" + score + ", team=" + team + "]";
		}

		public Implementation() {
			super();
			// TODO Auto-generated constructor stub
		}
		
}

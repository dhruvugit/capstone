package capstone.hackathon.capstone.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import capstone.hackathon.capstone.entities.Implementation;


public interface IfImplementationService {
		public Implementation createImplementation(Implementation implementation);
		public List<Implementation> fetchAllImplementations();
		public Implementation fetchImplementationById(int implementationId);
		public Implementation updateImplementationById(int implementationId, Implementation implementation);
		public String deleteImplementationById(int implementationId);
		
		
		public Implementation findImplementationByTeamId(Long teamId);
	    public void updateImplementationFields(Long teamId, String newField1, String newField2,String newField3, String newField4) ;
		public void updateScoreList(Long teamId, List<Integer> scoreList);

		}

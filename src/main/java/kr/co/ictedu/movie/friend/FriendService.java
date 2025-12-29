package kr.co.ictedu.movie.friend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.movie.vo.FriendRequestVO;
import kr.co.ictedu.movie.vo.MemberVO;

@Service
public class FriendService {
	@Autowired
	private FriendRequestDao dao;
	
	public List<MemberVO> getAllExcept(String myUserid){
		return dao.selectAllExceptMe(myUserid);
	}
	public void sendRequest(String from, String to) {
		dao.sendRequest(from, to);
	}
	public List<FriendRequestVO> getPendingRequests(String userid){
		return dao.getPending(userid);
	}
	public void respond(Long id, String action) {
		dao.updateStatus(id, action.equals("accept") ? "accepted" : "rejected");
	}
	public List<MemberVO> getFreisnds(String userid){
		return dao.getfriends(userid);
	}
	public List<FriendRequestVO> getSentRequests(String userid){
		return dao.getSentRequests(userid);
	}
}

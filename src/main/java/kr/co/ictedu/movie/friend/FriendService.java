package kr.co.ictedu.movie.friend;

import java.util.List;

import kr.co.ictedu.movie.vo.FriendRequestVO;
import kr.co.ictedu.movie.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FriendService {
    @Autowired
    private FriendRequestDao dao;
    public List<MemberVO> getAllExcept(int member_num) {
        return dao.selectAllExceptMe(member_num);
    }
    
    public void sendRequest(String from, String to) {
        dao.sendRequest(from, to);
    }
    
    public List<FriendRequestVO> getPendingRequests(String receiverNickname) {
        return dao.getPending(receiverNickname);
    }
    
    public void respond(Long id, String action) {
        dao.updateStatus(id, action.equals("accept") ? "accepted" : "rejected");
    }
    
    public List<MemberVO> getFriends(String memberNickname) {
        return dao.getFriends(memberNickname);
    }
    
    public List<FriendRequestVO> getSentRequests(String memberNickname) {
        return dao.getSentRequests(memberNickname);
    }
}
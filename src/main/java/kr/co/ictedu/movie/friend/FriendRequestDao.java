package kr.co.ictedu.movie.friend;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.movie.vo.MemberVO;
import kr.co.ictedu.movie.vo.FriendRequestVO;

@Mapper
public interface FriendRequestDao {
	List<MemberVO> selectAllExceptMe(@Param("userid") String userid);
	void sendRequest(@Param("requester_id") String requestId, @Param("receiver_id") String receiverId);
	List<FriendRequestVO> getPending(@Param("userid") String userid);
	void updateStatus(@Param("id") Long id, @Param("status") String status);
	List<MemberVO> getfriends(@Param("userid") String userid);
	List<FriendRequestVO> getSentRequests(@Param("userid") String userid);
	int checkRequestExists(@Param("requester_id") String requesterId, @Param("receiver_id") String receiverId);
	void resendRequest(@Param("requester_id") String requesterId, @Param("receiver_id") String receiverId);
}

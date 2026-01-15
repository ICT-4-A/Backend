package kr.co.ictedu.movie.friend;

import kr.co.ictedu.movie.vo.FriendRequestVO;
import kr.co.ictedu.movie.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendRequestDao {

    List<MemberVO> selectAllExceptMe(@Param("member_num") int userid);
    
    // 친구 신청
    void sendRequest(@Param("requester_id") String requesterId,
                     @Param("receiver_id") String receiverId);

    // 나에게 온 요청 목록을 VO에 담아서 List로 반환
    List<FriendRequestVO> getPending(@Param("receiver_id") String receiverNickname);
    
    // 친구 요청 수락/거절
    void updateStatus(@Param("id") Long id, @Param("status") String status);
    
    // 나의 친구 목록을 VO에 담아서 List로 반환
    List<MemberVO> getFriends(@Param("member_nickname") String memberNickname);
    
    // 내가 보낸 친구 요청 목록 조회
    List<FriendRequestVO> getSentRequests(@Param("member_nickname") String memberNickname);
    
    //친구 요청 존재 여부 확인 - 재요청을 할 때 구분용
    int checkRequestExists(@Param("requester_id") String requesterId,
                           @Param("receiver_id") String receiverId);
    
    // 만약 기존 요청이 있는 경우 상태를 다시 pending으로 갱신
    void resendRequest(@Param("requester_id") String requesterId,
                       @Param("receiver_id") String receiverId);

}

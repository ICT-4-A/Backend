package kr.co.ictedu.movie.friend;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.movie.vo.FriendRequestVO;
import kr.co.ictedu.movie.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;
    //GET => /apxi/friends/members : Login이후에 호출됨 , 쿼리에 따라서 본인은 제외하고 회원 목록
    
    @GetMapping("/members")
    public List<MemberVO> getAllMembers(HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        return friendService.getAvailableMembers(
            loginMember.getMember_num(),
            loginMember.getNickname()
        );
    }
    
    // 친구 요청 전송
    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@RequestBody Map<String, String> body, HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        String receiverId = body.get("receiver_id");
        friendService.sendRequest(loginMember.getNickname(), receiverId);
        return ResponseEntity.ok("친구 요청 완료");
    }
    
    // 받은 친구 요청
    @GetMapping("/incoming")
    public List<FriendRequestVO> getIncomingRequests(HttpSession session) {
    	// 세션에서 로그인한 유저 정보를 가져옴 (loginMember)
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            throw new RuntimeException("세션 만료 or 로그인 필요");
        }
        return friendService.getPendingRequests(loginMember.getNickname());
    }
    
    // 친구 요청 수락/거절
    @PostMapping("/respond")
    public ResponseEntity<?> respondToRequest(@RequestBody Map<String, String> body, HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            throw new RuntimeException("세션 만료 or 로그인 필요");
        }
        Long requestId = Long.parseLong(body.get("id"));
        String action = body.get("action"); // "accept" or "reject"
        friendService.respond(requestId, action, loginMember.getNickname());
        return ResponseEntity.ok("처리 완료");
    }

    // 나의 친구 목록
    @GetMapping("/myfriends")
    public List<MemberVO> myFriends(HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        
        // 세션 확인용 로그
        if (loginMember == null) {
            System.out.println("loginMember is null!");
            throw new RuntimeException("세션 만료 or 로그인 필요");
        } else {
            System.out.println("로그인 유저: " + loginMember.getNickname());
        }
        return friendService.getFriends(loginMember.getNickname());
    }

    // 내가 보낸 친구 요청 리스트 반환
    @GetMapping("/outgoing")
    public List<FriendRequestVO> getOutgoingRequests(HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        return friendService.getSentRequests(loginMember.getNickname());
    }
}
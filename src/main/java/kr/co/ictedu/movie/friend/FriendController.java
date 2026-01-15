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
        System.out.println("Num:"+loginMember.getMember_num());
        return friendService.getAllExcept(loginMember.getMember_num());
    }
    // 친구 요청 전송
    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@RequestBody Map<String, String> body, HttpSession session) {
        System.out.println("=== 전체 body: " + body);  // 이거 추가!
        System.out.println("body.keys: " + body.keySet());  // 이거 추가!
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        String receiverId = body.get("receiver_id");
        System.out.println("=== DEBUG ===");
        System.out.println("loginMember nickname: " + loginMember.getNickname());
        System.out.println("receiverId from body: " + receiverId);
        System.out.println("=============");
        friendService.sendRequest(loginMember.getNickname(), receiverId);
        return ResponseEntity.ok("친구 요청 완료");
    }
    // 받은 친구 요청
    @GetMapping("/incoming")
    public List<FriendRequestVO> getIncomingRequests(HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        return friendService.getPendingRequests(loginMember.getNickname());
    }
    // 친구 요청 수락/거절
    @PostMapping("/respond")
    public ResponseEntity<?> respondToRequest(@RequestBody Map<String, String> body) {
        Long requestId = Long.parseLong(body.get("id"));
        String action = body.get("action"); // "accept" or "reject"
        friendService.respond(requestId, action);
        return ResponseEntity.ok("처리 완료");
    }
    // 나의 친구 목록
    @GetMapping("/myfriends")
    public List<MemberVO> myFriends(HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        return friendService.getFriends(loginMember.getNickname());
    }
    // 보낸 친구 요청 리스트 반환
    @GetMapping("/outgoing")
    public List<FriendRequestVO> getOutgoingRequests(HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        return friendService.getSentRequests(loginMember.getNickname());
    }
}
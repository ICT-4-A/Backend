package kr.co.ictedu.movie.friend;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.movie.vo.FriendRequestVO;
import kr.co.ictedu.movie.vo.MemberVO;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
	@Autowired
	private FriendService friendService;
	@GetMapping("/mambers")
	public List<MemberVO> getAllMembers(HttpSession session){
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return friendService.getAllExcept(loginMember.getUserid());
	}
	@PostMapping("/request")
	public ResponseEntity<?> sendFriendRequest(@RequestBody Map<String, String> body, HttpSession session){
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		String receiverId = body.get("receiverId");
		friendService.sendRequest(loginMember.getUserid(), receiverId);
		return ResponseEntity.ok("친구 요청 완료");
	}
	@GetMapping("/incoming")
	public List<FriendRequestVO> getIncomingRequests(HttpSession session){
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return friendService.getPendingRequests(loginMember.getUserid());
	}
	@PostMapping("/respond")
	public ResponseEntity<?> respondToRequest(@RequestBody Map<String, String> body){
		Long requestId = Long.parseLong(body.get("id"));
		String action = body.get("action");
		friendService.respond(requestId, action);
		return ResponseEntity.ok("처리 완료");
	}
	@GetMapping("/myfriends")
	public List<MemberVO> myFriends(HttpSession session){
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return friendService.getFreisnds(loginMember.getUserid());
	}
	@GetMapping("/outgoing")
	public List<FriendRequestVO> getOutgoingRequests(HttpSession session){
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return friendService.getSentRequests(loginMember.getUserid());
	}
}

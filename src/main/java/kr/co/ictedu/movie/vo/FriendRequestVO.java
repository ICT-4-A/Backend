package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("frvo")
public class FriendRequestVO {
	private int id;
	private String request_id;
	private String receiver_id;
	private String status;
	private String request_date;
}

package com.gyojincompay.MVCBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.gyojincompay.MVCBoard.dto.BDto;
import com.gyojincompay.MVCBoard.util.Constant;

public class BDao {
	
	// JDBC를 쓰면 BDao의 코드 양이 얼마나 줄어드는지 봐!
	// 와. 진짜 많이 줄어드네. 근데 SQL문 4종류가 각각 다른 문법을 쓴다. 그것만 조심해, ㅋ.
	
	DataSource dataSource;
	JdbcTemplate template; // 새로 들어온 놈. 잘 부탁한다.
	
	public BDao() {
		super();

		this.template = Constant.template;
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public ArrayList<BDto> list() {
		
		String query = "select * from mvc_board order by bgroup desc, bstep asc";
		
		return (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper(BDto.class));
		
//		ArrayList<BDto> dtos = new ArrayList<BDto>();
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "select * from mvc_board order by bgroup desc, bstep asc";
//			pstmt = conn.prepareStatement(query);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				
//				 int bId = rs.getInt("bid");
//				 String bName = rs.getString("bname");
//				 String bTitle = rs.getString("btitle");
//				 String bContent = rs.getString("bcontent");
//				 Timestamp bDate = rs.getTimestamp("bdate");
//				 int bHit = rs.getInt("bhit");
//				 int bGroup = rs.getInt("bgroup");
//				 int bStep = rs.getInt("bstep");
//				 int bIndent = rs.getInt("bindent");
//				 
//				 BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//				 dtos.add(dto);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return dtos;
	}
	
	
	// 들어가는 변수들이 final String형이어야 한다. (중간에 변수를 바꾸는 걸 막기 위해서다.)
	public void write(final String bname, final String btitle, final String bcontent) {
		
		this.template.update(new PreparedStatementCreator() { // 어 write는 UPDATE문 써야 해서 문법 어렵네. 근데 코드가 정말 많이 주네.
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

				String query = "INSERT INTO mvc_board(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent) VALUES (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
				PreparedStatement pstmt = con.prepareStatement(query);
				
				pstmt.setString(1, bname);
				pstmt.setString(2, btitle);
				pstmt.setString(3, bcontent);
				
				return pstmt;
			}
		});
			
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;		
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "INSERT INTO mvc_board(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent) VALUES (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
//			pstmt = conn.prepareStatement(query);
//			
//			pstmt.setString(1, bname);
//			pstmt.setString(2, btitle);
//			pstmt.setString(3, bcontent);
//			
//			pstmt.executeUpdate();//데이터 삽입에 성공하면 1이 반환
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {				
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}		
		
	}
	
	public BDto content_view(String cid) {
		
		upHit(cid);//조회수 계산 함수
		
		String query = "select * from mvc_board where bid = " + cid; // 헐 굳이 ?에 집어넣지 않고 이래도... "된다"
		
		return template.queryForObject(query, new BeanPropertyRowMapper(BDto.class));
		
//		BDto dto = null;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "select * from mvc_board where bid = ?";
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, cid);
//			//pstmt.setInt(1, Integer.parseInt(cid)); //bid 필드 값이 number인 경우 
//			
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				
//				 int bId = rs.getInt("bid");
//				 String bName = rs.getString("bname");
//				 String bTitle = rs.getString("btitle");
//				 String bContent = rs.getString("bcontent");
//				 Timestamp bDate = rs.getTimestamp("bdate");
//				 int bHit = rs.getInt("bhit");
//				 int bGroup = rs.getInt("bgroup");
//				 int bStep = rs.getInt("bstep");
//				 int bIndent = rs.getInt("bindent");
//				 
//				 dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//				 
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		
//		return dto; 
	}
	
	public void modify(final String bid, final String bname, final String btitle, final String bcontent) {
		
		String query = "UPDATE mvc_board SET bname=?, btitle=?, bcontent=? WHERE bid=?";
		
		// modify() 얘도 DB에 글을 쓰긴 한다. 그렇지만 write()과는 달리 이미 있는 글을 업데이트하는 거니까,
		// PreparedStatementCreator() 아니고 PreparedStatementSetter()라는 다른 구문을 쓴다. 
		this.template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				
				pstmt.setString(1, bname);
				pstmt.setString(2, btitle);
				pstmt.setString(3, bcontent);
				pstmt.setString(4, bid);
				
			}
		});
		
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;		
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "UPDATE mvc_board SET bname=?, btitle=?, bcontent=? WHERE bid=?";
//			pstmt = conn.prepareStatement(query);
//			
//			pstmt.setString(1, bname);
//			pstmt.setString(2, btitle);
//			pstmt.setString(3, bcontent);
//			pstmt.setString(4, bid);
//			
//			pstmt.executeUpdate();//데이터 삽입에 성공하면 1이 반환
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {				
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}		
//		
	}

	public void delete(final String bid) {

		String query = "delete from mvc_board where bid=?";
		
		this.template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bid);
			}
		});
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;		
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "delete from mvc_board where bid=?";
//			pstmt = conn.prepareStatement(query);			
//			
//			pstmt.setString(1, bid);
//			
//			pstmt.executeUpdate();//데이터 삽입에 성공하면 1이 반환
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {				
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}	
		
	}
	
	public void upHit(final String bid) {
		
		String query = "UPDATE mvc_board SET bhit=bhit+1 WHERE bid=?";
		
		this.template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, bid);
			}
		});
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;		
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "UPDATE mvc_board SET bhit=bhit+1 WHERE bid=?";
//			pstmt = conn.prepareStatement(query);			
//			
//			pstmt.setString(1, bid);
//			
//			pstmt.executeUpdate();//데이터 삽입에 성공하면 1이 반환
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {				
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}		
		
	}

	public BDto reply_view(String cid) {
		
		String query = "select * from mvc_board where bid =" + cid;
		
		return template.queryForObject(query, new BeanPropertyRowMapper(BDto.class));
		
//		BDto dto = null;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "select * from mvc_board where bid = ?";
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, cid);
//			//pstmt.setInt(1, Integer.parseInt(cid)); //bid 필드 값이 number인 경우 
//			
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				
//				 int bId = rs.getInt("bid");
//				 String bName = rs.getString("bname");
//				 String bTitle = rs.getString("btitle");
//				 String bContent = rs.getString("bcontent");
//				 Timestamp bDate = rs.getTimestamp("bdate");
//				 int bHit = rs.getInt("bhit");
//				 int bGroup = rs.getInt("bgroup");
//				 int bStep = rs.getInt("bstep");
//				 int bIndent = rs.getInt("bindent");
//				 
//				 dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
//				 
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}		
//		
//		return dto;
	}

	public void reply(final String bid, final String bname, final String btitle, final String bcontent, final String bgroup, final String bstep,
			final String bindent) {

		reply_sort(bgroup, bstep);
		
		this.template.update(new PreparedStatementCreator() { // 어 write는 UPDATE문 써야 해서 문법 어렵네. 근데 코드가 정말 많이 주네.
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

				String query = "INSERT INTO mvc_board(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent) VALUES (mvc_board_seq.nextval, ?, ?, ?, 0, ?, ?, ?)";
				PreparedStatement pstmt = con.prepareStatement(query);
				
				pstmt.setString(1, bname);
				pstmt.setString(2, btitle);
				pstmt.setString(3, bcontent);
				pstmt.setInt(4, Integer.parseInt(bgroup));
				pstmt.setInt(5, Integer.parseInt(bstep)+1); // 댓글은 원글보다 한 칸 더 들어가 있어야 하니까, ㅋ
				pstmt.setInt(6, Integer.parseInt(bindent)+1);
				
				return pstmt;
			}
		});
		
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;		
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "INSERT INTO mvc_board(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent) VALUES (mvc_board_seq.nextval, ?, ?, ?, 0, ?, ?, ?)";
//			pstmt = conn.prepareStatement(query);
//			
//			pstmt.setString(1, bname);
//			pstmt.setString(2, btitle);
//			pstmt.setString(3, bcontent);
//			pstmt.setInt(4, Integer.parseInt(bgroup));
//			pstmt.setInt(5, Integer.parseInt(bstep)+1); // 댓글은 원글보다 한 칸 더 들어가 있어야 하니까, ㅋ
//			pstmt.setInt(6, Integer.parseInt(bindent)+1);
//			
//			pstmt.executeUpdate();//데이터 삽입에 성공하면 1이 반환
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {				
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}	
//		
	}
	
	
	// 댓글 모양 잡아 주는 놈. 원리 이해 못 하겠으면 이 함수 껐다 켰다 하면서 차이 봐라. (어 원리 이해 못 하겠어...)
	public void reply_sort(final String cgroup, final String cstep) {
		
		String query = "UPDATE mvc_board SET bstep=bstep+1 WHERE bgroup=? and bstep>?";
		
		this.template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, cgroup);
				pstmt.setString(2, cstep);
			}
		});
		
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;		
//		
//		try {
//			conn = dataSource.getConnection();
//			String query = "UPDATE mvc_board SET bstep=bstep+1 WHERE bgroup=? and bstep>?";
//			pstmt = conn.prepareStatement(query);			
//			
//			pstmt.setString(1, cgroup);
//			pstmt.setString(2, cstep);
//			
//			pstmt.executeUpdate();//데이터 삽입에 성공하면 1이 반환
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {				
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}	
	}
	
	
}






























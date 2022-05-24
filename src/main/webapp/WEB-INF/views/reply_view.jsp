<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 댓글 쓰기</title>
</head>
<body>
	<h2>자유게시판 댓글 쓰기</h2>
	<hr>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<form action="reply">
			<input type="hidden" name="bgroup" value="${reply_view.bgroup}">
			<input type="hidden" name="bstep" value="${reply_view.bstep}">
			<input type="hidden" name="bindent" value="${reply_view.bindent}">
			<tr>
				<td>글번호</td>
				<td><input type="text" name="bid" size="60" value="${reply_view.bid }" readonly></td>
			</tr>
			<tr>
				<td>이 름</td>
				<td><input type="text" name="bname" size="60" value="${reply_view.bname }"></td>
			</tr>	
			<tr>
				<td>글제목</td>
				<td><input type="text" name="btitle" size="60" value="[답변] ${reply_view.btitle }"></td>
			</tr>
			<tr>
				<td>글내용</td>
				<td><textarea name="bcontent" rows="10" cols="45">${reply_view.bcontent }
				
				
				
				</textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="submit" value="댓글입력">&nbsp;&nbsp;				
					<a href="list">글목록보기</a>&nbsp;&nbsp;		
				</td>
			</tr>		
		</form>	
	</table>
</body>
</html>
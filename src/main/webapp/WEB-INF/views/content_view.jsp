<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 글보기</title>
</head>
<body>
	<h2>글내용 보기</h2>
	<hr>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>글번호</td>
			<td>${content.bid }</td>
		</tr>	
		<tr>
			<td>조회수</td>
			<td>${content.bhit }</td>
		</tr>
		<tr>
			<td>글쓴이</td>
			<td>${content.bname }</td>
		</tr>
		<tr>
			<td>글제목</td>
			<td>${content.btitle }</td>
		</tr>
		<tr>
			<td>내 용</td>
			<td>${content.bcontent }</td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<a href="reply_view?bid=${content.bid }">답변</a>&nbsp;&nbsp;
				<a href="modify_view?bid=${content.bid }">수정</a>&nbsp;&nbsp;
				<a href="delete?bid=${content.bid }">삭제</a>&nbsp;&nbsp;
				<a href="list">글목록보기</a>
			</td>
		</tr>
	</table>
</body>
</html>
package com.gyojincompay.MVCBoard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.gyojincompay.MVCBoard.dao.BDao;

public class BReplayCommand implements BCommand {

	@Override
	public void excute(Model model) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bid = request.getParameter("bid");
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		String bgroup = request.getParameter("bgroup");
		String bstep = request.getParameter("bstep");
		String bindent = request.getParameter("bindent");
		
		
		BDao dao = new BDao(); 
		dao.reply(bid, bname, btitle, bcontent, bgroup, bstep, bindent);	

	}

}

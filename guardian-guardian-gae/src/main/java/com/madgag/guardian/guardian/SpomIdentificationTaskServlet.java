package com.madgag.guardian.guardian;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.madgag.guardian.guardian.spom.detection.SpomIdentifier;

@SuppressWarnings("serial")
@Singleton
public class SpomIdentificationTaskServlet extends HttpServlet {

	private final SpomIdentifier spomIdentifier;
	
	@Inject
	public SpomIdentificationTaskServlet(SpomIdentifier spomIdentifier) {
		this.spomIdentifier = spomIdentifier;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String targetId=req.getParameter("targetId");
		List<String> possibleSpomIds= asList(req.getParameter("possibleSpomIds").split(","));
		
//		SpomReport spomReport = spomIdentifier.identifySpomsFor(targetId, possibleSpomIds);
//		resp.getWriter().write(spomReport.toString());
//		
	}

}

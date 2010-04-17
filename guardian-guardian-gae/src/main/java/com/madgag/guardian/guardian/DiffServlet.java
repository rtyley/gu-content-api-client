package com.madgag.guardian.guardian;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.madgag.guardian.contentapi.ContentApiClient;
import com.madgag.guardian.contentapi.jaxb.Content;

@SuppressWarnings("serial")
@Singleton
public class DiffServlet extends HttpServlet {
	
	private final ContentApiClient apiClient;
	
	@Inject
	public DiffServlet(ContentApiClient apiClient) {
		this.apiClient = apiClient;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String left=req.getParameter("left");
		String right=req.getParameter("right");
		Content leftContent = contentFor(left), rightContent= contentFor(right);
		req.setAttribute("left", leftContent);
		req.setAttribute("right", rightContent);
		req.getRequestDispatcher("/WEB-INF/diff.jsp").forward(req, res); 

	}

	private Content contentFor(String id) {
		return apiClient.loadPageWith(id).showFields("body").showTags("all").execute().content;
	}
}

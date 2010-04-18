package com.madgag.guardian.guardian;

import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.List;

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
		System.out.println(leftContent);
		List<DiffedField> diffs=newArrayList();
		diffs.add(new DiffedField(leftContent.webTitle, "", rightContent.webTitle));
		diffs.add(new DiffedField(leftContent.webUrl, "", rightContent.webUrl));
		diffs.add(new DiffedField(leftContent.getField("body"), "", rightContent.getField("body")));
		req.setAttribute("diffs", diffs);
		req.getRequestDispatcher("/WEB-INF/diff.jsp").forward(req, res); 

	}

	private Content contentFor(String id) {
		return apiClient.loadPageWith(id).showFields("body").showTags("all").execute().content;
	}
	
	public static class DiffedField {
		private final Object left;
		private final String diff;
		private final Object right;

		public DiffedField(Object left, String diff, Object right) {
			this.left = left;
			this.diff = diff;
			this.right = right;
		}
		
		public Object getLeft() {
			return left;
		}
		
		public String getDiff() {
			return diff;
		}
		
		public Object getRight() {
			return right;
		}
	}
}

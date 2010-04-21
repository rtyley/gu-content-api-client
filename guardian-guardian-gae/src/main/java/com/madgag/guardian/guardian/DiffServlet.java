package com.madgag.guardian.guardian;

import static com.google.common.collect.Lists.newArrayList;
import static com.madgag.guardian.guardian.spom.detection.NormalisedArticle.removeTags;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.madgag.guardian.contentapi.ContentApiClient;
import com.madgag.guardian.contentapi.jaxb.Content;

@SuppressWarnings("serial")
@Singleton
public class DiffServlet extends HttpServlet {
	private final static PeriodFormatter PERIOD_FORMAT = getPeriodFormat();
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
		Map<String,Content> c=contentFor(left,right);
		if (c.size()!=2) {
			throw new IllegalArgumentException("Only found "+c.keySet());
		}
		Content leftContent = c.get(left), rightContent=c.get(right);
		
		req.setAttribute("left", leftContent);
		req.setAttribute("right", rightContent);
		List<DiffedField> diffs=newArrayList();
		diffs.add(new DiffedField("Title",leftContent.webTitle, "", rightContent.webTitle));
		diffs.add(new DiffedField("Url",leftContent.webUrl, "", rightContent.webUrl));
		Period period = new Period(leftContent.webPublicationDate,rightContent.webPublicationDate);
		diffs.add(new DiffedField("Web-Pub Date",leftContent.webPublicationDate, period.toString(PERIOD_FORMAT), rightContent.webPublicationDate));
		
		
		diff_match_patch diffMatchPatch = new diff_match_patch();
		LinkedList<Diff> foo = diffMatchPatch.diff_main(removeTags( leftContent.getField("body")), removeTags(rightContent.getField("body")));
		diffMatchPatch.diff_cleanupSemantic(foo);
		
		diffs.add(new DiffedField("Body",leftContent.getField("body"), diff_prettyHtml(foo), rightContent.getField("body")));
		req.setAttribute("diffs", diffs);
		req.getRequestDispatcher("/WEB-INF/diff.jsp").forward(req, res); 

	}

	private Map<String,Content> contentFor(String... ids) {
		return apiClient.search().withIds(ids).showFields("body").showTags("all").execute().getContentById();
	}
	
	
	
	  public String diff_prettyHtml(LinkedList<Diff> diffs) {
		    StringBuilder html = new StringBuilder();
		    int i = 0;
		    for (Diff aDiff : diffs) {
		      String text = aDiff.text.replace("&", "&amp;").replace("<", "&lt;")
		          .replace(">", "&gt;").replace("\n", "&para;<BR>");
		      switch (aDiff.operation) {
		      case INSERT:
		        html.append("<ins>").append(text).append("</ins>");
		        break;
		      case DELETE:
		        html.append("<del>").append(text).append("</del>");
		        break;
		      case EQUAL:
		        html.append("<span>").append(text).append("</span>");
		        break;
		      }
		      if (aDiff.operation != Operation.DELETE) {
		        i += aDiff.text.length();
		      }
		    }
		    return html.toString();
		  }
	
	private static PeriodFormatter getPeriodFormat() {
        return new PeriodFormatterBuilder()
                .appendYears()
                .appendSuffix(" year", " years")
                .appendSeparator(", ")
                .appendMonths()
                .appendSuffix(" month", " months")
                .appendSeparator(", ")
                .appendDays()
                .appendSuffix(" day", " days")
                .appendSeparator(", ")
                .appendHours()
                .appendSuffix(" hour", " hours")
                .appendSeparator(", ")
                .appendMinutes()
                .appendSuffix(" minute", " minutes")
                .appendSeparator(", ")
                .appendSeconds()
                .appendSuffix(" second", " seconds")
                .appendSeparator(", ")
                .appendMillis()
                .appendSuffix(" ms")
                .toFormatter();
    }
	
	public static class DiffedField {
		private final Object left;
		private final String diff;
		private final Object right;
		private final String fieldName;

		public DiffedField(String fieldName,Object left, String diff, Object right) {
			this.fieldName = fieldName;
			this.left = left;
			this.diff = diff;
			this.right = right;
		}
		
		public String getFieldName() {
			return fieldName;
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

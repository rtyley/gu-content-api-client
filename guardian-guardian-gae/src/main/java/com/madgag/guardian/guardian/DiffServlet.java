package com.madgag.guardian.guardian;

import static com.google.common.collect.Lists.newArrayList;
import static com.madgag.guardian.guardian.spom.detection.NormalisedArticle.removeTags;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.madgag.guardian.contentapi.ContentApiClient;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

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
		Content leftContent = contentFor(left), rightContent= contentFor(right);
		req.setAttribute("left", leftContent);
		req.setAttribute("right", rightContent);
		System.out.println(leftContent);
		List<DiffedField> diffs=newArrayList();
		diffs.add(new DiffedField(leftContent.webTitle, "", rightContent.webTitle));
		diffs.add(new DiffedField(leftContent.webUrl, "", rightContent.webUrl));
		Period period = new Period(leftContent.webPublicationDate,rightContent.webPublicationDate);
		diffs.add(new DiffedField(leftContent.webPublicationDate, period.toString(PERIOD_FORMAT), rightContent.webPublicationDate));
		
		
		diff_match_patch diffMatchPatch = new diff_match_patch();
		LinkedList<Diff> foo = diffMatchPatch.diff_main(removeTags( leftContent.getField("body")), removeTags(rightContent.getField("body")));
		diffMatchPatch.diff_prettyHtml(foo);
		
		diffs.add(new DiffedField(leftContent.getField("body"), diffMatchPatch.diff_prettyHtml(foo), rightContent.getField("body")));
		req.setAttribute("diffs", diffs);
		req.getRequestDispatcher("/WEB-INF/diff.jsp").forward(req, res); 

	}

	private Content contentFor(String id) {
		return apiClient.loadPageWith(id).showFields("body").showTags("all").execute().content;
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

package com.madgag.spom.util;

import static java.lang.Math.max;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matcher;
import org.junit.Test;



public class LevenshteinWithDistanceThresholdTest {
	
	LevenshteinWithDistanceThreshold levenshteinWithDistanceThreshold = new LevenshteinWithDistanceThreshold();
	
	@Test
	public void shouldGetFreakOn() {
		assertThatNewImplIsGoodFor("sfsfdsewuregjlkjsd3kr23432432","sfsfdsadsadsewuregjlkj21313sd3kr23432432");
		assertThatNewImplIsGoodFor("","");
		assertThatNewImplIsGoodFor("","1");
		assertThatNewImplIsGoodFor("1","1");
		assertThatNewImplIsGoodFor("1","2");
		assertThatNewImplIsGoodFor("1","12");
		assertThatNewImplIsGoodFor("111","222");
		assertThatNewImplIsGoodFor("1111","1112");
		assertThatNewImplIsGoodFor("123","456");
		assertThatNewImplIsGoodFor("123","321");
		assertThatNewImplIsGoodFor("123","123456");
		assertThatNewImplIsGoodFor("  1  "," 12  ");
		assertThatNewImplIsGoodFor("qwerrew","ads");
		assertThatNewImplIsGoodFor("q3werrew","qwerrew");
		assertThatNewImplIsGoodFor("qwrew","qwerty");
	}
	
	private void assertThatNewImplIsGoodFor(String s1,String s2) {
		int trueCost=StringUtils.getLevenshteinDistance(s1, s2);
		
		assertThatNewImplIsGoodFor( s1, s2, trueCost+1);
		
		for (int threshold=max(s1.length(), s2.length())+2;threshold>=1;--threshold) {
			assertThatNewImplIsGoodFor( s1, s2, threshold);
			assertThatNewImplIsGoodFor( s2, s1, threshold);
		}
		assertThatNewImplIsGoodFor( s1, s2, Integer.MAX_VALUE);
	}
	
	@SuppressWarnings("unchecked")
	private void assertThatNewImplIsGoodFor(String s1,String s2, int threshold) {
		int trueCost=StringUtils.getLevenshteinDistance(s1, s2);
		int distCalculatedByNewImpl=levenshteinWithDistanceThreshold.get(s1, s2,threshold);

		Matcher<Integer> expectedResult = (trueCost < threshold)?equalTo(trueCost):anyOf(equalTo(trueCost),equalTo(Integer.MAX_VALUE));
		
		if (!expectedResult.matches(distCalculatedByNewImpl)) {
			levenshteinWithDistanceThreshold.get(s1, s2, threshold);
			assertThat("With threshold "+threshold,distCalculatedByNewImpl,is(expectedResult));
		}
	}
	
	
	@Test
	public void simplePerfTest() throws Exception {
		String s1="The artist himself is wandering the halls - there are various things he has to do to 'maintain' the work, including telling his athletes to run faster (slacking is emphatically not allowed). At the moment, Creed tells us, he is desperate for new recruits - finding the right runners for the task is not easy. 'They need to be able to sprint fast and to be able to keep it up,' he says (each one has to undergo a heart test before they are taken on). 'The best ones keep their speed up throughout the half-hour.' Like Nathan, our rugby player, who is Creed's favourite. 'Super fast,' he says. 'He's the star in my mind."
			,s2="Edwards is loving it. He has already picked his favourite: a tall chap with jet-black ponytail who seems to glide along on the balls of his feet. 'It's smooth, it's graceful, and there's a nice coordination to his running,' says Edwards. 'It's probably the most aesthetically pleasing because it's the most harmonious.' Art criticism, it turns out, is not as hard as it looks. But on to the more important question: what does it all mean? For Creed, running is the ultimate sign of life - because it's the absolute opposite of stillness. Edwards recalls some research he once did into the ancient Olympics";
		int n=1001;
		int total=0;
		
		long startTime,endTime;
		int trueCost=StringUtils.getLevenshteinDistance(s1, s2);
		
		startTime=currentTimeMillis();
		for (int i=0;i<n;++i) {
			total+=StringUtils.getLevenshteinDistance(s1, s2);
		}
		endTime=currentTimeMillis();
		System.out.println("Standard : "+(endTime-startTime));
		
		startTime=System.currentTimeMillis();
		for (int i=0;i<n;++i) {
			int blah = levenshteinWithDistanceThreshold.get(s1, s2, s1.length()/4);
			//assertThat(blah, equalTo(trueCost));
			total+=blah;
		}
		endTime=currentTimeMillis();
		System.out.println("Thresh : "+(endTime-startTime));
	}
}

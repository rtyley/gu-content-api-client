package com.madgag.guardian.guardian.spom.detection;
import com.madgag.guardian.guardian.NormalisedArticleProvider;
import com.madgag.text.util.LevenshteinWithDistanceThreshold;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SpomIdentifierTest {

	@Mock SpomMatchScorer spomMatchScorer;
	@Mock SpomDetectionReporter spomDetectionReporter;
	NormalisedArticleProvider articleProvider;
	private SpomIdentifier spomIdentifier;
	private NormalisedArticle preferredMaster, someMonkey, someOtherMonkey, anArticleWhichLooksVeryLikeTheMaster;

	@Before
	public void setUp() {
		preferredMaster = anArticleWhichLooksVeryLikeTheMaster = new NormalisedArticle("preferredMaster",null,null, "", null, null);
		someMonkey = new NormalisedArticle("someMonkey",null,null, "", null, null);
		someOtherMonkey = new NormalisedArticle("someOtherMonkey",null,null, "", null, null);
		anArticleWhichLooksVeryLikeTheMaster = new NormalisedArticle("anArticleWhichLooksVeryLikeTheMaster",null,null, "", null, null);
		articleProvider=new StubArticleProvider(someMonkey,someOtherMonkey,anArticleWhichLooksVeryLikeTheMaster);
		spomIdentifier = new SpomIdentifier(spomMatchScorer, articleProvider, spomDetectionReporter);

		when(spomMatchScorer.getThresholdFor(any(NormalisedArticle.class))).thenReturn(0.1f);
		when(spomMatchScorer.getMatchScore(eq(preferredMaster),eq(someMonkey), anyInt())).thenReturn(0.666f);
		when(spomMatchScorer.getMatchScore(eq(preferredMaster),eq(someOtherMonkey), anyInt())).thenReturn(0.667f);
		when(spomMatchScorer.getMatchScore(eq(preferredMaster),eq(anArticleWhichLooksVeryLikeTheMaster), anyInt())).thenReturn(0.03f);
	}
	
	@Test
	public void shouldNotDetectAHeavilyQuotingArticleAsSpom() throws Exception {
		String spomArticleBodyString = "<p>In the aftermath of Labour's third successive defeat at the 1959 election, a famous pamphlet asked the question: \"Must Labour lose?\" Today, the temptation is similar fatalism. We must not yield to it. We need to remember that there is little real sense among the public — or even among Tory MPs — of what the Conservatives stand for, or what they would do in power.</p><p>The odds are against us, no question. But I still believe we can win the next election. I agree with Jack Straw that we don't need a summer of introspection. The starting point is not debating personalities but winning the argument about our record, our vision for the future and how we achieve it. </p><p>When people hear exaggerated claims, either about failure or success, they switch off. That is why politicians across all parties fail to connect. <br/>To get our message across, we must be more humble about our shortcomings but more compelling about our achievements.</p><p>With hindsight, we should have got on with reforming the NHS sooner. We needed better planning for how to win the peace in Iraq, not just win the war. We should have devolved more power away from Whitehall and Westminster. We needed a clearer drive towards becoming a low-carbon, energy-efficient economy, not just to tackle climate change but to cut energy bills.</p><p>But 10 years of rising prosperity, a health service brought back from the brink, and social norms around women's and minority rights transformed, have not come about by accident. After all, the Tories opposed almost all the measures that have made a difference — from the windfall tax on privatised utilities to family-friendly working.</p><p>Now what are they offering? The Tories say society is broken. By what measure? Rising crime? No, crime has fallen more in the past 10 years than at any time in the past century. Knife crime and gun crime are serious problems. But since targeting the spike in gun crime, it has been cut by 13% in a year, and we have to do the same with knife crime.</p><p>What about the social breakdown that causes crime? More single parents dependent on the state? No, employment has risen sharply for lone parents because the state has funded childcare and made work pay. Falling school standards? No, they are rising. More asylum seekers? No, we said we would reform the system and slash the numbers, and we did.</p><p>The Tories overclaim for what they are against because they don't know what they are for. I disagreed with Margaret Thatcher, but at least it was clear what she stood for. She sat uncomfortably within the Tory party because she was a radical, not a conservative. She wanted change and was prepared to take unpopular decisions to achieve it.</p><p>The problem with David Cameron is the reverse. His problem is he is a conservative, not a radical. He doesn't share a restlessness for change. He may be likable and sometimes hard to disagree with, but he is empty. He is a politician of the status quo — even a status quo he consistently voted against — not change.</p><p>Every member of the Labour party carries with them a simple guiding mission on the membership card: to put power, wealth and opportunity in the hands of the many, not the few. When debating public service reform, tax policies or constitutional changes, we apply those values to the latest challenges.</p><p>What is on Cameron's party card? What is his vision for Britain? He doesn't have one. His project is \"decontaminating the Tory party\", not changing the country. He is stuck, reconciling himself to New Labour Mark I at just the time when the times demand a radical new phase.</p><p>The economic challenge is new. People want protection from a downturn made in Wall Street. The country needs to prepare for an upturn when new service industries — insurance, education, care, creative industries — are growing at home but also among the new Chinese and Indian middle classes. </p><p>The public service challenge is new, too. The task of government after 1997 was a rescue mission. Now we need the imagination to distribute more power and control to citizens over the education, healthcare and social services they receive. So is the challenge to society — to build a genuine sense of belonging and responsibility on the back of greater protection from outside risks and greater control of local issues.</p><p>I really believe that it is only our means, the political creed of the Labour party combining government action and personal freedom, that can achieve the ends the Tories now claim to share.</p><p>The modernisation of the Labour party means pursuing traditional goals in a modern way. The Tories claim the reverse. They say they have adopted \"progressive ends\" — social justice, better public services and fighting climate change — but they insist on traditional Tory means of charity, deregulation and lower spending to deliver them. It doesn't add up.</p><p>If people and business are to take responsibility, you need government to act as a catalyst. High polluting products will not disappear unless government regulates. New nuclear power stations need planning policy to facilitate them. And if we act through the EU, we green the largest single market in the world. In opposition, you can sound green while embracing Euroscepticism. </p><p>But in government, unless you choose sides, you get found out. <br/>New Labour won three elections by offering real change, not just in policy but in the way we do politics. We must do so again. So let's stop feeling sorry for ourselves, enjoy a break, and then find the confidence to make our case afresh.</p><p><strong>&#183;</strong> David Miliband is the foreign secretary<br/><a href=\"mailto:milibandd@parliament.uk\">milibandd@parliament.uk</a></p>";
		String preferredMasterBodyText = "<p>In the aftermath of Labour's third successive defeat at the 1959 election, a famous pamphlet asked the question: \"Must Labour lose?\" Today, the temptation is similar fatalism. <blockquote><strong>Because he who shall not be named is our leader.</strong></blockquote> We must not yield to it. <blockquote><strong>We could get rid of him, after all.</strong></blockquote> We need to remember that there is little real sense among the public - or even among Tory MPs - of what the Conservatives stand for, or what they would do in power. </p>\n" + 
				"<p></p><p>The odds are against us, no question. But I still believe we can win the next election. <blockquote><strong>If we change leader.</blockquote></strong> I agree with Jack Straw that we don't need a summer of introspection <blockquote><strong>especially not from a gloomy man on a beach in Southwold.</blockquote></strong> The starting point is not debating personalities <blockquote><strong>- oh yes it is -</blockquote></strong>  but winning the argument about our record, our vision for the future and how we achieve it. <blockquote><strong>Though really it's obvious. We need a new leader.</blockquote></strong> </p>\n" + 
				"<p>When people hear exaggerated claims, either about failure or success, they switch off. <blockquote><strong>Which is why I always sleep during he who shall not be named's dreary speeches.</blockquote></strong> That is why politicians across all parties fail to connect. To get our message across, we must be more humble about our shortcomings <blockquote><strong>- he who shall not be named doesn't do humble - </blockquote></strong> but more compelling about our achievements. <blockquote><strong>Or compelling, come to that.</blockquote></strong> </p>\n" + 
				"<p>With hindsight, we should have got on with reforming the NHS sooner. <blockquote><strong>Unfortunately, he who shall not be named blocked reform.</blockquote></strong> We needed better planning for how to win the peace in Iraq, not just win the war. <blockquote><strong>Of course, I wasn't in the cabinet at the time. Unlike my leadership rival, Jack Straw, who was foreign secretary and is to blame for it all.</blockquote></strong> We should have devolved more power away from Whitehall and Westminster. <blockquote><strong>But he who shall not be named wouldn't let us.</blockquote></strong> We needed a clearer drive towards becoming a low-carbon, energy-efficient economy, not just to tackle climate change but to cut energy bills.</p>\n" + 
				"<p>But 10 years of rising prosperity, a health service brought back from the brink, and social norms around women's and minority rights transformed, have not come about by accident. <blockquote><strong>No, Tony Blair made it happen.</blockquote></strong> After all, the Tories opposed almost all the measures that have made a difference - from the windfall tax on privatised utilities to family-friendly working.</p>\n" + 
				"<p>Now what are they offering? The Tories say society is broken. By what measure? Rising crime? No, crime has fallen more in the past 10 years than at any time in the past century. Knife crime and gun crime are serious problems. But since targeting the spike in gun crime, it has been cut by 13% in a year, and we have to do the same with knife crime.</p>\n" + 
				"<p>What about the social breakdown that causes crime? More single parents dependent on the state? No, employment has risen sharply for lone parents because the state has funded childcare and made work pay. Falling school standards? No, they are rising. More asylum seekers? No, we said we would reform the system and slash the numbers, and we did. <blockquote><strong>All this happened before he who shall not be named became PM.</blockquote></strong>  </p>\n" + 
				"<p>The Tories overclaim for what they are against because they don't know what they are for. I disagreed with Margaret Thatcher, but at least it was clear what she stood for. <blockquote><strong>I haven't got a clue what he who shall not be named stands for.</blockquote></strong> She sat uncomfortably within the Tory party because she was a radical, not a conservative. She wanted change and was prepared to take unpopular decisions to achieve it. <blockquote><strong>He who shall not be named doesn't take decisions.</blockquote></strong></p>\n" + 
				"<p>The problem with David Cameron is the reverse. His problem is he is a conservative, not a radical. He doesn't share a restlessness for change. He may be likable and sometimes hard to disagree with, but he is empty. He is a politician of the status quo - even a status quo he consistently voted against - not change. <blockquote><strong>This token attack on the Tories has gone on long enough. So back to he who shall not be named's weaknesses.</blockquote></strong></p>\n" + 
				"<p>Every member of the Labour party carries with them a simple guiding mission on the membership card: to put power, wealth and opportunity in the hands of the many, not the few. When debating public service reform, tax policies <blockquote><strong>such as the 10p tax</blockquote></strong> or constitutional changes, we <blockquote><strong>should</blockquote></strong> apply those values to the latest challenges. <blockquote><strong>But have failed to do so since he who shall not be named became our leader.</blockquote></strong></p>\n" + 
				"<p>What is on Cameron's party card? What is his vision for Britain? He doesn't have one. His project is \"decontaminating the Tory party\", not changing the country. He is stuck, reconciling himself to New Labour Mark I at just the time when the times demand a radical new phase. <blockquote><strong>Preferably one involving me as leader.</blockquote></strong></p>\n" + 
				"<p>The economic challenge is new. People want protection from a downturn made in Wall Street <blockquote><strong>and assisted by the man in Downing Street.</blockquote></strong>  The country needs to prepare for an upturn when new service industries - insurance, education, care, creative industries - are growing at home but also among the new Chinese and Indian middle classes. </p>\n" + 
				"<p>The public service challenge is new, too. The task of government after 1997 was a rescue mission. Now we need the imagination to distribute more power and control to citizens over the education, healthcare and social services they receive. So is the challenge to society - to build a genuine sense of belonging and responsibility on the back of greater protection from outside risks and greater control of local issues. <blockquote><strong>No one will know what this paragraph means. But it sounds good.</blockquote></strong></p>\n" + 
				"<p>I really believe that it is only our means, the political creed of the Labour party combining government action and personal freedom, that can achieve the ends the Tories now claim to share.</p>\n" + 
				"<p>The modernisation of the Labour party means pursuing traditional goals in a modern way <blockquote><strong>with a modern leader.</blockquote></strong> The Tories claim the reverse. They say they have adopted \"progressive ends\" - social justice, better public services and fighting climate change - but they insist on traditional Tory means of charity, deregulation and lower spending to deliver them. It doesn't add up.</p>\n" + 
				"<p>If people and business are to take responsibility, you need government to act as a catalyst. High polluting products will not disappear unless government regulates. New nuclear power stations need planning policy to facilitate them. And if we act through the EU, we green the largest single market in the world. In opposition, you can sound green while embracing Euroscepticism.</p>\n" + 
				"<p>But in government, unless you choose sides, you get found out. <blockquote><strong>We know this because he who shall not be named has been found out.</blockquote></strong> New Labour won three elections by offering real change, not just in policy but in the way we do politics. We must do so again. So let's stop feeling sorry for ourselves, enjoy a break, and then find the confidence to make our case afresh. <blockquote><strong>With a new leader. That's me.</blockquote></strong></p>";
		
		
		NormalisedArticle canonicalArticle = new NormalisedArticleBuilder().id("goodo").originalBody(preferredMasterBodyText).contributorIds("profile/fred").toArticle();
		NormalisedArticle spomArticle = new NormalisedArticleBuilder().id("legitimate commentary on goodo").originalBody(spomArticleBodyString).contributorIds("profile/joe").toArticle();
		DetectedSpom detectedSpom = getSpomFor(canonicalArticle,spomArticle);
		assertThat(detectedSpom, nullValue());
		
	}
	
	@Test
	public void shouldDetectDalglishArticlesAsSpom() throws Exception {
		String preferredMasterBodyText = "<p><strong>To celebrate the launch of <a href=\"http://www.guardianbooks.co.uk/webapp/wcs/stores/servlet/ProductDisplay?storeId=10401&amp;mpe_id=34461&amp;intv_id=100001&amp;partNumber=5QII&amp;evtype=CpgnClick&amp;langId=100&amp;catalogId=25501&amp;ddkey=http:ClickInfo\">The Guardian book of football</a>, a collection of the best football writing in this paper from the last 50 years, we have asked five great names from the world of football and football journalism to nominate the best player from the UK in that time. Every day this week one of them will explain their choice, today Kevin McCarra sings Kenny Dalglish's praises. <em>You can now <a href=\"http://www.guardian.co.uk/football/poll/2008/jul/29/greatest.footballer\">vote for your favourite UK footballer of the last 50 years</a></em></strong></p><p>No other player in modern British football history has had the combination of talent, dedication, durability and football intelligence possessed by Kenny Dalglish. Sometimes I wonder if this marvellous performer can actually have been Scottish at all, so unrelated was he to the hell-raising, self-destructive virtuosos that were once a speciality north of the border.</p><p>Dalglish was never at risk of burn-out. The fire indeed was so hard to extinguish that he was 39 when, in 1990, he made his last appearance for Liverpool. Because of a dry manner, with its sardonic humour, his sheer passion for the game gets overlooked. That joy was unmistakable on the field, particularly after a left-footed curler clinched a 3-1 win for Scotland over Spain in 1984. An explosion of delight vaporised every line on his 33-year-old face and it was a schoolboy's features that filled the camera lenses.</p><p>When the need was great, Dalglish could be the individualist who came to the team's rescue. As someone who watched many of his games in the mid-1970s, it is my feeling that there were more examples of spectacular virtuosity from him in those days. He was probably reacting to the needs of the side because Celtic had by then slipped back a little from their European Cup-winning peak.</p><p>Dalglish knew what was required by each of his teams and tailored his style accordingly. In retrospect it seems silly that anyone wondered if he could adapt when he moved to Liverpool as Kevin Keegan's replacement in the summer of 1977. At the close of the ensuing campaign, he composed himself to gather a Graeme Souness pass at Wembley and dink the ball over the onrushing goalkeeper as his new club beat Bruges 1-0 to retain the European Cup.</p><p>He had the sort of career that bludgeons sceptics with a statistical barrage. There were a total of 336 goals for Celtic and Liverpool, with another 30 from 102 caps placing him alongside Denis Law as Scotland's highest-ever scorer. The most impressive aspect, though, is that despite such figures he was not a pure predator. Dalglish, instead, was really a deep-lying striker.</p><p>Lacking raw speed, his approach was founded on technique, imagination and the sturdiness to hold off defenders. He was complemented exquisitely by Ian Rush, the striker he released for so many Liverpool goals. The lasting value of Dalglish is quantified in the honours he accumulated. For reasons of space, it might be as well to confine ourselves to mentioning the six League titles and three European Cups at Anfield alone.</p><p>Familiar though defenders were with an opponent who was around for so long, few could stop the adroit Dalglish from exploiting their weaknesses. Nowadays people seem to have forgotten that the astuteness carried over into a managerial record that was formidable at both Anfield and Ewood Park. There were sneers that he had bought Blackburn the 1995 title with Jack Walker's money, but many clubs have spent heavily and failed since then. In addition, it should be recalled that Blackburn made a total profit of over £16m on the eventual sales of Alan Shearer and Chris Sutton alone.</p><p>Over a wonderful career on the field and some fruitful years in charge of teams, Dalglish showed strength of character. He not only succeeded Joe Fagan after the Heysel Stadium disaster but did so as player-manager of Liverpool, a dual role that now looks inconceivable. Dalglish remained in charge, too, during the harrowing times after the carnage at Hillsborough in 1989.</p><p>In all circumstances of sport, from the euphoric to the tragic, he was exceptional.</p><p><strong>Kenny Dalglish on YouTube</strong></p><p>A collection of his <a href=\"http://www.youtube.com/watch?v=4K9schV5wug\">best Celtic goals</a>.</p><p>A collection of his <a href=\"http://www.youtube.com/watch?v=maeBCaY41jM\">best Liverpool goals</a>.</p><p>Scoring for <a href=\"http://www.youtube.com/watch?v=blsCccZxU_M\">Scotland against England</a> at Hampden. What more could you want?</p><p><em>Tomorrow: John Barnes on Ian Rush</em></p>";
		String spomArticleBodyString = "<p>No other player in modern British football history has had the combination of talent, dedication, durability and football intelligence possessed by Kenny Dalglish. Sometimes I wonder if this marvellous performer can actually have been Scottish at all, so unrelated was he to the hell-raising, self-destructive virtuosos that were once a speciality north of the border.</p><p>Dalglish was never at risk of burn-out. The fire indeed was so hard to extinguish that he was 39 when, in 1990, he made his last appearance for Liverpool. Because of a dry manner, with its sardonic humour, his sheer passion for the game gets overlooked. That joy was unmistakable on the field, particularly after a left-footed curler clinched a 3-1 win for Scotland over Spain in 1984. An explosion of delight vaporised every line on his 33-year-old face and it was a schoolboy's features that filled the camera lenses.</p><p>When the need was great, Dalglish could be the individualist who came to the team's rescue. As someone who watched many of his games in the mid-1970s, it is my feeling that there were more examples of spectacular virtuosity from him in those days. He was probably reacting to the needs of the side because Celtic had by then slipped back a little from their European Cup-winning peak.</p><p>Dalglish knew what was required by each of his teams and tailored his style accordingly. In retrospect it seems silly that anyone wondered if he could adapt when he moved to Liverpool as Kevin Keegan's replacement in the summer of 1977. At the close of the ensuing campaign, he composed himself to gather a Graeme Souness pass at Wembley and dink the ball over the onrushing goalkeeper as his new club beat Bruges 1-0 to retain the European Cup. </p><p>He had the sort of career that bludgeons sceptics with a statistical barrage. There were a total of 336 goals for Celtic and Liverpool, with another 30 from 102 caps placing him alongside Denis Law as Scotland's highest-ever scorer. The most impressive aspect, though, is that despite such figures he was not a pure predator. Dalglish, instead, was really a deep-lying striker.</p><p>Lacking raw speed, his approach was founded on technique, imagination and the sturdiness to hold off defenders. He was complemented exquisitely by Ian Rush, the striker he released for so many Liverpool goals. The lasting value of Dalglish is quantified in the honours he accumulated. For reasons of space, it might be as well to confine ourselves to mentioning the six League titles and three European Cups at Anfield alone.</p><p>Familiar though defenders were with an opponent who was around for so long, few could stop the adroit Dalglish from exploiting their weaknesses. Nowadays people seem to have forgotten that the astuteness carried over into a managerial record that was formidable at both Anfield and Ewood Park. There were sneers that he had bought Blackburn the 1995 title with Jack Walker's money, but many clubs have spent heavily and failed since then. In addition, it should be recalled that Blackburn made a total profit of over &pound;16m on the eventual sales of Alan Shearer and Chris Sutton alone.</p><p>Over a wonderful career on the field and some fruitful years in charge of teams, Dalglish showed strength of character. He not only succeeded Joe Fagan after the Heysel Stadium disaster but did so as player-manager of Liverpool, a dual role that now looks inconceivable. Dalglish remained in charge, too, during the harrowing times after the carnage at Hillsborough in 1989.</p><p>In all circumstances of sport, from the euphoric to the tragic, he was exceptional. </p><p><a href=\"http://www.guardianbooks.co.uk/webapp/wcs/stores/servlet/ProductDisplay?storeId=10401&amp;mpe_id=34461&amp;intv_id=100001&amp;partNumber=5QII&amp;evtype=CpgnClick&amp;langId=100&amp;catalogId=25501&amp;ddkey=http:ClickInfo\">Buy the Guardian book of Football at the Guardian bookshop</a></p>";
		
		DetectedSpom detectedSpom = getSpomFor(preferredMasterBodyText,
				spomArticleBodyString);
		assertThat(detectedSpom, not(nullValue()));
	}

	@Test
	public void shouldIdentifyBestScoringMatch() throws Exception {
		DetectedSpom detectedSpom = spomIdentifier.identifySpomsFor(preferredMaster, 
				newHashSet(someMonkey.getId() ,anArticleWhichLooksVeryLikeTheMaster.getId(), someOtherMonkey.getId()));
		
		assertThat(detectedSpom.getSpom(), equalTo(anArticleWhichLooksVeryLikeTheMaster));
	}
	
	@Test
	public void shouldNotIdentifySpomWorseThanThreshold() throws Exception {
		DetectedSpom detectedSpom = spomIdentifier.identifySpomsFor(preferredMaster, newHashSet(someMonkey.getId(), someOtherMonkey.getId()));
		
		assertThat(detectedSpom, nullValue());
	}
	

	
	private DetectedSpom getSpomFor(String preferredMasterBodyText,	String spomArticleBodyString) {
		NormalisedArticle canonicalArticle = new NormalisedArticleBuilder().id("goodie").originalBody(preferredMasterBodyText).toArticle();
		NormalisedArticle spomArticle = new NormalisedArticleBuilder().id("baddie").originalBody(preferredMasterBodyText).toArticle();

		return getSpomFor(canonicalArticle, spomArticle);
	}

	private DetectedSpom getSpomFor(NormalisedArticle preferredMaster,	NormalisedArticle somePossibleSpom) {
		SpomMatchScorer realSpomScorer = new SpomMatchScorer(new LevenshteinWithDistanceThreshold());
		SpomIdentifier spomIdentifier = new SpomIdentifier(realSpomScorer,new StubArticleProvider(somePossibleSpom),spomDetectionReporter);
		return spomIdentifier.identifySpomsFor(preferredMaster, newHashSet(somePossibleSpom.getId()));
	}
	
	
	
//	@Test
//	public void shouldBeTheCoolest() {
//		NormalisedArticleProvider articleProvider=new DumpedArticleProvider();
//		NormalisedArticle pm=articleProvider.normalisedArticleFor("books/2010/jan/17/mark-kermode-only-movie-extract");
//		SpomMatchScorer realSpomScorer = new SpomMatchScorer(new LevenshteinWithDistanceThreshold());
//		SpomIdentifier spomIdentifier = new SpomIdentifier(realSpomScorer,articleProvider,spomDetectionReporter);
//		DetectedSpom detectedSpom = spomIdentifier.identifySpomsFor(preferredMaster, newHashSet("lifeandstyle/gardening-blog/2010/jan/29/gardens"));
//		
//		assertThat(detectedSpom.getSpom().getId(), equalTo("lifeandstyle/gardening-blog/2010/jan/29/gardens"));
//	}
	

	
	public class StubArticleProvider implements NormalisedArticleProvider {

		private final Map<String,NormalisedArticle> articleMap;

		public StubArticleProvider(NormalisedArticle... articles) {
			articleMap = newHashMap();
			for (NormalisedArticle na:articles) {
				articleMap.put(na.getId(), na);
			}
		}

		@Override
		public NormalisedArticle normalisedArticleFor(String id) {
			return articleMap.get(id);
		}

	}
	
}

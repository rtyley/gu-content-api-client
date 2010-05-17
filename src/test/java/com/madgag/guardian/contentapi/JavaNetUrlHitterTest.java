package com.madgag.guardian.contentapi;

import org.junit.Test;

import java.net.URI;
import java.net.URL;


public class JavaNetUrlHitterTest {
    @Test
    public void shouldAcceptAProperUrlDammit() throws Exception {
        String foo = "http://content.guardianapis.com/search?ids=commentisfree/2008/jan/02/thebrotherhoodopensup,lifeandstyle/lostinshowbiz/2008/jan/02/parishiltondecides2008will,money/2008/jan/02/consumeraffairs.consumerandethicalliving,media/2008/jan/02/itv.television,world/2008/jan/02/china.features11,football/2008/jan/02/newsstory.sport4,football/2008/jan/02/europeanfootball.sport,media/mediamonkeyblog/2008/jan/02/whyarewehere,media/2008/jan/02/radio1,media/mediamonkeyblog/2008/jan/02/burchillsback,society/2008/jan/02/voluntarysector.socialcare,media/2008/jan/02/advertising,education/mortarboard/2008/jan/02/whyperfectstormshouldbeth,theguardian/2008/jan/02/features11.g2,media/mediamonkeyblog/2008/jan/02/beautifulkitchensrevamp,technology/blog/2008/jan/02/whatsyourmostannoyingprogr,business/2008/jan/02/allianceleicesterbusiness,music/musicblog/2008/jan/02/noughtiessofarthesoundtrac,business/marketforceslive/2008/jan/02/analystsatcreditsuissehave,commentisfree/2008/jan/02/thistimeitspersonal,uk/2008/jan/02/haroonsiddique,media/2008/jan/02/itv.tvnews,uk/2008/jan/02/world.transport2,commentisfree/2008/jan/02/leadersandreply.mainsection,society/joepublic/2008/jan/02/theviewfromthefrontline,media/2008/jan/02/tvratings.television1,stage/theatreblog/2008/jan/02/notetoselfbeopentoexperi,lifeandstyle/wordofmouth/2008/jan/02/atasteofthenoughties,commentisfree/2008/jan/02/leadersandreply.mainsection2,uk/2008/jan/02/1,commentisfree/2008/jan/02/leadersandreply.mainsection1,commentisfree/2008/jan/02/crisiswhatcrisis,business/marketforceslive/2008/jan/02/bitofaturnaroundon,uk/2008/jan/02/ukguns,world/2008/jan/02/kenya.davidberesford,world/2008/jan/02/kenya.matthewweaver,world/2008/jan/02/ianmackinnon,society/2008/jan/02/nhs.health1,world/2008/jan/02/israelandthepalestinians.haroonsiddique,education/2008/jan/02/students.uk,commentisfree/2008/jan/02/goldencompost,music/musicblog/2008/jan/02/tonynaylorthesolutionis,media/pda/2008/jan/02/livebloggingwill2008bethe,world/deadlineusa/2008/jan/02/huckabeesupporterhellpisso,uk/2008/jan/02/ukcrime,music/2008/jan/02/popandrock.amywinehouse,football/2008/jan/02/sport.comment,world/2008/jan/02/kenya,environment/2008/jan/02/travelandtransport.energyefficiency,books/booksblog/2008/jan/02/thebbcshouldtakeitsballet&tag=type/article&api-key=techdev&show-tags=all&format=xml&order-by=oldest&show-fields=body,short-url";

        URI uri=new URI(foo);

        URL url=uri.toURL();


    }
}

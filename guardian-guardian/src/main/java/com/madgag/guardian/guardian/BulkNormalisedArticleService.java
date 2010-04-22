package com.madgag.guardian.guardian;

import java.util.List;

import com.google.common.base.Function;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public interface BulkNormalisedArticleService extends Function<List<String>, List<NormalisedArticle>> {

}
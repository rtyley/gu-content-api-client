package com.madgag.spom.detection;
import java.util.ArrayList;
import java.util.List;

public class SpomCandidateSet {

	private final List<NormalisedArticle> preferredMasterArticles;
	private final List<NormalisedArticle> listOfPossibleSpoms;

	public SpomCandidateSet(List<NormalisedArticle> preferredMasterArticles, List<NormalisedArticle> listOfPossibleSpoms) {
		this.preferredMasterArticles = preferredMasterArticles; 
		this.listOfPossibleSpoms = new ArrayList<NormalisedArticle>(listOfPossibleSpoms);
		this.listOfPossibleSpoms.removeAll(preferredMasterArticles);
	}

	public List<NormalisedArticle> getPreferredMasterArticles() {
		return preferredMasterArticles;
	}

	public List<NormalisedArticle> getListOfPossibleSpoms() {
		return listOfPossibleSpoms;
	}

}

package test.by.epam.auction.parser;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.auction.domain.AuctionType;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.AuctionTypeParser;

public class AuctionTypeParserTest {
	AuctionTypeParser auctionTypeParser;
	
	@BeforeSuite
	public void initAuctionTypeParser() {
		this.auctionTypeParser = new AuctionTypeParser();
	}
	
	@DataProvider
	public Object[][] correctAuctionType() {
		return new Object [][] {
			{"direct ", AuctionType.DIRECT},
			{" direct", AuctionType.DIRECT},
			{" direct ", AuctionType.DIRECT},
			{"reverse", AuctionType.REVERSE},
			{" reverse", AuctionType.REVERSE},
			{"reverse ", AuctionType.REVERSE}
		};
	}
	
	@Test(groups = {"checkins"}, dataProvider = "correctAuctionType")
	public void isAbleToParseCorrectAuctionType(String auctionTypeStr, AuctionType expectedValue) throws WrongInputException {
		Assert.assertEquals(auctionTypeParser.parse(auctionTypeStr), expectedValue);
	}
	
	@DataProvider
	public String[][] incorrectAuctionType() {
		return new String[][] {
			{""},
			{"    "},
			{"unmatching regEx"},
			{null}
		};
	}
	
	@Test(groups = {"functests"}, dataProvider = "incorrectAuctionType", expectedExceptions = {WrongInputException.class})
	public void isAbleToFilterOffIncorrectValues(String auctionTypeStr) throws WrongInputException {
		auctionTypeParser.parse(auctionTypeStr);
	}
}

package test.by.epam.auction.parser;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.PageNumberParser;

public class PageNumberParserTest {
	PageNumberParser pageNumberParser;
	
	@BeforeSuite
	public void initPageNumberParser() {
		this.pageNumberParser = new PageNumberParser();
	}
	
	@DataProvider
	public Object[][] correctPageNumber() {
		return new Object [][] {
			{"1223445566", 1223445566L},
			{" 1223445566", 1223445566L},
			{"1223445566 ", 1223445566L},
			{" 1223445566 ", 1223445566L},
			{"1", 1L},
			{"0", 0L},
		};
	}
	
	@Test(groups = {"checkins"}, dataProvider = "correctPageNumber")
	public void isAbleToParseCorrectAmount(String idStr, Long expectedValue) throws WrongInputException {
		Assert.assertEquals(pageNumberParser.parse(idStr), expectedValue);
	}
	
	@DataProvider
	public String[][] incorrectPageNumberParser() {
		return new String[][] {
			{"-1"},
			{""},
			{"    "},
			{"string value"},
			{null}
		};
	}
	
	@Test(groups = {"functests"}, dataProvider = "incorrectPageNumberParser", expectedExceptions = {WrongInputException.class})
	public void isAbleToFilterOffIncorrectValues(String idStr) throws WrongInputException {
		pageNumberParser.parse(idStr);
	}
}

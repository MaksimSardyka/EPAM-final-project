package test.by.epam.auction.parser;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.IdParser;

public class IdParserTest {
	IdParser idParser;
	
	@BeforeSuite
	public void initIdParser() {
		this.idParser = new IdParser();
	}
	
	@DataProvider
	public Object[][] correctId() {
		return new Object [][] {
			{"1223445566", 1223445566L},
			{" 1223445566", 1223445566L},
			{"1223445566 ", 1223445566L},
			{" 1223445566 ", 1223445566L},
			{"1", 1L},
		};
	}
	
	@Test(groups = {"checkins"}, dataProvider = "correctId")
	public void isAbleToParseCorrectAmount(String idStr, Long expectedValue) throws WrongInputException {
		Assert.assertEquals(idParser.parse(idStr), expectedValue);
	}
	
	@DataProvider
	public String[][] incorrectId() {
		return new String[][] {
			{"-1"},
			{"0"},
			{""},
			{"    "},
			{"string value"},
			{null}
		};
	}
	
	@Test(groups = {"functests"}, dataProvider = "incorrectId", expectedExceptions = {WrongInputException.class})
	public void isAbleToFilterOffIncorrectValues(String idStr) throws WrongInputException {
		idParser.parse(idStr);
	}
}

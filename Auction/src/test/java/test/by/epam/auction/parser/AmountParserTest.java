package test.by.epam.auction.parser;

import java.math.BigDecimal;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.AmountParser;

public class AmountParserTest {
	AmountParser amountParser;
	
	@BeforeSuite
	public void initAmountParser() {
		this.amountParser = new AmountParser();
	}
	
	@DataProvider
	public Object[][] correctAmount() {
		return new Object [][] {
			{"3.14 ", new BigDecimal("3.14")},
			{" 3.14", new BigDecimal("3.14")},
			{" 3.14 ", new BigDecimal("3.14")},
			{"0.00", new BigDecimal("0.00")},
			{"1.00", new BigDecimal("1.00")}
		};
	}
	
	@Test(groups = {"checkins"}, dataProvider = "correctAmount")
	public void isAbleToParseCorrectAmount(String amountStr, BigDecimal expectedValue) throws WrongInputException {
		Assert.assertEquals(amountParser.parse(amountStr), expectedValue);
	}
	
	@DataProvider
	public String[][] incorrectAmount() {
		return new String[][] {
			{"-1.00"},
			{""},
			{"    "},
			{"unmatching regEx"},
			{null}
		};
	}
	
	@Test(groups = {"functests"}, dataProvider = "incorrectAmount", expectedExceptions = {WrongInputException.class})
	public void isAbleToFilterOffIncorrectValues(String amountStr) throws WrongInputException {
		amountParser.parse(amountStr);
	}
}
